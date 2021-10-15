## intra-app analysis 
the code used for parse all the Official Samsung SmartThings apps and exstrapolate the informations such as:
  - app descriptions
  - triggers and actions
  - capabilities

simply use the script: ./script_parse_smartapp.sh

datasets:

  - Smart_apps: is the list of app from the link: https://github.com/nsslabcuus/IoTMon
  - Smart_apps_NEW: is the list of app downloaded from the Official Samsung SmartThings web page:  https://github.com/SmartThingsCommunity/SmartThingsPublic/tree/master/smartapps/smartthings
  - 0_Smartapp_DES: list of all different apps used to extrapolate the descriptions (union of Smart_app and Smart_apps_NEW)
  - 0_Smartapp_WORKS: is the list of all Samsung smartapp that can be correctly analyzed and extrapolate the AST 

## ALERT
I always change the structure of the parse tool (comment or uncomment operations) in order to:
1. extrapolate app description
2. extrapolate trigger/action

because since that it's not always possible to extrapolate trigger/action from every app, due to the fact that over the years the apps change their own code structure 
