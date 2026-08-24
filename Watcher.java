public class Watcher extends Thread{
    private Thread[] threads;

    Watcher(Thread[] ts){
        this.threads = ts;
    }

    @Override
    public void run(){
        while (true) {
            try {
                for(int i=0;i<threads.length;i++){
                    String state = threads[i].getState().toString();
                    String name = threads[i].getName();
                    System.out.println("Thread: " + name + "; State: " + state);
                }
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println(e);            
            }
        }
    }
}