#
# CLS, 2011
#
# WORK IN PROGRESS
#
# CHC GA algoritm in R.
#
# Genes are represented as a vector of numbers (0 or 1).
# The gene set is represented as a list of these vectors.
#

source("galib.r")

################################################################################
# LOWER-LEVEL ALGORITHM CONTROL
################################################################################
ff          <- function(gene)  { return(oneMax(gene)) }
getParents  <- function(genes) { return(getParentsFitProportionate(genes)) }
recombinate <- function(a, b)  { return(hux(a, b)) }
naturalSelection <- function(genes, n) { return(elitistSelect(genes, n, ff)) }
################################################################################

##############################
# HIGH-LEVEL ALGORITHM LOGIC #
##############################

chc <- function(sizePopulation, chromosomes, generations, pCMutation)
{
    print("RUNNING")
    print("-------")
    print(paste("sizePopulation       = ", sizePopulation))
    print(paste("chromosomes          = ", chromosomes))
    print(paste("generations          = ", generations))
    print(paste("pCataclysmicMutation = ", pCMutation))
    print("-------")

    runs <- c()
    runAvgFitnesses <- c()
    runBestFitnesses <- c()

    genes <- initPopulation(sizePopulation, chromosomes)

    beginGenes <- genes

    threshold = round(length(genes) / 4)

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
            genes <- c(genes, children) # add children to population
            genes <- naturalSelection(genes, sizePopulation) # remove unfit.
        }

        if(threshold < 0)
        {
            genes <- cataclysmicMutation(genes, pCMutation, ff)
            threshold <- pCMutation * (1 - pCMutation) * chromosomes
        }

        # Update tracking vectors for plotting a graph.
        runs <- c(runs, i)
        runAvgFitnesses <- c(runBestFitnesses, avgFitness(genes))
        runBestFitnesses <- c(runBestFitnesses, bestFitness(genes))
    }

    #plot(runs, runBestFitnesses, type="p")
    #par(new <- TRUE)
    #plot(runs, runAvgFitnesses, type="l")
    write(cbind(runs, runBestFitnesses, runAvgFitnesses), file = "chc.dat")
}

print("Available: chc(sizePopulation, chromosomes, generations, pCMutation)")
print("Example:   chc(50, 100, 200, 0.35)")
