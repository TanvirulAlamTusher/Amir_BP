package com.example.amirbp.Model;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @Nullable
    @ColumnInfo(name = "case_number")
    public String case_number;


    @ColumnInfo(name = "name")
    public String name;

    @Nullable
    @ColumnInfo(name = "identity_type")
    public String identity_type;

    @Nullable
    @ColumnInfo(name = "rank")
    public String rank;


    @Nullable
    @ColumnInfo(name = "bp_number")
    public String bp_number;

    @Nullable
    @ColumnInfo(name = "nid")
    public String nid;

    @Nullable
    @ColumnInfo(name = "date_of_birth")
    public String date_of_birth;

    @Nullable
    @ColumnInfo(name = "bank_account_number")
    public String bank_account_number;

    @Nullable
    @ColumnInfo(name = "mobile_number")
    public String mobile_number;

    @Nullable
    @ColumnInfo(name = "mobile_number_2")
    public String mobile_number_2;

    @Nullable
    @ColumnInfo(name = "mobile_number_3")
    public String mobile_number_3;

    @Nullable
    @ColumnInfo(name = "fathers_name")
    public String fathers_name;

    @Nullable
    @ColumnInfo(name = "mother_husband_wife_name")
    public String mother_husband_wife_name;

    @Nullable
    @ColumnInfo(name = "village")
    public String village;

    @Nullable
    @ColumnInfo(name = "post_office")
    public String post_office;

    @Nullable
    @ColumnInfo(name = "thana")
    public String thana;

    @Nullable
    @ColumnInfo(name = "dristic")
    public String dristic;



    @Nullable
    @ColumnInfo(name = "date_of_joining_job")
    public String date_of_joining_job;

    @Nullable
    @ColumnInfo(name = "old_workplace")
    public String old_workplace;

    @Nullable
    @ColumnInfo(name = "old_workplace_2")
    public String old_workplace_2;

    @Nullable
    @ColumnInfo(name = "date_of_joining_current_workplace")
    public String date_of_joining_current_workplace;

    @Nullable
    @ColumnInfo(name = "current_workplace")
    public String current_workplace;

    @Nullable
    @ColumnInfo(name = "facebook_link")
    public String facebook_link;

    @Nullable
    @ColumnInfo(name = "whatsapp")
    public String whatsapp;

    @Nullable
    @ColumnInfo(name = "imo")
    public String imo;

    @ColumnInfo(name = "date", defaultValue = "CURRENT_TIMESTAMP")
    public String date;


}
