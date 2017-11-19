package isasim.physical;


import isasim.helper.BitSetHelper;

import java.util.BitSet;

public class Register {
    private Integer memory ;

    private RegisterAddress address ;
    public Register(int size,int address){
        memory = 0 ;
        this.address = new RegisterAddress(address) ;
    }

    public RegisterAddress getAddress() {
        return address;
    }


    public void save(int ToSave){
        this.memory = ToSave ;
    }
    public int load(){
        return this.memory;
    }

    public int getMemory(){
        return memory ;
    }



}
