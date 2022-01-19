package v2.core.action.domain.special;


import v2.core.constant.ActionAttribute;
import v2.core.constant.KeyMapping;
import v2.core.log.Log;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Map;


/**
 * Special action to copy string value from workflow configuration, put it into a clipboard
 * and paste via pressing CTRL+V
 */
public class PasteActionExecutor implements SpecialActionExecutor {
    @Override
    public void execute(Robot r, Map<ActionAttribute, String> attributes) {
        String valuesToPaste = attributes.get(ActionAttribute.VALUE_TO_PASTE);
        StringSelection selection = new StringSelection(valuesToPaste);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        Log.debug("copied to clipboard: " + valuesToPaste);

        //paste content
        r.keyPress(KeyMapping.CTRL.getNativeHookCode());
        r.delay(5);
        r.keyPress(KeyMapping.LETTER_V.getNativeHookCode());
        r.delay(5);

        r.keyRelease(KeyMapping.CTRL.getNativeHookCode());
        r.delay(5);
        r.keyRelease(KeyMapping.LETTER_V.getNativeHookCode());
        r.delay(5);
    }

}
