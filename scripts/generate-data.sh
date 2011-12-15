#!/bin/bash

echo -n "Generating run data; this might take a while ... "

for file in config/*.properties
do
    rawName=${file%\.*};

    i=0
    while [ $i -lt 10 ]
    do
        java -jar dist/sisga.jar $rawName
        i=$[$i+1]
    done
done

echo "done."
