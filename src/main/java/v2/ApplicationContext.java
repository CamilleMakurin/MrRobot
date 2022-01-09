package v2;

import lombok.Getter;
import lombok.Setter;
import v2.log.Log;
import v2.workflow.Timer;

import javax.swing.*;

@Getter
@Setter
public class ApplicationContext {

    private static ApplicationContext context;
    private boolean isRecording = false;
    private boolean isExecuting = false;
    private boolean isNewWorkflow = true;
    private boolean firstClickKeyPressMade = false;

    private JTextField textField;

    private Timer timer;

    private ApplicationContext() {
        this.timer = new Timer();
    }

    public static ApplicationContext getContext() {
        if (context == null) {
            context = new ApplicationContext();
        }
        return context;
    }

    public String getWorkflowName() {
        if (textField == null) {
            return "output.txt";
        }
        String outputileName = textField.getText();
        return outputileName == null || outputileName.equals("") ? "output.txt" : outputileName + ".txt";
    }

    public void trackFirstRecordableAction() {
        this.firstClickKeyPressMade = true;
        if (timer.isStarted()) {
            Log.info("Timer started. Resuming timer...");
            timer.resume();
        } else {
            Log.info("Timer NOT started. Starting timer...");
            timer.start();
        }
    }

    public void startRecording() {
        this.isRecording = true;
    }

    public void stopRecording() {
        this.isRecording = false;
        timer.stop();
    }

    public void pauseRecording() {
        this.isRecording = false;
        this.firstClickKeyPressMade = false;
        if (timer.isStarted()) {
            timer.pause();
        } else {
            throw new IllegalStateException("Timer is not started");
        }
    }

    public long getDelay() {
        return this.timer.doDelta();
    }
}
