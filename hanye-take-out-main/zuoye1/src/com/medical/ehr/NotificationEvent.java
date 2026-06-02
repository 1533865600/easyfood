package com.medical.ehr;

/**
 * 病历操作通知事件
 */
public class NotificationEvent {
    private String source;
    private String message;
    private EHRRecord ehrRecord;

    public NotificationEvent(String source, String message, EHRRecord ehrRecord) {
        this.source = source;
        this.message = message;
        this.ehrRecord = ehrRecord;
    }

    public String getSource() { return source; }
    public String getMessage() { return message; }
    public EHRRecord getEhrRecord() { return ehrRecord; }
}