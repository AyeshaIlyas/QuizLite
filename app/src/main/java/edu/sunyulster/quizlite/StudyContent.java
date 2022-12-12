package edu.sunyulster.quizlite;

import androidx.annotation.NonNull;
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

    @NonNull
    private String key;
    @NonNull
    private String value;

    public StudyContent(@NonNull String key, @NonNull String value) {
        if (key.trim().isEmpty() || value.trim().isEmpty())
            throw new IllegalArgumentException("key and value must contain at least one non whitespace character");
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

    public @NonNull String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        if (key.trim().isEmpty())
            throw new IllegalArgumentException("key must contain at least one non whitespace character");
        this.key = key.trim();
    }

    public @NonNull String getValue() {
        return value;
    }

    public void setValue(@NonNull String value) {
        if (value.trim().isEmpty())
            throw new IllegalArgumentException("value must contain at least one non whitespace character");
        this.value = value.trim();
    }

}
