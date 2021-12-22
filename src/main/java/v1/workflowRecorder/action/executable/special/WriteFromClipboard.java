package v1.workflowRecorder.action.executable.special;

import lombok.Getter;
import lombok.Setter;
import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.constant.ActionAttribute;
import v1.workflowRecorder.context.WorkflowContext;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Map;

@Getter
@Setter
public class WriteFromClipboard implements ExecutableAction {

    private String name;
    private Map<ActionAttribute, String> attributes;

    public WriteFromClipboard() {
    }

    public WriteFromClipboard(String name, Map<ActionAttribute, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    @Override
    public void execute(Robot robot) {
        try {

            // Create a Clipboard object using getSystemClipboard() method
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            // Get data stored in the clipboard that is in the form of a string (text)
            String data = (String) c.getData(DataFlavor.stringFlavor);
            System.out.println("got data from clipboard: " + data);
            WorkflowContext.setAttribute(name, data);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
