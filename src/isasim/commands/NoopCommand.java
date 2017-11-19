package isasim.commands;

import isasim.commands.rcommands.RCommand;
import isasim.main.Processor;
import isasim.physical.Register;

public class NoopCommand extends RCommand {
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
