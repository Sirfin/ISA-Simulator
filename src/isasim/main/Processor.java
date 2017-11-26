package isasim.main;

import isasim.commands.jcommands.JCommand;
import isasim.commands.rcommands.AddCommand;
import isasim.commands.Command;
import isasim.gui.MainWindow;
import isasim.physical.Flags;
import isasim.physical.Memory;
import isasim.physical.ProgramCounter;
import isasim.physical.Register;
import isasim.pipeline.*;

import java.util.ArrayList;
import java.util.List;

public class Processor {
    public  int REGISTER_COUNT = 32 ;
    public  int  REGISTER_SIZE = 32 ;
    public int RAM_SIZE_WORDS = 1000 ;
    public int ROM_SIZE_WORDS = 1000 ;
    private MainWindow mw ;
    public List<Register> Registerbank = new ArrayList<Register>() ;
    private MemoryWriteBack MWB = new MemoryWriteBack(this) ;
    private Execute exec = new Execute(this) ;
    private Load load = new Load(this) ;
    private Fetch fetch = new Fetch(this) ;
    private Decode decode = new Decode(this) ;
    private Ticker ticker ;
    public Memory rom ;
    public Memory ram ;
    public ProgramCounter PC = new ProgramCounter(this) ;

    public Flags flags = new Flags() ;

    public Flags getFlags(){
        return flags ;
    }
    public void setFrequency(int frequency){
        ticker.setFrequency(frequency);
    }

    public void setFlags(int value , boolean Overflow, boolean Underflow){

        flags.clear();


        flags.setC(Underflow);
        flags.setV(Overflow);
        flags.setN(value < 0 ) ;
        flags.setZ(value == 0);
    }
    public boolean ConditionCheck(JCommand.Condition c){
        boolean returnValue = false ;
        switch (c){

            case eq:
                returnValue = flags.isZ() ;
                break;
            case ne:
                returnValue = !flags.isZ() ;
                break;
            case cs:
                returnValue = flags.isC() ;
                break;
            case cc:
                returnValue = !flags.isC() ;
                break;
            case mi:
                returnValue = flags.isN() ;
                break;
            case pl:
                returnValue = !flags.isN() ;
                break;
            case vs:
                returnValue = flags.isV() ;
                break;
            case vc:
                returnValue = !flags.isV() ;
                break;
            case hi:
                returnValue = (flags.isV() == true)&&(flags.isZ() == false) ;
                break;
            case ls:
                returnValue = 	(flags.isC()==false) || (flags.isZ()==true) ;
                break;
            case ge:
                returnValue = (flags.isN()==flags.isV()) ;
                break;
            case lt:
                returnValue = (flags.isN()!=flags.isV()) ;
                break;
            case gt:
                returnValue = (flags.isZ()==false) && (flags.isN()==flags.isV()) ;
                break;
            case le:
                returnValue = (flags.isZ()==true) && (flags.isN()!=flags.isV()) ;
                break;
            case al:
                returnValue = true ;
                break;
        }
        return returnValue ;
    }

    public Processor(MainWindow m){
        initRegister();
        initMemory();
        this.mw = m ;
        ticker = new Ticker(this) ;
        new Thread(ticker).start();
        Registerbank.get(0).save(8);
        Registerbank.get(1).save(10);
    }

    public void OnTick(){
        MWB.OnTick();
        exec.OnTick();
        load.OnTick();
        decode.OnTick();
        fetch.OnTick();
        PC.increment();
        mw.UpdatePipeline();
    }

    public void Test(){
        Registerbank.get(0).save(8);
        Registerbank.get(1).save(10);
        Command a = new AddCommand(Registerbank.get(0),Registerbank.get(1),Registerbank.get(2),false) ;
        load.SendToBuffer(a);
        load.OnTick();
        exec.OnTick();
        System.out.println(Integer.toHexString(Registerbank.get(2).load()));
    }
    private void initRegister(){
        for (int i = 0 ; i< REGISTER_COUNT ; i++){
            Registerbank.add(new Register(REGISTER_SIZE,i)) ;
        }
    }
    private void initMemory(){
        rom = new Memory(ROM_SIZE_WORDS) ;
        ram = new Memory(RAM_SIZE_WORDS);
    }

    public MemoryWriteBack getMWB() {
        return MWB;
    }

    public Execute getExec() {
        return exec;
    }
    public Load getLoad(){return load;}
    public Fetch getFetch(){return fetch;}
    public Decode getDecode(){return decode;}
}
