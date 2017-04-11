package com.frame.huxh.mvpdemo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class FileUtils {

    public static boolean saveFile(byte[] data, String filePath) {
        BufferedOutputStream os = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            os = new BufferedOutputStream(new FileOutputStream(file));
            os.write(data);
            os.flush();
        } catch (Exception e) {
            return false;
        } finally {
            StreamUtils.closeStream(os);
        }
        return true;
    }

    public static byte[] readFile(String filePath) {
        BufferedInputStream is = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            is = new BufferedInputStream(new FileInputStream(file));
            int len = is.available();
            byte[] data = new byte[len];
            is.read(data);
            return data;
        } catch (Exception e) {
            return null;
        } finally {
            StreamUtils.closeStream(is);
        }
    }

    public static String readFile(String filePath, String charset) {
        byte[] data = readFile(filePath);
        if (data != null && data.length > 0) {
            try {
                return new String(data, charset);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String readLine(String filePath) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(filePath, "r");
            return file.readLine();
        } catch (Exception e) {
            return null;
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static boolean fileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static void appendFile(String data, String filePath) {
        BufferedWriter writer = null;
        try {
            File file = getFile(filePath);
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(data);
            writer.flush();
        } catch (Exception e) {
        } finally {
            StreamUtils.closeStream(writer);
        }
    }

    public static BufferedWriter appendFile(BufferedWriter writer, File file, String data) {
        try {
            if (writer == null) {
                writer = new BufferedWriter(new FileWriter(file, true));
            }
            writer.append(data);
            writer.flush();
        } catch (Exception e) {
            StreamUtils.closeStream(writer);
            writer = null;
        }
        return writer;
    }

    public static File getFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            File pFile = file.getParentFile();
            if (!pFile.exists()) {
                pFile.mkdirs();
            }

            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
        return file;
    }

    public static String getExternalSDCardPath() {
        String path;
        try {
            path =  Environment.getExternalStorageDirectory().getAbsolutePath();
        }catch (Exception e){
            path = "";
        }
        return path;
    }

    public static boolean isExternalSDCardWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalSDCardReadable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }

    public static boolean mkdirIfNotExist(String path) {
        File file = new File(path);
        boolean ret = true;
        if (!file.exists()) {
            try {
                ret = file.mkdirs();
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        int index = filePath.lastIndexOf("/");
        if (index >= 0) {
            return filePath.substring(index + 1);
        } else {
            return filePath;
        }
    }

    public static String getFileNameWithoutSuffix(String filePath) {
        String fileName = getFileName(filePath);
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int index = fileName.lastIndexOf(".");
        if (index >= 0) {
            return fileName.substring(0, index);
        } else {
            return fileName;
        }
    }

    public static String getFileSuffix(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        int index = filePath.lastIndexOf(".");
        if (index >= 0) {
            return filePath.substring(index + 1);
        } else {
            return "";
        }
    }

    public static String getFileDir(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        int index = filePath.lastIndexOf("/");
        if (index >= 0) {
            return filePath.substring(0, index);
        } else {
            return "";
        }
    }

    public static void removeDir(String dir) {
        removeDir(dir, true);
    }

    public static void removeDir(String dir, boolean removeRootDir) {
        File dirFile = new File(dir);
        File[] subFiles = dirFile.listFiles();
        if (!CollectionUtils.isEmpty(subFiles)) {
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    removeDir(subFile.getAbsolutePath());
                } else {
                    subFile.delete();
                }
            }
        }
        if (removeRootDir) {
            dirFile.delete();
        }
    }

    public static InputStream readAssetFile(Context context, String filePath) throws IOException {
        AssetManager am = context.getAssets();
        return am.open(filePath);
    }

    /**
     * 获取目录下所有文件，返回的为文件相对于dir的相对路径
     *
     * @param dir
     * @return
     */
    public static List<String> getAllFiles(String dir) {
        List<String> list = new ArrayList<String>();
        list.addAll(getAllFiles(null, dir));
        return list;
    }

    private static List<String> getAllFiles(String dirName, String path) {
        List<String> list = new ArrayList<String>();
        File dirFile = new File(path);
        File[] subFiles = dirFile.listFiles();
        if (!CollectionUtils.isEmpty(subFiles)) {
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    list.addAll(getAllFiles(TextUtils.isEmpty(dirName) ? subFile.getName() : dirName + "/" + subFile.getName(), path + "/" + subFile.getName()));
                } else {
                    String name = TextUtils.isEmpty(dirName) ? subFile.getName() : dirName + "/" + subFile.getName();
                    list.add(name);
                }
            }
        }
        return list;
    }

    /**
     * 获取子目录名
     *
     * @param dir
     * @return
     */
    public static List<String> getSubDirs(String dir) {
        List<String> list = new ArrayList<String>();
        File dirFile = new File(dir);
        File[] subFiles = dirFile.listFiles();
        if (!CollectionUtils.isEmpty(subFiles)) {
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    list.add(subFile.getName());
                }
            }
        }
        return list;
    }

    public static long getFileSize(String paramString) {
        if (TextUtils.isEmpty(paramString))
            return 0L;
        return new File(paramString).length();
    }

    public static boolean isInSDcard0() {
        return false;
    }

    public static boolean isExternalSDcard(Context context, String filePath) {
        // internal file path
        ConcurrentLinkedQueue<String> mInternalPathList = new ConcurrentLinkedQueue<String>();
        // external file path
        ConcurrentLinkedQueue<String> mExternalPathList = new ConcurrentLinkedQueue<String>();

        StorageManager mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        if (mStorageManager != null) {
            Class<?> mStorageVolume = null;
            Method mGetVolumeListMethod = null;
            Method mGetVolumeStateMethod = null;
            Method mGetPathMethod = null;
            Method mIsRemovableMethod = null;
            Object[] mStorageVolumeList = null;
            try {
                mStorageVolume = Class.forName("android.os.storage.StorageVolume");
                mGetVolumeListMethod = mStorageManager.getClass().getMethod("getVolumeList", new Class[0]);
                mGetVolumeStateMethod = mStorageManager.getClass().getMethod("getVolumeState", new Class[]{String.class});

                mIsRemovableMethod = mStorageVolume.getMethod("isRemovable", new Class[0]);
                mGetPathMethod = mStorageVolume.getMethod("getPath", new Class[0]);

                mStorageVolumeList = (Object[]) mGetVolumeListMethod.invoke(mStorageManager, new Object[0]);

                boolean mIsRemovable = false;

                if (mStorageVolumeList != null && mStorageVolumeList.length > 0) {
                    int mStorageVolumeCount = mStorageVolumeList.length;

                    mInternalPathList.clear();
                    mExternalPathList.clear();

                    for (int i = 0; i < mStorageVolumeCount; ++i) {
                        String mStoragePath = (String) mGetPathMethod.invoke(mStorageVolumeList[i], new Object[0]);
                        mIsRemovable = ((Boolean) mIsRemovableMethod.invoke(mStorageVolumeList[i], new Object[0])).booleanValue();
                        if (!TextUtils.isEmpty(mStoragePath)) {
                            String state = (String) mGetVolumeStateMethod.invoke(mStorageManager, new Object[]{mStoragePath});
                            if ((state != null) && (state.equals("mounted"))) {
                                if (mIsRemovable) {
                                    mExternalPathList.add(mStoragePath);
                                } else {
                                    mInternalPathList.add(mStoragePath);
                                }
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
            } catch (NoSuchMethodException e) {
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        // 外置存储卡路径
        String exterPath = mExternalPathList.peek();
        if (exterPath != null && filePath.startsWith(exterPath)) {
            return true;
        }
        return false;
    }

    /**
     * 使用文件通道的方式复制文件
     *
     * @param oldPath 源文件
     * @param newPath 复制到的新文件
     */
    public static boolean copyFileByChannel(File oldPath, File newPath) {
        boolean ret = false;
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(oldPath);
            fo = new FileOutputStream(newPath);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
            ret = true;
        } catch (Exception e) {
            ret = false;
            e.printStackTrace();
            Log.e("FileUtils.class", "copyFileByChannel() Exception", e);
        } finally {
            try {
                if (fi != null) {
                    fi.close();
                }
                if (in != null) {
                    in.close();
                }
                if (fo != null) {
                    fo.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                ret = false;
                e.printStackTrace();
            }
        }
        return ret;
    }

    private static AtomicLong atomicLong = new AtomicLong(0);
    private static final long MAX_INCREMENT_AND_GET = 99;

    public static synchronized String createFileName() {
        return createFileName(null, null);
    }

    public static synchronized String createFileName(String prefix, String extension) {
        String fileName = null;
        if (prefix == null) {
            fileName = "";
        } else {
            fileName = prefix;
        }


        Date date = new Date(System.currentTimeMillis());
        String dateString = DateUtils.format("yyMMddHHmmss", date);
        NumberFormat formatter = new DecimalFormat("00");

        long incrementAndGet = atomicLong.incrementAndGet();
        if (incrementAndGet >= MAX_INCREMENT_AND_GET) {
            atomicLong.set(0);
        }
        String number = formatter.format(incrementAndGet);
        fileName = fileName + dateString + number;

        if (extension != null && extension.length() != 0) {
            fileName = fileName + "." + extension;
        }
        return fileName;
    }

    public static void removeAllFiles(String directory) {
        boolean sdcardfound = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!sdcardfound) {
            return;
        }

        if (!TextUtils.isEmpty(directory)) {
            File dir = new File(directory);
            if (dir != null) {
                File[] imagefiles = dir.listFiles();
                if (imagefiles != null && imagefiles.length > 0) {
                    for (File image : imagefiles) {
                        if (image != null) {
                            image.delete();
                        }
                    }
                }
            }
        }
    }

    /**
     * 复制文件夹
     *
     * @param sourceDir
     * @param targetDir
     * @throws Exception
     */
    public static void copyDirectiory(String sourceDir, String targetDir) throws Exception {
        Log.i("FileUtils.class", " copyDirectiory()");
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        if (file == null) {
            throw new Exception("sourceDir is null ");
        }
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * 复制文件
     *
     * @param sourceFile
     * @param targetFile
     * @throws Exception
     */
    public static void copyFile(File sourceFile, File targetFile) throws Exception {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    public static String replaceSuffix(String filename, String newSuffix) {
        if (TextUtils.isEmpty(filename)) {
            return filename;
        }
        int index = filename.lastIndexOf(".");
        if (index >= 0) {
            String dest = filename.substring(0, index + 1);
            return dest + newSuffix;
        } else {
            return filename;
        }
    }

    /**
     * 转换文件的大小以B,KB,M,G等计算
     *
     * @param fileS
     * @return
     */
    public static String formetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.000");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static boolean exist(String filePath) {
        return TextUtils.isEmpty(filePath) ? false : new File(filePath).exists();
    }

    /**
     * 获取扩展名
     *
     * @param file
     * @return
     */
    public static String getExtension(final File file) {
        String suffix = "";
        String name = file.getName();
        final int idx = name.lastIndexOf(".");
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    public static class FileComParator implements Comparator<File> {

        /**
         * 按时间排序
         *
         * @param lhs
         * @param rhs
         * @return
         */
        @Override
        public int compare(File lhs, File rhs) {
            if (lhs.lastModified() < rhs.lastModified()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
