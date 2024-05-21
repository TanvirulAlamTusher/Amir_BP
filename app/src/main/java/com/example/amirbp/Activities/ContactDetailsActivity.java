package com.example.amirbp.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.amirbp.R;
import com.example.amirbp.Utils.AppUtils;
import com.example.amirbp.ViewModel.ContactViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityContactDetailsBinding;

public class ContactDetailsActivity extends AppCompatActivity {
     ActivityContactDetailsBinding binding;
    int contactId;
    private ContactViewModel contactViewModel;
    private Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding = ActivityContactDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        animation = AnimationUtils.loadAnimation(this, R.anim.click_animation);

        Intent intent = getIntent();
        contactId = intent.getIntExtra("contactId", -1);

        topbar();
        allButtonTask();

        ViewModelFactory viewModelFactory = new ViewModelFactory.Builder()
                .add(ContactViewModel.class, new ContactViewModel(getApplication())).build();

        contactViewModel = new ViewModelProvider(ContactDetailsActivity.this, viewModelFactory).get(ContactViewModel.class);
        observedata();
        contactViewModel.getContactsListById(contactId);

        binding.materialToolbar.setOnMenuItemClickListener(item -> {
         if(item.getItemId() == R.id.delete_id){
             new AlertDialog.Builder(ContactDetailsActivity.this)
                     .setTitle(R.string.warning)
                     .setCancelable(true)
                     .setMessage("Are you sure you want to delete this contact?")
                     .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                     .setPositiveButton("Delete", (dialog, which) -> {
                       contactViewModel.deleteContactById(contactId);
                         dialog.dismiss();
                         finish();
                     })
                     .show();
             return true;

         } if(item.getItemId() == R.id.edit_id){
             Intent intent2 = new Intent(ContactDetailsActivity.this, EditContactActivity.class);
                intent2.putExtra("contactId", contactId);
             startActivity(intent2);
             return true;
            }
            return false;
        });

    }

    private void allButtonTask() {
       binding.nidCopyBtnId.setOnClickListener(view -> {
           view.startAnimation(animation);
           AppUtils.copyText(this, binding.nidNumTvId.getText().toString());
       });
       binding.mobileNumberCopyBtnId.setOnClickListener(view -> {
           view.startAnimation(animation);
           AppUtils.copyText(this, binding.mobileNumberTvId.getText().toString());
       });
       binding.bankAccountNumberCopyBtnId.setOnClickListener(view -> {
           view.startAnimation(animation);
           AppUtils.copyText(this, binding.bankAccountNumberTvId.getText().toString());
       });
       binding.facebookIdCopyBtnId.setOnClickListener(view -> {
           view.startAnimation(animation);
           AppUtils.copyText(this, binding.facebookIdTvId.getText().toString());
       });
       binding.imoCopyBtnId.setOnClickListener(view -> {
           view.startAnimation(animation);
           AppUtils.copyText(this, binding.imoIdTvId.getText().toString());
       });
       binding.whatsappCopyBtnId.setOnClickListener(view -> {
           view.startAnimation(animation);
           AppUtils.copyText(this, binding.whatsappIdTvId.getText().toString());
       });
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
        contactViewModel.getIs_delete_by_id().observe(ContactDetailsActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Contact delete successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        contactViewModel.getIs_updated().observe(ContactDetailsActivity.this, aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Contact updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void topbar() {
        binding.materialToolbar.setNavigationOnClickListener(v -> finish());
    }
    @Override
    protected void onResume() {
        super.onResume();
        contactViewModel.getContactsListById(contactId);
    }
}