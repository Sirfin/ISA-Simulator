package isasim.main;

import isasim.commands.AddCommand;
import isasim.commands.Command;
import isasim.gui.MainWindow;
import isasim.helper.BitSetHelper;
import isasim.physical.Memory;
import isasim.physical.Register;
import isasim.pipeline.Execute;
import isasim.pipeline.Load;
import isasim.pipeline.MemoryWriteBack;

import java.util.ArrayList;
import java.util.List;

public class Processor {
    public  int REGISTER_COUNT = 32 ;
    public  int  REGISTER_SIZE = 32 ;
    private MainWindow mw ;
    public List<Register> Registerbank = new ArrayList<Register>() ;
    private MemoryWriteBack MWB = new MemoryWriteBack(this) ;
    private Execute exec = new Execute(this) ;
    private Load load = new Load(this) ;
    public Memory rom ;
    public Memory ram ;
    public Processor(MainWindow m){
        initRegister();
        initMemory();

        this.mw = m ;
        Test();
    }

    public void OnTick(){
        load.OnTick();
        exec.OnTick();
        MWB.OnTick();
    }

    public void Test(){
        Registerbank.get(0).save(8);
        Registerbank.get(1).save(10);
        Command a = new AddCommand(Registerbank.get(0),Registerbank.get(1),Registerbank.get(2)) ;
        load.SendToBuffer(a);
        load.OnTick();
        exec.OnTick();
        //MWB.On
        System.out.println(Integer.toHexString(Registerbank.get(2).load()));
    }
    private void initRegister(){
        for (int i = 0 ; i< REGISTER_COUNT ; i++){
            Registerbank.add(new Register(REGISTER_SIZE,i)) ;
        }
    }
    private void initMemory(){
        rom = new Memory(16) ;
        ram = new Memory(256);
    }

    public MemoryWriteBack getMWB() {
        return MWB;
    }

    public Execute getExec() {
        return exec;
    }

}
