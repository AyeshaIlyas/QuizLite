package edu.sunyulster.quizlite;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StudySet {
    @NonNull
    @Embedded
    private final StudySetInfo info;

    @NonNull
    @Relation(
            parentColumn = "id",
            entityColumn = "set_id"
    )
    private final List<StudyContent> content;

    public StudySet(@NonNull StudySetInfo info, @NonNull List<StudyContent> content) {
        this.info = info;
        this.content = content;
    }

    public @NonNull StudySetInfo getInfo() {
        return info;
    }

    public @NonNull List<StudyContent> getContent() {
        return content;
    }
}
