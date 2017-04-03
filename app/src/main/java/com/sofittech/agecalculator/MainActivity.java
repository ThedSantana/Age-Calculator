package com.sofittech.agecalculator;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    int mYear,mMonth,mDay;
    long days,hours,minutes,seconds;
    Button button;
    TextView age,month, day, hour, min,sec;
    EditText editText;
    Calendar mcurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=(EditText)findViewById(R.id.ed);

        month=(TextView)findViewById(R.id.month);
        day=(TextView)findViewById(R.id.day);
        hour=(TextView)findViewById(R.id.hour);
        min=(TextView)findViewById(R.id.min);
        age=(TextView)findViewById(R.id.age);
        sec=(TextView)findViewById(R.id.sec);

        button=(Button)findViewById(R.id.btn_cal);

        editText.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                mcurrentDate=Calendar.getInstance();
                mYear=mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        Log.e("Date", String.valueOf(selectedyear));
                        Log.e("Month", String.valueOf(selectedmonth));
                        Log.e("Day", String.valueOf(selectedday));
                        mcurrentDate.set(selectedyear, selectedmonth , selectedday, 0, 0);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                        Log.e("Date Format",dateFormat.format(mcurrentDate.getTime()));
                        editText.setText(dateFormat.format(mcurrentDate.getTime()));

                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long diff;
                if(editText.getText().length()>8){
                    Calendar today = Calendar.getInstance();
                    Log.e("Today", String.valueOf(today.getTimeInMillis()));
                    Log.e("Mcurrent", String.valueOf(mcurrentDate.getTimeInMillis()));

                    diff = today.getTimeInMillis() - mcurrentDate.getTimeInMillis();
                    Log.e("diff", String.valueOf(diff));

                    long months = (long) (diff / 2629745999.9981);
                    int year= (int) (months/12);

                    age.setText(" "+String.valueOf(year));
                    month.setText(String.valueOf(months));

                    Log.e("Year", String.valueOf(year));
                    Log.e("months **", String.valueOf(months));
                    Log.e("Total",getDurationBreakdown(diff));
                }else{
                    Toast.makeText(MainActivity.this, "Select Some value from Calander",
                            Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    public String getDurationBreakdown(long millis) {
        if(millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        day.setText(String.valueOf(days));
        hour.setText(String.valueOf(hours));
        min.setText(String.valueOf(minutes));
        sec.setText(String.valueOf(seconds));

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return sb.toString();


    }

}
