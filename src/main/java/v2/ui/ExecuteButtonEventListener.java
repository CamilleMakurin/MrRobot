package v2.ui;

import v2.workflow.WorkflowService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExecuteButtonEventListener extends JFrame implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        WorkflowService.getInstance().executeWorkflowRecording();
    }
}
