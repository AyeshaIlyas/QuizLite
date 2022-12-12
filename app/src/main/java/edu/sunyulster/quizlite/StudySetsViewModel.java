package edu.sunyulster.quizlite;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class StudySetsViewModel extends AndroidViewModel {
    private final StudySetsRepository repo;
    private final LiveData<List<StudySetInfo>> info;
    private final MutableLiveData<StudySet> studySet;

    public StudySetsViewModel(Application app) {
        super(app);
        repo = new StudySetsRepository(app);
        info = repo.getAllStudySetInfo();
        studySet = repo.getStudySet();
    }

    public LiveData<List<StudySetInfo>> getAllStudySetInfo() {
        return info;
    }

    public MutableLiveData<StudySet> getStudySet() {
        return studySet;
    }

    public void getContentForSet(long id) {
        repo.getContentForSet(id);
    }


}
