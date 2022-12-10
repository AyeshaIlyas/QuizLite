package edu.sunyulster.quizlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.sunyulster.quizlite.databinding.ActivityCreateContentBinding;

public class CreateContentActivity extends AppCompatActivity {

    private ActivityCreateContentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}