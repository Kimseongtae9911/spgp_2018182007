package com.example.termproject.MonsterSurvival.framework.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Data {
    public static String loadJSON(Context context, String fileName) {
        String json = null;

        // Check if the modified file exists in the internal storage
        File file = new File(context.getFilesDir(), fileName);
        if (file.exists()) {
            try {
                FileInputStream fis = context.openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                isr.close();
                fis.close();

                json = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // If the modified file doesn't exist in the internal storage, load from the assets folder
            try {
                InputStream inputStream = context.getAssets().open(fileName);
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    public static void  writeJSONToFile(Context context, String fileName, String json) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearStatInfo(Context context) {
        File file = new File(context.getFilesDir(), "statInfo.json");
        if (file.exists()) {
            file.delete();
        }
    }
}
