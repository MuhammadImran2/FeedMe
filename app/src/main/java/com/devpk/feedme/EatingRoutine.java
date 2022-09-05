package com.devpk.feedme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EatingRoutine extends AppCompatActivity {

    private TextView mg_consumed;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eating_routine);
        mg_consumed = findViewById(R.id.mg_consumed);
        firebaseDatabase = FirebaseDatabase.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DatabaseReference firebaseDatabaseReferencePoured_today = firebaseDatabase.
                getReference("poured_today");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String poured_today = dataSnapshot.getValue().toString();
                mg_consumed.setText(poured_today + " g consumed");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        firebaseDatabaseReferencePoured_today.addListenerForSingleValueEvent(valueEventListener);

    }
}