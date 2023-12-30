package com.demo.moonphases.MoonHelpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.lang.reflect.Field;
import java.util.Calendar;


public class MonthPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private OnMonthPickedListener delegate;
    private boolean dateActuallySet = false;
    private int month = Calendar.getInstance().get(2);
    private int year = Calendar.getInstance().get(5);

    public OnMonthPickedListener getOnMonthPickedListener() {
        return this.delegate;
    }

    public void setOnMonthPickedListener(OnMonthPickedListener onMonthPickedListener) {
        this.delegate = onMonthPickedListener;
    }

    public void setMonth(int i, int i2) {
        this.month = i;
        this.year = i2;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        return createMonthPickerDialog();
    }

    private DatePickerDialog createMonthPickerDialog() {
        Field[] declaredFields;
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, this.year, this.month, 1);
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePickerDialog.setTitle("");
        if (Build.VERSION.SDK_INT < 21) {
            try {
                for (Field field : datePicker.getClass().getDeclaredFields()) {
                    if ("mDaySpinner".equals(field.getName())) {
                        field.setAccessible(true);
                        ((View) field.get(datePicker)).setVisibility(View.GONE);
                    }
                }
            } catch (Exception unused) {
            }
            datePickerDialog.setButton(-1, getResources().getString(17039370), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MonthPickerFragment.this.dateActuallySet = true;
                }
            });
        }
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
        if (this.delegate == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 21 || this.dateActuallySet) {
            this.delegate.onMonthPicked(i, i2);
        }
    }

    public interface OnMonthPickedListener {
        void onMonthPicked(int i, int i2);
    }
}
