package kenny.ml.utils;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by kenny on 4/5/15.
 */
public class ImageUtils {

    private static final Logger LOGGER = Logger.getLogger(ImageUtils.class);

    public static kenny.ml.data.image.Image resize(final kenny.ml.data.image.Image image, final int width, final int height) {
        return new kenny.ml.data.image.Image(resize(image.data(), width, height));
    }

    public static BufferedImage resize(final BufferedImage image, final int width, final int height) {
        final Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        final BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return scaledImage;
    }

    public static int[] toRgb(final int rgb) {
        int r = (rgb) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = (rgb >> 16) & 0xFF;

        return new int[] {r, g, b};
    }

    public static int[] toRgba(final int argb) {
        int r = (argb) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = (argb >> 16) & 0xFF;
        int a = (argb >> 24) & 0xFF;

        return new int[] {r, g, b, a};
    }

    public static int[] toYcBcR(final int rgb) {
        int[] rgbArr = toRgb(rgb);
        int r = rgbArr[0];
        int g = rgbArr[1];
        int b = rgbArr[2];
        int y = (int) Math.round(16 + (65.738 * r + 129.057 * g + 25.064 * b) / 256);
        int cb = (int) Math.round(128 + (-37.945 * r - 74.494 * g + 112.439 * b) / 256);
        int cr = (int) Math.round(128 + (112.439 * r - 94.154 * g - 18.285 * b) / 256);

        return new int[] {y, cb, cr};
    }

    public static void saveThumbnail(final BufferedImage bi, final String file, final double scale) {
        int width = (int) (bi.getWidth() * scale);
        int height = (int) (bi.getHeight() * scale);

        int imgWidth = bi.getWidth();
        int imgHeight = bi.getHeight();
        if (imgWidth * height < imgHeight * width) {
            width = imgWidth * height / imgHeight;
        } else {
            height = imgHeight * width / imgWidth;
        }
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setBackground(Color.BLACK);
            g.clearRect(0, 0, width, height);
            g.drawImage(bi, 0, 0, width, height, null);
        } finally {
            g.dispose();
        }
        try {
            LOGGER.info("Writing file: " + file);
            ImageIO.write(newImage, getFormat(file), new File(file));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static String getFormat(String file) {
        String[] parts =  file.split("\\.");
        return parts[parts.length - 1];
    }

}
