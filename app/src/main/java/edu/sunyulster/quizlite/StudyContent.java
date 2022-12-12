package edu.sunyulster.quizlite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "study_content",
        foreignKeys = {@ForeignKey(entity = StudySetInfo.class,
        parentColumns = "id",
        childColumns = "set_id",
        onDelete = ForeignKey.CASCADE)
})
public class StudyContent implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "set_id")
    private long studySetId;

    private String key;
    private String value;

    public StudyContent(String key, String value) {
        if (key == null || value == null || key.trim().isEmpty() || value.trim().isEmpty())
            throw new IllegalArgumentException("key and value must be nonnull and contain at least one non whitespace character");
        this.key = key.trim();
        this.value = value.trim();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudySetId() {
        return studySetId;
    }

    public void setStudySetId(long setId) {
        this.studySetId = setId;
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
