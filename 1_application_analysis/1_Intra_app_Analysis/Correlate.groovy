import org.codehaus.groovy.ast.expr.MethodCallExpression

import Preferences
import Outputs
import Handlers

public class Correlate {
	// sync takes the input objects in the preferences method of the run
	// method and syncs inputs from preferences with the handlers
	// Correlate.sync(runMethod, methods)
	public static sync(runMethod, functions, myFile) {
		// Preferences object for getting handlers and description
		def prefs = getObjectFromRun(runMethod, "preferences")
		if (!prefs) {
			throw new Exception("No Preferences Method Found")
		}

		// Get description from definition function
		def description = getDescription(runMethod)	
		println "description of the app: " + description + "\n"


		//myFile.append("\""+description+"\",\n")
		println "****************************\t\t\tanalysing the preferences block{}:"
		def inputs = getInputArray(prefs)			
		println "\n-------- TROVATI TUTTI INPUT e CAPABILITIES tot: " + inputs.size()
		//myFile.append("----- List of all inputs and capabilities found tot: " + inputs.size()+"\n")
		inputs.eachWithIndex{item , index -> println "$index: $item"}


		def translateFunc = { handler ->
			//handler.each{item -> println "handler trovato: $item"}
			def hfunc = functions[handler[2]]
			println "\n****************************\t\t\tanalysing the handler:" + hfunc.getName()			
			Outputs.translate(handler, hfunc, inputs, functions, myFile)
		}

		def relations = getHandlers(functions, myFile)
		.stream()
		.map(translateFunc)
		.collect()



		return [description, relations]
	}

	private static getDescription(runMethod) {
		def definition = getObjectFromRun(runMethod, "definition")
		def description = getDefKey(definition, "description")
		return description
	}

	private static getInputArray(prefs) {
		//println "tutto il codice da analizzare ------------" + prefs
		def prefsCode = prefs.getArguments()[0].getCode() // analizza il codice relativo solo alla parte preferences{}
			
		def inputs = Preferences.parse(prefsCode)			
		// Insert default inputs that can be used without definition
		inputs << ["app", "capability.app"]
		inputs << ["location", "capability.location"]
		return inputs
	}

	private static getHandlers(functions, myFile) {
		// Relate the inputs to the functions found in callbacks of
		//`installed` capabilities
		println "\n****************************\t\t\tanalysing the installed():"
		def instCode = functions["installed"]?.getCode()
		def inputHandlers = Handlers.getFromInstalled(instCode, functions)
		println "tot channel found: " + inputHandlers.size()
	 	inputHandlers.eachWithIndex{item, index -> 
			def tripla = item
			println "$index: "+tripla[1]+"\t\tmanaged by the handler: "+tripla[2]+"\tuse input: "+tripla[0]	
		}
		return inputHandlers
	}

	private static getObjectFromRun(runMethod, name) {
		def mt = runMethod.getStatements()
		.stream()
		.filter({ stmt -> def e = stmt.getExpression()
			return (e instanceof MethodCallExpression) && (e.getMethodAsString() == name)
		})
		.collect()

		return mt[0].getExpression()
	}

	private static getDefKey(definition, definitionKey) {
		def argList = definition.getArguments().getExpressions()[0]

		def matches = argList.getMapEntryExpressions()
		.stream()
		.filter({ e ->
			def key = e.getKeyExpression().getValue()
			return e.getKeyExpression().getValue() == definitionKey
		})
		.collect()
		
		return matches[0].getValueExpression().getValue()
	}
}
