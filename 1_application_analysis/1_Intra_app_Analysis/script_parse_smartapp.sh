#!/bin/bash


read -p "select what parsing execute[0=app-description / 1=trigger-action]:" parse_execution  
echo "insert: $parse_execution"

if [ $parse_execution -eq 0 ]; then
    for file in 0_Smartapp_DES/*
    do
        groovy parse.groovy $file ## command for parsing of a smartapp ##
        echo " ** processed ${file} "
    done

elif [$parse_execution -eq 1 ]; then
    for file in 0_Smartapp_WORKS/*
    do
        groovy parse.groovy $file ## command for parsing of a smartapp ##
        echo " ** processed ${file} "
    done

else
    echo "input wrong"

fi