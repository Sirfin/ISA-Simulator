package isasim.commands.jcommands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by ftoet on 26.11.2017.
 */
public enum Condition {
    eq,
    ne,
    cs,
    cc,
    mi,
    pl,
    vs,
    vc,
    hi,
    ls,
    ge,
    lt,
    gt,
    le,
    al;
    private static final Map<Integer, Condition> ConditionMap;
    static {
        Map<Integer, Condition> aMap = new HashMap<Integer,Condition>() ;
        aMap.put(1, eq);
        aMap.put(2, ne);
        aMap.put(3, cs);
        aMap.put(4, cc);
        aMap.put(5, mi);
        aMap.put(6, pl);
        aMap.put(7, vs);
        aMap.put(8, vc);
        aMap.put(9, hi);
        aMap.put(10, ls);
        aMap.put(11, ge);
        aMap.put(12, lt);
        aMap.put(13, gt);
        aMap.put(14, le);
        aMap.put(15, al);
        ConditionMap = Collections.unmodifiableMap(aMap);
    }
    public static Condition getConditionFromCode(int Code){
        return ConditionMap.get(Code) ;
    }
}
