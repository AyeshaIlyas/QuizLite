package edu.sunyulster.quizlite;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity(tableName = "study_sets")
public class StudySetInfo {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private String topic;

    @NonNull
    private String desc;

    @NonNull
    private String date;

    @ColumnInfo(name = "number_of_cards")
    private int numberOfCards;

    public StudySetInfo(@NonNull String name, @NonNull String topic, @NonNull String desc, int numberOfCards) {
        if (name.trim().isEmpty() || topic.trim().isEmpty())
            throw new IllegalArgumentException("name and topic must both contain at least one non whitespace character");
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

    public @NonNull String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        if (name.trim().isEmpty())
            throw new IllegalArgumentException("name must contain at least one non whitespace character");
        this.name = name;
    }

    public @NonNull String getTopic() {
        return topic;
    }

    public void setTopic(@NonNull String topic) {
        if (topic.trim().isEmpty())
            throw new IllegalArgumentException("topic must contain at least one non whitespace character");
        this.topic = topic;
    }

    public @NonNull String getDesc() {
        return desc;
    }

    public void setDesc(@NonNull String desc) {
        this.desc = desc;
    }

    public @NonNull String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
}