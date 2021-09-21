import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.NamedArgumentListExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression

import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.BlockStatement


public class Preferences {
	// parse takes in a preferences closure and returns a
	// list of inputs associated with the closure. These can be referenced
	// by the callback bodies
	
	public static parse(statement) {
		def inputs = []
		//println "INSIDE PREFERENCES/parse"
		switch (statement) {
		
		case { it instanceof DeclarationExpression }:
			println "DeclarationExpression found not used---- "
			inputs += []
			break
	/*	case { it instanceof MapExpression }:
			//println "inside PREFERENCES/parse -> MapExpression"
			// ********** inserita la gestione di MapExpression!! ---non verificatia!
			println "!!!!FOUND MapExpression!!!!!"
			println "--- NOT USED!!!!!!!"

			inputs << findInputs(statement)
		
			break*/
		case { it instanceof ConstantExpression }:
			println "ConstantExpression found: " + statement.getText()+"\t--->info_eliminata"
			inputs += []
			break
		case { it instanceof ArgumentListExpression }:
			println "analizzo ArgumentListExpression, del metodo trovato"
			//println statement.getExpressions()
			//inputs << findInputs(statement)
			for (s in statement.getExpressions()) {
				//println s
				inputs += parse(s)
			}
			break
		case { it instanceof ClosureExpression }:
			println "ClosureExpression"
			def stmts = statement.getCode().getStatements()
			for (s in stmts) {
				inputs += parse(s)
			}
			break
		case { it instanceof BlockStatement }:
			println "BlockStatement"
			println "TOT BLOCKSTATEMENT trovati(section): " + statement.getStatements().size()
			println ""
			for (s in statement.getStatements()) {
				inputs += parse(s)
			}
			break
		case {it instanceof ExpressionStatement }:
			println "\nExpressionStatement"
			//println statement.getExpression()
			inputs += parse(statement.getExpression())
			break
		case { it instanceof MethodCallExpression }:
			print "\t methodcallExpression"
			println " - analysing method: " + statement.getMethodAsString()
			switch (statement.getMethodAsString()) {	// i found page...
			
			case "paragraph":
				println ">>> trovato paragraph not used----"
				break
			
			case "href":
				println ">>> trovato href not used----"
				break
			
			case "options":
				println ">>> trovato href not used----"
				break
			
			case "trace":
				println ">>> trovato trace not used----"
				break

			case "section":
				println "---found: " + statement.getText()
				//println "analysing: " + statement.getArguments()
				def args = statement.getArguments()
				inputs += parse(args)
				
				break
			case "input":
				print "found inputs"
				def args = statement.getArguments()
				def exps = args.getExpressions()
				// Input expressions can be different depending
				// on how it's used. Input can be used like a
				// section in rare cases
				if (exps.size() == 1) {
					println " - tot: " + exps.size()
					inputs << findInputs(args)
				}else if (exps.size() == 2) {
					println " - tot: " + exps.size()
					def value = exps[0].getValue()
					//println "EXPS[0]:" + value
					def type = exps[1].getValue()
					//println "EXPS[1]" + type
					inputs << [value, type]
				}else if (exps.size() == 3) {
					println " - tot: : " + exps.size()
					//println "EXPS[0]: " + exps[0]
					def value = exps[1].getValue()
					//println "EXPS[1]:" + value
					def type = exps[2].getValue()
					//println "EXPS[2]" + type
					inputs << [value, type]
				}
				// A size of 4 means that there are
				// more inputs for that function
				if (exps.size() == 4) {
					println " - found more inputs"
					inputs += parse(exps[3])
				}
				break
			


			default:
				throw new Exception("MethodCallExpression Broken. No route for:" + statement)
			}
			break
		default:
			throw new Exception("findInputs broken: No route for: " + statement)
		}
		return inputs
	}

	// findInputs is a helper function for parse that
	// takes in a statement and returns an array of variable names and
	// capability types associated with the input statement
	private static findInputs(preferencesExpression) {
		// Get input from a function call to a list of expressions
		def expressions = preferencesExpression.getExpressions()

		// input may be called in one of two ways, either as a map, or
		// a list, so we parse both
		if (expressions[0] instanceof NamedArgumentListExpression) {
			println "found NamedArgumentListExpression ******"
			def input = []
			def mapexp = expressions[0].getMapEntryExpressions()

			for (expression in mapexp) {
				def key = expression.getKeyExpression()
				def value = expression.getValueExpression()
				
				if (key.getValue() == "name") {
					input[0] = value.getValue()
				} else if (key.getValue() == "type") {
					input[1] = value.getValue()
				}
			}

			return input

		} else if (expressions[0] instanceof MapExpression) {
			println "found MapExpression ******"
			def name = expressions[1].getValue()
			def type = expressions[2].getValue()

			return [name, type]
		} else {
			def name = expressions[0].getValue()
			def type = expressions[1].getValue()
			
			return [name, type]
		}
	}

	// parseInputExpression takes in an ExpressionStatement and returns the
	// arguments to the `input` function call
	private static parseInputExpression(exp) {
		def arguments = exp.getExpression().getArguments()
		def name = arguments[0].getValue()
		def capability = arguments[1].getValue()

		return [name, capability]
	}
}
