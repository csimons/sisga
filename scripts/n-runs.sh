#!/bin/bash

if [ ! $# -eq 2 ]
then
    echo "usage: <script> <times to run> <configuration name>"
    exit 1
fi

n=$1
config=$2

if [ ! -f dist/sisga.jar ]
then
    echo "You must build the project first."
else
    echo -n "Generating run data; this might take a while ... "

    i=0
    while [ $i -lt $n ]
    do
        java -jar dist/sisga.jar $config
        i=$[$i+1]
    done

    echo "done."
fi
