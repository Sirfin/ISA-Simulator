package isasim.physical;

/**
 * Created by ftoet on 19.11.2017.
 */
public class Flags {
    public boolean isN() {
        return N;
    }

    public void setN(boolean n) {
        N = n;
    }

    public boolean isZ() {
        return Z;
    }

    public void setZ(boolean z) {
        Z = z;
    }

    public boolean isC() {
        return C;
    }

    public void setC(boolean c) {
        C = c;
    }

    public boolean isO() {
        return O;
    }

    public void setO(boolean o) {
        O = o;
    }
    public void clear(){
        N = false ;
        Z = false ;
        C = false ;
        O = false ;
    }
    boolean N ;
    boolean Z ;
    boolean C ;
    boolean O ;

    public Flags() {
    }
}
