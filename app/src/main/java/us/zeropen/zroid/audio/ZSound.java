package us.zeropen.zroid.audio;


import android.media.SoundPool;

/**
 * Created by 병걸 on 2015-04-08.
 */
public class ZSound {
    private final String id;
    private final int index;
    private SoundPool soundPool;

    public ZSound(String _id, int _index, SoundPool _soundPool) {
        id = _id;
        index = _index;
        soundPool = _soundPool;
    }

    public String getId() {
        return id;
    }

    public void play(float volume) {
        soundPool.play(index, volume, volume, 0, 0, 1);
    }

    public void dispose() {
        soundPool.unload(index);
    }
}
