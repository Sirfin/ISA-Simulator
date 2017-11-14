package isasim.main;

public class Ticker implements Runnable{
    Processor proc ;
    public Ticker(Processor proc){
        super() ;
        this.proc = proc ;
    }
    @Override
    public void run() {
        while (!Thread.interrupted()) {

            System.out.println("Tick");
            try {
                Thread.sleep(3000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            proc.OnTick();
        }

    }
}
