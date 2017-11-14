package isasim.pipeline;

import isasim.commands.AddCommand;
import isasim.commands.Command;
import isasim.commands.RCommand;
import isasim.helper.Tuple;
import isasim.main.Processor;
import isasim.physical.RegisterAddress;

import java.util.LinkedList;

public class Execute extends PipelineStage {
    LinkedList<Command> Buffer = new LinkedList<Command>() ;
    public Execute(Processor p ){
        super(p) ;
    }

    @Override
    public String GetStringFormatOfPipelineStage() {
        String returnValue = "" ;
        String NameOfCommand  ;
        String Parameter = "" ;
        if (Buffer.size() == 0){
            return "NOOP" ;
        }
        Command c = Buffer.get(0) ;
        NameOfCommand = c.getName() ;
        if (c instanceof RCommand) {
            int ra1;
            int ra2;
            RegisterAddress ra3;
            ra1 = ((RCommand) c).getValue1();
            ra2 = ((RCommand) c).getValue2();
            ra3 = ((RCommand) c).getZiel().getAddress();
            Parameter = " "+String.valueOf(ra1) + " " + String.valueOf(ra2) + " R" + String.valueOf(ra3.m_Address) ;
        }
        returnValue = NameOfCommand + Parameter ;
        return returnValue ;
    }

    @Override
    public void OnTick() {
        if (Buffer.size() > 0 ) {
            Command c = Buffer.pop();
            c.execute(p);
        }
        System.out.println(Buffer);
    }
    public void SendToBuffer(Command a){
        Buffer.addLast(a);
    }
}
