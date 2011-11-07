BUILDING

To build:

1. Install the JDK (versions 5 and up should work).
   a. Ensure that the JAVA_HOME environment variable is set.
   b. Ensure that JAVA_HOME/bin is in your PATH.

2. Install Apache Ant.
   a. Ensure the ANT_HOME environment variable is set.
   b. Ensure that ANT_HOME/bin is in your PATH.

3. Run "ant".  The build should succeed, and should end with a usage message.

4. From the java/ directory, run something like:

   $ java -jar dist/JGA.jar GA 50 100 0.7 0.1 1000 100

   This will create a GnuPlot data file with the name "[algorithm].dat",
   where [algorithm] is the high-level GA algorithm specified on the
   command-line (above, "GA").

   To see what the arguments do, run the JAR without them:

   $ java -jar dist/JGA.jar GA 50 100 0.7 0.1 1000 100

5. To plot the results, run the most appropriate of the following
   (hopefully one generic script will take the place of the multiple
    scripts soon):

   $ gnuplot scripts/plot-ga.gnuplot  > my-graph.png
   $ gnuplot scripts/plot-chc.gnuplot > my-graph.png

   Note that GnuPlot must be installed, and its path in your environment's
   PATH variable.

