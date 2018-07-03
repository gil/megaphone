package net.somepixels.megaphone;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Vibrator;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationListener extends NotificationListenerService {

    private MediaPlayer mediaPlayer = null;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        super.onNotificationPosted(sbn, rankingMap);

        if( sbn.getPackageName().equals("com.pushbullet.android") || sbn.getPackageName().equals("com.nest.android") ) {
            SystemClock.sleep(3000);
            //wakeDisplay();
            vibrate();
            playAlarm();
        }
    }

    /*private void wakeDisplay() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wl.acquire();
        wl.release();
    }*/

    private void vibrate() {
        try {

            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = { 0, 1000, 100, 1000, 100, 1000 };
            v.vibrate(pattern, -1);

        } catch(Exception e ) {}
    }

    private void playAlarm() {
        try {

            createMediaPlayer();
            mediaPlayer.start();
            SystemClock.sleep(6000);
            mediaPlayer.start();

        } catch(Exception e ) {}
    }

    private void createMediaPlayer() {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.ding_dong));
                mediaPlayer.prepare();
            }
        } catch(Exception e ) {}
    }

    private void destroyMediaPlayer() {
        try {
            if( mediaPlayer != null ) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch(Exception e ) {}
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        destroyMediaPlayer();
    }
}