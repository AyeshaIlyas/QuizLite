package edu.sunyulster.quizlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ShareActionProvider;

import edu.sunyulster.quizlite.databinding.ActivityCreateContentBinding;

public class CreateContentActivity extends AppCompatActivity {

    private ActivityCreateContentBinding binding;
    private int lastCard;
    private int currentCard;
    private CreateContentViewModel vm;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentCard = 0;
        lastCard = 0;
        setCardNumber(lastCard + 1);
        sp = getSharedPreferences(getString(R.string.saved_study_set), MODE_PRIVATE);
        vm = new CreateContentViewModel(getApplication(), sp);
        // if saved data, then load into view model
        if (vm.hasData()) {
            fillForm(vm.getNthItem(lastCard));
            lastCard = vm.dataLength() - 1;
        }

        // set listeners
        binding.newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (storeDataLocally()) {
                    clearFields();
                    lastCard = vm.dataLength();
                    currentCard = lastCard;
                    setCardNumber(lastCard + 1);
                }
            }
        });

        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (storeDataLocally()) {
                    vm.addDataToDb();
                    Intent i = new Intent(CreateContentActivity.this, StudySetsActivity.class);
                    startActivity(i);
                    CreateContentActivity.this.finishAffinity(); // clear activity backstack completely
                }
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentCard > 0 && storeDataLocally()) {
                    fillForm(vm.getNthItem(--currentCard));
                    setCardNumber(currentCard + 1);
                }
            }
        });

        binding.forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentCard < lastCard && storeDataLocally()) {
                    fillForm(vm.getNthItem(++currentCard));
                    setCardNumber(currentCard + 1);
                }
            }
        });
    }

    public void setCardNumber(int number) {
        binding.cardNumber.setText("Card: " + number);
    }


    public void setFields(String key, String value) {
        binding.key.setText(key);
        binding.value.setText(value);
    }

    public void clearFields() {
        setFields("", "");
    }

    public void fillForm(StudyContent content) {
        setFields(content.getKey(), content.getValue());
    }

    public String[] getFieldData() {
        String[] data = new String[2];
        data[0] = binding.key.getText().toString();
        data[1] = binding.value.getText().toString();
        return data;
    }

    public boolean storeDataLocally() {
        // data is invalid if either field contains empty strings
        String[] data = getFieldData();
        for (int i = 0; i < data.length; i++)
            if (data[i].isEmpty()) {
                binding.error.setVisibility(View.VISIBLE);
                return false;
            }
        binding.error.setVisibility(View.GONE);
        vm.saveDataToIndex(currentCard, data);
        return true;
    }
    
     public void showNotSavedDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.not_saved_title)
                .setMessage(R.string.not_saved_message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        vm.saveToSharedPrefs();
                        CreateContentActivity.this.finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }


    @Override
    public void onBackPressed() {
       if (!storeDataLocally()) 
           showNotSavedDialog();
       else {
           vm.saveToSharedPrefs();
           super.onBackPressed();
       }  
    }

    @Override
    public void onPause() {
        super.onPause();
        storeDataLocally(); // if the current card's data is not valid, then the card's data will NOT be saved. However all other data will be saved.
        vm.saveToSharedPrefs(); // save valid data to shared pref
    }

}
