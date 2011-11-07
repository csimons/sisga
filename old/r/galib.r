#
# CLS, 2011
#
# WORK IN PROGRESS
#
# Library of GA-related functions.
#
# Genes are represented as a vector of numbers (0 or 1).
# The gene set is represented as a list of these vectors.
#

#####################
# UTILITY FUNCTIONS #
#####################

prettyPrint <- function(genes)
{
    for (i in 1:length(genes))
    {
        print(genes[[i]])
    }
}

initPopulation <- function(sizePopulation, chromosomes)
{
    genes <- list()

    for (i in 1:sizePopulation)
    {
        gene <- list(round(runif(chromosomes, 0, 1)))
        genes <- c(genes, gene)
    }

    return(genes)
}

listEq <- function(list1, list2)
{
    result <- TRUE

    if (length(list1) != length(list2))
    {
        result <- FALSE
    }
    else
    {
        for (i in 1:length(list1))
        {
            if (list1[[i]] != list2[[i]])
            {
                result <- FALSE
            }
        }
    }

    return(result)
}

flip <- function(x)
{
    if (x == 0)
    {
        result <- 1
    }
    else
    {
        result <- 0
    }

    return(result)
}

avgFitness <- function(genes)
{
    fitnessSum <- 0

    for (i in 1:length(genes))
    {
        fitnessesSum <- fitnessSum + ff(genes[[i]])
    }

    avg <- (fitnessSum / length(genes))

    return(avg)
}

bestFitness <- function(genes)
{
    best <- 0

    for (i in 1:length(genes))
    {
        current <- ff(genes[[i]])

        if (current > best)
        {
            best <- current
        }
    }

    return(best)
}

HammingDistance <- function(gene1, gene2)
{
    if (length(gene1) != length(gene2))
    {
        hDistance <- (-1)
    }
    else
    {
        bits <- length(gene1)

        hDistance <- 0

        for (i in 1:bits)
        {
            if(gene1[i] != gene2[i])
            {
                hDistance <- hDistance + 1
            }
        }
    }

    return(hDistance)
}

#####################
# FITNESS FUNCTIONS #
#####################

# Return the sum of the bits in the bit string.
#
oneMax <- function(gene)
{
    result <- 0

    for (i in 1:length(gene))
    {
        result <- result + gene[i]
    }

    return(result)
}

#########################################
# MUTATION ALGORITHMS (mutate one gene) #
#########################################

# TODO: implement bit-swapping and other algorithms.

# Randomly select a bit from the gene, and based on
# probability p, flip the bit.
#
randomBitFlip <- function(gene, p)
{
    bit <- round(runif(1, 1, length(gene)))
    chance <- runif(1, 0, 1)

    if (chance < p)
    {
        gene[bit] <- flip(gene[bit])
    }

    return(gene)
}

# Cataclysmic mutation, for CHC:
#
# For every bit in every gene in the gene set, except for the
# gene with the highest fitness, flip per probability 'p'.
#
cataclysmicMutation <- function(genes, p, ff)
{
    bestGene <- genes[[1]]

    for (i in 1:length(genes))
    {
        if (ff(genes[[i]]) > ff(bestGene))
        {
            bestGene <- genes[[i]]
        }
    }

    for (i in 1:length(genes))
    {
        gene <- genes[[i]]

        if (! identical(gene, bestGene)) # != doesn't work with vectors
        {
            for (j in 1:length(gene))
            {
                chance <- runif(1, 0, 1)

                if (chance < p)
                {
                    gene[j] <- flip(gene[j])
                }
            }
        }
    }

    return(genes)
}

##################################################################
# RECOMBINATION ALGORITHMS (create a gene from two parent genes) #
##################################################################

hux <- function(gene1, gene2)
{
    # HUX
    #
    # "Crosses over exactly half the non-matching alleles, where the
    #  bits to be exchanged are chosen at random without replacement."
    #
    # (Eshelman, p. 271)
    #

    nonMatchingAlleles <- c() # indexes of non-matching alleles

    for (i in 1:length(gene1))
    {
        if (gene1[i] != gene2[i])
        {
            nonMatchingAlleles <- c(nonMatchingAlleles, i)
        }
    }

    swapAlleles <- c()

    while (length(swapAlleles) < ceiling(length(nonMatchingAlleles) / 2))
    {
        j <- round(runif(1, 1, length(nonMatchingAlleles))) # get rand index

        if (! any(swapAlleles == j)) # If j is not in swapAlleles ...
        {
            swapAlleles <- c(swapAlleles, j) # ... add it.
        }
    }

    newGene <- c()

    for (i in 1:length(gene1))
    {
        # Now, use all alleles from gene 1 except the 'swap' alleles above.

        if (any(swapAlleles == i))
        {
            newGene <- c(newGene, gene2[i])
        }
        else
        {
            newGene <- c(newGene, gene1[i])
        }
    }

    return(newGene)
}

# Single-point crossover at a random point, per probability p.
#
singlePointCrossover <- function(gene1, gene2, p)
{
    a <- runif(1, 0, 1)

    if (a < p) # Crossover case.  Pick a random point and crossover.
    {
        chromosomes <- length(gene1)

        bit <- round(runif(1, 1, chromosomes))

        newGene <- c()

        for (i in 1:chromosomes)
        {
            if (i < bit)
            {
                newGene <- c(newGene, gene1[i])
            }
            else
            {
                newGene <- c(newGene, gene2[i])
            }
        }
    }
    else # Non-crossover case; pick one parent (randomly) and return it.
    {
        b <- round(runif(1, 0, 1)) # will be 0 or 1

        if (b == 0)
        {
            newGene = gene1
        }
        else
        {
            newGene = gene2
        }
    }

    return(newGene)
}

###############################
# PARENT SELECTION ALGORITHMS #
###############################

# Randomly select two parents.
#
getTwoParents <- function(genes)
{
    sizePopulation <- length(genes)
    parents <- list()

    while(length(parents) < 2)
    {
        pick <- round(runif(1, 1, sizePopulation))
        parents <- c(parents, list(genes[[pick]]))
    }

    return(parents)
}

# Randomly select two distinct parents with higher-than-average fitness.
#
getTwoHigherFitParents <- function(genes)
{
    sizePopulation <- length(genes)

    avg <- avgFitness(genes)
    parents <- list()

    attempts <- 0

    while((length(parents) < 2) & (attempts <= length(genes)))
    {
        attempts <- attempts + 1

        pick <- round(runif(1, 1, sizePopulation))

        if (ff(genes[[pick]]) > avg)
        {
            if (length(parents) == 1)
            {
                if (! (listEq(parents[[1]], genes[[pick]])))
                {
                    parents <- c(parents, list(genes[[pick]]))
                }
            }
            else
            {
                parents <- c(parents, list(genes[[pick]]))
            }
        }
    }

    # Two DISTINCT higher-than-average-fitness genes do not exist, so pick a
    # random gene to be the other parent.
    #
    if (length(parents) < 2)
    {
        pick <- round(runif(1, 1, sizePopulation))
        parents <- c(parents, list(genes[[pick]]))
    }

    return(parents)
}

# Select two parents via roulette-wheel (fitness-proportional) selection.
#
getParentsFitProportionate <- function(genes)
{
    absoluteFitnesses <- c()
    proportionalFitnesses <- c()

    totalFitness <- 0
    for (i in 1:length(genes))
    {
        geneFitness = ff(genes[[i]])
        totalFitness <- totalFitness + geneFitness
        absoluteFitnesses <- c(absoluteFitnesses, geneFitness)
    }

    for (i in 1:length(genes))
    {
        proportionalFitnesses <- c(proportionalFitnesses,
                                    absoluteFitnesses[i] / totalFitness)
    }

    parents <- list()

    for (i in 1:2)
    {
        p <- runif(1, 0, 1) # get probability between 0 and 1.

        for (j in 1:length(genes))
        {
            if (p < proportionalFitnesses[j])
            {
                parents <- c(parents, list(genes[[j]]))
                next
            }
            else
            {
                p <- p - proportionalFitnesses[j]
            }
        }

    }

    return(parents)
}

#######################################################################
# SURVIVOR SELECTION ALGORITHMS (taking population from |M+N| -> |M|) #
#######################################################################

# Rank genes from best to worst, per function ff, then keep n best genes.
#
elitistSelect <- function(genes, n, ff)
{
    fitnesses <- c()

    for (i in 1:length(genes))
    {
        fitnesses <- c(fitnesses, ff(genes[[i]]))
    }

    fitnesses <- sort(fitnesses, decreasing=TRUE)

    survivors <- list()

    for (i in 1:n)
    {
        startingSurvivors <- length(survivors)

        j <- 0
        while (length(survivors) == startingSurvivors)
        {
            j <- j + 1

            if (ff(genes[[j]]) == fitnesses[i])
            {
                survivors <- c(survivors, list(genes[[j]]))
            }
        }
    }

    return (survivors)
}

# Randomly select two genes and kill off the one with lower fitness,
# until the population size is as desired (sizePopulation).
#
# If one gene has the misfortune of being randomly selected twice, it
# will compete against itself and die.
#
nTournaments <- function(genes, n, ff)
{
    while (length(genes) > n)
    {
        pick1 <- round(runif(1, 1, length(genes)))
        gladiator1 <- genes[[pick1]]

        pick2 <- round(runif(1, 1, length(genes)))
        gladiator2 <- genes[[pick2]]

        if (ff(gladiator1) <= ff(gladiator2))
        {
            genes[[pick1]] <- NULL # remove gladiator 1 from list.
        }
        else
        {
            genes[[pick2]] <- NULL # remove gladiator 2 from list.
        }
    }

    return(genes)
}
