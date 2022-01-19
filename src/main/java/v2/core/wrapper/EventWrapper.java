package v2.core.wrapper;


public interface EventWrapper <T>{

    long getWhen();
    long getDelay();
    void setWhen(long when);
    EventType getType();
    T getNativeEvent();

    void setIsLastBeforePause(boolean value);
    boolean isLastBeforePause();


}
