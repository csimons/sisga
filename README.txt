An extensible genetic algorithm engine.


BUILDING

1. Install the JDK (versions 5 and up should work).
   a. Ensure that the JAVA_HOME environment variable is set.
   b. Ensure that JAVA_HOME/bin is present in your PATH.

2. Install Apache Ant (1.7+ REQUIRED, due to JUnit4 stuff).
   a. Ensure the ANT_HOME environment variable is set.
   b. Ensure that ANT_HOME/bin is present in your PATH.

3. Install Gnuplot
   a. Ensure that the directory containing the "gnuplot" executable
      is present in your PATH environment variable.

4. From the project root directory, run "ant".  The build should
   succeed, and should end with a usage message.


RUNNING

1. Algorithm configuration and parameters are specified in configuration
   files located in the config/ directory.  An example has been provided,
   "ga.properties".  To run a configuration, execute the JAR, specifying
   the configuration name (the file name without the ".properties" suffix)
   as the argument.  Optionally, you may also specify the "-v" flag, which
   will print summary information during execution; if specified, this flag
   must come BEFORE the configuration name:

   $ java -jar dist/sisga.jar -v config/ga

   This will create a GnuPlot data file with a name "A-T.dat", where A
   is the name of the run configuration and T is a timestamp,
   in the format specified in the run's configuration file (here,
   "config/ga.properties").  For example, for the default "ga"
   configuration, the file might be named "ga-201112131733.dat".

2. To plot the results as a PNG image file, run the following:

   $ bash scripts/plot.sh

   Th above will create PNG image files in the current directory for any
   GnuPlot data files it finds in the current directory ("testing-1234.png"
   would be generated from "testing-1234.dat").

