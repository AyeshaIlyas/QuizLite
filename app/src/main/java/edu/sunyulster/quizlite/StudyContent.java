package edu.sunyulster.quizlite;

import java.io.Serializable;

public class StudyContent implements Serializable {

    private String key;
    private String value;

    public StudyContent(String key, String value) {
        if (key == null || value == null || key.trim().isEmpty() || value.trim().isEmpty())
            throw new IllegalArgumentException("key and value must be nonnull and contain at least one non whitespace character");
        this.key = key.trim();
        this.value = value.trim();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (key == null || key.trim().isEmpty())
            throw new IllegalArgumentException("key must be nonnull and contain at least one non whitespace character");
        this.key = key.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value == null || value.trim().isEmpty())
            throw new IllegalArgumentException("value must be nonnull and contain at least one non whitespace character");
        this.value = value.trim();
    }

    @Override
    public String toString() {
        return String.format("Key: %s%nValue: %s", key, value);
    }

}
