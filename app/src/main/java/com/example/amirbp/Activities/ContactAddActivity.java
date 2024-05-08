package com.example.amirbp.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.amirbp.R;
import com.example.amirbp.Utils.AppUtils;
import com.example.amirbp.databinding.ActivityContactAddBinding;

public class ContactAddActivity extends AppCompatActivity {
    ActivityContactAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityContactAddBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

       topbar();
       allButtonTask();
    }

    private void allButtonTask() {
        binding.saveButtonId.setOnClickListener(view ->{
            Toast.makeText(this, "save Click", Toast.LENGTH_SHORT).show();
        });
        binding.resetButtonId.setOnClickListener(view ->{
           resetAllField();
            Toast.makeText(this, "Form Reset", Toast.LENGTH_SHORT).show();
        });
        binding.birthDateButtonId.setOnClickListener(view -> {
            AppUtils.setDate(new AppUtils.DatePickerCallback() {
                @Override
                public void onDateSelected(String selectedDate) {
                    binding.birthDayTvId.setText(selectedDate);
                }
            },ContactAddActivity.this);
        });
        binding.jobJoningDateButtonId.setOnClickListener(view -> {
            AppUtils.setDate(new AppUtils.DatePickerCallback() {
                @Override
                public void onDateSelected(String selectedDate) {
                    binding.jobJoningDayTvId.setText(selectedDate);
                }
            },ContactAddActivity.this);
        });
        binding.currentWorkplaceJoningDateButtonId.setOnClickListener(view -> {
            AppUtils.setDate(new AppUtils.DatePickerCallback() {
                @Override
                public void onDateSelected(String selectedDate) {
                    binding.currentWorkplaceJoningDayTvId.setText(selectedDate);
                }
            },ContactAddActivity.this);
        });
    }

    private void resetAllField() {
        binding.caseNumberEditTextId.setText("");
        binding.nameEditTextId.setText("");
        binding.identityEditTextId.setText("");
        binding.rankEditTextId.setText("");
        binding.bpNumberEditTextId.setText("");
        binding.nidEditTextId.setText("");
        binding.birthDayTvId.setText("");
        binding.bankAccountNumberEditTextId.setText("");
        binding.mobileNumberEditTextId.setText("");
        binding.mobileNumber2EditTextId.setText("");
        binding.mobileNumber3EditTextId.setText("");
        binding.fathersNameEditTextId.setText("");
        binding.mothersHusbandWifeNameEditTextId.setText("");
        binding.vaiilageEditTextId.setText("");
        binding.postOfficeEditTextId.setText("");
        binding.thansEditTextId.setText("");
        binding.districtEditTextId.setText("");
        binding.jobJoningDayTvId.setText("");
        binding.oldWorksPlace1EditTextId.setText("");
        binding.oldWorksPlace2EditTextId.setText("");
        binding.currentWorkplaceJoningDayTvId.setText("");
        binding.facebookIdEditTextId.setText("");
        binding.imoidEditTextId.setText("");
        binding.whatsappIdEditTextId.setText("");
    }

    private void topbar() {
        binding.materialToolbar.setNavigationOnClickListener(v -> finish());
    }
}