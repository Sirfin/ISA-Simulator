package isasim.pipeline;

import isasim.commands.Command;
import isasim.commands.RCommand;
import isasim.main.Processor;
import isasim.physical.RegisterAddress;

import java.util.LinkedList;

public class Load extends PipelineStage {
    LinkedList<Command> Buffer = new LinkedList<Command>() ;
    public Load(Processor p){
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
            RegisterAddress ra1;
            RegisterAddress ra2;
            RegisterAddress ra3;
            ra1 = ((RCommand) c).getQuelle1().getAddress();
            ra2 = ((RCommand) c).getQuelle2().getAddress();
            ra3 = ((RCommand) c).getZiel().getAddress();
            Parameter = " R"+String.valueOf(ra1.m_Address) + " R" + String.valueOf(ra2.m_Address) + " R" + String.valueOf(ra3.m_Address) ;
        }
        returnValue = NameOfCommand + Parameter ;
        return returnValue ;



    }

    @Override
    public void OnTick() {
        if (Buffer.size() > 0 ) {
            Command c = Buffer.pop();
            if (c instanceof RCommand) {
                ((RCommand) c).setValue1(((RCommand) c).getQuelle1().load());
                ((RCommand) c).setValue2(((RCommand) c).getQuelle2().load());
            }

            p.getExec().SendToBuffer(c);
        }

    }
    public void SendToBuffer(Command c){
        Buffer.addLast(c);
    }
}
