package edu.sunyulster.quizlite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface StudySetsDao {
    @Query("SELECT * FROM study_sets")
    LiveData<List<StudySetInfo>> getStudySets();

    @Insert
    long addStudySetInfo(StudySetInfo info);

    @Insert
    void addStudyContent(List<StudyContent> content);

    @Transaction
    @Query("SELECT * FROM study_sets " +
            "WHERE study_sets.id = :setId")
    StudySet getContent(long setId);
}
