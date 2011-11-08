package cls.alg.selection.survivor;

import cls.model.Chromosome;
import java.util.List;

public interface SurvivorSelection
{
    public List<Chromosome> select(List<Chromosome> population,
            int targetPopulationSize);
}
