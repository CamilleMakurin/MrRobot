package v1.workflowRecorder.action.nativeaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopyToClipboardAction implements Action {
//{"textToCopy":"fromClipBoard"}

    private String textToCopy;

    public CopyToClipboardAction() {
    }

    public CopyToClipboardAction(String textCopied) {
        this.textToCopy = textCopied;
    }

}
