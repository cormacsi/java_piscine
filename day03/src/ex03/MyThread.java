package ex03;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class MyThread implements Runnable {
    public MyThread() {
    }

    @Override
    public void run() {
        int next = 0;
        String url;
        String fileName;
        while ((next = Program.getNextNum()) != 0) {
            System.out.printf("%s start download file number %d\n", Thread.currentThread().getName(), next);
            url = Program.getFilename(next);
            fileName = getNewFileName(url, next);
            if (download(url, fileName)) {
                System.out.printf("%s finish download file number %d\n", Thread.currentThread().getName(), next);
            } else {
                System.err.printf("%s canceled downloading file number %d\n", Thread.currentThread().getName(), next);
            }
        }
    }

    private boolean download(String url, String fileName) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int count = 0;
            while((count = in.read(buffer,0,1024)) != -1) {
                out.write(buffer,0, count);
            }
        } catch (IOException e) {
            System.err.println("IOException in download!");
            return false;
        }
        return true;
    }
    private String getNewFileName(String url, int next) {
        String tmp = url.substring(url.lastIndexOf("/"));
        String fileName = String.valueOf(next);
        if (tmp.contains(".")) {
            fileName += tmp.substring(tmp.lastIndexOf("."));
        }
        return fileName;
    }
}
