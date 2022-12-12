package edu.sunyulster.quizlite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudySetsRepository {

    private StudySetsDao dao;
    private LiveData<List<StudySetInfo>> studySets;
    private MutableLiveData<StudySet> studySet = new MutableLiveData<>();

    public StudySetsRepository(Application application) {
        StudySetsDb db = StudySetsDb.getInstance(application);
        dao = db.studySetsDao();
        studySets = dao.getStudySets();
    }

    public LiveData<List<StudySetInfo>> getStudySets() {
        return studySets;
    }

    public MutableLiveData<StudySet> getStudySet() {
        return studySet;
    }

    public void addStudySet(StudySet studySet) {
        new AddStudySetAsyncTask(dao).execute(studySet);
    }

    public void getContent(long setId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                studySet.postValue(dao.getContent(setId));
            }
        });
        executor.shutdown();
    }

    private static class AddStudySetAsyncTask extends AsyncTask<StudySet, Void, Void> {
        private StudySetsDao dao;

        public AddStudySetAsyncTask(StudySetsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(final StudySet... studySet) {
            StudySet set = studySet[0];

            // add set info to db and get id
            long id = dao.addStudySetInfo(set.getInfo());

            // assign set id to each StudyContent obj (foreign key)
            List<StudyContent> content = set.getContent();
            for (StudyContent c: content)
                c.setStudySetId(id);
            dao.addStudyContent(content);
            return null;
        }
    }

}
