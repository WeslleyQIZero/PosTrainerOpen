package com.wiseass.postrainer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.wiseass.postrainer.R;
import com.wiseass.postrainer.api.WriteToDatabase;
import com.wiseass.postrainer.model.database.AlarmDatabase;
import com.wiseass.postrainer.model.objects.Reminder;

import java.io.IOException;

/**
 * Alarm Receiver. In need of being cleaned the f*** up when I have more time.
 * Created by Ryan on 17/04/2016.
 */
public class ActivityAlarmReceiver extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    private static final String VIBRATE_ONLY = "VIBRATE_ONLY";
    private static final String BUNDLE_ITEM = "BUNDLE_ITEM";
    private static final boolean VIBRATE_ONLY_DEFAULT = false;

    private PowerManager.WakeLock wakeLock;
    private MediaPlayer mediaPlayer;

    private Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("AR", "Alarm Fired");
        super.onCreate(savedInstanceState);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Wake Log");
        wakeLock.acquire();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_alarm_receiver);

        Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if (vibe != null) {
                    vibe.cancel();
                }
                finish();
                Intent i = new Intent(ActivityAlarmReceiver.this, ActivityReminders.class);
                startActivity(i);
            }
        });

        Reminder item = getIntent().getParcelableExtra(BUNDLE_ITEM);

        if (!item.isRenewAutomatically()) {
            item.setActive(false);
        }
        writeReminderToDatabase(item);
        if (getIntent().getBooleanExtra(VIBRATE_ONLY, VIBRATE_ONLY_DEFAULT)) {
            vibrate();
        } else {
            vibrate();
            String toneURI = getAlarmUri().toString();
            playSound(this, Uri.parse(toneURI));
        }
    }

    private void writeReminderToDatabase(Reminder item) {
        WriteToDatabase writer = new WriteToDatabase();
        writer.setData(item);
        writer.execute(AlarmDatabase.getInstance(getApplicationContext()));
        writer.setWriteCompleteListener(new WriteToDatabase.OnWriteComplete() {
            @Override
            public void setWriteComplete(long result) {

            }
        });
    }

    private void vibrate() {
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] vPatternOne = {0, 1000, 2000, 1000, 2000, 1000, 2000, 1000, 2000};
        vibe.vibrate(vPatternOne, -1);
    }

    private Uri getAlarmUri() {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }

    private void playSound(Context context, Uri alert) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager =
                    (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                    }
                }.start();
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.prepareAsync();
            }
        } catch (IOException e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }
}
