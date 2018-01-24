package isasim.main;

/**
 * Der Ticker der die Frequenz des Simulators bestimmt.
 * @author Fin Töter
 */
public class Ticker implements Runnable{
    Processor proc ;
    int frequency = 3000; //Default Frequenz
    boolean halted = true ;

    /**
     * Initialisiert den Ticker mit der Referenz auf den zu tickenden Prozessor
     * @see Processor
     * @param proc
     */
    public Ticker(Processor proc){
        super() ;
        this.proc = proc ;
    }

    /**
     * Setzt die Frequenz des Prozessors
     * @param frequency Zu setzende Frequenz
     */
    public void setFrequency(int frequency){
        this.frequency = frequency ;
    }

    /**
     * Stoppt den Prozess
     */
    public void stop(){
        this.halted = true ;
    }

    /**
     * Startet ihn neu
     */
    public void start(){
        this.halted = false ;
    }

    @Override
    public void run() {
        while (!halted) {
            proc.OnTick() ;
            try {
                Thread.sleep(frequency);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
