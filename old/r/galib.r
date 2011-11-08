#
# CLS, 2011
#
# WORK IN PROGRESS
#
# Library of GA-related functions.
#
# Chromosomes are represented as a vector of numbers (0 or 1).
# The population is represented as a list of these vectors.
#

#####################
# UTILITY FUNCTIONS #
#####################

prettyPrint <- function(population)
{
    for (i in 1:length(population))
    {
        print(population[[i]])
    }
}

initPopulation <- function(sizePopulation, chromosomeSize)
{
    population <- list()

    for (i in 1:sizePopulation)
    {
        chromosome <- list(round(runif(chromosomeSize, 0, 1)))
        population <- c(population, chromosome)
    }

    return(population)
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

avgFitness <- function(population)
{
    fitnessSum <- 0

    for (i in 1:length(population))
    {
        fitnessesSum <- fitnessSum + ff(population[[i]])
    }

    avg <- (fitnessSum / length(population))

    return(avg)
}

bestFitness <- function(population)
{
    best <- 0

    for (i in 1:length(population))
    {
        current <- ff(population[[i]])

        if (current > best)
        {
            best <- current
        }
    }

    return(best)
}

HammingDistance <- function(chromosome1, chromosome2)
{
    if (length(chromosome1) != length(chromosome2))
    {
        hDistance <- (-1)
    }
    else
    {
        bits <- length(chromosome1)

        hDistance <- 0

        for (i in 1:bits)
        {
            if(chromosome1[i] != chromosome2[i])
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
oneMax <- function(chromosome)
{
    result <- 0

    for (i in 1:length(chromosome))
    {
        result <- result + chromosome[i]
    }

    return(result)
}

###############################################
# MUTATION ALGORITHMS (mutate one chromosome) #
###############################################

# TODO: implement bit-swapping and other algorithms.

# Randomly select an allele from the chromosome, and
# based on probability p, flip the allele/bit.
#
randomBitFlip <- function(chromosome, p)
{
    bit <- round(runif(1, 1, length(chromosome)))
    chance <- runif(1, 0, 1)

    if (chance < p)
    {
        chromosome[bit] <- flip(chromosome[bit])
    }

    return(chromosome)
}

# Cataclysmic mutation, for CHC:
#
# For every allele in every chromosome in the population, except for
# the chromosome with the highest fitness, flip per probability 'p'.
#
cataclysmicMutation <- function(population, p, ff)
{
    bestChromosome <- population[[1]]

    for (i in 1:length(population))
    {
        if (ff(population[[i]]) > ff(bestChromosome))
        {
            bestChromosome <- population[[i]]
        }
    }

    for (i in 1:length(population))
    {
        chromosome <- population[[i]]

        # != doesn't work with vectors
        if (! identical(chromosome, bestChromosome))
        {
            for (j in 1:length(chromosome))
            {
                chance <- runif(1, 0, 1)

                if (chance < p)
                {
                    chromosome[j] <- flip(chromosome[j])
                }
            }
        }
    }

    return(population)
}

#############################################################################
# RECOMBINATION ALGORITHMS (create a chromosomefrom two parent chromosomes) #
#############################################################################

hux <- function(dad, mom)
{
    # HUX
    #
    # "Crosses over exactly half the non-matching alleles, where the
    #  bits to be exchanged are chosen at random without replacement."
    #
    # (Eshelman, p. 271)
    #

    nonMatchingAlleles <- c() # indexes of non-matching alleles

    for (i in 1:length(chromosome1))
    {
        if (dad[i] != mom[i])
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

    child <- c()

    for (i in 1:length(dad))
    {
        # Now, use all alleles from 'dad' except the 'swap' alleles.

        if (any(swapAlleles == i))
        {
            child <- c(child, mom[i])
        }
        else
        {
            child <- c(child, dad[i])
        }
    }

    return(child)
}

# Single-point crossover at a random point, per probability p.
#
singlePointCrossover <- function(dad, mom, p)
{
    a <- runif(1, 0, 1)

    if (a < p) # Crossover case.  Pick a random point and crossover.
    {
        alleles <- length(dad)

        bit <- round(runif(1, 1, alleles))

        child <- c()

        for (i in 1:alleles)
        {
            if (i < bit)
            {
                child <- c(child, dad[i])
            }
            else
            {
                child <- c(child, mom[i])
            }
        }
    }
    else # Non-crossover case; pick one parent (randomly) and return it.
    {
        b <- round(runif(1, 0, 1)) # will be 0 or 1

        if (b == 0)
        {
            child <- dad
        }
        else
        {
            child <- mom
        }
    }

    return(child)
}

###############################
# PARENT SELECTION ALGORITHMS #
###############################

# Randomly select two parents.
#
getTwoParents <- function(population)
{
    sizePopulation <- length(population)
    parents <- list()

    while(length(parents) < 2)
    {
        pick <- round(runif(1, 1, sizePopulation))
        parents <- c(parents, list(population[[pick]]))
    }

    return(parents)
}

# Randomly select two distinct parents with higher-than-average fitness.
#
getTwoHigherFitParents <- function(population)
{
    sizePopulation <- length(population)

    avg <- avgFitness(population)
    parents <- list()

    attempts <- 0

    while((length(parents) < 2) & (attempts <= length(population)))
    {
        attempts <- attempts + 1

        pick <- round(runif(1, 1, sizePopulation))

        if (ff(population[[pick]]) > avg)
        {
            if (length(parents) == 1)
            {
                if (! (listEq(parents[[1]], population[[pick]])))
                {
                    parents <- c(parents, list(population[[pick]]))
                }
            }
            else
            {
                parents <- c(parents, list(population[[pick]]))
            }
        }
    }

    # Two DISTINCT higher-than-average-fitness chromosomes do not exist,
    # so pick a random chromosome to be the other parent.
    #
    if (length(parents) < 2)
    {
        pick <- round(runif(1, 1, sizePopulation))
        parents <- c(parents, list(population[[pick]]))
    }

    return(parents)
}

# Select two parents via roulette-wheel (fitness-proportional) selection.
#
getParentsFitProportionate <- function(population)
{
    absoluteFitnesses <- c()
    proportionalFitnesses <- c()

    totalFitness <- 0
    for (i in 1:length(population))
    {
        chromosomeFitness = ff(population[[i]])
        totalFitness <- totalFitness + chromosomeFitness
        absoluteFitnesses <- c(absoluteFitnesses, chromosomeFitness)
    }

    for (i in 1:length(population))
    {
        proportionalFitnesses <- c(proportionalFitnesses,
                                    absoluteFitnesses[i] / totalFitness)
    }

    parents <- list()

    for (i in 1:2)
    {
        p <- runif(1, 0, 1) # get probability between 0 and 1.

        for (j in 1:length(population))
        {
            if (p < proportionalFitnesses[j])
            {
                parents <- c(parents, list(population[[j]]))
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
elitistSelect <- function(population, n, ff)
{
    fitnesses <- c()

    for (i in 1:length(population))
    {
        fitnesses <- c(fitnesses, ff(population[[i]]))
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

            if (ff(population[[j]]) == fitnesses[i])
            {
                survivors <- c(survivors, list(population[[j]]))
            }
        }
    }

    return (survivors)
}

# Randomly select two chromosomes and kill off the one with lower
# fitness, until the population size is as desired (sizePopulation).
#
# If one chromosome has the misfortune of being randomly selected
# twice, it will compete against itself and die.
#
nTournaments <- function(population, n, ff)
{
    while (length(population) > n)
    {
        pick1 <- round(runif(1, 1, length(population)))
        gladiator1 <- population[[pick1]]

        pick2 <- round(runif(1, 1, length(population)))
        gladiator2 <- population[[pick2]]

        if (ff(gladiator1) <= ff(gladiator2))
        {
            population[[pick1]] <- NULL # remove gladiator 1 from list.
        }
        else
        {
            population[[pick2]] <- NULL # remove gladiator 2 from list.
        }
    }

    return(population)
}
