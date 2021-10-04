#!/bin/bash

for file in 0_Smartapp_WORKS/*
do
    groovy parse.groovy $file ## command for parsing of a smartapp ##
	
    echo "** processed ${file}\n\n"

done