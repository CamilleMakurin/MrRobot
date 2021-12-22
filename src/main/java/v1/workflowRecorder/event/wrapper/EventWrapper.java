package v1.workflowRecorder.event.wrapper;

import v1.workflowRecorder.event.EventType;

public interface EventWrapper <T>{
    long getWhen();
    void setWhen(long when);
    EventType getType();
    T getNativeEvent();
}
