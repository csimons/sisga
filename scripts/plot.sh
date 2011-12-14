#!/bin/bash

for file in *.dat
do
    rawName=${file%\.*};

    gnuplot > $rawName.png << EOF
        reset
        set terminal png

        set xlabel "Generations"
        set ylabel "Avg. and Best Fitness"

        set title "Results for run $rawName"
        set nokey
        set grid

        plot \
        "$file" using 1:2 title "Average Fitness" with points lw 1, \
             "" using 1:3 title "Best Fitness"    with lines lw 3
EOF
done
