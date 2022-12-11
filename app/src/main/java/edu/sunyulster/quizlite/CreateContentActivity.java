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
    private int cardNumber;
    private CreateContentViewModel vm;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cardNumber = 0;
        setCardNumber(cardNumber + 1);
        sp = getSharedPreferences(getString(R.string.saved_study_set), MODE_PRIVATE);
        vm = new CreateContentViewModel(getApplication(), sp);
        // if saved data, then load into view model
        if (vm.hasData())
            fillForm(vm.getNthItem(cardNumber));

        // set listeners
        binding.newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (processData()) {
                    clearFields();
                    ++cardNumber;
                    setCardNumber(cardNumber + 1);
                }
            }
        });

        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (processData()) {
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
                if (cardNumber > 0) {
                    fillForm(vm.getNthItem(--cardNumber));
                    setCardNumber(cardNumber + 1);
                }
            }
        });

        binding.forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardNumber < vm.dataLength() - 1) {
                    fillForm(vm.getNthItem(++cardNumber));
                    setCardNumber(cardNumber + 1);
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

    public boolean processData() {
        // data is invalid if either field contains empty strings
        String[] data = getFieldData();
        for (int i = 0; i < data.length; i++)
            if (data[i].isEmpty()) {
                binding.error.setVisibility(View.VISIBLE);
                return false;
            }
        binding.error.setVisibility(View.GONE);
        vm.saveData(data);
        return true;
    }

    @Override
    public void onBackPressed() {
       processData();
       vm.saveToSharedPrefs();
       super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        processData();
        vm.saveToSharedPrefs();
    }

}
