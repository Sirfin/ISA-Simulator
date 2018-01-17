package isasim.commands;

import isasim.main.Processor;
import isasim.physical.Register;

public abstract class Command {
    public abstract int execute(Processor main) ;
    public abstract String getName() ;
    public static int TestInt = 0 ;
    public boolean setFlags   ;
    Register Ziel ;

    public Register getZiel() {
        return Ziel;
    }
    public void setZiel(Register ziel){
        Ziel = ziel ;
    }
}
