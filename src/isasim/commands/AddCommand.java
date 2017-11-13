package isasim.commands;

import isasim.helper.Tuple;
import isasim.main.Processor;
import isasim.physical.Register;
import isasim.physical.RegisterAddress;

public class AddCommand extends RCommand{
    public AddCommand(Register q1 , Register q2, Register z){
        super(q1,q2,z) ;
    }

    @Override
    public String getName() {
        return "Add" ;
    }

    @Override
    public void execute(Processor main) {
        int sum = this.Value1 + this.Value2 ;
        main.getMWB().SendToBuffer(new Tuple<RegisterAddress,Integer>(Ziel.getAddress(),sum));

    }
}
