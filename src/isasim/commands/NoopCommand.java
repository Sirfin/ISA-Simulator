package isasim.commands;

import isasim.helper.Tuple;
import isasim.main.Processor;
import isasim.physical.Register;
import isasim.physical.RegisterAddress;

public class NoopCommand extends RCommand{
    public NoopCommand(Register q1 , Register q2, Register z){
        super(q1,q2,z) ;
    }

    @Override
    public String getName() {
        return "Add" ;
    }

    @Override
    public void execute(Processor main) {
    }
}
