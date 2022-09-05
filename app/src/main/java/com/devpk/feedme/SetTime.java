package com.devpk.feedme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wx.wheelview.widget.WheelViewDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SetTime extends AppCompatActivity {

    private LinearLayout linearLayoutSetTimer, linearLayoutFeedInterval;
    private TextView textViewSetTimer;
    private TextView time;
    private String feedTime;
    private String feedTime24Hours;

    private ImageView imageViewSetTimer;
    int hour, min;
    FirebaseDatabase database;
    DatabaseReference feedTimeMyRef;
    DatabaseReference feedIntervalMyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        database = FirebaseDatabase.getInstance();
        feedTimeMyRef = database.getReference("feedTime");
        feedIntervalMyRef = database.getReference("feedInterval");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        time = findViewById(R.id.time);

        linearLayoutFeedInterval = findViewById(R.id.linearLayoutFeedInterval);
        linearLayoutFeedInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_wheelView();
            }
        });


        linearLayoutSetTimer = findViewById(R.id.linearLayoutFeedTime);
        textViewSetTimer = findViewById(R.id.textViewFeedTime);
        imageViewSetTimer = findViewById(R.id.imageViewSetTimer);

        linearLayoutSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker();
            }
        });

        textViewSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker();
            }
        });

        imageViewSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker();
            }
        });

    }

    private void show_wheelView() {
        WheelViewDialog dialog = new WheelViewDialog(this);
        dialog.setTitle("Select Hour For Feed Interval")
                .setItems(wheel_count())
                .setDialogStyle(Color.parseColor("#f8cec9"))
                .setCount(5)
                .setLoop(false)
                .setButtonText("Ok")
                .setOnDialogItemClickListener(new WheelViewDialog.OnDialogItemClickListener() {
                    @Override
                    public void onItemClick(int position, String s) {
                        feedIntervalMyRef.setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(SetTime.this, "Update Feed Interval " + s, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SetTime.this, "Error " + e, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).show();
    }

    private ArrayList<String> wheel_count() {
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("0");
        arraylist.add("1");
        arraylist.add("2");
        arraylist.add("3");
        arraylist.add("4");
        arraylist.add("5");
        arraylist.add("6");
        arraylist.add("7");
        arraylist.add("8");
        arraylist.add("9");
        arraylist.add("10");
        arraylist.add("11");
        arraylist.add("12");
        arraylist.add("13");
        arraylist.add("14");
        arraylist.add("15");
        arraylist.add("16");
        arraylist.add("17");
        arraylist.add("18");
        arraylist.add("19");
        arraylist.add("20");
        arraylist.add("21");
        arraylist.add("22");
        arraylist.add("23");
        arraylist.add("24");
        return arraylist;
    }


    public void timePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHours, int selectedMin) {
                hour = selectedHours;
                min = selectedMin;

                boolean isPM = (selectedHours >= 12);
                feedTime = String.format("%02d:%02d %s",
                        (selectedHours == 12 || selectedHours == 0) ? 12 : selectedHours % 12,
                        selectedMin, isPM ? "PM" : "AM");
                time.setText(feedTime);

                updateFeedTime();
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(SetTime.this, onTimeSetListener, hour,
                min, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    @SuppressLint("SimpleDateFormat")
    private void updateFeedTime() {
        SimpleDateFormat h_mm_a = new SimpleDateFormat("h:mm a");
        SimpleDateFormat hh_mm = new SimpleDateFormat("HH:mm");

        try {
            Date d1 = h_mm_a.parse(feedTime);
            feedTime24Hours = hh_mm.format(d1);

            feedTimeMyRef.setValue(feedTime24Hours).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(SetTime.this, "Update Feed Time " + feedTime24Hours, Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SetTime.this, "Error " + e, Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Log.i("Checking ", "" + e);
            e.printStackTrace();
        }


    }

}