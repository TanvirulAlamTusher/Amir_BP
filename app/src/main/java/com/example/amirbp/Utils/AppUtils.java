package com.example.amirbp.Utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtils {
    public static String getCurrrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Format the date as desired
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(currentDate);
    }
    public static void setDate(DatePickerCallback datePickerCallback, Context context) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        int updatedMonth = month + 1;
                        String selectedDate = day + "/" + updatedMonth + "/" + year;
                        datePickerCallback.onDateSelected(selectedDate);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
    public interface DatePickerCallback {
        void onDateSelected(String selectedDate);
    }
}
