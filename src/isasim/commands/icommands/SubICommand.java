package isasim.commands.icommands;

import isasim.commands.rcommands.RCommand;
import isasim.helper.Tuple;
import isasim.main.Processor;
import isasim.physical.Register;
import isasim.physical.RegisterAddress;

public class SubICommand extends ICommand{
    public SubICommand(Register q1 , int q2, Register z, Boolean setFlags){
        super(q1,q2,z) ;
        this.setFlags = setFlags ;
    }

    @Override
    public String getName() {
        if (setFlags)
            return "Subfi" ;
        return "Subi" ;
    }

    @Override
    public void execute(Processor main) {
        int sum = this.Value1 - this.Value2 ;
        if (setFlags){
            long a = this.Value1 - this.Value2 ;
            boolean underflow ;
            boolean overflow ;
            overflow = a > Integer.MAX_VALUE ;
            underflow = a < Integer.MIN_VALUE ;
            main.setFlags(sum,overflow,underflow);
        }
        main.getMWB().SendToBuffer(new Tuple<RegisterAddress,Integer>(Ziel.getAddress(),sum));
    }
}
