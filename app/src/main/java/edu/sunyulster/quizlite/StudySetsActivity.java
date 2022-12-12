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
    private StudySetsViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudySetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(StudySetsViewModel.class);
        final StudySetsAdapter adapter = new StudySetsAdapter(this);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));

        vm.getStudySets().observe(this, new Observer<List<StudySetInfo>>() {
            @Override
            public void onChanged(@Nullable final List<StudySetInfo> info) {
                // Update the cached copy of the words in the adapter.
                adapter.setData(info);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
    
    @Override
    public void onItemClicked(int setId) {
        Intent i = new Intent(this, StudyCardsActivity.class);
        i.putExtra("studySetId", setId);
        startActivity(i);
    }
}
