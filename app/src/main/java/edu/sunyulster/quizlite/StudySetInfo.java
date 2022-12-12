package edu.sunyulster.quizlite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity(tableName = "study_sets")
public class StudySetInfo {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String topic;
    private String desc;
    private String date;

    @ColumnInfo(name = "number_of_cards")
    private int numberOfCards;

    public StudySetInfo(String name, String topic, String desc, int numberOfCards) {
        this.name = name;
        this.topic = topic;
        this.desc = desc;
        date = new SimpleDateFormat("EEE, MMM d, yyyy").format(Calendar.getInstance().getTime());
        this.numberOfCards = numberOfCards;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
}