package com.fdhg.projects.toggleringer;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ToggleRinger extends Activity implements View.OnClickListener {

    private ImageView ivToggle;
    private AudioManager mAudioManager;
    private int ringerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_ringer);

        initialize();
        checkPhoneState();
    }

    private void initialize() {
        ivToggle = (ImageView) findViewById(R.id.ivToggle);
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        ivToggle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (ringerState) {
            // if silent, change to normal
            case 0:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(ToggleRinger.this,
                        R.string.toast_toggle02, Toast.LENGTH_SHORT).show();
                ringerState = 2;
                break;
            // if vibrate, change to silent
            case 1:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(ToggleRinger.this,
                        R.string.toast_toggle00, Toast.LENGTH_SHORT).show();
                ringerState = 0;
                break;
            // if normal, change to vibrate
            case 2:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                Toast.makeText(ToggleRinger.this,
                        R.string.toast_toggle01, Toast.LENGTH_SHORT).show();
                ringerState = 1;
                break;
        }
        toggleUI();
    }

    // check current ringer mode state
    private void checkPhoneState() {
        ringerState = mAudioManager.getRingerMode();
    }

    // change image according to ringer mode state
    private void toggleUI() {
        Drawable phoneImage = null;
        switch (ringerState) {
            case 0:
                phoneImage = getResources().getDrawable(R.drawable.phone_silent);
                break;
            case 1:
                phoneImage = getResources().getDrawable(R.drawable.phone_vibrate);
                break;
            case 2:
                phoneImage = getResources().getDrawable(R.drawable.phone_normal);
                break;
        }
        ivToggle.setImageDrawable(phoneImage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPhoneState();
        toggleUI();
    }
}