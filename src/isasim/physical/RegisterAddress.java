package isasim.physical;

import isasim.main.Processor;

public class RegisterAddress {
    public int m_Address ;
    private Processor p ;
    public RegisterAddress(int a){
        m_Address = a ;
        //this.p = p ;
    }
    /*
    public void writeToAdress(int a){
        p.Registerbank.get(m_Address).save(a);
    }
    public int readFromAdress(){
        return p.Registerbank.get(m_Address).load() ;
    }*/

}
