package isasim.main;

import isasim.commands.AddCommand;
import isasim.commands.Command;
import isasim.helper.BitSetHelper;
import isasim.physical.Register;
import isasim.pipeline.Execute;
import isasim.pipeline.MemoryWriteBack;

import java.util.ArrayList;
import java.util.List;

public class Processor {
    public  int REGISTER_COUNT = 32 ;
    public  int  REGISTER_SIZE = 32 ;

    public List<Register> Registerbank = new ArrayList<Register>() ;

    private MemoryWriteBack MWB = new MemoryWriteBack(this) ;
    private Execute exec = new Execute(this) ;
    public Processor(){
        initRegister();
        Test();
    }

    public void Test(){
        Registerbank.get(0).save(8);
        Registerbank.get(1).save(10);
        Command a = new AddCommand(Registerbank.get(0),Registerbank.get(1),Registerbank.get(2)) ;
        exec.SendToBuffer(a);
        exec.OnTick();
        MWB.OnTick();
    }
    private void initRegister(){
        for (int i = 0 ; i< REGISTER_COUNT ; i++){
            Registerbank.add(new Register(REGISTER_SIZE,i)) ;
        }
    }

    public MemoryWriteBack getMWB() {
        return MWB;
    }

    public Execute getExec() {
        return exec;
    }

}
