package isasim.commands.icommands;

import isasim.helper.Tuple;
import isasim.main.Processor;
import isasim.physical.Register;
import isasim.physical.RegisterAddress;

public class StoreCommand extends ICommand{
    public StoreCommand(Register q1 , int q2, Register z){
        super(q1,q2,z) ;
        this.setFlags = false ;
    }

    @Override
    public String getName() {

        return "Store" ;
    }

    @Override
    public int execute(Processor main) {

        return this.getZiel().load()+this.getValue2() ;
        //Value1 liest die execute
    }
}
