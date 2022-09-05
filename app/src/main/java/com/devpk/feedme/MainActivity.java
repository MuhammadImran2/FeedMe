package com.devpk.feedme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4;
    private TextView textView2, textView4;
    private ImageView imageView2, imageView4;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linearLayout1 = findViewById(R.id.linearLayout1);
        DatabaseReference firebaseDatabaseReferencePour_food = firebaseDatabase.getReference("pour_food");

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String pour_food = dataSnapshot.getValue().toString();
                        if (pour_food.equals("0")) {
                            firebaseDatabaseReferencePour_food.setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this, "Feeding Now", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                };
                firebaseDatabaseReferencePour_food.addListenerForSingleValueEvent(valueEventListener);
            }
        });

        linearLayout3 = findViewById(R.id.linearLayout3);
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LiveStream.class);
                startActivity(intent);
            }
        });

        linearLayout2 = findViewById(R.id.linearLayout2);
        textView2 = findViewById(R.id.textView2);
        imageView2 = findViewById(R.id.imageView2);

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimerClass();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimerClass();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimerClass();
            }
        });


        linearLayout4 = findViewById(R.id.linearLayout4);
        textView4 = findViewById(R.id.textView4);
        imageView4 = findViewById(R.id.imageView4);

        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eatingRoutineClass();
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eatingRoutineClass();
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eatingRoutineClass();
            }
        });
    }

    public void setTimerClass() {
        Intent intent = new Intent(getApplicationContext(), SetTime.class);
        startActivity(intent);
    }

    public void eatingRoutineClass() {
        Intent intent = new Intent(getApplicationContext(), EatingRoutine.class);
        startActivity(intent);
    }
}