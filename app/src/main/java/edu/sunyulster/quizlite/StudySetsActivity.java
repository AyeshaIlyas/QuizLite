package edu.sunyulster.quizlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.sunyulster.quizlite.databinding.ActivityStudySetsBinding;

public class StudySetsActivity extends AppCompatActivity {

    private ActivityStudySetsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudySetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}