package isasim.commands.icommands;

import isasim.commands.rcommands.RCommand;
import isasim.main.Processor;
import isasim.physical.Register;

/**
 * Created by ftoet on 19.11.2017.
 */
public class MoveICommand extends  ICommand {
    @Override
    public String getName() {
        return "Movei";
    }

    public MoveICommand(Register q1 , int q2, Register z){
        super(q1,q2,z) ;
    }

    @Override
    public void execute(Processor main) {
        getQuelle1().save(this.getValue2());
    }
}
