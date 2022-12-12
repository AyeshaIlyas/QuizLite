package edu.sunyulster.quizlite;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class StudySetsViewModel extends AndroidViewModel {
    private StudySetsRepository repo;
    private LiveData<List<StudySetInfo>> info;
    private MutableLiveData<StudySet> studySet;

    public StudySetsViewModel(Application app) {
        super(app);
        repo = new StudySetsRepository(app);
        info = repo.getStudySets();
        studySet = repo.getStudySet();
    }

    public LiveData<List<StudySetInfo>> getStudySets() {
        return info;
    }

    public MutableLiveData<StudySet> getStudySet() {
        return studySet;
    }

    public void addStudySet(StudySet set) {
        repo.addStudySet(set);
    }

    public void getContent(long id) {
        repo.getContent(id);
    }


}
