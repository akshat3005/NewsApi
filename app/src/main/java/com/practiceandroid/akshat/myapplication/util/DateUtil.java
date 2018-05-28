package com.practiceandroid.akshat.myapplication.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.practiceandroid.akshat.myapplication.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by akshat-3049 on 29/05/18.
 */

public class DateUtil {

    private static String[] date;

    public static String[] datePickerDialog(final Context context) {
        final Calendar calendar = Calendar.getInstance();
        date = new String[2];

        DatePickerDialog from = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date[0] = sdf.format(calendar.getTime());

                DatePickerDialog to = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        date[1] = sdf.format(calendar.getTime());
                    }

                }, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                to.setTitle("To");
                to.show();

            }

        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        from.setTitle("From");
        from.show();

        return date;

    }
}
