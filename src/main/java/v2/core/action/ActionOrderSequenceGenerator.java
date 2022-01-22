package v2.core.action;

public class ActionOrderSequenceGenerator {

    public static final int INCREMENT_STEP = 10;
    public static final int SPECIAL_INCREMENT_STEP = 1;
    private static int current = 0;
    //separate counter for special action names
    private static int currentSpecial = 0;

    public static int getNext() {
        int returnValue = current;
        current = current + INCREMENT_STEP;
        return returnValue;
    }

    public static int getNextSpecial() {
        int returnValue = currentSpecial;
        currentSpecial = currentSpecial + SPECIAL_INCREMENT_STEP;
        return returnValue;
    }

    public static void resetAllSequences(){
        resetSequence();
        resetSpecialSequence();
    }

    public static void resetSequence() {
        current = 0;
    }

    public static void resetSpecialSequence() {
        currentSpecial = 0;
    }

    public static void setCurrent(int lastOrder) {
        current = lastOrder + INCREMENT_STEP;
    }

    public static void setCurrentSpecial(int lastOrder) {
        currentSpecial = lastOrder + INCREMENT_STEP;
    }
}
