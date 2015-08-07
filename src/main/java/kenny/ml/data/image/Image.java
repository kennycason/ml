package kenny.ml.data.image;

import kenny.ml.utils.ImageUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by kenny on 5/20/14.
 */
public class Image {

    private static final Logger LOGGER = Logger.getLogger(Image.class);

    private BufferedImage bi;

    public Image(String file) {
        try {
            bi = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(file));
        } catch (IOException e) {
            LOGGER.info(e.getMessage(), e);
        }
    }

    public Image(final BufferedImage bi) {
        this.bi = bi;
    }

    public void set(int x, int y, int rgb) {
        bi.setRGB(x, y, rgb);
    }

    public int get(int x, int y) {
        return bi.getRGB(x, y);
    }

    public BufferedImage data() {
        return bi;
    }

    public int width() {
        return bi.getWidth();
    }

    public int height() {
        return bi.getHeight();
    }

    public void save(String file) {
        try {
            LOGGER.info("Writing file: " + file);
            ImageIO.write(bi, ImageUtils.getFormat(file), new File(file));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
