package isasim.commands;

import isasim.main.Processor;
import isasim.physical.Register;

public class RCommand extends Command {
    Register Quelle1 ;
    Register Quelle2 ;
    Register Ziel ;
    public RCommand(Register q1 , Register q2, Register z){
        this.Quelle1 = q1 ;
        this.Quelle2 = q2 ;
        this.Ziel = z ;
    }
    @Override
    public void execute(Processor main) {

    }
}
