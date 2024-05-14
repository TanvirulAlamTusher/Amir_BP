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

import com.example.amirbp.R;
import com.example.amirbp.ViewModel.ContactViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityContactDetailsBinding;

public class ContactDetailsActivity extends AppCompatActivity {
     ActivityContactDetailsBinding binding;
    private ContactViewModel contactViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding = ActivityContactDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int contactId = intent.getIntExtra("contactId", -1);

        topbar();

        ViewModelFactory viewModelFactory = new ViewModelFactory.Builder()
                .add(ContactViewModel.class, new ContactViewModel(getApplication())).build();

        contactViewModel = new ViewModelProvider(ContactDetailsActivity.this, viewModelFactory).get(ContactViewModel.class);
        observedata();

        contactViewModel.getContactsListById(contactId);


    }

    private void observedata() {
        contactViewModel.getContactInfoById().observe(ContactDetailsActivity.this, contact -> {
            if (contact != null) {
                binding.caseNumTvId.setText(contact.getCase_number());
                binding.nameTvId.setText(contact.getName());
                binding.identityTvId.setText(contact.getIdentity_type());
                binding.rankTvId.setText(contact.getRank());
                binding.bpNumTvId.setText(contact.getBp_number());
                binding.nidNumTvId.setText(contact.getNid());
                binding.birthdayTvId.setText(contact.getDate_of_birth());
                binding.bankAccountNumberTvId.setText(contact.getBank_account_number());
                binding.mobileNumberTvId.setText(contact.getMobile_number());
                binding.mobileNumber2TvId.setText(contact.getMobile_number_2());
                binding.mobileNumber3TvId.setText(contact.getMobile_number_3());
                binding.fatherNameTvId.setText(contact.getFathers_name());
                binding.motherHusbandWifeNameTvId.setText(contact.getMother_husband_wife_name());
                binding.villageTvId.setText(contact.getVillage());
                binding.postOfficeTvId.setText(contact.getPost_office());
                binding.thanaTvId.setText(contact.getThana());
                binding.dristrictTvId.setText(contact.getDristict());
                binding.jobJoiningDateTvId.setText(contact.getDate_of_joining_job());
                binding.oldWorkplace1TvId.setText(contact.getOld_workplace());
                binding.oldWorkplace2TvId.setText(contact.getOld_workplace_2());
                binding.currentWorkplaceJoningDateTvId.setText(contact.getDate_of_joining_current_workplace());
                binding.currentWorkplaceTvId.setText(contact.getCurrent_workplace());
                binding.facebookIdTvId.setText(contact.getFacebook_id());
                binding.imoIdTvId.setText(contact.getImo_id());
                binding.whatsappIdTvId.setText(contact.getWhatsapp_id());
            }
        });
        contactViewModel.getIs_delete().observe(ContactDetailsActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Contact delete successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        contactViewModel.getIs_updated().observe(ContactDetailsActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Contact update successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void topbar() {
        binding.materialToolbar.setNavigationOnClickListener(v -> finish());
    }
}