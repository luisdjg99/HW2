package Servants;

public class Servant extends Thread{
    int delay;

    public Servant(){
        this.delay = 500;
    }

    @Override
    public void run(){

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.err.println("Failure in execute the sleep operation on the ThreadsLauncher, generated Exception:");
                e.printStackTrace();
            }
            Servant.run();


            try {
                Servant.join();
            } catch (InterruptedException e) {
                System.err.println("Failure in execute join on a list internal Thread, generated Exception:");
                e.printStackTrace();
            }

    }
    }

}
