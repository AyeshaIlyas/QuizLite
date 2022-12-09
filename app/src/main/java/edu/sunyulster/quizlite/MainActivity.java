package edu.sunyulster.quizlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import edu.sunyulster.quizlite.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // launch activity to start creating a new quiz/studyset
        binding.createBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });

        // launch activity that displays all the created study sets
        binding.studyBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                study();
            }
        });
    }

    public void create() {
        Intent i = new Intent(this, CreateStudySetActivity.class);
        startActivity(i);
    }

    public void study() {
        Intent i = new Intent(this, StudySetsActivity.class);
        startActivity(i);
    }
}