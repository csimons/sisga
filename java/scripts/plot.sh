#!/bin/bash

if [ $# -ne 1 ]
then
    echo "usage: plot.sh <data file name, sans the \".dat\">"
    exit
fi

gnuplot > $1.png << EOF

reset
set terminal png

set xlabel "Generations"
set ylabel "Avg. and Best Fitness"

set title "GA Results"
set grid

plot \
"$1.dat" using 1:2 title "Average Fitness" with linespoints, \
      "" using 1:3 title "Best Fitness"    with linespoints, \
      "" using 1:4 title "Worst Fitness"   with linespoints

EOF
