import org.codehaus.groovy.ast.MethodNode

import org.codehaus.groovy.ast.stmt.SwitchStatement
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.IfStatement

import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression


public class Outputs {
	// which input goes to which outputs
	//Outputs.translate(handler, hfunc, inputs, functions)
	public static translate(input, function, allInp, allFuncs, myFile) {
		def capVar = eleFromList(input[0], allInp)
		
		if (capVar != null){
			//println capVar.size()//each{it -> println "$it"}
			def inputCapability = capVar[1]
			println "___-----__" +inputCapability
			myFile.append("\t\""+inputCapability+"\":[")
			def outputs = findOutputs(function, allInp, allFuncs)
			def nonDupes = removeDuplicateElements(outputs)
			println "outputs found:"
			//myFile.append("\n-----for the handler: "+function.getName()+"\tfound inputs(trigger) and correlated output(action)\n")
			nonDupes.eachWithIndex{item, index -> 
				println "$index: $item"
				def tripla = item
				if(tripla[1] == "send")
					myFile.append("[\""+tripla[0]+"\",\""+tripla[2]+"\"], ")
				else
					myFile.append("[\""+tripla[0]+"\",\""+tripla[0].replace("capability.", "") +"."+tripla[2]+"\"], ")
			}
			myFile.append("],\n")
			println "----------" + function.getName() + " è ok"
			println ""
			return [[inputCapability, input[1]], nonDupes]
		}else{
			println "!! ------ input non gestito dall'app -------- !!"
			return[["null", "null"], []]
		}
	}

	// findOutputs is called recursively on a statement or expression until all
	// `MethodCallExpression`s are found that have a variable from the list
	private static findOutputs(stmt, allInp, allFuncs) {
		def outputs = []

		switch (stmt) {
			// When a method is called, see if the method name is
			// an input
			// if not: call method and parse it to see if inputs
			// appear
			case { it instanceof MethodCallExpression }:


				//println stmt.getReceiver().getText()
				def rec = stmt.getReceiver().getText()
				print "found method\tClass ["+rec
				def met = stmt.getMethodAsString()
				println "] method\t__"+met+"__" 
				def ivar = eleFromList(rec, allInp)
				if(ivar == null)
					println "\t\t\tthis method doesn't use the analysed inputs||"


				// SmartThings built-ins
				if (met == "setLocationMode" ||
				(rec == "location" && met == "setMode")) {
					//println "found method setLocationMode"
					// prima configurato come: outputs << ["locationMode", "location", "setLocationMode"]
					outputs << ["locationMode", "location", "setLocationMode"]

				} else if (met == "runIn") {
					print "found method runIn --analysing it "
					def args = stmt.getArguments()[1] // the the second argument
					//println "na" + args
					//println args.getClass()
					def funcName = args.getText()
					println "---funzione trovata secondo argomento: " + funcName
					def func = allFuncs[funcName]
					println "---funzione trovata: " + func
					outputs += findOutputs(func, allInp, allFuncs)

				} else if (met == "sendNotificationToContacts") {
					//println "found method sendNotificationToContacts"
					outputs << ["sendAction", "send", "sendNotificationToContacts"]
				} else if (met == "sendPush") {
					//println "found method sendPush"
					outputs << ["sendAction", "send", "sendPush"]
				} else if (met == "sendSms") {
					//println "found method sendSms"
					outputs << ["sendAction", "send", "sendSms"]
				} else if (rec != "this" && ivar) {
					println "\t\t\t\tsave directly the capability - trigger(input) - action(output) "
					outputs << [ivar[1], ivar[0], met]
				} else if (rec == "this") {
					// When `this` is reciever, call function
					// and get outputs
					def f = allFuncs[met]
					//allFuncs.each{item -> println "tutte le funzioni passate: $item"}
					//println "numero: " + met
					print "è un metodo dell'app o no [null]:" 
					if(f != null)
						println "SI -> analisi di:"+f.getText()
					else
						println "null"

					outputs += findOutputs(f, allInp, allFuncs)
					//print " --- OUTPUTS: "
					//outputs.each{item -> print "$item - "}
					//println ""
				}
				break
			case { it instanceof DeclarationExpression }:
				//println "findOutputs-> DeclarationExpression"
				//TODO: Do I need to handle this?
				break

			case { it instanceof VariableExpression }:
				//println "findOutputs-> VariableExpression"
				// TODO: Maybe a variable is used, but no method is
				// called on it
				break

			case { it instanceof BinaryExpression }:
				//println "findOutputs-> BinaryExpression"
				def le = stmt.getLeftExpression()
				def re = stmt.getRightExpression()
				outputs += findOutputs(le, allInp, allFuncs)
				outputs += findOutputs(re, allInp, allFuncs)
				break

			case { it instanceof IfStatement }:
				//println "findOutputs-> IfStatement"
				def is = stmt.getIfBlock()
				def eb = stmt.getElseBlock()
				outputs += findOutputs(is, allInp, allFuncs)
				outputs += findOutputs(eb, allInp, allFuncs)
				break

			case { it instanceof ExpressionStatement }:
				//println "findOutputs -> ExpressionStatement"
				def e = stmt.getExpression()
				//println "espressione trovata " + e 
				outputs += findOutputs(e, allInp, allFuncs)
				break

			case { it instanceof BlockStatement }:
				//println "findOutputs-> BlockStatement"
				for (s in stmt.getStatements()) {
					outputs += findOutputs(s, allInp, allFuncs)
				}
				break

			case { it instanceof SwitchStatement }:
				//println "findOutputs-> SwitchStatement"
				for (c in stmt.getCaseStatements()) {
					outputs += findOutputs(c, allInp, allFuncs)
				}
				break

			case { it instanceof MethodNode }:
				//println "findOutputs-> MethodNode"
				def c = stmt.getCode()
				outputs += findOutputs(c, allInp, allFuncs)
				break

			default:
				//println "Do not understand how to parse: statement"
				break
		}

		return outputs
	}

	// TODO: Might make into map so that I don't have to go through each input
	private static def eleFromList(target, allInp) {
		//println allInp.find { it[0] == target }
		return allInp.find { it[0] == target }
	}

	// Remove
	private static def removeDuplicateElements(outputs) {
		def nonDuplicates = []
		for (output in outputs) {
			if (!(nonDuplicates.contains(output))) {
				nonDuplicates << output
			}
		}

		return nonDuplicates
	}
}
