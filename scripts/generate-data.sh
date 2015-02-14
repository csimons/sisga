#!/bin/bash

if [ ! -f target/sisga.jar ]
then
    echo "You must build the project first."
else
    echo -n "Generating run data; this might take a while ... "

    for file in config/*.properties
    do
        rawName=${file%\.*};

        i=0
        while [ $i -lt 10 ]
        do
            java -jar target/sisga.jar $rawName
            i=$[$i+1]
        done
    done

    echo "done."
fi
