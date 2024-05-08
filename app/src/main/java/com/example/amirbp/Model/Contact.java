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
    @ColumnInfo(name = "dristict")
    public String dristict;



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
    @ColumnInfo(name = "facebook_id")
    public String facebook_id;

    @Nullable
    @ColumnInfo(name = "whatsapp_id")
    public String whatsapp_id;

    @Nullable
    @ColumnInfo(name = "imo_id")
    public String imo_id;

    @ColumnInfo(name = "date", defaultValue = "CURRENT_TIMESTAMP")
    public String date;

    public Contact(@Nullable String case_number, String name, @Nullable String identity_type, @Nullable String rank, @Nullable String bp_number, @Nullable String nid, @Nullable String date_of_birth, @Nullable String bank_account_number, @Nullable String mobile_number, @Nullable String mobile_number_2, @Nullable String mobile_number_3, @Nullable String fathers_name, @Nullable String mother_husband_wife_name, @Nullable String village, @Nullable String post_office, @Nullable String thana, @Nullable String dristict, @Nullable String date_of_joining_job, @Nullable String old_workplace, @Nullable String old_workplace_2, @Nullable String date_of_joining_current_workplace, @Nullable String current_workplace, @Nullable String facebook_id, @Nullable String whatsapp_id, @Nullable String imo_id) {
        this.case_number = case_number;
        this.name = name;
        this.identity_type = identity_type;
        this.rank = rank;
        this.bp_number = bp_number;
        this.nid = nid;
        this.date_of_birth = date_of_birth;
        this.bank_account_number = bank_account_number;
        this.mobile_number = mobile_number;
        this.mobile_number_2 = mobile_number_2;
        this.mobile_number_3 = mobile_number_3;
        this.fathers_name = fathers_name;
        this.mother_husband_wife_name = mother_husband_wife_name;
        this.village = village;
        this.post_office = post_office;
        this.thana = thana;
        this.dristict = dristict;
        this.date_of_joining_job = date_of_joining_job;
        this.old_workplace = old_workplace;
        this.old_workplace_2 = old_workplace_2;
        this.date_of_joining_current_workplace = date_of_joining_current_workplace;
        this.current_workplace = current_workplace;
        this.facebook_id = facebook_id;
        this.whatsapp_id = whatsapp_id;
        this.imo_id = imo_id;
    }

    @Nullable
    public String getCase_number() {
        return case_number;
    }

    public void setCase_number(@Nullable String case_number) {
        this.case_number = case_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(@Nullable String identity_type) {
        this.identity_type = identity_type;
    }

    @Nullable
    public String getRank() {
        return rank;
    }

    public void setRank(@Nullable String rank) {
        this.rank = rank;
    }

    @Nullable
    public String getBp_number() {
        return bp_number;
    }

    public void setBp_number(@Nullable String bp_number) {
        this.bp_number = bp_number;
    }

    @Nullable
    public String getNid() {
        return nid;
    }

    public void setNid(@Nullable String nid) {
        this.nid = nid;
    }

    @Nullable
    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(@Nullable String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Nullable
    public String getBank_account_number() {
        return bank_account_number;
    }

    public void setBank_account_number(@Nullable String bank_account_number) {
        this.bank_account_number = bank_account_number;
    }

    @Nullable
    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(@Nullable String mobile_number) {
        this.mobile_number = mobile_number;
    }

    @Nullable
    public String getMobile_number_2() {
        return mobile_number_2;
    }

    public void setMobile_number_2(@Nullable String mobile_number_2) {
        this.mobile_number_2 = mobile_number_2;
    }

    @Nullable
    public String getMobile_number_3() {
        return mobile_number_3;
    }

    public void setMobile_number_3(@Nullable String mobile_number_3) {
        this.mobile_number_3 = mobile_number_3;
    }

    @Nullable
    public String getFathers_name() {
        return fathers_name;
    }

    public void setFathers_name(@Nullable String fathers_name) {
        this.fathers_name = fathers_name;
    }

    @Nullable
    public String getMother_husband_wife_name() {
        return mother_husband_wife_name;
    }

    public void setMother_husband_wife_name(@Nullable String mother_husband_wife_name) {
        this.mother_husband_wife_name = mother_husband_wife_name;
    }

    @Nullable
    public String getVillage() {
        return village;
    }

    public void setVillage(@Nullable String village) {
        this.village = village;
    }

    @Nullable
    public String getPost_office() {
        return post_office;
    }

    public void setPost_office(@Nullable String post_office) {
        this.post_office = post_office;
    }

    @Nullable
    public String getThana() {
        return thana;
    }

    public void setThana(@Nullable String thana) {
        this.thana = thana;
    }

    @Nullable
    public String getDristict() {
        return dristict;
    }

    public void setDristict(@Nullable String dristict) {
        this.dristict = dristict;
    }

    @Nullable
    public String getDate_of_joining_job() {
        return date_of_joining_job;
    }

    public void setDate_of_joining_job(@Nullable String date_of_joining_job) {
        this.date_of_joining_job = date_of_joining_job;
    }

    @Nullable
    public String getOld_workplace() {
        return old_workplace;
    }

    public void setOld_workplace(@Nullable String old_workplace) {
        this.old_workplace = old_workplace;
    }

    @Nullable
    public String getOld_workplace_2() {
        return old_workplace_2;
    }

    public void setOld_workplace_2(@Nullable String old_workplace_2) {
        this.old_workplace_2 = old_workplace_2;
    }

    @Nullable
    public String getDate_of_joining_current_workplace() {
        return date_of_joining_current_workplace;
    }

    public void setDate_of_joining_current_workplace(@Nullable String date_of_joining_current_workplace) {
        this.date_of_joining_current_workplace = date_of_joining_current_workplace;
    }

    @Nullable
    public String getCurrent_workplace() {
        return current_workplace;
    }

    public void setCurrent_workplace(@Nullable String current_workplace) {
        this.current_workplace = current_workplace;
    }

    @Nullable
    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(@Nullable String facebook_id) {
        this.facebook_id = facebook_id;
    }

    @Nullable
    public String getWhatsapp_id() {
        return whatsapp_id;
    }

    public void setWhatsapp_id(@Nullable String whatsapp_id) {
        this.whatsapp_id = whatsapp_id;
    }

    @Nullable
    public String getImo_id() {
        return imo_id;
    }

    public void setImo_id(@Nullable String imo_id) {
        this.imo_id = imo_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
