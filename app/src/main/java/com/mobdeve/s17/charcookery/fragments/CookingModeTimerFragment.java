package com.mobdeve.s17.charcookery.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.mobdeve.s17.charcookery.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CookingModeTimerFragment extends Fragment {

    private TextView timerTxtView;
    private ImageButton startTimerBtn;
    private ImageButton pauseTimerBtn;
    private ImageButton restartTimerBtn;
    private Button setTimerBtn;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 00;
    private boolean timerRunning = false;

    private int hour, minute;

    public CookingModeTimerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cookingmode, container, false);

        timerTxtView = view.findViewById(R.id.timerTxtView);
        startTimerBtn = view.findViewById(R.id.startTimerBtn);
        pauseTimerBtn = view.findViewById(R.id.pauseTimerBtn);
        restartTimerBtn = view.findViewById(R.id.restartTimerBtn);
        setTimerBtn = view.findViewById(R.id.setTimerBtn);

        updateTimer();

        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the timer was set, start the timer
                if (timeLeftInMillis != 0) {
                    startTimer();
                }
                // Else pop up the time picker
                else {
                    popTimePicker(v);
                }
            }
        });

        pauseTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        restartTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartTimer();
            }
        });

        return view;
    }

    private void startTimer() {
        if (!timerRunning) {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                }
            }.start();

            timerRunning = true;
        }
    }

    private void pauseTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
        }
    }

    private void restartTimer() {
        countDownTimer.cancel();
        timeLeftInMillis = 00; // Reset to initial time
        timerRunning = false;
        updateTimer();
    }

    private void updateTimer() {
        NumberFormat f = new DecimalFormat("00");
        long hour = (timeLeftInMillis / 3600000) % 24;
        long min = (timeLeftInMillis / 60000) % 60;
        long sec = (timeLeftInMillis / 1000) % 60;
        timerTxtView.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
    }

    public void setTime(int hour, int minute) {
        // Convert hour and minute to milliseconds
        timeLeftInMillis = (hour * 60L + minute) * 60L * 1000L;

        // Set the time to the TextView in CookingModeFragment
        NumberFormat f = new DecimalFormat("00");
        timerTxtView.setText(f.format(hour) + ":" + f.format(minute) + ":00");
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                setTime(hour, minute);
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}
