#!/bin/bash

# Run all configurations specified number of times.

if [ ! $# -eq 1 ]
then
    echo "usage: <script> <run-count>"
    exit 1
fi

if [ ! -f target/sisga.jar ]
then
    echo "You must build the project first."
    exit 1
fi

n=$1

for configFile in config/*.properties
do
    rawName=${configFile%\.*};

    echo -n "Collecting for configuration $rawName ... "

    i=0
    while [ $i -lt $n ]
    do
        java -jar target/sisga.jar $rawName
        i=$[$i+1]
    done

    echo "done."
done
