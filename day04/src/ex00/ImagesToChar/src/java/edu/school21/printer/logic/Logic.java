package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logic {

    private static final int whiteRGB = Color.white.getRGB();
    private static final int blackRGB = Color.black.getRGB();
    public static List<String> readImage(File file, char white, char black) {
        List<String> list = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            BufferedImage image = ImageIO.read(fileInputStream);
            int h = image.getHeight();
            int w = image.getWidth();
            for (int i = 0; i < h; i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < w; j++) {
                    int pix = image.getRGB(j, i);
                    if (pix == whiteRGB) {
                        row.append(white);
                    } else if (pix == blackRGB) {
                        row.append(black);
                    } else {
                        row.append(" ");
                    }
                }
                list.add(row.toString());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found!");
        } catch (IOException e) {
            System.err.println("File Can Not Be Opened!");
        }
        return list;
    }
}
