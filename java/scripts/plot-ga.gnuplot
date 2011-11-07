#!/usr/bin/gnuplot

reset
set terminal png

set xlabel "Generations"
set ylabel "Avg. and Best Fitness"

set title "GA Results"
set grid

#set style data linespoints

plot \
"GA.dat" using 1:2 title "Average Fitness" with lines, \
      "" using 1:3 title "Best Fitness" with dots

