#
# CLS, 2011
#
# WORK IN PROGRESS
#
# Canonical GA algoritm in R.
#
# Chromosomes are represented as a vector of numbers (0 or 1).
# The population is represented as a list of these vectors.
#

source("galib.r")

################################################################################
# LOWER-LEVEL ALGORITHM CONTROL
################################################################################
ff          <- function(chrom)     { return(oneMax(chrom)) }
getParents  <- function(pop)       { return(getParentsFitProportionate(pop)) }
recombinate <- function(a, b, pC)  { return(singlePointCrossover(a, b, pC)) }
mutate      <- function(chrom, pM) { return(randomBitFlip(chrom, pM)) }
################################################################################

##############################
# HIGH-LEVEL ALGORITHM LOGIC #
##############################

ga <- function(sizePopulation, chromosomeSize, generations, pM, pC)
{
    print("RUNNING")
    print("-------")
    print(paste("sizePopulation = ", sizePopulation))
    print(paste("chromosomeSize = ", chromosomeSize))
    print(paste("generations    = ", generations))
    print(paste("pMutation      = ", pM))
    print(paste("pCrossover     = ", pC))
    print("-------")

    # data collection
    runs <- c()
    runAvgFitnesses <- c()
    runBestFitnesses <- c()

    population <- initPopulation(sizePopulation, chromosomeSize)

    for (i in 1:generations)
    {
        offspring = c()
        for (j in 1:sizePopulation)
        {
            parents <- getParents(population)

            dad <- parents[[1]]
            mom <- parents[[2]]

            reco <- recombinate(dad, mom, pC)
            muta <- mutate(reco, pM)

            offspring <- c(offspring, list(muta))
        }

        # Replace parent population with offspring population.
        genes <- offspring

        # Update tracking vectors for plotting a graph.
        runs <- c(runs, i)
        runAvgFitnesses <- c(runBestFitnesses, avgFitness(population))
        runBestFitnesses <- c(runBestFitnesses, bestFitness(population))
    }

    plot(runs, runBestFitnesses, type="p")
    par(new <- TRUE)
    plot(runs, runAvgFitnesses, type="l")
    #write(cbind(runs, runBestFitness, runAvgFitness), file = "ga.dat")
}

print("Available: ga(sizePopulation, chromosomeSize, generations, pM, pC)")
print("Example:   ga(10, 100, 200, 0.2, 0.7)")
