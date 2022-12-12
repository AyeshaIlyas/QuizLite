package edu.sunyulster.quizlite;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StudySet {
    @Embedded
    private StudySetInfo info;
    @Relation(
            parentColumn = "id",
            entityColumn = "set_id"
    )
    private List<StudyContent> content;

    public StudySet(StudySetInfo info, List<StudyContent> content) {
        this.info = info;
        this.content = content;
    }

    public StudySetInfo getInfo() {
        return info;
    }

    public List<StudyContent> getContent() {
        return content;
    }
}
