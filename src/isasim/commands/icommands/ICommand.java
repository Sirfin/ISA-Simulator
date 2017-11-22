package isasim.commands.icommands;

import isasim.commands.Command;
import isasim.main.Processor;
import isasim.physical.Register;

public abstract class ICommand extends Command {
    public abstract String getName() ;


    public Register getQuelle1() {
        return Quelle1;
    }

    Register Quelle1 ;
    Register Ziel ;

    int Value1 ;
    int Value2 ;
    public int getValue1(){
        return Value1;
    }
    public int getValue2(){
        return Value2;
    }
    public Register getZiel(){
        return Ziel;}

    public void setValue1(int value1) {
        Value1 = value1;
    }

    public void setValue2(int value2) {
        Value2 = value2;
    }

    public ICommand(Register q1 , int immediate, Register z){
        this.Quelle1 = q1 ;
        this.Value2 = immediate ;
        this.Ziel = z ;
    }
    public void execute(Processor main){
    }
}
