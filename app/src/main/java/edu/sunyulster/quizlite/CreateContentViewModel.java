package edu.sunyulster.quizlite;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;

import java.io.IOException;
import java.util.LinkedList;

public class CreateContentViewModel extends AndroidViewModel {
    private final SharedPreferences sp;
    private LinkedList<StudyContent> data;
    private final Application app;
    private final String CONTENT_KEY;
    private final String EMPTY_FLAG;
    private final StudySetsRepository repo;
    private boolean savedToDb;


    public CreateContentViewModel(Application app, SharedPreferences sp) {
        super(app);
        this.app = app;
        this.sp = sp;
        repo = new StudySetsRepository(app);
        savedToDb = false;

        CONTENT_KEY = app.getString(R.string.set_content);
        EMPTY_FLAG = app.getString(R.string.empty_flag);

        loadData();
        Log.i("CreateContentViewModel", String.valueOf(dataLength()));
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        // deserialize from shared prefs
        if (!sp.getString(CONTENT_KEY, EMPTY_FLAG).equals(EMPTY_FLAG)) {
            try {
                data = (LinkedList<StudyContent>) ObjectSerializer.deserialize(
                        sp.getString(CONTENT_KEY,
                                ObjectSerializer.serialize(new LinkedList<StudyContent>())));
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        data = new LinkedList<>();
    }

    public void saveToSharedPrefs() {
        if (!savedToDb) {
            SharedPreferences.Editor editor = sp.edit();
            try {
                editor.putString(CONTENT_KEY, ObjectSerializer.serialize(data));
                editor.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int dataLength() {
        return data.size();
    }

    public StudyContent getNthItem(int index) {
        if (index >= data.size())
            throw new IllegalArgumentException("illegal index: " + index);
        return data.get(index);
    }

    public boolean hasData() {
        return data.size() > 0;
    }
    
    public void saveDataToIndex(int index, String[] data) {
        if (index > this.data.size())
            throw new IllegalArgumentException("illegal index: " + index);
        if (index == this.data.size())
            this.data.add(new StudyContent(data[0], data[1]));
        else
            this.data.set(index, new StudyContent(data[0], data[1]));
    }

    public void addDataToDb() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(app.getString(R.string.has_saved), "false");
        editor.putString(CONTENT_KEY, EMPTY_FLAG);
        editor.apply();

        // create StudySetInfo object
        String name = app.getString(R.string.set_name);
        String topic = app.getString(R.string.set_topic);
        String desc = app.getString(R.string.desc);
        name = sp.getString(name, "");
        topic = sp.getString(topic, "");
        desc = sp.getString(desc, "");
        StudySetInfo info = new StudySetInfo(name, topic, desc, data.size());

        // create StudySet object
        StudySet studySet = new StudySet(info, data);
        // add to db
        repo.addStudySet(studySet);
        savedToDb = true;

        Log.i("CreateContentViewModel", "Added to db");
    }
}
