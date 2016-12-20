package com.app.mukesh.androidtrials;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by sahana on 4/12/16.
 */
public class FileExperimentActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_experiment_activity);
        findViewById(R.id.split_size_btn).setOnClickListener(this);
        findViewById(R.id.split_line_btn).setOnClickListener(this);
    }

    public void splitFilesBySize(String filePath, long bytesPerChunk) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        String path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        File dir = new File(path + fileName);
        dir.mkdir();
        path = path + fileName + "/";
        File file = new File(filePath);
        if (file != null && file.exists()) {
            try {
                DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
                long sourceLength = inputStream.available();
                int numOfChunks = (int) (sourceLength / bytesPerChunk);
                long remainingBytes = sourceLength % bytesPerChunk;
                int maxReadBufferSize = 8 * 1024;
                for (int destIx = 1; destIx <= numOfChunks; destIx++) {
                    BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(new File(path + fileName + "_part" + destIx + ".txt")));
                    if (bytesPerChunk > maxReadBufferSize) {
                        long numReads = bytesPerChunk / maxReadBufferSize;
                        long numRemainingRead = bytesPerChunk % maxReadBufferSize;
                        for (int i = 0; i < numReads; i++) {
                            readWrite(inputStream, bw, maxReadBufferSize);
                        }
                        if (numRemainingRead > 0) {
                            readWrite(inputStream, bw, numRemainingRead);
                        }
                    } else {
                        readWrite(inputStream, bw, bytesPerChunk);
                    }
                    bw.close();
                }
                if (remainingBytes > 0) {
                    BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(new File(path + fileName + "_part" + (numOfChunks + 1) + ".txt")));
                    readWrite(inputStream, bw, remainingBytes);
                    bw.close();
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readWrite(DataInputStream inputStream, BufferedOutputStream bw, long numBytes) {
        byte[] buf = new byte[(int) numBytes];
        int val;
        try {
            val = inputStream.read(buf);
            if (val != -1) {
                bw.write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void splitByLine(String filePath, long bytesPerChunk) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        String path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        File dir = new File(path + fileName + "_lines");
        dir.mkdir();
        path = dir.getAbsolutePath() + "/";
        File file = new File(filePath);
        if (file != null && file.exists()) {

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                long sourceLength = file.length();
                long totalBytesRead = 0;
                int partCount = 0;
                while (totalBytesRead < sourceLength) {
                    long bytesRead = 0;
                    BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(new File(path + fileName + "_part" + (++ partCount) + ".txt")));
                    while (bytesRead < bytesPerChunk) {
                        String line = reader.readLine();
                        if (!TextUtils.isEmpty(line)) {
                            bytesRead += line.getBytes().length;
                            reader.mark(Integer.MAX_VALUE);
                            if (bytesRead <= bytesPerChunk) {
                                write(line.getBytes(), bw, bytesRead);
                            } else {
                                //reader.reset();
                            }
                        }
                    }
                    bw.close();
                    totalBytesRead += bytesRead;
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void write(byte[] data, BufferedOutputStream bw, long bytesRead) {
        try {
            bw.write(data);
            bw.write(new String("\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.split_size_btn:
                Thread splitThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        splitFilesBySize(Environment.getExternalStorageDirectory().getAbsolutePath() + "/large_file.txt", 10 * 1024);
                    }
                });
                splitThread.start();
                break;
            case R.id.split_line_btn:
                Thread splitLineThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        splitByLine(Environment.getExternalStorageDirectory().getAbsolutePath() + "/large_file.txt", 10 * 1024);
                    }
                });
                splitLineThread.start();
                break;
        }
    }
}
