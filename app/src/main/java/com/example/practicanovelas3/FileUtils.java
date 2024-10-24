package com.example.practicanovelas3;
import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileUtils {

    // Save data to internal storage
    public static void saveToInternalStorage(Context context, String fileName, String data) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read data from internal storage
    @NonNull
    public static String getFromInternalStorage(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        return readFromFile(file);
    }

    // Save data to external storage (public directory)
    public static void saveToExternalStorage(String fileName, String data) throws IOException {
        if (isExternalStorageWritable()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        } else {
            throw new IOException("External storage is not writable");
        }
    }

    // Read data from external storage
    public static String getFromExternalStorage(String fileName) throws IOException {
        if (isExternalStorageReadable()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
            return readFromFile(file);
        } else {
            throw new IOException("External storage is not readable");
        }
    }

    // Helper function to read from a file
    private static String readFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    // Check if external storage is writable
    public static boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // Check if external storage is readable
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
