package com.devpk.feedme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class LiveStream extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stream);
        custom_Dialog();
    }

    @Override
    public void onBackPressed() {
        simpleExoPlayer.release();
        simpleExoPlayer.stop();
        super.onBackPressed();
    }

    private void custom_Dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_custom_dialog);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        final EditText editTextUrl = dialog.findViewById(R.id.url);
        final Button button = dialog.findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editTextUrl.getText().toString();
                if (url.isEmpty()){
                    Toast.makeText(LiveStream.this, "Please Enter Url !", Toast.LENGTH_SHORT).show();
                } else {
                    simpleExoPlayer = new SimpleExoPlayer.Builder(getApplicationContext()).build();
                    playerView = findViewById(R.id.exoPlayer);
                    playerView.setPlayer(simpleExoPlayer);
                    MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
                    simpleExoPlayer.addMediaItem(mediaItem);
                    simpleExoPlayer.prepare();
                    simpleExoPlayer.play();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}