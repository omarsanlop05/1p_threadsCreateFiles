import java.util.List;

public class Watcher extends Thread{
    private List<Thread> threads;

    Watcher(List<Thread> ts){
        this.threads = ts;
    }

    @Override
    public void run(){
        while (true) {
            try {
                boolean terminado = true;

                for(Thread hilo : threads){
                    String state = hilo.getState().toString();
                    String name = hilo.getName();
                    System.out.println("Thread: " + name + "; State: " + state);

                    if (hilo.getState() != Thread.State.TERMINATED) {
                        terminado = false;
                    }
                }

                if(terminado){
                    System.out.println("Todos los hilos terminados, cerrando watcher");
                    break;
                }
                Thread.sleep(200);


            } catch (InterruptedException e) {
                System.out.println(e);            
            }
        }
    }
}