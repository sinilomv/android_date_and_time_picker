package com.soft.ground.datepickerexample;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Calendar calFrom, calTo;
    private TextView timeFrom, timeTo, dateFrom, dateTo;
    private String[] daysStr = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private SimpleDateFormat sdf;
    private int hourFrom, minutFrom, hourTo, minutTo;
    private ImageView dateFromNext, dateToNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set title and subtitle
        this.setTitle("MY TIME PICKER");

        init();
    }

    private void init() {

        sdf = new SimpleDateFormat("HH:mm");

        ImageView dateFromBefore = findViewById(R.id.imageViewDateFromBefore);
        dateFromNext = findViewById(R.id.imageViewDateFromNext);

        ImageView dateToBefore = findViewById(R.id.imageViewDateToBefore);
        dateToNext = findViewById(R.id.imageViewDateToNext);

        ImageView calDateFrom = findViewById(R.id.imageViewDateFrom);
        ImageView calDateTo = findViewById(R.id.imageViewDateTo);

        dateFrom = findViewById(R.id.textViewDateFrom);
        dateTo = findViewById(R.id.textViewDateTo);

        timeFrom = findViewById(R.id.textViewTimeFrom);
        timeTo = findViewById(R.id.textViewTimeTo);

        calFrom = Calendar.getInstance();
        calTo = Calendar.getInstance();

        dateFromBefore.setOnClickListener(arg0 -> {

            dateFromBefore();

        });

        dateFromNext.setOnClickListener(arg0 -> {

            dateFromNext();

        });

        dateToBefore.setOnClickListener(arg0 -> {

            dateToBefore();

        });

        dateToNext.setOnClickListener(arg0 -> {

            dateToNext();

        });


        calDateFrom.setOnClickListener(arg0 -> {

            setDateFrom();

        });

        calDateTo.setOnClickListener(arg0 -> {

            setDateTo();

        });

        dateFrom.setOnClickListener(arg0 -> {

            setDateFrom();

        });

        dateTo.setOnClickListener(arg0 -> {

            setDateTo();

        });

        timeFrom.setOnClickListener(v -> {

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(this,
                    (timePicker, selectedHour, selectedMinute) -> {

                        hourFrom = selectedHour;
                        minutFrom = selectedMinute;

                        calFrom.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calFrom.set(Calendar.MINUTE, selectedMinute);
                        calFrom.set(Calendar.SECOND, 00);

                        timeFrom.setText(sdf.format(calFrom.getTime()));
                    },

                    calFrom.get(Calendar.HOUR_OF_DAY), // Current hour value
                    calFrom.get(Calendar.MINUTE), // Current minute value
                    true); // Check 24 Hour or AM/PM format

            mTimePicker.setTitle("Set time");
            mTimePicker.show();

        });

        timeTo.setOnClickListener(v -> {

            TimePickerDialog mTimePicker = new TimePickerDialog(this,
                    (timePicker, selectedHour, selectedMinute) -> {

                        hourTo = selectedHour;
                        minutTo = selectedMinute;

                        calTo.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calTo.set(Calendar.MINUTE, selectedMinute);
                        calTo.set(Calendar.SECOND, 00);

                        timeTo.setText(sdf.format(calTo.getTime()));
                    },

                    calTo.get(Calendar.HOUR_OF_DAY), // Current hour value
                    calTo.get(Calendar.MINUTE), // Current minute value
                    true); // Check 24 Hour or AM/PM format

            mTimePicker.setTitle("Set time");
            mTimePicker.show();

        });

        setCurDate();

    }


    private void setDateTo() {

        int yy = calTo.get(Calendar.YEAR);
        int mm = calTo.get(Calendar.MONTH);
        int dd = calTo.get(Calendar.DAY_OF_MONTH);

        Calendar maxDate = Calendar.getInstance();

        DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {

            calTo.set(year, monthOfYear, dayOfMonth);
            calTo.set(Calendar.HOUR_OF_DAY, hourTo);
            calTo.set(Calendar.MINUTE, minutTo);
            calTo.set(Calendar.SECOND, 00);

            String dayName = daysStr[calTo.get(Calendar.DAY_OF_WEEK) - 1];

            String date = dayName + ", " + dayOfMonth + "." + (monthOfYear + 1)
                    + "." + year;

            dateTo.setText(date);

            if (calTo.before(calFrom)) {

                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                String formatted = formatDate.format(calTo.getTime());

                try {

                    calFrom.setTime(formatDate.parse(formatted));
                    calFrom.set(Calendar.HOUR_OF_DAY, hourFrom);
                    calFrom.set(Calendar.MINUTE, minutFrom);
                    calFrom.set(Calendar.SECOND, 00);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dateFrom.setText(date);

            }

            if (isSameDay(calTo, maxDate)) {

                dateToNext.setImageResource(R.drawable.next_disabled);
                dateToNext.setEnabled(false);

            } else {

                dateToNext.setImageResource(R.drawable.next);
                dateToNext.setEnabled(true);

            }

            if (isSameDay(calTo, calFrom)) {

                dateFromNext.setImageResource(R.drawable.next_disabled);
                dateFromNext.setEnabled(false);

            } else {

                dateFromNext.setImageResource(R.drawable.next);
                dateFromNext.setEnabled(true);

            }



        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

    private void setDateFrom() {

        int yy = calFrom.get(Calendar.YEAR);
        int mm = calFrom.get(Calendar.MONTH);
        int dd = calFrom.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {

            calFrom = Calendar.getInstance();
            calFrom.set(year, monthOfYear, dayOfMonth);
            calFrom.set(Calendar.HOUR_OF_DAY, hourFrom);
            calFrom.set(Calendar.MINUTE, minutFrom);
            calFrom.set(Calendar.SECOND, 00);

            String dayName = daysStr[calFrom.get(Calendar.DAY_OF_WEEK) - 1];

            String date = dayName + ", " + dayOfMonth + "." + (monthOfYear + 1)
                    + "." + year;
            dateFrom.setText(date);


            if (isSameDay(calTo, calFrom)) {

                dateFromNext.setImageResource(R.drawable.next_disabled);
                dateFromNext.setEnabled(false);

            } else {

                dateFromNext.setImageResource(R.drawable.next);
                dateFromNext.setEnabled(true);

            }


        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(calTo.getTimeInMillis());
        datePicker.show();

    }

    private void dateFromBefore() {

        calFrom.add(Calendar.DAY_OF_MONTH, -1);

        String dayName = daysStr[calFrom.get(Calendar.DAY_OF_WEEK) - 1];

        String date = dayName + ", " + calFrom.get(Calendar.DAY_OF_MONTH) + "." + (calFrom.get(Calendar.MONTH) + 1)
                + "." + calFrom.get(Calendar.YEAR);
        dateFrom.setText(date);

        dateFromNext.setImageResource(R.drawable.next);
        dateFromNext.setEnabled(true);

    }

    private void dateToBefore() {

        calTo.add(Calendar.DAY_OF_MONTH, -1);
        calTo.set(Calendar.HOUR_OF_DAY, hourTo);
        calTo.set(Calendar.MINUTE, minutTo);
        calTo.set(Calendar.SECOND, 00);

        String dayName = daysStr[calTo.get(Calendar.DAY_OF_WEEK) - 1];

        String date = dayName + ", " + calTo.get(Calendar.DAY_OF_MONTH) + "." + (calTo.get(Calendar.MONTH) + 1)
                + "." + calTo.get(Calendar.YEAR);
        dateTo.setText(date);

        if (calTo.before(calFrom)) {

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            String formatted = formatDate.format(calTo.getTime());

            try {

                calFrom.setTime(formatDate.parse(formatted));
                calFrom.set(Calendar.HOUR_OF_DAY, hourFrom);
                calFrom.set(Calendar.MINUTE, minutFrom);
                calFrom.set(Calendar.SECOND, 00);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            dateFrom.setText(date);

            dateFromNext.setImageResource(R.drawable.next_disabled);
            dateFromNext.setEnabled(false);

        }

        dateToNext.setImageResource(R.drawable.next);
        dateToNext.setEnabled(true);

    }

    private void dateFromNext() {

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = formatDate.format(calTo.getTime());

        Calendar maxDate = Calendar.getInstance();

        try {

            maxDate.setTime(formatDate.parse(formatted));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (calFrom.before(maxDate)) {

            calFrom.add(Calendar.DAY_OF_MONTH, +1);
            calFrom.set(Calendar.HOUR_OF_DAY, hourFrom);
            calFrom.set(Calendar.MINUTE, minutFrom);
            calFrom.set(Calendar.SECOND, 00);

            String dayName = daysStr[calFrom.get(Calendar.DAY_OF_WEEK) - 1];

            String date = dayName + ", " + calFrom.get(Calendar.DAY_OF_MONTH) + "." + (calFrom.get(Calendar.MONTH) + 1)
                    + "." + calFrom.get(Calendar.YEAR);

            dateFrom.setText(date);

            dateFromNext.setImageResource(R.drawable.next);
            dateFromNext.setEnabled(true);

        } else {

            dateFromNext.setImageResource(R.drawable.next_disabled);
            dateFromNext.setEnabled(false);

        }

        maxDate.set(Calendar.HOUR_OF_DAY, hourTo);
        maxDate.set(Calendar.MINUTE, minutTo);

        if (isSameDay(maxDate, calFrom)) {

            dateFromNext.setImageResource(R.drawable.next_disabled);
            dateFromNext.setEnabled(false);

        }


    }

    public boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            return false;
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    private void dateToNext() {

        Calendar maxDate = Calendar.getInstance();

        if (calTo.before(maxDate)) {

            calTo.add(Calendar.DAY_OF_MONTH, +1);
            calTo.set(Calendar.HOUR_OF_DAY, hourTo);
            calTo.set(Calendar.MINUTE, minutTo);
            calTo.set(Calendar.SECOND, 00);

            String dayName = daysStr[calTo.get(Calendar.DAY_OF_WEEK) - 1];

            String date = dayName + ", " + calTo.get(Calendar.DAY_OF_MONTH) + "." + (calTo.get(Calendar.MONTH) + 1)
                    + "." + calTo.get(Calendar.YEAR);

            dateTo.setText(date);

            dateToNext.setImageResource(R.drawable.next);
            dateToNext.setEnabled(true);

        }

        maxDate.set(Calendar.HOUR_OF_DAY, hourFrom);
        maxDate.set(Calendar.MINUTE, minutTo);

        if (isSameDay(maxDate, calTo)) {

            dateToNext.setImageResource(R.drawable.next_disabled);
            dateToNext.setEnabled(false);

        }

        if (calFrom.before(calTo)) {

            dateFromNext.setImageResource(R.drawable.next);
            dateFromNext.setEnabled(true);

        }
    }

    public void setCurDate() {

        calFrom.set(Calendar.HOUR_OF_DAY, 00);
        calFrom.set(Calendar.MINUTE, 00);
        calFrom.set(Calendar.SECOND, 00);

        hourFrom = 0;
        minutFrom = 0;

        timeFrom.setText(sdf.format(calFrom.getTime()));

        calTo.set(Calendar.HOUR_OF_DAY, 23);
        calTo.set(Calendar.MINUTE, 55);
        calTo.set(Calendar.SECOND, 00);

        hourTo = 23;
        minutTo = 55;

        timeTo.setText(sdf.format(calTo.getTime()));

        String dayName = daysStr[calFrom.get(Calendar.DAY_OF_WEEK) - 1];

        String date = dayName + ", " + calFrom.get(Calendar.DAY_OF_MONTH) + "." + (calFrom.get(Calendar.MONTH) + 1)
                + "." + calFrom.get(Calendar.YEAR);

        dateFrom.setText(date);
        dateTo.setText(date);

        dateFromNext.setImageResource(R.drawable.next_disabled);
        dateFromNext.setEnabled(false);

        dateToNext.setImageResource(R.drawable.next_disabled);
        dateToNext.setEnabled(false);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        this.finish();
    }

}
