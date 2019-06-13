package com.lieying.petcat.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by user on 2017/9/12.
 */

public class FilePathUtils {
    private static final String CACHE_DIR = "d_cache";

    public static String getSaveThumbnailImagePath(Context context) {
        return getExternalFilesDirPath(context, Environment.DIRECTORY_PICTURES);
    }

    public static String getSavePath(Context context) {//更新包文件夹路径
        return getExternalFilesDirPath(context, Environment.DIRECTORY_DOWNLOADS);
    }

    public static String getExternalFilesDirPath(Context context, String dirName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File dir = context.getExternalFilesDir(dirName);
            if (dir != null) {
                return dir.getPath();
            }
        }
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + context.getPackageName();
        File dir = new File(path);
        boolean success = true;
        if (!dir.isDirectory()) {
            success = dir.mkdirs();
        }
        return success ? path : null;
    }

    /**
     * 在 i_cache 文件夹下创建子文件夹，可以删除 i_cache 的所有文件
     *
     * @param context
     * @param dirName
     * @return
     */
    public static String getCanDeleteCacheExternalFilesDirChildPath(Context context, String dirName) {
        String cacheDirPath = getCanDeleteCacheExternalFilesDirPath(context);
        File file = new File(cacheDirPath, dirName);
        boolean state = true;
        if (!file.isDirectory()) {
            if (file.exists()) {
                state = file.delete();
            }
            state = file.mkdirs();
        }
        return state ? file.getPath() : null;
    }

    //可以删除的缓存文件夹路径
    public static String getCanDeleteCacheExternalFilesDirPath(Context context) {
        return getExternalFilesDirPath(context, CACHE_DIR);
    }
}
