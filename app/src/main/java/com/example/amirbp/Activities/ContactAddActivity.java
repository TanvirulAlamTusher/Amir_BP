package com.example.amirbp.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.amirbp.Model.Contact;
import com.example.amirbp.Utils.AppUtils;
import com.example.amirbp.ViewModel.ContactViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityContactAddBinding;

public class ContactAddActivity extends AppCompatActivity {
    ActivityContactAddBinding binding;
    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topbar();
        allButtonTask();
        ViewModelFactory viewModelFactory = new ViewModelFactory.Builder()
                .add(ContactViewModel.class, new ContactViewModel(getApplication())).build();

        contactViewModel = new ViewModelProvider(ContactAddActivity.this, viewModelFactory).get(ContactViewModel.class);
        observedata();

        contactViewModel.callContactsListFromDB();


    }

    private void observedata() {
        contactViewModel.getContactsList().observe(ContactAddActivity.this,contacts -> {
            if (contacts != null) {
                // here the contact list come
            }
        });
        contactViewModel.getIs_inserted().observe(ContactAddActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Contact add successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        contactViewModel.getIs_delete().observe(ContactAddActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Contact delete successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        contactViewModel.getIs_updated().observe(ContactAddActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Contact update successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void allButtonTask() {
        binding.saveButtonId.setOnClickListener(view -> {
            String caseNumber = binding.caseNumberEditTextId.getText().toString();
            String name = binding.nameEditTextId.getText().toString();
            String indentityType = binding.identityEditTextId.getText().toString();
            String rank = binding.rankEditTextId.getText().toString();
            String bpNumber = binding.bpNumberEditTextId.getText().toString();
            String nid = binding.nidEditTextId.getText().toString();
            String birthDay = binding.birthDayTvId.getText().toString();
            String bankAccountNumber = binding.bankAccountNumberEditTextId.getText().toString();
            String mobileNumber = binding.mobileNumberEditTextId.getText().toString();
            String mobileNumber2 = binding.mobileNumber2EditTextId.getText().toString();
            String mobileNumber3 = binding.mobileNumber3EditTextId.getText().toString();
            String fathersName = binding.fathersNameEditTextId.getText().toString();
            String mothersHusbandWifeName = binding.mothersHusbandWifeNameEditTextId.getText().toString();
            String village = binding.villageEditTextId.getText().toString();
            String postOffice = binding.postOfficeEditTextId.getText().toString();
            String thana = binding.thansEditTextId.getText().toString();
            String district = binding.districtEditTextId.getText().toString();
            String date_of_joining_job = binding.jobJoningDayTvId.getText().toString();
            String oldWorksPlace = binding.oldWorksPlace1EditTextId.getText().toString();
            String oldWorksPlace2 = binding.oldWorksPlace2EditTextId.getText().toString();
            String date_of_joining_current_workplace = binding.currentWorkplaceJoningDayTvId.getText().toString();
            String currentWorksPlace = binding.currentWorksPlaceEditTextId.getText().toString();
            String facebookId = binding.facebookIdEditTextId.getText().toString();
            String imoid = binding.imoidEditTextId.getText().toString();
            String whatsappId = binding.whatsappIdEditTextId.getText().toString();

         if(name.isEmpty()){
             binding.nameEditTextId.requestFocus();
             binding.nameEditTextId.setError("Name required");
             return;
         }
             //insert data to database here
            Contact contact = new Contact(caseNumber,name,indentityType,rank,bpNumber,nid,birthDay,bankAccountNumber,mobileNumber,mobileNumber2,mobileNumber3,fathersName,mothersHusbandWifeName,village,postOffice,thana,district,date_of_joining_job,oldWorksPlace,oldWorksPlace2,date_of_joining_current_workplace,currentWorksPlace,facebookId,imoid,whatsappId);
            contactViewModel.insertContact(contact);
            resetAllField();




        });
        binding.resetButtonId.setOnClickListener(view -> {
            resetAllField();
            Toast.makeText(this, "Form Reset", Toast.LENGTH_SHORT).show();
        });
        binding.birthDateButtonId.setOnClickListener(view -> {
            AppUtils.setDate(new AppUtils.DatePickerCallback() {
                @Override
                public void onDateSelected(String selectedDate) {
                    binding.birthDayTvId.setText(selectedDate);
                }
            }, ContactAddActivity.this);
        });
        binding.jobJoningDateButtonId.setOnClickListener(view -> {
            AppUtils.setDate(new AppUtils.DatePickerCallback() {
                @Override
                public void onDateSelected(String selectedDate) {
                    binding.jobJoningDayTvId.setText(selectedDate);
                }
            }, ContactAddActivity.this);
        });
        binding.currentWorkplaceJoningDateButtonId.setOnClickListener(view -> {
            AppUtils.setDate(new AppUtils.DatePickerCallback() {
                @Override
                public void onDateSelected(String selectedDate) {
                    binding.currentWorkplaceJoningDayTvId.setText(selectedDate);
                }
            }, ContactAddActivity.this);
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
        binding.villageEditTextId.setText("");
        binding.postOfficeEditTextId.setText("");
        binding.thansEditTextId.setText("");
        binding.districtEditTextId.setText("");
        binding.jobJoningDayTvId.setText("");
        binding.oldWorksPlace1EditTextId.setText("");
        binding.oldWorksPlace2EditTextId.setText("");
        binding.currentWorkplaceJoningDayTvId.setText("");
        binding.currentWorksPlaceEditTextId.setText("");
        binding.facebookIdEditTextId.setText("");
        binding.imoidEditTextId.setText("");
        binding.whatsappIdEditTextId.setText("");
    }

    private void topbar() {
        binding.materialToolbar.setNavigationOnClickListener(v -> finish());
    }
}