# SISGA

[![Build Status](https://travis-ci.org/csimons/sisga.svg?branch=master)](https://travis-ci.org/csimons/sisga)

An extensible genetic algorithm engine and benchmarking suite.

## BUILDING

Assuming the JDK (version 1.8+ should work), Apache Maven, and Gnuplot have
been properly installed, the project can be built by issuing the following
command from the project's root directory: mvn install.

## RUNNING (SINGLE RUNS)

Algorithm configuration and parameters are specified in configuration files
located in the config/ directory.  An example has been provided in the
"ga.properties" file.  To run a configuration, execute the JAR, specifying the
configuration name (the file name without the ".properties" suffix) as the
argument.  Optionally, you may also specify the "-v" flag, which will print
summary information during execution; if specified, this flag must come BEFORE
the configuration name:

    $ java -jar target/sisga.jar -v config/ga

This will create a GnuPlot data file with a name "A-T.dat", where A is the name
of the run configuration and T is a timestamp, in the format specified in the
run's configuration file (here, "config/ga.properties").  For example, for the
default "ga" configuration, the file might be named "ga-201112131733.dat".

To plot the results as a PNG image file, run the following:

    $ bash scripts/plot.sh

The above will create PNG image files in the current directory for any GnuPlot
data files it finds in the current directory ("testing-1234.png" would be
generated from "testing-1234.dat").

## AGGREGATE ANALYSIS

Scripts can be used to handle multiple runs of data.  These scripts must be run
from the project root directory, and can be executed only after the project has
been built.

1. Several runs can be executed at once by using the scripts/n-runs.sh program.
   Run the program without arguments to view the usage message.
2. Aggregate metrics can be viewed by using the aggregate analyzer program.
   The user interface for this is the scripts/analyze.sh program.  Run the
   program without arguments to view the usage message.

So, a common manner of gathering aggregate data might be as follows:

    $ git clone http://github.com/csimons/sisga  # download the project
    $ cd sisga                                   # go to project root directory
    $ ant                                        # build the project
    $ cd config                                  # navigate to configurations
    $ cp ga.properties my-ga.properties          # create a configuration
    $ vim my-ga.properties                       # set parameters, function
    $ cd ..                                      # go to project root directory
    $ bash scripts/n-runs.sh 50 config/my-ga     # execute 50 runs
    $ bash scripts/analyze.sh my-ga-*.dat        # view aggregate metrics

The script "scripts/do-collect.sh" will run a collection for all existing
configurations in the "config" directory, for the number of executions
specified in the command-line argument.  This may be useful if one wishes to
compare performance among configurations.

## EXTENDING

To extend the engine, to do things like implementing other GAs (GENITOR, etc.),
fitness functions, decoders, and so forth, simply create a new class extending
the appropriate interface, which will be one of the following:

    com.oracli.sisga.alg.ga.GA
    com.oracli.sisga.alg.mutation.chromosome.ChromosomeMutation
    com.oracli.sisga.alg.mutation.population.PopulationMutation
    com.oracli.sisga.alg.recombination.Recombination
    com.oracli.sisga.alg.selection.parent.ParentSelection
    com.oracli.sisga.alg.selection.survivor.SurvivorSelection
    com.oracli.sisga.decode.Decoder
    com.oracli.sisga.fitness.Function

The class com.oracli.sisga.alg.ga.AbstractGA has been provided to make the
development of new GA implementations more finger-friendly.  It implements the
GA interface and can be extended rather than implementing GA directly.  See
how this is used in the CHC and CanonicalGA classes for examples.

Once you have created your new component implementations (and they are in the
appropriate directory and implement the appropriate interface), simply rebuild
the project in order to be able to use the components in new configurations.
