package xss.it.util;

import javafx.scene.image.Image;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.nio.IntBuffer;

/**
 *
 * @author XDSSWAR
 * Created on 05/16/2023
 * Utility class for working with JavaFX images and AWT BufferedImages.
 */
@SuppressWarnings("all")
public final class PortableImageUtils {
    /**
     * Converts a BufferedImage to a WritableImage.
     *
     * @param bimg The BufferedImage to convert.
     * @param wimg The existing WritableImage, if any.
     * @return The converted WritableImage.
     */
    public static WritableImage convertToFxImage(BufferedImage bimg, WritableImage wimg) {
        int bw = bimg.getWidth();
        int bh = bimg.getHeight();
        switch (bimg.getType()) {
            case BufferedImage.TYPE_INT_ARGB:
            case BufferedImage.TYPE_INT_ARGB_PRE:
                break;
            default:
                BufferedImage converted =
                        new BufferedImage(bw, bh, BufferedImage.TYPE_INT_ARGB_PRE);
                Graphics2D g2d = converted.createGraphics();
                g2d.drawImage(bimg, 0, 0, null);
                g2d.dispose();
                bimg = converted;
                break;
        }
        // Check the existing WritableImage, if provided
        if (wimg != null) {
            int iw = (int) wimg.getWidth();
            int ih = (int) wimg.getHeight();
            if (iw < bw || ih < bh) {
                wimg = null;
            } else if (bw < iw || bh < ih) {
                int[] empty = new int[iw];
                PixelWriter pw = wimg.getPixelWriter();
                PixelFormat<IntBuffer> pf = PixelFormat.getIntArgbPreInstance();
                if (bw < iw) {
                    pw.setPixels(bw, 0, iw-bw, bh, pf, empty, 0, 0);
                }
                if (bh < ih) {
                    pw.setPixels(0, bh, iw, ih-bh, pf, empty, 0, 0);
                }
            }
        }
        // Create a new WritableImage if needed
        if (wimg == null) {
            wimg = new WritableImage(bw, bh);
        }
        PixelWriter pw = wimg.getPixelWriter();
        DataBufferInt db = getBufferInt(bimg);
        int[] data = db.getData();
        int offset = bimg.getRaster().getDataBuffer().getOffset();
        int scan =  0;
        SampleModel sm = bimg.getRaster().getSampleModel();
        if (sm instanceof SinglePixelPackedSampleModel) {
            scan = ((SinglePixelPackedSampleModel)sm).getScanlineStride();
        }

        PixelFormat<IntBuffer> pf = (bimg.isAlphaPremultiplied() ?
                PixelFormat.getIntArgbPreInstance() :
                PixelFormat.getIntArgbInstance());
        pw.setPixels(0, 0, bw, bh, pf, data, offset, scan);
        return wimg;
    }

    /**
     * Converts a JavaFX Image to an AWT BufferedImage.
     *
     * @param img  JavaFX Image to be converted
     * @param bimg BufferedImage to be used as the destination image, or null to create a new BufferedImage
     * @return AWT BufferedImage equivalent of the input image
     */
    public static BufferedImage convertToAwtImage(Image img, BufferedImage bimg) {
        PixelReader pr = img.getPixelReader();
        if (pr == null) {
            return null;
        }
        int iw = (int) img.getWidth();
        int ih = (int) img.getHeight();
        PixelFormat<?> fxFormat = pr.getPixelFormat();
        boolean srcPixelsAreOpaque = false;
        switch (fxFormat.getType()) {
            case INT_ARGB_PRE, INT_ARGB, BYTE_BGRA_PRE, BYTE_BGRA -> {
                // Check fx image opacity only if
                // supplied BufferedImage is without alpha channel
                if (bimg != null &&
                        (bimg.getType() == BufferedImage.TYPE_INT_BGR ||
                                bimg.getType() == BufferedImage.TYPE_INT_RGB)) {
                    srcPixelsAreOpaque = checkFXImageOpaque(pr, iw, ih);
                }
            }
            case BYTE_RGB -> srcPixelsAreOpaque = true;
        }
        int prefBimgType = getBestBufferedImageType(pr.getPixelFormat(), bimg, srcPixelsAreOpaque);
        if (bimg != null) {
            int bw = bimg.getWidth();
            int bh = bimg.getHeight();
            if (bw < iw || bh < ih || bimg.getType() != prefBimgType) {
                bimg = null;
            } else if (iw < bw || ih < bh) {
                Graphics2D g2d = bimg.createGraphics();
                g2d.setComposite(AlphaComposite.Clear);
                g2d.fillRect(0, 0, bw, bh);
                g2d.dispose();
            }
        }
        if (bimg == null) {
            bimg = new BufferedImage(iw, ih, prefBimgType);
        }
        DataBufferInt db = getBufferInt(bimg);
        int[] data = db.getData();
        int offset = bimg.getRaster().getDataBuffer().getOffset();
        int scan =  0;
        SampleModel sm = bimg.getRaster().getSampleModel();
        if (sm instanceof SinglePixelPackedSampleModel) {
            scan = ((SinglePixelPackedSampleModel)sm).getScanlineStride();
        }

        WritablePixelFormat<IntBuffer> pf = getAssociatedPixelFormat(bimg);
        pr.getPixels(0, 0, iw, ih, pf, data, offset, scan);
        return bimg;
    }

    /**
     * Determines the best BufferedImage type based on the given PixelFormat, an existing
     * BufferedImage, and whether the source pixels are opaque.
     *
     * @param fxFormat   The PixelFormat of the source image.
     * @param bimg       The existing BufferedImage, if any.
     * @param isOpaque   Flag indicating if the source pixels are opaque.
     * @return The best BufferedImage type.
     */
    static int getBestBufferedImageType(PixelFormat<?> fxFormat, BufferedImage bimg, boolean isOpaque) {
        if (bimg != null) {
            int bimgType = bimg.getType();
            if (bimgType == BufferedImage.TYPE_INT_ARGB ||
                    bimgType == BufferedImage.TYPE_INT_ARGB_PRE ||
                    (isOpaque &&
                            (bimgType == BufferedImage.TYPE_INT_BGR ||
                                    bimgType == BufferedImage.TYPE_INT_RGB)))
            {
                return bimgType;
            }
        }
        return switch (fxFormat.getType()) {
            default -> BufferedImage.TYPE_INT_ARGB_PRE;
            case BYTE_BGRA, INT_ARGB -> BufferedImage.TYPE_INT_ARGB;
            case BYTE_RGB -> BufferedImage.TYPE_INT_RGB;
            case BYTE_INDEXED -> (fxFormat.isPremultiplied()
                    ? BufferedImage.TYPE_INT_ARGB_PRE
                    : BufferedImage.TYPE_INT_ARGB);
        };
    }

    /**
     * Returns the associated WritablePixelFormat for the given BufferedImage type.
     *
     * @param bimg The BufferedImage.
     * @return The associated WritablePixelFormat.
     * @throws InternalError if the BufferedImage type is not recognized.
     */
    private static WritablePixelFormat<IntBuffer> getAssociatedPixelFormat(BufferedImage bimg){
        return switch (bimg.getType()) {
            case BufferedImage.TYPE_INT_RGB, BufferedImage.TYPE_INT_ARGB_PRE -> PixelFormat.getIntArgbPreInstance();
            case BufferedImage.TYPE_INT_ARGB -> PixelFormat.getIntArgbInstance();
            default ->throw new InternalError("Failed to validate BufferedImage type");
        };
    }

    /**
     * Checks if the given JavaFX image is fully opaque.
     *
     * @param pr The PixelReader to read the image pixels.
     * @param iw The width of the image.
     * @param ih The height of the image.
     * @return {@code true} if the image is fully opaque, {@code false} otherwise.
     */
    private static boolean checkFXImageOpaque(PixelReader pr, int iw, int ih) {
        for (int x = 0; x < iw; x++) {
            for (int y = 0; y < ih; y++) {
                Color color = pr.getColor(x,y);
                if (color.getOpacity() != 1.0) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Retrieves the DataBufferInt from a BufferedImage.
     *
     * @param image The BufferedImage to retrieve the DataBufferInt from.
     * @return The DataBufferInt of the BufferedImage.
     */
    private static DataBufferInt getBufferInt(BufferedImage image){
        return (DataBufferInt)image.getRaster().getDataBuffer();
    }

}
