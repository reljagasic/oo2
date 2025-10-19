package sistem;

import java.awt.*;

public class Timer extends Thread{

    private Frame parentFrame;
    private volatile boolean running = true;
    private volatile boolean activityDetected = false;

    public Timer(Frame parentFrame){
        this.parentFrame = parentFrame;
        setName("timer");
        setDaemon(true);
    }

    public void activity(){
        activityDetected = true;
        if(isAlive()){
            interrupt();
        }
    }

    public void shutdown(){
        running = false;
        interrupt();
    }

    private boolean mySleep(int s) throws InterruptedException{
        long endTime = System.currentTimeMillis() + s * 1000L;

        while(running && System.currentTimeMillis() < endTime){
            if(activityDetected){
                activityDetected = false;
                return true;
            }
            long remaining = endTime - System.currentTimeMillis();
        }
        return false;
    }

    @Override
    public void run(){
        while(!isInterrupted()){
            try {
                boolean shouldReset = mySleep(55);
                if(!running){
                    break;
                }
                if(shouldReset){
                    continue;
                }
                // new dialog
                new ErrorDialog(parentFrame, "Program ce se ugasiti za 5 sekundi.", this);
                shouldReset = mySleep(5);
                if(!running){
                    break;
                }
                if(shouldReset){
                    continue;
                }
                System.exit(0);
                break;
            } catch (InterruptedException e) {

            }
        }
    }


}
