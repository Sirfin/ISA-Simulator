package isasim.pipeline;

import isasim.commands.Command;
import isasim.commands.CommandType;
import isasim.commands.icommands.ICommand;
import isasim.commands.icommands.LoadCommand;
import isasim.commands.icommands.MoveICommand;
import isasim.commands.icommands.StoreCommand;
import isasim.commands.jcommands.JCommand;
import isasim.commands.rcommands.MoveCommand;
import isasim.commands.rcommands.RCommand;
import isasim.helper.Triple;
import isasim.main.Processor;
import isasim.physical.RegisterAddress;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.LinkedList;

public class Execute extends PipelineStage {
    LinkedList<Command> Buffer = new LinkedList<Command>() ;
    public Execute(Processor p ){
        super(p) ;
    }
    public void flush(){
        this.Buffer.clear();
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
        if (c == null){
            return "NOOP" ;
        }
        NameOfCommand = c.getName() ;
        if (c instanceof RCommand) {
            if (c instanceof MoveCommand) {
                int ra1;
                RegisterAddress ra2;
                RegisterAddress ra3;
                ra1 = ((RCommand) c).getValue1();
                ra2 = ((RCommand) c).getQuelle2().getAddress();
                ra3 = ((RCommand) c).getZiel().getAddress();
                Parameter = " " + String.valueOf(ra1) + " R" + String.valueOf(ra2.m_Address) ;
            }else{
                int ra1;
                int ra2;
                RegisterAddress ra3;
                ra1 = ((RCommand) c).getValue1();
                ra2 = ((RCommand) c).getValue2();
                ra3 = ((RCommand) c).getZiel().getAddress();
                Parameter = " " + String.valueOf(ra1) + " " + String.valueOf(ra2) + " R" + String.valueOf(ra3.m_Address);
            }
        }
        if (c instanceof ICommand) {
                int ra1;
                int ra2;
                RegisterAddress ra3;
                ra1 = ((ICommand) c).getValue1();
                ra2 = ((ICommand) c).getValue2();
                ra3 = ((ICommand) c).getZiel().getAddress();
                Parameter = " " + String.valueOf(ra1) + " " + String.valueOf(ra2) + " R" + String.valueOf(ra3.m_Address);
        }

        if (c instanceof JCommand) {
            int ra1;
            int ra2;
            ra1 = ((JCommand) c).getBasis_loaded() ;
            ra2 = ((JCommand) c).getOffset() ;
            Parameter = String.valueOf(ra1+ra2) ;
        }
        returnValue = NameOfCommand + Parameter ;
        return returnValue ;
    }

    @Override
    public void OnTick() {
        if (Buffer.size() > 0 ) {
            Command c = Buffer.pop();
            int output = c.execute(p);

            if (c instanceof LoadCommand){
                p.getMWB().SendToBuffer(new Triple<CommandType,RegisterAddress,Integer>(CommandType.LOAD,
                        c.getZiel().getAddress(),output));
                return ;
            }
            if (c instanceof StoreCommand){
                p.getMWB().SendToBuffer(new Triple<CommandType,RegisterAddress,Integer>(CommandType.STORE,
                        ((StoreCommand) c).getQuelle1().getAddress(),output));
                return ;
            }
            if (c instanceof JCommand){
                return ;
            }
            p.getMWB().SendToBuffer(new Triple<CommandType,RegisterAddress,Integer>(CommandType.OTHER,
                    c.getZiel().getAddress(),output));
            return;

        }
    }
    public void SendToBuffer(Command a){
        Buffer.addLast(a);
    }
}
