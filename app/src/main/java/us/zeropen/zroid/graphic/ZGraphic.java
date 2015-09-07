package us.zeropen.zroid.graphic;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import us.zeropen.zroid.device.ZToast;

/**
 * Created by 병걸 on 2015-03-18.
 *
 * ZTexture 는 이미지 출력시 필요한 이미지들을 저장하는 클래스입니다
 *
 * 직접 이미지를 추가하려면,
 * ZTexture.textures.add(ZImage) 와 같은 방식으로 사용할 수 있습니다
 *
 * 사용할 이미지는 /app/src/main/assets 에 저장하시기 바랍니다
 */
public class ZGraphic {
    public static enum ImageFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public static HashMap<String, Bitmap> textures;
    public static HashMap<String, Typeface> fonts;
    public static AssetManager assets;

    public static void addTexture(String id, String fileName, ImageFormat imageFormat) {
        Bitmap.Config config;
        BitmapFactory.Options options = new BitmapFactory.Options();

        if (imageFormat == ImageFormat.RGB565) {
            config = Bitmap.Config.RGB_565;
        }
        else if (imageFormat == ImageFormat.ARGB4444) {
            config = Bitmap.Config.ARGB_4444;
        }
        else {
            config = Bitmap.Config.ARGB_8888;
        }
        options.inPreferredConfig = config;

        InputStream inputStream = null;
        Bitmap bitmap = null;

        try {
            inputStream = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            if (bitmap == null) {
                throw new RuntimeException("ZGraphic / addTexture - 이미지 파일 " + fileName + " 을 불러올 수 없습니다.");
            }
        }
        catch (IOException e) {
            throw new RuntimeException("ZGraphic / addTexture - 이미지 파일 " + fileName + " 을 불러올 수 없습니다.");
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    //
                }
            }
        }

        if (textures.get(id) == null)
            textures.put(id, bitmap);
    }

    public static void addTexture(String id, Bitmap bitmap) {
        textures.put(id, bitmap);
    }

    public static void addTexture(String id, String fileName) {
        addTexture(id, fileName, ImageFormat.ARGB8888);
    }

    public static Bitmap getTexture(String id) {
        try {
            return textures.get(id);
        }
        catch (Exception e) {
            throw new RuntimeException("ZGraphic / getTexture - " + id + "라는 id를 가진 로딩된 이미지가 존재하지 않습니다");
        }
    }

    public static void removeTexture(String id) {
        try {
            if (textures.get(id) != null) {
                textures.get(id).recycle();
                textures.remove(id);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearTexture() {
        textures.clear();
        System.gc();
    }

    public static void addFont(String id, String fileName) {
        Typeface font;
        try {
            font = Typeface.createFromAsset(assets, fileName);
        }
        catch (Exception e) {
            throw new RuntimeException(fileName + "을 불러올 수 없습니다.");
        }

        fonts.put(id, font);
    }

    public static Typeface getFont(String id) {
        try {
            return fonts.get(id);
        }
        catch (Exception e) {
            throw new RuntimeException("ZGraphic / getFont - " + id + " 라는 id를 가진 로딩된 폰트가 존재하지 않습니다");
        }
    }

    public static void removeFont(String id) {
        try {
            fonts.remove(id);
            System.gc();
        }
        catch (Exception e) {
            throw new RuntimeException("ZGraphic / removeFont - " + id + " 라는 id를 가진 로딩된 폰트가 존재하지 않습니다");
        }
    }

    public static void clearFont() {
        fonts.clear();
        System.gc();
    }
}
