package isasim.pipeline;

import isasim.commands.CommandType;
import isasim.helper.Triple;
import isasim.helper.Tuple;
import isasim.main.Processor;
import isasim.physical.Register;
import isasim.physical.RegisterAddress;

import java.util.LinkedList;

public class MemoryWriteBack extends PipelineStage {
    LinkedList<Triple<CommandType,RegisterAddress,Integer>> Buffer = new LinkedList<Triple<CommandType,RegisterAddress,Integer>>();
    public MemoryWriteBack(Processor p ){
        super(p) ;
    }
    @Override
    public void OnTick() {
        if (Buffer.size() > 0) {
            Triple<CommandType,RegisterAddress, Integer> toSave = Buffer.pop();
            switch (toSave.getFirst()){
                case LOAD:
                    int Value = p.ram.load(toSave.getThird()) ;
                    p.getRWB().SendToBuffer(new Tuple<RegisterAddress,Integer>(
                            toSave.getSecond(),Value
                    ));
                    break;
                case STORE:
                    p.ram.store(toSave.getThird(),p.Registerbank.get(toSave.getSecond().m_Address).load());
                    break;
                case OTHER:
                    p.getRWB().SendToBuffer(new Tuple<RegisterAddress,Integer>(
                            toSave.getSecond(),toSave.getThird()));
                    break;
            }
        }
    }
    public void SendToBuffer(Triple<CommandType,RegisterAddress,Integer> a){
        Buffer.addLast(a);
    }


    @Override
    public String GetStringFormatOfPipelineStage() {
        if (Buffer.size() > 0) {
            //return  Buffer.get(0).y.toString() + " -> R" + String.valueOf(Buffer.get(0).x.m_Address) ;
        }
        return "NOOP" ;
    }

    public void flush(){
        this.Buffer.clear();
    }
}
