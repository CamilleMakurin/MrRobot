package v2.core.workflow;

import org.apache.commons.lang3.time.StopWatch;
import v2.core.log.Log;

public class Timer {

    private StopWatch watch;

    private long previousTime;

    public void start() {
        Log.info("Starting watch");
        this.watch = new StopWatch();
        this.watch.start();
    }

    public boolean isStarted() {
        return this.watch != null;
    }

    public void resume() {
        Log.info("Resuming watch");
        watch.resume();
    }

    public void pause() {
        Log.info("Suspending watch");
        watch.suspend();
    }

    public void stop() {
        Log.info("Stoping watch");
        watch.stop();
    }

    public long doDelta() {


        if (previousTime != 0) {
            long delay = watch.getTime() - previousTime;
            previousTime = watch.getTime();
            return delay;

        }
        previousTime = watch.getTime();
        return 0;
    }

    public long getTime() {
        return watch.getTime();
    }
}
