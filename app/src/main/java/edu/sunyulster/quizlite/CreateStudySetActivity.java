package edu.sunyulster.quizlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import edu.sunyulster.quizlite.databinding.ActivityCreateStudySetBinding;

public class CreateStudySetActivity extends AppCompatActivity {

    private ActivityCreateStudySetBinding binding;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateStudySetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // if data cached in shared preferences, load data into form
        sp = getSharedPreferences(getString(R.string.saved_study_set), MODE_PRIVATE);
        String saved = getString(R.string.has_saved);
        if (sp.contains(saved) && sp.getString(saved, "").equals("true"))
            fillFormValues();

        // listeners
        binding.eraseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showEraseDataDialog();
            }
        });

        binding.nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = validateData();
                if (isValid) {
                    binding.error.setVisibility(View.GONE);
                    saveData();
                    Intent i = new Intent(CreateStudySetActivity.this, CreateContentActivity.class);
                    startActivity(i);
                } else {
                    binding.error.setVisibility(View.VISIBLE);
                    binding.error.setText(R.string.error1);
                }
            }
        });
    }

    public void setFields(String name, String topic, String desc) {
        binding.nameField.setText(name);
        binding.topicField.setText(topic);
        binding.descField.setText(desc);
    }

    public void clearFields() {
        setFields("", "", "");
    }

    public void fillFormValues() {
        setFields(sp.getString(getString(R.string.set_name), ""),
                sp.getString(getString(R.string.set_topic), ""),
                sp.getString(getString(R.string.desc), ""));
    }

    public String[] getFieldData() {
        // name, topic, and desc at index 0, 1, 2 respectively
        String[] data = new String[3];
        data[0] = binding.nameField.getText().toString();
        data[1] = binding.topicField.getText().toString();
        data[2] = binding.descField.getText().toString();
        return data;
    }

    public void eraseData() {
        invalidateSavedData();
        clearFields();
    }

    public void invalidateSavedData() {
        // set saved flag to false
        SharedPreferences.Editor editor = sp.edit();
        // reset set info
        editor.putString(getString(R.string.set_name), "");
        editor.putString(getString(R.string.set_topic), "");
        editor.putString(getString(R.string.desc), "");
        // reset content info
        // TODO: How should this be saved
        editor.putString(getString(R.string.set_content), getString(R.string.empty_flag));

        editor.putString(getString(R.string.has_saved), "false");
        editor.commit();
    }

    public void showEraseDataDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.erase_title)
                .setMessage(R.string.erase_message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        eraseData();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public boolean validateData() {
        // data is invalid if either name or topic are empty strings
        String[] data = getFieldData();
        for (int i = 0; i < data.length-2; i++)
            if (data[i].isEmpty())
                return false;
        return true;
    }

    public void saveData() {
        String[] data = getFieldData();
        // if valid, save to shared preferences
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(getString(R.string.set_name), data[0]);
        editor.putString(getString(R.string.set_topic), data[1]);
        editor.putString(getString(R.string.desc), data[2]);
        editor.putString(getString(R.string.has_saved), "true");
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        saveData();
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }


}