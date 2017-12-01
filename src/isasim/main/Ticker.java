package isasim.main;

public class Ticker implements Runnable{
    Processor proc ;
    int frequency = 3000;
    boolean halted = true ;
    public Ticker(Processor proc){
        super() ;
        this.proc = proc ;
    }
    public void setFrequency(int frequency){
        this.frequency = frequency ;
    }
    public void stop(){
        this.halted = true ;
    }
    public void start(){
        this.halted = false ;
    }
    @Override
    public void run() {
        while (!halted) {
            System.out.println("Tick");
            try {
                Thread.sleep(frequency);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            proc.OnTick();
        }

    }
}
