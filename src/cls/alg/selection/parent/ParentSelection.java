package cls.alg.selection.parent;

import cls.model.Chromosome;
import java.util.List;

public interface ParentSelection
{
    public List<Chromosome> select(List<Chromosome> population);
}
