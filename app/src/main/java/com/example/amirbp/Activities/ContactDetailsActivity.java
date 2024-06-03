package com.example.amirbp.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;


import com.example.amirbp.Model.Contact;
import com.example.amirbp.R;
import com.example.amirbp.Utils.AppUtils;
import com.example.amirbp.ViewModel.ContactViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityContactDetailsBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ContactDetailsActivity extends AppCompatActivity {

     ActivityContactDetailsBinding binding;
//    private static final int PERMISSION_REQUEST_CODE = 100;
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
            if(item.getItemId() == R.id.download_pdf){
                contactViewModel.getContactInfoById().observe(this, contact -> {
                    if (contact != null) {
                        generatePDF(contact);
                    }
                });
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
                binding.emailTvId.setText(contact.getEmail());
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
    private void generatePDF(Contact contact) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(24);
        titlePaint.setColor(ContextCompat.getColor(this, R.color.black));

        int x = 10, y = 25;

        canvas.drawText("Contact Details", x, y, titlePaint);
        y += 30;
        paint.setTextSize(12);

        canvas.drawText("Case Number: " + contact.getCase_number(), x, y, paint);
        y += 20;
        canvas.drawText("Name: " + contact.getName(), x, y, paint);
        y += 20;
        canvas.drawText("Identity Type: " + contact.getIdentity_type(), x, y, paint);
        y += 20;
        canvas.drawText("rank: " + contact.getRank(), x, y, paint);
        y += 20;
        canvas.drawText("BP number: " + contact.getBp_number(), x, y, paint);
        y += 20;
        canvas.drawText("NID number: " + contact.getNid(), x, y, paint);
        y += 20;
        canvas.drawText("Birth Day: " + contact.getDate_of_birth(), x, y, paint);
        y += 20;
        canvas.drawText("Bank Acc number: " + contact.getBank_account_number(), x, y, paint);
        y += 20;
        canvas.drawText("Mobile number: " + contact.getMobile_number(), x, y, paint);
        y += 20;
        canvas.drawText("Mobile number 2: " + contact.getMobile_number_2(), x, y, paint);
        y += 20;
        canvas.drawText("Mobile number 3: " + contact.getMobile_number_3(), x, y, paint);
        y += 20;
        canvas.drawText("Email: " + contact.getEmail(), x, y, paint);
        y += 20;
        canvas.drawText("Father Name: " + contact.getFathers_name(), x, y, paint);
        y += 20;
        canvas.drawText("Mother/Husband/Wife Name: " + contact.getMother_husband_wife_name(), x, y, paint);
        y += 20;
        canvas.drawText("Village: " + contact.getVillage(), x, y, paint);
        y += 20;
        canvas.drawText("Post Office: " + contact.getPost_office(), x, y, paint);
        y += 20;
        canvas.drawText("Thana: " + contact.getThana(), x, y, paint);
        y += 20;
        canvas.drawText("Dristict: " + contact.getDristict(), x, y, paint);
        y += 20;
        canvas.drawText("Job joining date: " + contact.getDate_of_joining_job(), x, y, paint);
        y += 20;
        canvas.drawText("Old Workplace: " + contact.getOld_workplace(), x, y, paint);
        y += 20;
        canvas.drawText("Old Workplace 2: " + contact.getOld_workplace_2(), x, y, paint);
        y += 20;
        canvas.drawText("Current Workplace joining date: " + contact.getDate_of_joining_current_workplace(), x, y, paint);
        y += 20;
        canvas.drawText("Current Workplace: " + contact.getCurrent_workplace(), x, y, paint);
        y += 20;
        canvas.drawText("Facebook ID: " + contact.getFacebook_id(), x, y, paint);
        y += 20;
        canvas.drawText("Imo ID: " + contact.getImo_id(), x, y, paint);
        y += 20;
        canvas.drawText("Whatsapp ID: " + contact.getWhatsapp_id(), x, y, paint);
        y += 20;

        // Add other contact details similarly

        pdfDocument.finishPage(page);

        String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(directoryPath, "ContactDetails.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF saved in Downloads folder", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while saving PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        pdfDocument.close();
    }


//    private void checkPermissions() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted
//            } else {
//                Toast.makeText(this, "Permission denied to write to external storage", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        contactViewModel.getContactsListById(contactId);
    }
}