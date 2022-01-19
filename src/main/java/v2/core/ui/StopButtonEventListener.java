package v2.core.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import v2.core.exception.GenericException;
import v2.core.workflow.WorkflowService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopButtonEventListener extends JFrame implements ActionListener {

    private final WorkflowService workflowService;

    public StopButtonEventListener(WorkflowService workflowService){
        this.workflowService = workflowService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            workflowService.stopWorkflowRecording();
        } catch (GenericException genericException) {
            genericException.printStackTrace();
        }
    }
}
