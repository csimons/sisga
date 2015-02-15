#!/bin/bash

if [ ! -f target/sisga.jar ]
then
    echo "You must build the project first."
else
    java -cp target/sisga.jar com.oracli.sisga.util.AggregateAnalyzer $*
fi
