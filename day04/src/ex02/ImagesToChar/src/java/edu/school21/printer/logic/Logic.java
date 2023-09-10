package edu.school21.printer.logic;

import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.Ansi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Logic {
    private final Attribute whiteColor;
    private final Attribute blackColor;
    public Logic(String whiteColor, String blackColor) {
        this.whiteColor = findColor(whiteColor);
        this.blackColor = findColor(blackColor);
    }

    public void readImage(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            BufferedImage image = ImageIO.read(fileInputStream);
            int h = image.getHeight();
            int w = image.getWidth();
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    int pixel = image.getRGB(j, i);
                    if (pixel == Color.black.getRGB()) {
                        System.out.print(Ansi.colorize(" ", blackColor));
                    } else {
                        System.out.print(Ansi.colorize(" ", whiteColor));
                    }
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found!");
        } catch (IOException e) {
            System.err.println("File Can Not Be Opened!");
        }
    }

    private Attribute findColor(String color) {
        return switch (color) {
            case "BLACK" -> Attribute.BLACK_BACK();
            case "RED" -> Attribute.RED_BACK();
            case "GREEN" -> Attribute.GREEN_BACK();
            case "YELLOW" -> Attribute.YELLOW_BACK();
            case "BLUE" -> Attribute.BLUE_BACK();
            case "MAGENTA" -> Attribute.MAGENTA_BACK();
            case "CYAN" -> Attribute.CYAN_BACK();
            case "WHITE" -> Attribute.WHITE_BACK();
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
    }
}
