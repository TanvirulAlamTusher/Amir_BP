package com.example.amirbp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.amirbp.Model.Contact;
import com.example.amirbp.R;
import com.example.amirbp.Utils.AppUtils;
import com.example.amirbp.ViewModel.ContactViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityEditContactBinding;

public class EditContactActivity extends AppCompatActivity {
        ActivityEditContactBinding binding;
        ContactViewModel contactViewModel;

    int contactId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        contactId = intent.getIntExtra("contactId", -1);

        topbar();

        ViewModelFactory viewModelFactory = new ViewModelFactory.Builder()
                .add(ContactViewModel.class, new ContactViewModel(getApplication())).build();

        contactViewModel = new ViewModelProvider(EditContactActivity.this, viewModelFactory).get(ContactViewModel.class);
        observedata();
        contactViewModel.getContactsListById(contactId);
        allButtonTask();

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
            String email = binding.emailEditTextId.getText().toString();
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
            //update data to database here

            Contact contact = new Contact();
            contact.id = contactId;
            contact.case_number = caseNumber;
            contact.name = name;
            contact.identity_type = indentityType;
            contact.rank = rank;
            contact.bp_number= bpNumber;
            contact.nid = nid;
            contact.date_of_birth = birthDay;
            contact.bank_account_number = bankAccountNumber;
            contact.mobile_number = mobileNumber;
            contact.mobile_number_2 = mobileNumber2;
            contact.mobile_number_3 = mobileNumber3;
            contact.email = email;
            contact.fathers_name = fathersName;
            contact.mother_husband_wife_name = mothersHusbandWifeName;
            contact.village = village;
            contact.post_office = postOffice;
            contact.thana = thana;
            contact.dristict = district;
            contact.date_of_joining_job = date_of_joining_job;
            contact.old_workplace = oldWorksPlace;
            contact.old_workplace_2 = oldWorksPlace2;
            contact.date_of_joining_current_workplace = date_of_joining_current_workplace;
            contact.current_workplace = currentWorksPlace;
            contact.facebook_id = facebookId;
            contact.imo_id = imoid;
            contact.whatsapp_id = whatsappId;
            contactViewModel.updateContact(contact);

        });
        binding.resetButtonId.setOnClickListener(view -> {
            finish();
        });
        binding.birthDateButtonId.setOnClickListener(view -> {
            AppUtils.setDate(new AppUtils.DatePickerCallback() {
                @Override
                public void onDateSelected(String selectedDate) {
                    binding.birthDayTvId.setText(selectedDate);
                }
            }, EditContactActivity.this);
        });
        binding.jobJoningDateButtonId.setOnClickListener(view -> {
            AppUtils.setDate(new AppUtils.DatePickerCallback() {
                @Override
                public void onDateSelected(String selectedDate) {
                    binding.jobJoningDayTvId.setText(selectedDate);
                }
            }, EditContactActivity.this);
        });
        binding.currentWorkplaceJoningDateButtonId.setOnClickListener(view -> {
            AppUtils.setDate(new AppUtils.DatePickerCallback() {
                @Override
                public void onDateSelected(String selectedDate) {
                    binding.currentWorkplaceJoningDayTvId.setText(selectedDate);
                }
            }, EditContactActivity.this);
        });
    }

    private void observedata() {
        contactViewModel.getContactInfoById().observe(EditContactActivity.this, contact -> {
            if (contact != null) {
                binding.caseNumberEditTextId.setText(contact.getCase_number());
                binding.nameEditTextId.setText(contact.getName());
                binding.identityEditTextId.setText(contact.getIdentity_type());
                binding.rankEditTextId.setText(contact.getRank());
                binding.bpNumberEditTextId.setText(contact.getBp_number());
                binding.nidEditTextId.setText(contact.getNid());
                binding.birthDayTvId.setText(contact.getDate_of_birth());
                binding.bankAccountNumberEditTextId.setText(contact.getBank_account_number());
                binding.mobileNumberEditTextId.setText(contact.getMobile_number());
                binding.mobileNumber2EditTextId.setText(contact.getMobile_number_2());
                binding.mobileNumber3EditTextId.setText(contact.getMobile_number_3());
                binding.emailEditTextId.setText(contact.getEmail());
                binding.fathersNameEditTextId.setText(contact.getFathers_name());
                binding.mothersHusbandWifeNameEditTextId.setText(contact.getMother_husband_wife_name());
                binding.villageEditTextId.setText(contact.getVillage());
                binding.postOfficeEditTextId.setText(contact.getPost_office());
                binding.thansEditTextId.setText(contact.getThana());
                binding.districtEditTextId.setText(contact.getDristict());
                binding.jobJoningDayTvId.setText(contact.getDate_of_joining_job());
                binding.oldWorksPlace1EditTextId.setText(contact.getOld_workplace());
                binding.oldWorksPlace2EditTextId.setText(contact.getOld_workplace_2());
                binding.currentWorkplaceJoningDayTvId.setText(contact.getDate_of_joining_current_workplace());
                binding.currentWorksPlaceEditTextId.setText(contact.getCurrent_workplace());
                binding.facebookIdEditTextId.setText(contact.getFacebook_id());
                binding.imoidEditTextId.setText(contact.getImo_id());
                binding.whatsappIdEditTextId.setText(contact.getWhatsapp_id());
            }
        });

        contactViewModel.getIs_updated().observe(EditContactActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Contact update successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void topbar() {
        binding.materialToolbar.setNavigationOnClickListener(v -> finish());
    }


}