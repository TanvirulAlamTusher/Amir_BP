package com.example.amirbp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amirbp.Activities.ContactDetailsActivity;
import com.example.amirbp.Model.Contact;
import com.example.amirbp.R;
import com.example.amirbp.databinding.ContactListBinding;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private  Context context;
    private  List<Contact> contactList;

    private  Animation animation;
    private AlertDialog alertDialog = null;

    public ContactListAdapter(Context context, List<Contact> contactList, Animation animation) {
        this.context = context;
        this.contactList = contactList;
        this.animation = animation;
    }

    @NonNull
    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.contact_list, parent, false);
        return new ContactListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ViewHolder holder, int position) {
        holder.binding.nameTvId.setText(contactList.get(position).getName());
        holder.binding.phoneNumTvId.setText(contactList.get(position).getMobile_number());
        holder.binding.thanaTvId.setText(contactList.get(position).getThana());
        holder.binding.districtTvId.setText(contactList.get(position).getDristict());
       // holder.binding.dateTvId.setText( contactList.get(position).getDate());

//        holder.binding.moreBtnId.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//               view.startAnimation(animation);
//            }
//        });
        holder.binding.llayoutId.setOnClickListener(v ->{
            v.startAnimation(animation);
            int contactId = contactList.get(position).getId();
            context.startActivity(new Intent(context, ContactDetailsActivity.class).putExtra("contactId", contactId));
        });

    }

    @Override
    public int getItemCount() {
        if(contactList == null){
            return 0;
        }
        return contactList.size();
    }

    public void filterList(List<Contact> filteredList) {
        if(filteredList == null){
            return;
        }
        contactList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ContactListBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ContactListBinding.bind(itemView);
        }
    }
}
