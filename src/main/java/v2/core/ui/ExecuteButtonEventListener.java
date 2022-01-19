package v2.core.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import v2.core.workflow.WorkflowService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExecuteButtonEventListener extends JFrame implements ActionListener {

    private final WorkflowService workflowService;

    public ExecuteButtonEventListener(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        workflowService.executeWorkflowRecording();
    }
}
