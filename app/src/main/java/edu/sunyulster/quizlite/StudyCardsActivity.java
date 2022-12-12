package edu.sunyulster.quizlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import edu.sunyulster.quizlite.databinding.ActivityStudyCardsBinding;

public class StudyCardsActivity extends AppCompatActivity {

    ActivityStudyCardsBinding binding;
    StudySetsViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyCardsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = getIntent();
        long id = i.getLongExtra("studySetId", 1);

        // set adapter and layout manager for rv
        final StudyCardsAdapter adapter = new StudyCardsAdapter(this);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));

        // get card data from db using vm
        vm = new ViewModelProvider(this).get(StudySetsViewModel.class);
        vm.getStudySet().observe(this, new Observer<StudySet>() {
            @Override
            public void onChanged(StudySet studySet) {
                adapter.setData(studySet);
                binding.nameTitle.setText(studySet.getInfo().getName());
                binding.cardsInSet.setText(String.valueOf(studySet.getInfo().getNumberOfCards()));
            }
        });
        vm.getContent(id);
    }
}