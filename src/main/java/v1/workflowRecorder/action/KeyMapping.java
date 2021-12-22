package v1.workflowRecorder.action;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public enum KeyMapping {

    NUMBER_ONE(2, "1", KeyEvent.VK_1),
    NUMBER_TWO(3, "2", KeyEvent.VK_2),
    NUMBER_THREE(4, "3", KeyEvent.VK_3),
    NUMBER_FOUR(5, "4", KeyEvent.VK_4),
    NUMBER_FIVE(6, "5", KeyEvent.VK_5),
    NUMBER_SIX(7, "6", KeyEvent.VK_6),
    NUMBER_SEVEN(8, "7", KeyEvent.VK_7),
    NUMBER_EIGHT(9, "8", KeyEvent.VK_8),
    NUMBER_NINE(10, "9", KeyEvent.VK_9),
    NUMBER_ZERO(11, "0", KeyEvent.VK_0),

    LETTER_Q(16, "q", KeyEvent.VK_Q),
    LETTER_w(17, "w", KeyEvent.VK_W),
    LETTER_E(18, "e", KeyEvent.VK_E),
    LETTER_R(19, "r", KeyEvent.VK_R),
    LETTER_T(20, "t", KeyEvent.VK_T),
    LETTER_Y(21, "y", KeyEvent.VK_Y),
    LETTER_U(22, "u", KeyEvent.VK_U),
    LETTER_I(23, "i", KeyEvent.VK_I),
    LETTER_O(24, "o", KeyEvent.VK_O),
    LETTER_P(25, "p", KeyEvent.VK_P),
    LETTER_A(30, "a", KeyEvent.VK_A),
    LETTER_S(31, "s", KeyEvent.VK_S),
    LETTER_D(32, "d", KeyEvent.VK_D),
    LETTER_F(33, "f", KeyEvent.VK_F),
    LETTER_G(34, "g", KeyEvent.VK_G),
    LETTER_H(35, "h", KeyEvent.VK_H),
    LETTER_J(36, "j", KeyEvent.VK_F),
    LETTER_K(37, "k", KeyEvent.VK_K),
    LETTER_L(38, "l", KeyEvent.VK_L),
    LETTER_Z(44, "z", KeyEvent.VK_Z),
    LETTER_X(45, "x", KeyEvent.VK_X),
    LETTER_C(46, "c", KeyEvent.VK_C),
    LETTER_V(47, "v", KeyEvent.VK_V),
    LETTER_B(48, "b", KeyEvent.VK_B),
    LETTER_N(49, "n", KeyEvent.VK_N),
    LETTER_M(50, "m", KeyEvent.VK_M),
    SEMICOLON(39, ";", KeyEvent.VK_SEMICOLON),

    F1(59, "F1", KeyEvent.VK_F1),
    F2(60, "F2", KeyEvent.VK_F2),
    F3(61, "F3", KeyEvent.VK_F3),
    F4(62, "F4", KeyEvent.VK_F4),
    F5(63, "F5", KeyEvent.VK_F5),
    F6(64, "F6", KeyEvent.VK_F6),
    F7(65, "F7", KeyEvent.VK_F7),
    F8(66, "F8", KeyEvent.VK_F8),
    F9(67, "F9", KeyEvent.VK_F9),
    F10(68, "F10", KeyEvent.VK_F10),
    F11(87, "F11", KeyEvent.VK_F11),
    F12(88, "F12", KeyEvent.VK_F12),

    ARROW_UP(57416, "ARROW_UP", KeyEvent.VK_UP),
    ARROW_LEFT(57419, "ARROW_LEFT", KeyEvent.VK_LEFT),
    ARROW_DOWN(57424, "ARROW_DOWN", KeyEvent.VK_DOWN),
    ARROW_RIGHT(57421, "ARROW_RIGHT", KeyEvent.VK_RIGHT),

    SQUARE_BRACKET_LEFT(26, "[", KeyEvent.VK_OPEN_BRACKET),
    SQUARE_BRACKET_RIGHT(27, "]", KeyEvent.VK_CLOSE_BRACKET),
    BACK_SLASH(43, "\\", KeyEvent.VK_BACK_SLASH),
    MINUS(12, "-", KeyEvent.VK_MINUS),
    EQUALS(13, "=", KeyEvent.VK_EQUALS),
    COMMA(51, ",", KeyEvent.VK_COMMA),
    DOT(52, ".", KeyEvent.VK_PERIOD),
    SLASH(53, "/", KeyEvent.VK_SLASH),

    SHIFT(42, "SHIFT", KeyEvent.VK_SHIFT),
    CTRL(29, "CTRL", KeyEvent.VK_CONTROL),
    ALT(56, "ALT", KeyEvent.VK_ALT),
    ENTER(28, "ENTER", KeyEvent.VK_ENTER),
    SPACE(57, "SPACE", KeyEvent.VK_SPACE),

    //special for unknown keys
    ASTERISK(111, "*", KeyEvent.VK_ASTERISK);


    public int nativeHookCode;
    public String keyName;
    public int awtKeyCode;

    KeyMapping(int nativeHookCode, String keyName, int awtKeyCode) {
        this.nativeHookCode = nativeHookCode;
        this.keyName = keyName;
        this.awtKeyCode = awtKeyCode;
    }

    public static int getAwtKeyCode(int nativeHookCode) {
        return Arrays.stream(KeyMapping.values()).
                filter(v -> v.getNativeHookCode() == nativeHookCode).
                findFirst().
                map(KeyMapping::getAwtKeyCode).
                orElseGet(ASTERISK::getAwtKeyCode);
    }

    public int getNativeHookCode() {
        return nativeHookCode;
    }

    public void setNativeHookCode(int nativeHookCode) {
        this.nativeHookCode = nativeHookCode;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public int getAwtKeyCode() {
        return awtKeyCode;
    }

    public void setAwtKeyCode(int awtKeyCode) {
        this.awtKeyCode = awtKeyCode;
    }
}
