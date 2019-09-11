package com.zaytsevaa.eptaskmanager.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zaytsevaa.eptaskmanager.viewmodel.MainViewModel;

import java.util.Calendar;

public class TextOnClickHandler {
    private Context mContext;
    private MainViewModel mViewModel;
    private Calendar dateAndTime = Calendar.getInstance();

    public TextOnClickHandler(Context context, MainViewModel viewModel) {
        this.mContext = context;
        this.mViewModel = viewModel;
    }

    public void onClick(final View textView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dateAndTime.set(Calendar.YEAR, year);
                    dateAndTime.set(Calendar.MONTH, monthOfYear);
                    dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            dateAndTime.set(Calendar.MINUTE, minute);
                            mViewModel.changeTime(textView.getId(), dateAndTime.getTimeInMillis());
                        }
                    };

                    // отображаем диалоговое окно для выбора времени
                    new TimePickerDialog(mContext, t,
                            dateAndTime.get(Calendar.HOUR_OF_DAY),
                            dateAndTime.get(Calendar.MINUTE), true).show();
                }
            };

            // отображаем диалоговое окно для выбора даты
            new DatePickerDialog(mContext, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        } else {
            Toast.makeText(mContext, "You can't change date", Toast.LENGTH_SHORT).show();
        }
    }
}
