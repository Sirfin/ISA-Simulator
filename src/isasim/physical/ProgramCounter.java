package isasim.physical;

import isasim.main.Processor;

public class ProgramCounter {
    private Processor processor ;
    public ProgramCounter(Processor processor){
        this.processor = processor ;
        value = 0 ;
    }
    private int value ;
    public int getCount(){
        return value ;
    }
    public void setCount(int count){
        value = count ;
    }
    public void increment(){
        value++ ;
        if (value >= processor.ROM_SIZE_WORDS){
            value = 0;
        }
    }
    public void reset(){
        value = 0 ;
    }
}
