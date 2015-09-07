package us.zeropen.zroid.audio;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 병걸 on 2015-04-08.
 */
public class ZAudio {
    public static AssetManager assets;
    public static SoundPool soundPool;
    public static HashMap<String, ZSound> sounds;
    public static HashMap<String, ZMusic> musics;

    public static void addSound(String id, String fileName) {
        try {
            AssetFileDescriptor assetFileDescriptor = assets.openFd(fileName);
            int index = soundPool.load(assetFileDescriptor, 0);
            sounds.put(id, new ZSound(id, index, soundPool));
        } catch (IOException e) {
            throw new RuntimeException("사운드 파일 '" + fileName + "' 을 불러올 수 없습니다");
        }
    }

    public static void addMusic(String id, String fileName) {
        try {
            if (musics.get(id) == null) {
                AssetFileDescriptor assetFileDescriptor = assets.openFd(fileName);
                musics.put(id, new ZMusic(id, assetFileDescriptor));
            }
        } catch (IOException e) {
            throw new RuntimeException("음악 파일 '" + fileName + "' 을 불러올 수 없습니다");
        }
    }

    public static void removeSound(String id) {
        try {
            sounds.get(id).dispose();
            sounds.remove(id);
            System.gc();
        } catch (Exception e) {
            throw new RuntimeException("ZAudio / removeSound - " + id + " 라는 id를 가진 로딩된 사운드가 존재하지 않습니다");
        }
    }

    public static void removeMusic(String id) {
        try {
            musics.get(id).dispose();
            musics.remove(id);
            System.gc();
        } catch (Exception e) {
            throw new RuntimeException("ZAudio / removeMusic - " + id + " 라는 id를 가진 로딩된 음악이 존재하지 않습니다");
        }
    }

    public static void playSound(String id, float volume) {
        try {
            sounds.get(id).play(volume);
        } catch (Exception e) {
            throw new RuntimeException("ZAudio / playSound - " + id + " 라는 id를 가진 로딩된 사운드가 존재하지 않습니다");
        }
    }

    public static void playSound(String id) {
        try {
            sounds.get(id).play(1);
        } catch (Exception e) {
            throw new RuntimeException("ZAudio / playSound - " + id + " 라는 id를 가진 로딩된 사운드가 존재하지 않습니다");
        }
    }

    public static void playMusic(String id, float volume, boolean isLooping) {
        try {
            musics.get(id).play(volume, isLooping);
        } catch (Exception e) {
            throw new RuntimeException("ZAudio / playMusic - " + id + " 라는 id를 가진 로딩된 음악이 존재하지 않습니다");
        }
    }

    public static void playMusic(String id, boolean isLooping) {
        try {
            musics.get(id).play(1, isLooping);
        } catch (Exception e) {
            throw new RuntimeException("ZAudio / playMusic - " + id + " 라는 id를 가진 로딩된 음악이 존재하지 않습니다");
        }
    }

    public static void playMusic(String id, float volume) {
        try {
            musics.get(id).play(1, true);
        } catch (Exception e) {
            throw new RuntimeException("ZAudio / playMusic - " + id + " 라는 id를 가진 로딩된 음악이 존재하지 않습니다");
        }
    }

    public static void playMusic(String id) {
        try {
            musics.get(id).play(1, true);
        } catch (Exception e) {
            throw new RuntimeException("ZAudio / playMusic - " + id + " 라는 id를 가진 로딩된 음악이 존재하지 않습니다");
        }
    }

    public static void stopMusic(String id) {
        try {
            musics.get(id).stop();
        } catch (Exception e) {
            throw new RuntimeException("ZAudio / playMusic - " + id + " 라는 id를 가진 로딩된 음악이 존재하지 않습니다");
        }
    }

    public static void stopAllMusic() {
        Object[] m = musics.values().toArray();
        for (int i = 0; i < musics.size(); i++) {
            ((ZMusic) m[i]).stop();
        }
    }
    public static void setVolume(String id, float volume){
        musics.get(id).setVolume(volume);
    }
    public static ArrayList<String> getMusicList() {
        return new ArrayList<String>(musics.keySet());
    }

    public static ArrayList<String> getPlayingMusicList() {
        ArrayList<String> ms = new ArrayList<String>(musics.keySet());
        ArrayList<String> dms = new ArrayList<String>();

        for (String m : ms) {
            if (musics.get(m).player.isPlaying()) {
                dms.add(m);
            }
        }

        return dms;
    }

    public static ZMusic getMusic(String id) {
        return musics.get(id);
    }
}
