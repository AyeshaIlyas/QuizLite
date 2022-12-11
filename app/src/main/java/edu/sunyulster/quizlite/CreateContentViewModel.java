package edu.sunyulster.quizlite;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CreateContentViewModel extends AndroidViewModel {
    private SharedPreferences sp;
    private LinkedList<StudyContent> data;
    private Application app;
    private final String CONTENT_KEY;
    private final String EMPTY_FLAG;


    public CreateContentViewModel(Application app, SharedPreferences sp) {
        super(app);
        this.app = app;
        this.sp = sp;

        CONTENT_KEY = app.getString(R.string.set_content);
        EMPTY_FLAG = app.getString(R.string.empty_flag);

        loadData();
    }

    private void loadData() {
        // deserialize from shared prefs
        if (!sp.getString(CONTENT_KEY, EMPTY_FLAG).equals(EMPTY_FLAG)) {
            try {
                data = (LinkedList<StudyContent>) ObjectSerializer.deserialize(
                        sp.getString(CONTENT_KEY,
                                ObjectSerializer.serialize(new LinkedList<StudyContent>())));
                return;
            } catch (IOException e) {}
        }
        data = new LinkedList<StudyContent>();
    }

    public void saveToSharedPrefs() {
        SharedPreferences.Editor editor = sp.edit();
        try {
            editor.putString(CONTENT_KEY, ObjectSerializer.serialize(data));
            editor.commit();
        } catch (IOException e) {
            Log.i("CreateContentViewModel", "Unable to save data to shared prefs");
            e.printStackTrace();
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
        editor.putString(CONTENT_KEY, EMPTY_FLAG);
        editor.commit();
        Log.i("CreateContentViewModel", "Added to db. heehehe not really. All that work was for naught!!! Mwahahaha");
    }
}
