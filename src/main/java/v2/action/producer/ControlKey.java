package v2.action.producer;

import v2.action.SpecialActionType;

public enum ControlKey {

    START_PAUSE_RECORDING(1, 2),
    SAVE_WORKFLOW(2, 3),
    EXECUTE_WORKFLOW(3, 4),
    SPEC_SLEEP(4, 5, SpecialActionType.SLEEP),
    SPEC_COPY(5, 6, SpecialActionType.COPY_TO_CLIPBOARD),
    SPEC_PASTE(6, 7, SpecialActionType.PASTE_FROM_CLIPBOARD),
    SPEC_EXECUTE_FILE(7, 8, SpecialActionType.EXECUTE_FILE);

    ControlKey(int actualKey, int keyCode) {
        this.actualKey = actualKey;
        this.keyCode = keyCode;
    }

    ControlKey(int actualKey, int keyCode, SpecialActionType type) {
        this.actualKey = actualKey;
        this.keyCode = keyCode;
        this.type = type;
    }

    //actual key on keyboard
    private int actualKey;
    private int keyCode;
    private SpecialActionType type;

    public static boolean isRecordingControlKey(int keyCode) {
        return START_PAUSE_RECORDING.getKeyCode() == keyCode ||
                SAVE_WORKFLOW.getKeyCode() == keyCode ||
                EXECUTE_WORKFLOW.getKeyCode() == keyCode;
    }


    public static boolean isControlKey(int keyCode) {
        for (ControlKey value : ControlKey.values()) {
            if (value.getKeyCode() == keyCode) {
                return true;
            }
        }
        return false;
    }

    public static ControlKey valueFromInt(int keyCode) {
        for (ControlKey value : ControlKey.values()) {
            if (value.keyCode == keyCode) {
                return value;
            }
        }
        return null;
    }

    public int getActualKey() {
        return actualKey;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public SpecialActionType getType() {
        return type;
    }
}
