package v2.core.constant;


public enum MouseButton {
    RIGHT, LEFT;

    public static MouseButton getButton(int buttonNumber) {
        if (buttonNumber == 2) {
            return RIGHT;
        } else if (buttonNumber == 1) {
            return LEFT;
        } else {
            throw new UnsupportedOperationException("Unknown button number: " + buttonNumber);
        }
    }
}
