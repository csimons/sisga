#
# CLS, 2011
#
# WORK IN PROGRESS
#
# Canonical GA algoritm in R.
#
# Genes are represented as a vector of numbers (0 or 1).
# The gene set is represented as a list of these vectors.
#

source("galib.r")

################################################################################
# LOWER-LEVEL ALGORITHM CONTROL
################################################################################
ff          <- function(gene)     { return(oneMax(gene)) }
getParents  <- function(genes)    { return(getParentsFitProportionate(genes)) }
recombinate <- function(a, b, pC) { return(singlePointCrossover(a, b, pC)) }
mutate      <- function(gene, pM) { return(randomBitFlip(gene, pM)) }
################################################################################

##############################
# HIGH-LEVEL ALGORITHM LOGIC #
##############################

ga <- function(sizePopulation, chromosomes, generations, pM, pC)
{
    print("RUNNING")
    print("-------")
    print(paste("sizePopulation = ", sizePopulation))
    print(paste("chromosomes    = ", chromosomes))
    print(paste("generations    = ", generations))
    print(paste("pMutation      = ", pM))
    print(paste("pCrossover     = ", pC))
    print("-------")

    # data collection
    runs <- c()
    runAvgFitnesses <- c()
    runBestFitnesses <- c()

    genes <- initPopulation(sizePopulation, chromosomes)

    for (i in 1:generations)
    {
        newGenes = c()
        for (j in 1:sizePopulation)
        {
            parents <- getParents(genes)

            dad <- parents[[1]]
            mom <- parents[[2]]

            reco <- recombinate(dad, mom, pC)
            muta <- mutate(reco, pM)

            newGenes <- c(newGenes, list(muta))
        }

        # Replace parent population with offspring population.
        genes <- newGenes

        # Update tracking vectors for plotting a graph.
        runs <- c(runs, i)
        runAvgFitnesses <- c(runBestFitnesses, avgFitness(genes))
        runBestFitnesses <- c(runBestFitnesses, bestFitness(genes))
    }

    #plot(runs, runBestFitnesses, type="p")
    #par(new <- TRUE)
    #plot(runs, runAvgFitnesses, type="l")
    write(cbind(runs, runBestFitness, runAvgFitness), file = "ga.dat")
}

print("Available: ga(sizePopulation, chromosomes, generations, pM, pC)")
print("Example:   ga(10, 100, 200, 0.2, 0.7)")
