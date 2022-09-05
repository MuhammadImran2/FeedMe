package com.devpk.feedme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyWorker extends Worker {

    private FirebaseDatabase firebaseDatabase;
    private Context ctx;

    public MyWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        ctx = context;
    }

    @Override
    public Result doWork() {
        try {
            for (int i = 0; i < 900; i++) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference firebaseDatabaseReferenceNeeds_filling = firebaseDatabase
                        .getReference("needs_filling");
                firebaseDatabaseReferenceNeeds_filling.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String needs_filling = dataSnapshot.getValue().toString();
                        if (needs_filling.equals("0")) {

                        } else {
                            // Configure the channel
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                            NotificationChannel channel = new NotificationChannel("myChannelId",
                                    "My Channel", importance);
                            channel.setDescription("Reminders");

                            // Register the channel with the notifications manager
                            NotificationManager mNotificationManager =
                                    (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.createNotificationChannel(channel);

                            NotificationCompat.Builder mBuilder =
                                    // Builder class for devices targeting API 26+ requires a channel ID
                                    new NotificationCompat.Builder(ctx, "myChannelId")
                                            .setSmallIcon(R.mipmap.ic_launcher_round)
                                            .setContentTitle("Feed Me")
                                            .setContentText("Refill Alert");

                            // mId allows you to update the notification later on.
                            mNotificationManager.notify(100, mBuilder.build());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                Thread.sleep(15000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return Result.success();
    }
}
