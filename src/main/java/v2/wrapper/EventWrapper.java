package v2.wrapper;


public interface EventWrapper <T>{
    long getWhen();
    void setWhen(long when);
    EventType getType();
    T getNativeEvent();
}
