package com.skyblue.androidfileshareandreceiveotherapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;

import com.skyblue.androidfileshareandreceiveotherapps.databinding.ActivityMainBinding;

import java.net.URI;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        if (intent != null){
            String action = intent.getAction();
            String type = intent.getType();
            if (Intent.ACTION_SEND.equals(action) && type != null){
                if (type.equalsIgnoreCase("text/plain")){
                    handleTextData(intent);
                }else if (type.startsWith("image/")){
                    handleImageIntent(intent);
                }else if (type.equalsIgnoreCase("video/*")){
                    handleVideoIntent(intent);
                }

            }else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null){
                if (type.startsWith("image/")){
                    handleMultipleImage(intent);
                }
            }
        }
    }

    private void handleMultipleImage(Intent intent) {

    }

    private void handleVideoIntent(Intent intent) {
        Uri videoFile = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (videoFile != null) {
            Log.d("video file path :" , "" + Objects.requireNonNull(videoFile.getPath()));

            binding.textView.setText(Objects.requireNonNull(videoFile.getPath()));

            //Set MediaController  to enable play, pause, forward, etc options.
            MediaController mediaController= new MediaController(this);
            mediaController.setAnchorView(binding.videoView);
            //Location of Media File
          //  Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);
            //Starting VideView By Setting MediaController and URI
            binding.videoView.setMediaController(mediaController);
            binding.videoView.setVideoURI(videoFile);
            binding.videoView.requestFocus();
            binding.videoView.start();
        }

    }

    private void handleImageIntent(Intent intent) {
        Uri imageFile = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageFile != null && !imageFile.equals(Uri.EMPTY)) {
            Log.d("Image file path :" , Objects.requireNonNull(imageFile.getPath()));

            binding.imageView.setImageURI(imageFile);
        }
    }

    private void handleTextData(Intent intent) {
        String textData = intent.getStringExtra(Intent.EXTRA_TEXT);

            Log.d("Intent received text :" , "" + textData);
            binding.textView.setText(textData);
    }
}