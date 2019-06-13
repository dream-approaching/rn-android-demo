package com.lieying.petcat.utils;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by luoyu01 on 2019/2/27
 * 同语音中的ImageHandler
 */
public class ImageHandler {
    public static final String TAG = ImageHandler.class.getSimpleName();
    @SuppressLint("SdCardPath")
    public static final String SCREENSHOT_PATH = "/sdcard/screenshot.jpg";

    public static String getImage(String path) {
        String image = null;
        final File imgFile = new File(path);
        if (imgFile.exists() && imgFile.length() > 0) {
            byte[] rawSource = null;
            final int maxLength = 500 * 1024;
            long start = System.currentTimeMillis();
            if (imgFile.length() > maxLength) {
                rawSource = BitmapUtil.compressBitmapToBytes(path, 1000, 1000,
                        85);
            } else {
                try {
                    rawSource = fileToByte(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (rawSource != null) {
                // upload handle image string formated in base64 to tv-sdk
                image = Base64.encodeToString(rawSource, Base64.NO_WRAP);
            } else {
            }
        }
        return image;
    }

    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] buffer;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer;
    }


    public static String execScreenShotsShell() {
        try {
            long start = System.currentTimeMillis();
            Process exec = Runtime.getRuntime().exec("screencap -p " + SCREENSHOT_PATH);
            //等待指令执行结束截图生成文件结束，不执行这里通过路径获取的图片是上一张
            exec.waitFor();
            exec.destroy();
            return SCREENSHOT_PATH;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
}
