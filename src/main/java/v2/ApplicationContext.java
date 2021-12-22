package v2;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class ApplicationContext {

    private static ApplicationContext context;
    private boolean isRecording = false;
    private boolean isExecuting = false;
    private boolean isNewWorkflow = true;
    private JTextField textField;

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
}
