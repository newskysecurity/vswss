package org.w3c.connectedcars;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple demo that uses java.util.Timer to schedule a task 
 * to execute once x seconds have passed.
 */

public class Reminder {
    Timer timer;
    ContinuousTask task;
    
    public Reminder(int seconds, ContinuousTask task) {
        timer = new Timer();
        timer.schedule(new RemindTask(), 100, seconds*1000);
        this.task = task;
	}

    public void cancel() {
    	timer.cancel();
    }
    
    class RemindTask extends TimerTask {
        public void run() {
        	if (task != null) {
        		task.remind();
        	}
        }
    }

}