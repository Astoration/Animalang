package us.zeropen.zroid.audio;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.media.MediaPlayer.OnCompletionListener;

/**
 * Created by 병걸 on 2015-04-15.
 */

public class ZMusic implements OnCompletionListener, OnSeekCompleteListener, OnPreparedListener, OnVideoSizeChangedListener {
    public MediaPlayer player;
    private boolean isPrepared = false;
    private boolean needPrepare = true;
    private final String id;

    public ZMusic(String id, AssetFileDescriptor assetFileDescriptor) {
        this.id = id;
        this.player = new MediaPlayer();

        try {
            player.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            player.prepare();
            isPrepared = true;

            player.setOnCompletionListener(this);
            player.setOnSeekCompleteListener(this);
            player.setOnPreparedListener(this);
            player.setOnVideoSizeChangedListener(this);
        } catch (Exception e) {
            throw new RuntimeException(id + "를 불러올 수 없습니다");
        }
    }

    public String getId() {
        return id;
    }

    public void dispose() {
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
    }

    public void play(float volume, boolean isLooping) {
        if (!player.isPlaying()) {
            try {
                if (!isPrepared) {
                    if (needPrepare) {
                        player.prepare();
                    }
                    player.setVolume(volume, volume);
                }

                setLooping(isLooping);
                player.start();
                needPrepare = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (player.isPlaying()) {
            player.stop();

            synchronized (this) {
                isPrepared = false;
            }
        }
    }

    public void seekBegin() {
        player.seekTo(0);
    }

    public void setLooping(boolean isLooping) {
        player.setLooping(isLooping);
    }

    public void setVolume(float volume) {
        player.setVolume(volume, volume);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        synchronized (this) {
            isPrepared = false;
            needPrepare = false;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        synchronized (this) {
            isPrepared = true;
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }
}
