package edu.sunyulster.quizlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import edu.sunyulster.quizlite.databinding.ActivityStudySetsBinding;

public class StudySetsActivity extends AppCompatActivity implements StudySetsAdapter.OnItemClickedListener {

    private ActivityStudySetsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudySetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        StudySetsViewModel vm = new ViewModelProvider(this).get(StudySetsViewModel.class);
        final StudySetsAdapter adapter = new StudySetsAdapter(this);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));

        vm.getAllStudySetInfo().observe(this, info -> {
            // Update the cached copy of the words in the adapter.
            adapter.setData(info);
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
    
    @Override
    public void onItemClicked(long setId) {
        Intent i = new Intent(this, StudyCardsActivity.class);
        i.putExtra("studySetId", setId);
        startActivity(i);
    }
}
