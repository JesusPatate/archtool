package sourceModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Block
{
    private Set<Call> calls;

    private Map<ProgramGlobalVariable, Integer> programGlobals;

    private Map<FileGlobalVariable, Integer>  fileGlobals;

    private Map<LocalVariable, Integer> locals;

    private Set<Block> subBlocks;

    public Block(Set<Call> calls,
           Map<ProgramGlobalVariable, Integer> programGlobals,
           Map<FileGlobalVariable, Integer> fileGlobals,
           Map<LocalVariable, Integer> locals,
           Set<Block> subBlocks)
    {
        this.calls = calls;
        this.programGlobals = programGlobals;
        this.fileGlobals = fileGlobals;
        this.locals = locals;
        this.subBlocks = subBlocks;
    }

    public Map<ProgramGlobalVariable, Integer> getProgramGlobals()
    {
        Map<ProgramGlobalVariable, Integer> total =
                new HashMap<ProgramGlobalVariable, Integer>(
                        this.programGlobals);
        
        for(Block b : this.subBlocks)
        {
            for(ProgramGlobalVariable v : b.getProgramGlobals().keySet())
            {
                if(total.containsKey(v))
                {
                    total.put(v, total.get(v) + b.getProgramGlobals().get(v));
                }
                else
                {
                    total.put(v, b.getProgramGlobals().get(v));
                }
            }
        }
        
        return total;
    }

    public Map<FileGlobalVariable, Integer> getFileGlobals()
    {
        Map<FileGlobalVariable, Integer> total =
                new HashMap<FileGlobalVariable, Integer>(
                        this.fileGlobals);
        
        for(Block b : this.subBlocks)
        {
            for(FileGlobalVariable v : b.getFileGlobals().keySet())
            {
                if(total.containsKey(v))
                {
                    total.put(v, total.get(v) + b.getFileGlobals().get(v));
                }
                else
                {
                    total.put(v, b.getFileGlobals().get(v));
                }
            }
        }
        
        return total;
    }

    public Map<LocalVariable, Integer> getLocals()
    {
        Map<LocalVariable, Integer> total =
                new HashMap<LocalVariable, Integer>(
                        this.locals);
        
        for(Block b : this.subBlocks)
        {
            for(LocalVariable v : b.getLocals().keySet())
            {
                if(total.containsKey(v))
                {
                    total.put(v, total.get(v) + b.getLocals().get(v));
                }
                else
                {
                    total.put(v, b.getLocals().get(v));
                }
            }
        }
        
        return total;
    }

    public Set<Call> getCalls()
    {
        Set<Call> total = new HashSet<Call>(this.calls);
        
        for(Block b : this.subBlocks)
        {
            total.addAll(b.getCalls());
        }
        
        return total;
    }
}
