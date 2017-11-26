package isasim.commands.jcommands;

import isasim.commands.Command;
import isasim.main.Processor;
import isasim.physical.Register;

public abstract class JCommand extends Command {
    public abstract String getName() ;
    public enum Condition {
        eq,ne,cs,cc,mi,pl,vs,vc,hi,ls,ge,lt,gt,le,al
    }

    public Register getBasis() {
        return Basis;
    }

    public void setBasis(Register basis) {
        Basis = basis;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getBasis_loaded() {
        return basis_loaded;
    }

    public void setBasis_loaded(int basis_loaded) {
        this.basis_loaded = basis_loaded;
    }

    Register Basis ;
    int offset ;
    int basis_loaded ;

    Condition c ;
    public JCommand(Register q, int offset,Condition c){
        this.Basis = q ;
        this.offset = offset ;

        this.c = c ;
    }
    public void execute(Processor main){
    }
}
