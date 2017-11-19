package isasim.main;

public class Ticker implements Runnable{
    Processor proc ;
    int frequency = 3000;
    public Ticker(Processor proc){
        super() ;
        this.proc = proc ;
    }
    public void setFrequency(int frequency){
        this.frequency = frequency ;
    }
    @Override
    public void run() {
        while (!Thread.interrupted()) {

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
