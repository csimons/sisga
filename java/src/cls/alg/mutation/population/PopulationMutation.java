package cls.alg.mutation.population;

import cls.model.Chromosome;
import java.util.List;

public interface PopulationMutation
{
    public List<Chromosome> mutate(List<Chromosome> population, double pM);
}
