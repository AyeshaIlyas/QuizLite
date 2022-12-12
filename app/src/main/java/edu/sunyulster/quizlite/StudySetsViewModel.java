package edu.sunyulster.quizlite;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class StudySetsViewModel extends AndroidViewModel {
    private StudySetsRepository repo;
    private LiveData<List<StudySetInfo>> info;

    public StudySetsViewModel(Application app) {
        super(app);
        repo = new StudySetsRepository(app);
        info = repo.getStudySets();
    }

    public LiveData<List<StudySetInfo>> getStudySets() {
        return info;
    }

    public void addStudySet(StudySet set) {
        repo.addStudySet(set);
    }

    public StudySet getContent(long id) {
        return repo.getContent(id);
    }


}
