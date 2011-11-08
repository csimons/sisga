#
# CLS, 2011
#
# WORK IN PROGRESS
#
# CHC GA algoritm in R.
#
# Chromosomes are represented as a vector of numbers (0 or 1).
# The population is represented as a list of these vectors.
#

source("galib.r")

################################################################################
# LOWER-LEVEL ALGORITHM CONTROL
################################################################################
ff               <- function(chrom)  { return(oneMax(chrom)) }
getParents       <- function(pop)    { return(getParentsFitProportionate(pop)) }
recombinate      <- function(a, b)   { return(hux(a, b)) }
naturalSelection <- function(pop, n) { return(elitistSelect(pop, n, ff)) }
################################################################################

##############################
# HIGH-LEVEL ALGORITHM LOGIC #
##############################

chc <- function(sizePopulation, chromosomeSize, generations, pCMutation)
{
    print("RUNNING")
    print("-------")
    print(paste("sizePopulation       = ", sizePopulation))
    print(paste("chromosomeSize       = ", chromosomeSize))
    print(paste("generations          = ", generations))
    print(paste("pCataclysmicMutation = ", pCMutation))
    print("-------")

    runs <- c()
    runAvgFitnesses <- c()
    runBestFitnesses <- c()

    population <- initPopulation(sizePopulation, chromosomeSize)

    beginPopulation <- population

    threshold = round(length(population) / 4)

    for (i in 1:generations)
    {
        matings = round(sizePopulation / 2)
        children = c()

        for (j in 1:matings)
        {
            parents <- getParents(genes)

            dad <- parents[[1]]
            mom <- parents[[2]]

            if((HammingDistance(dad, mom) / 2) > threshold)
            {
                for (j in 1:2)
                {
                    reco <- recombinate(dad, mom)
                    children <- c(children, list(reco))
                }
            }
        }

        if(length(children) == 0)
        {
            threshold <- threshold - 1
        }
        else
        {
            population <- c(population, children)
            population <- naturalSelection(population, sizePopulation)
        }

        if(threshold < 0)
        {
            population <- cataclysmicMutation(population, pCMutation, ff)
            threshold <- pCMutation * (1 - pCMutation) * chromosomeSize
        }

        # Update tracking vectors for plotting a graph.
        runs <- c(runs, i)
        runAvgFitnesses <- c(runBestFitnesses, avgFitness(population))
        runBestFitnesses <- c(runBestFitnesses, bestFitness(population))
    }

    #plot(runs, runBestFitnesses, type="p")
    #par(new <- TRUE)
    #plot(runs, runAvgFitnesses, type="l")
    write(cbind(runs, runBestFitnesses, runAvgFitnesses), file = "chc.dat")
}

print("Available: chc(sizePopulation, chromosomeSize, generations, pCMutation)")
print("Example:   chc(50, 100, 200, 0.35)")
