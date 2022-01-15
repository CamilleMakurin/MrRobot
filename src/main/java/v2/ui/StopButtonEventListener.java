package v2.ui;

import v2.exception.GenericException;
import v2.workflow.WorkflowService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopButtonEventListener extends JFrame implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            WorkflowService.getInstance().stopWorkflowRecording();
        } catch (GenericException genericException) {
            genericException.printStackTrace();
        }
    }
}
