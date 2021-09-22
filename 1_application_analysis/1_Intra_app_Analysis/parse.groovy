// Imports
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.Phases

import Correlate
import GraphRelation
import GraphWithRelations
import Graph

//import groovy.ui.Console

def main() {
	if (args.size() < 1) {
		println "usage: groovy parse.groovy [name of file]"
		return
	}
	println "****************************\t\t\tStart parsing tool"
	// Get correlations
	def fileData = new File(args[0])	// use: source.groovy
	def descriptions = new File("../2_physical_channel_identification/app-description.JSON")	// save descriptions
	//def trigger_action = new File("../2_physical_channel_identification/trigger-action.JSON")	// save trigger-action

	def correlationsData = getRelations(fileData, descriptions) 
	
	/*
	// Unpack data
	def description = correlationsData[0]
	def relations = correlationsData[1]

	def gr = GraphRelation.create(relations)
	println GraphWithRelations.create(description, gr[0], gr[1])

	//println Graph.create(description, relations)
*/
	println "--------- END ------------------"
}

// getRelations takes a file and outputs the relations between them
def getRelations(inputFile, myFile) {

	CompilationUnit cu = new CompilationUnit()
	cu.addSource(inputFile)
	cu.compile(Phases.SEMANTIC_ANALYSIS)
	println "****************************\t\t\tcreation of the app AST"

	def AST = cu.getAST().getClasses()[0]

	// from here start to save info into txt/Json file

	myFile.append("\""+AST+"\":") 	// saving descriptions: app name
	//trigger_action.append("\""+AST+"\":\n{")	// saving trigger-action
	println "AST dell'app - " + inputFile + ": " + cu.getAST()
	println "classe dell'AST:" + AST


	def methods = generateFunctionMap(AST.getMethods())
	
	def runMethod = methods["run"].getCode()
	println "****************************\t\t\tanalysing the run method:"
	def corr = Correlate.sync(runMethod, methods, myFile)
	//myFile.append("},\n")

	return corr
}

// findFunctions maps MethodNodes to names in a dictionary
def generateFunctionMap(methodArray) {
	def functionMap = [:] // create map
	for (method in methodArray) {
		functionMap[method.getName()] = method
	}
	println "Methods found inside the app:"
	def i = 0
	/*
	for (key in functionMap.keySet()) {
    	print i
		print " "
		println(key)
		i++
	}
*/
	return functionMap
}

//print "run the parse.groovy script\nplease insert the app that you want to analyse:"
//def analise_app = System.in.newReader().readLine()
//println "you will analyse the " + analise_app + " app 
//def file = "Dataset/Smart_apps/smart-security.groovy" 
/*    Console console = new Console();
    console.setVariable("args","Dataset/Smart_apps/smart-security.groovy");
    console.run();
  */  
main()