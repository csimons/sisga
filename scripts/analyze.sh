#!/bin/bash

if [ ! -f dist/sisga.jar ]
then
    echo "You must build the project first."
else
    java -cp dist/sisga.jar cls.util.AggregateAnalyzer $*
fi
