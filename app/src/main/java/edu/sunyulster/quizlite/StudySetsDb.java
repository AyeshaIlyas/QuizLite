package edu.sunyulster.quizlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {StudySetInfo.class, StudyContent.class}, version = 1, exportSchema = false)
public abstract class StudySetsDb extends RoomDatabase {

    public abstract StudySetsDao studySetsDao();
    private static StudySetsDb INSTANCE;

    public static StudySetsDb getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (StudySetsDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    StudySetsDb.class,
                                    "study_sets_database")
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }

        return INSTANCE;
    }
}
