package isasim.commands.rcommands;

import isasim.main.Processor;
import isasim.physical.Register;

/**
 * Created by ftoet on 19.11.2017.
 */
public class MoveCommand extends  RCommand {
    @Override
    public String getName() {
        return "Move";
    }

    public MoveCommand(Register q1 , Register q2, Register z){
        super(q1,q2,z) ;
    }

    @Override
    public int execute(Processor main) {

        getQuelle2().save(this.getValue1());
        //TODO
        this.setZiel(this.getQuelle1()) ;
        return this.getValue1() ;
    }
}
