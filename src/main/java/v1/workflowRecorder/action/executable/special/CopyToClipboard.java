package v1.workflowRecorder.action.executable.special;

import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.configuration.ConfigService;
import v1.workflowRecorder.constant.ActionAttribute;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class CopyToClipboard implements ExecutableAction {

    private String name;
    private Map<ActionAttribute, String> attributes;

    public CopyToClipboard() {
    }

    public CopyToClipboard(String name, Map<ActionAttribute, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    @Override
    public void execute(Robot robot) {
        try {
            copyToClipboard();
        } catch (IllegalStateException e) {
            try {
                Thread.sleep(5000);
                copyToClipboard();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void copyToClipboard() {
        Optional<String> configValueOpt = ConfigService.getConfigValue(name);
        String valueToCopy = configValueOpt.orElse(attributes.get(ActionAttribute.VALUE_TO_COPY));
        StringSelection selection = new StringSelection(valueToCopy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);

        System.out.println("copied to clipboard: " + valueToCopy);
    }


}
