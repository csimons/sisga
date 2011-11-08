This project has been built in the UNIX environment and has not been tested
under Windows.  The plotting script definitely will not work natively in
Windows (a bash script), although it might run with 'cygwin'.


BUILDING

To build:

1. Install the JDK (versions 5 and up should work).
   a. Ensure that the JAVA_HOME environment variable is set.
   b. Ensure that JAVA_HOME/bin is present in your PATH.

2. Install Apache Ant (1.7+ REQUIRED, due to JUnit4 stuff).
   a. Ensure the ANT_HOME environment variable is set.
   b. Ensure that ANT_HOME/bin is present in your PATH.

3. Install Gnuplot
   a. Ensure that the directory containing the "gnuplot" executable
      is present in your PATH environment variable.

4. From the java/ directory, run "ant".  The build should succeed, and
   should end with a usage message.

5. To see what arguments need to be supplied to the program, view a
   usage message by running the generated JAR file without any arguments:

   $ java -jar dist/JGA.jar

   Then run it, as follows, substituting other parameter values as desired:

   $ java -jar dist/JGA.jar GA 50 100 0.7 0.1 1000 100

   This will create a GnuPlot data file with the name "[algorithm].dat",
   where [algorithm] is the high-level GA algorithm specified on the
   command-line (above, "GA").

6. To plot the results as a PNG image file, run the following, passing in
   the same algorithm you passed to the JGA JAR (for example, "GA" or "CHC"):

   $ bash scripts/plot.sh GA

   Th above will create the file GA.png, containing the graph image.

