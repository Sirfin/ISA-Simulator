package isasim.main;

import isasim.commands.CommandDecoder;
import isasim.commands.jcommands.Condition;
import isasim.commands.rcommands.AddCommand;
import isasim.commands.Command;
import isasim.gui.MainWindow;
import isasim.physical.*;
import isasim.pipeline.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse die veranwortlich dafür ist, alle Teile des Simulators zusammenzuführen.
 * Im Rahmen der physischen Komponenten, wäre dies wohl das Steuerwerk.
 * @author Fin Töter
 *
 */
public class Processor {
    public  int REGISTER_COUNT = 32 ;
    public  int  REGISTER_SIZE = 32 ;
    public int RAM_SIZE_WORDS = 1000 ;
    public int ROM_SIZE_WORDS = 1000 ;
    public MainWindow mw ;
    public List<Register> Registerbank = new ArrayList<Register>() ;
    private RegisterWriteBack RWB = new RegisterWriteBack(this) ;
    private Execute exec = new Execute(this) ;
    private Fetch fetch = new Fetch(this) ;
    private DecodeLoad decode = new DecodeLoad(this) ;
    private MemoryWriteBack MWB = new MemoryWriteBack(this);
    private Ticker ticker ;
    public Memory rom ;
    public Memory ram ;
    public ProgramCounter PC = new ProgramCounter(this) ;

    public Flags flags = new Flags() ;
    boolean halted = false ;
    /**
     * Gibt eine Referenz auf die Flags des Prozessors zurück
     * @see Flags
     * @return flags
     */
    public Flags getFlags(){
        return flags ;
    }
    public void setFrequency(int frequency){
        ticker.setFrequency(frequency);
    }

    /**
     * Übergibt dem Steuwerk Eigenschaften eines Wertes um die Flags zu setzen
     * @param value Value to Check
     * @param Overflow Overflow occured
     * @param Underflow Underflow occured
     */
    public void setFlags(int value , boolean Overflow, boolean Underflow){
        flags.clear();
        flags.setC(Underflow);
        flags.setV(Overflow);
        flags.setN(value < 0 ) ;
        flags.setZ(value == 0);
    }

    /**
     * Gibt zurück ob eine Condition bei den momentanen Flags true/false ist.
     * @see Flags
     * @see Condition
     * @param c Condition to Check
     * @return
     */
    public boolean ConditionCheck(Condition c){
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

    /**
     * Flush the Pipeline
     */
    public void PipelineFlush(){
    this.getDecode().flush();
    this.getExec().flush();
    this.getMWB().flush();
    }

    /**
     * Konstruktur mit Referenz zur Main GUI
     * @param m Referenz zum Main Window
     * @see MainWindow
     */
    public Processor(MainWindow m){
        initRegister();
        initMemory();
        this.mw = m ;
        ticker = new Ticker(this) ;
        new Thread(ticker).start();
    }

    /**
     *
     * @param n How many Ticks
     */
    public Processor(int n,String PathToFile,String outputPath){
        initRegister();
        initMemory() ;
        RomLoader.FileInRom(PathToFile,this,0);
        if (n >0) {
            for (int i = 0; (i < n) && (!halted); i++) {
                this.OnTick();
            }
            if (!halted){
                this.Halt();
            }
        }
        if (n == 0){
            while (!halted){
                this.OnTick();
            }
        }
        ProcessorTextOutput.DumpProcessor(this,outputPath);
    }


    public void Reset(){
        this.Halt();
        this.PipelineFlush();
        ram.reset();
        rom.reset();
        PC.reset();
        for (int i = 0 ; i < 32 ; i++){
            Registerbank.get(i).save(0);
        }
        if(mw!=null) this.mw.UpdatePipeline();

    }
    /**
     * Stoppt den Prozessor
     *
     */
    public void Halt(){
            this.halted = true ;

            if (ticker!=null)ticker.stop();
            if (mw!=null)mw.UpdatePipeline();
    }

    /**
     * Wenn der Prozessor gestoppt ist, wird er hier forgesetzt
     */
    public void Continue(){
        if (ticker.halted) {
            ticker.start();
            new Thread(ticker).start();
            if (mw!=null)mw.UpdatePipeline();
        }
    }

    /**
     * Tick für die Frequenz
     */
    public void OnTick(){
        RWB.OnTick();
        MWB.OnTick();
        exec.OnTick();
        decode.OnTick();
        fetch.OnTick();
        PC.increment();
        if (mw !=null)mw.UpdatePipeline();
    }

    /**
     * Gibt zurück ob der Prozessor momentan läuft oder gestoppt wurde.
     * @return halted
     */
    public boolean Running(){
        return ticker.halted ;
    }
    public void TestDecode(){
        System.out.println(CommandDecoder.decodeCommand(0b01010000001000000000000000000000,this));
        System.out.println(CommandDecoder.decodeCommand(0b01001100000000100000000000000000,this));
    }

    public void Test(){
        Registerbank.get(0).save(8);
        Registerbank.get(1).save(10);
        Command a = new AddCommand(Registerbank.get(0),Registerbank.get(1),Registerbank.get(2),false) ;
        exec.OnTick();
        System.out.println(Integer.toHexString(Registerbank.get(2).load()));
    }

    /**
     * Initialisiert die Register
     */
    private void initRegister(){
        for (int i = 0 ; i< REGISTER_COUNT ; i++){
            Registerbank.add(new Register(REGISTER_SIZE,i)) ;
        }
    }

    /**
     * Initisalisiert ROM/RAM
     */
    private void initMemory(){
        rom = new Memory(ROM_SIZE_WORDS) ;
        ram = new Memory(RAM_SIZE_WORDS);
    }

    /**
     * Gibt die Referenz auf die RWB Stage zurück.
     * @see RegisterWriteBack
     * @return RWB
     */
    public RegisterWriteBack getRWB() {
        return RWB;
    }

    /**
     * Gibt die Referenz auf die Execute Stage zurück.
     * @see Execute
     * @return execute
     */
    public Execute getExec() {
        return exec;
    }



    /**
     * Gibt die Referenz auf die Fetch Stage zurück.
     * @see Fetch
     * @return fetch
     */
    public Fetch getFetch(){return fetch;}

    /**
     * Gibt die Referenz auf die DecodeLoad Stage zurück.
     * @see DecodeLoad
     * @return decode
     */
    public DecodeLoad getDecode(){return decode;}

    public MemoryWriteBack getMWB() {
        return MWB;
    }
}
