package isasim.commands.rcommands;

import isasim.main.Processor;
import isasim.physical.Register;

public class HaltCommand extends RCommand {
    @Override
    public String getName() {
        return "halt";
    }
    public HaltCommand(Register q, Register q2, Register Ziel){
        super(q,q2,Ziel) ;
    }

    @Override
    public int execute(Processor main){
        main.Halt();
        return 0 ;
    }
}
