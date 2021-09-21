import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement

public class Handlers {
	// Gets a list of all function names that are in subscribes
	public static getFromInstalled(instStmt, globalFunc) {
		def handlers = []
		switch (instStmt) {
		case { it instanceof MethodNode }:
			println "found MethodNode --> analysing all methods inside"
			def stmts = instStmt.getCode().getStatements()
			for (def stmt in stmts) {
				handlers += getFromInstalled(stmt, globalFunc)
			}
			break
		case { it instanceof BlockStatement }:
			print "found BlockStatement"
			def stmts = instStmt.getStatements()
			println " - tot trovati(subscribe/schedule/..): " + instStmt.getStatements().size()
			for (def stmt in stmts) {
				handlers += getFromInstalled(stmt, globalFunc)
			}
			break
		case { it instanceof MethodCallExpression }:
			println "found MethodCallExpression"
			def functionName = instStmt.getMethod().getText()
			println "-----" + functionName
			
			switch (functionName) {
			case "subscribe":
				handlers << parseSubscribe(instStmt)
				break
			case "schedule":
				handlers << parseSchedule(instStmt)
				break
			
			default:
				def func = globalFunc[functionName]
				if (!func) {
					println "*****---**** NESSUNA FUNZIONE PREDEFINITA TROVATA :( "
					break
				}
				handlers += getFromInstalled(func, globalFunc)
			}
			break
		case { it instanceof ExpressionStatement }:
			println "found ExpressionStatement"
			def e = instStmt.getExpression()
			handlers += getFromInstalled(e, globalFunc)
			break
		default:
			// I add all this part - before was empty...
			println "inside Handlers/getFromInstalled default case ..."
			println instStmt
			break

		}
		//handlers.eachWithIndex{item, index -> println "$index: $item"}

		return handlers
	}

	// parseSubscribe gets a MethodCallExpression object and checks for the
	// subscribes
	private static parseSubscribe(methodCall) {
		def arguments = methodCall.getArguments()
		//println arguments
		def variable = arguments[0].getName()
		def capability
		def callback

		//println "ARGUMENT SIZE:!!!!" + arguments.size()
		if (arguments.size() == 4) {
			capability = arguments[1].getText()			
			callback = arguments[2].getName()
		} else if (arguments.size() == 3) {
			capability = arguments[1].getText()
			callback = arguments[2].getText()
		} else if (arguments.size() == 2) {
			capability = "mode"
			callback = arguments[1].getName()
		}

		return [variable, capability, callback]
	}

	// parseSchedule parses schedules
	private static parseSchedule(methodCall) {
		def arguments = methodCall.getArguments()

		def timeVariable = arguments[0].getText()
		def callback = arguments[1].getText()

		return [timeVariable, "time", callback]
	}
}
