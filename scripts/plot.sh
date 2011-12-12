#!/bin/bash

for file in *.dat
do
    rawName=${file%\.*};

    gnuplot > $rawName.png << EOF
        reset
        set terminal png

        set xlabel "Generations"
        set ylabel "Avg. and Best Fitness"

        set title "GA Results"
        set grid

        plot \
        "$file" using 1:2 title "Average Fitness" with dots lw 2, \
             "" using 1:3 title "Best Fitness"    with lines lw 3, \
             "" using 1:4 title "Worst Fitness"   with dots lw 1
EOF
done
