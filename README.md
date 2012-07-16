# SISGA

An extensible genetic algorithm engine.


## Getting Started

1. Install the JDK (versions 5 and up should work).
   - Ensure that the JAVA_HOME environment variable is set.
   - Ensure that JAVA_HOME/bin is present in your PATH.
2. Install Apache Ant (1.7+ REQUIRED, due to JUnit4 stuff).
   - Ensure the ANT_HOME environment variable is set.
   - Ensure that ANT_HOME/bin is present in your PATH.
3. Install Gnuplot
   - Ensure that the directory containing the "gnuplot" executable
     is present in your PATH environment variable.
4. From the project root directory, run "ant".  The build should
   succeed, and should end with a usage message.  If you instead
   receive an error message about the JUnit task not being found,
   you probably installed Ant through a distribution package and/or
   don't have the ANT_HOME environment variable set properly.


## Running (individual runs)

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
   The above will create PNG image files in the current directory for any
   GnuPlot data files it finds in the current directory ("testing-1234.png"
   would be generated from "testing-1234.dat").


## Aggregate Analysis

1. Read above section on single runs.
2. Scripts can be used to handle multiple runs of data.  These scripts must be
   run from the project root directory (sisga/), and can be executed only after
   the project has been built.
   - Several runs can be executed at once by using the scripts/n-runs.sh
     program.  Run the program without arguments to view the usage message.
   - Aggregate metrics can be viewed by using the aggregate analyzer program.
     The user interface for this is the scripts/analyze.sh program.  Run the
     program without arguments to view the usage message.
     So, a common manner of gathering aggregate data might be as follows:

    $ git clone http://github.com/csimons/sisga  # Download the project.
    $ cd sisga                                   # Go to project root directory.
    $ ant                                        # Build the project.
    $ cd config                                  # Navigate to configurations.
    $ cp ga.properties my-ga.properties          # Create a configuration
    $ vim my-ga.properties                       # Set parameters, function.
    $ cd ..                                      # Go to project root directory.
    $ bash scripts/n-runs.sh 50 config/my-ga     # Execute 50 runs.
    $ bash scripts/analyze.sh my-ga-*.dat        # View aggregate metrics.

The script "scripts/do-collect.sh" will run a collection for all existing
configurations in the "config" directory, for the number of executions
specified in the command-line argument.  This may be useful if one wishes to
compare performance among configurations.


## Extending

To extend the engine, to do things like implementing other GAs (GENITOR, etc.),
fitness functions, decoders, and so forth, simply create a new class extending
the appropriate interface, which will be one of the following:

    cls.alg.ga.GA
    cls.alg.mutation.chromosome.ChromosomeMutation
    cls.alg.mutation.population.PopulationMutation
    cls.alg.recombination.Recombination
    cls.alg.selection.parent.ParentSelection
    cls.alg.selection.survivor.SurvivorSelection
    cls.decode.Decoder
    cls.fitness.Function

The class cls.alg.ga.AbstractGA has been provided to make the development of
new GA implementations more finger-friendly.  It implements the GA interface
and can be extended rather than implementing GA directly.  See how this is used
in the CHC and CanonicalGA classes for examples.

Once you have created your new component implementations (and they are in the
appropriate directory and implement the appropriate interface), simply rebuild
the project in order to be able to use the components in new configurations.


# Licensing

SISGA is distributed under the MIT/X Consortium license;
see file COPYING.txt for details.
