package v2.action.producer;

public enum ControlKey {

    START_PAUSE_RECORDING(1, 2),
    SAVE_WORKFLOW(2, 3),
    EXECUTE_WORKFLOW(3, 4),
    INSERT_SPECIAL_ACTION(4, 5);

    ControlKey(int actualKey, int keyCode) {
        this.actualKey = actualKey;
        this.keyCode = keyCode;
    }

    //actual key on keyboard
    private int actualKey;
    private int keyCode;

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
}
