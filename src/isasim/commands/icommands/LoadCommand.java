package isasim.commands.icommands;

import isasim.helper.Tuple;
import isasim.main.Processor;
import isasim.physical.Register;
import isasim.physical.RegisterAddress;

public class LoadCommand extends ICommand{
    public LoadCommand(Register q1 , int q2, Register z){
        super(q1,q2,z) ;
        this.setFlags = false ;
    }

    @Override
    public String getName() {

        return "Load" ;
    }

    @Override
    public void execute(Processor main) {
        int sum = main.ram.load(this.getValue1()+this.getValue2()) ;
        main.getMWB().SendToBuffer(new Tuple<RegisterAddress,Integer>(Ziel.getAddress(),sum));
    }
}
