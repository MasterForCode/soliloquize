package top.soliloquize.img;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import top.soliloquize.lang.ChainingMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wb
 * @date 2020/7/3
 */
public class QRCode {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    /**
     * 创建二维码
     *
     * @param content      二维码信息
     * @param imgPath      生成二维码的存储路径
     * @param needCompress 嵌入图片是否压缩
     * @return BufferedImage
     * @throws Exception Exception
     */
    private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
        ChainingMap<EncodeHintType, Object> hints = new ChainingMap<EncodeHintType, Object>()
                .put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
                .put(EncodeHintType.CHARACTER_SET, CHARSET)
                .put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints.getMap());
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCode.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入图片
     *
     * @param source       二维码
     * @param imgPath      生成二维码的存储路径
     * @param needCompress 嵌入图片是否压缩
     * @throws Exception Exception
     */
    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 创建二维码入口函数
     *
     * @param content      二维码信息
     * @param imgPath      生成二维码的存储路径
     * @param destPath
     * @param needCompress
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCode.createImage(content, imgPath, needCompress);
        mkdirs(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    public static BufferedImage encode(String content, String imgPath, boolean needCompress) throws Exception {
        return QRCode.createImage(content, imgPath, needCompress);
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void encode(String content, String imgPath, String destPath) throws Exception {
        QRCode.encode(content, imgPath, destPath, false);
    }
    // 被注释的方法
    /*
     * public static void encode(String content, String destPath, boolean
     * needCompress) throws Exception { QRCodeUtil.encode(content, null, destPath,
     * needCompress); }
     */

    public static void encode(String content, String destPath) throws Exception {
        QRCode.encode(content, null, destPath, false);
    }

    public static void encode(String content, String imgPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QRCode.createImage(content, imgPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    public static void encode(String content, OutputStream output) throws Exception {
        QRCode.encode(content, null, output, false);
    }

    /**
     * 读取二维码中的信息
     *
     * @param file 二维码文件
     * @return 信息
     * @throws Exception
     */
    public static String decode(File file) throws IOException, NotFoundException {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        ChainingMap<DecodeHintType, Object> hints = new ChainingMap<DecodeHintType, Object>().put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints.getMap());
        return result.getText();
    }

    public static String decode(String path) throws Exception {
        return QRCode.decode(new File(path));
    }

    public static void main(String[] args) throws Exception {
        // 存放在二维码中的内容
        String text = "我是wb";
        // 嵌入二维码的图片路径
        String imgPath = "C:\\Users\\wb\\Desktop\\test2.jpg";
        // 生成的二维码的路径及名称
        String destPath = "./jam.jpg";
        //生成二维码
        QRCode.encode(text, imgPath, destPath, true);
        // 解析二维码
        String str = QRCode.decode(destPath);
        // 打印出解析出的内容
        System.out.println(str);

    }

    private static class BufferedImageLuminanceSource extends LuminanceSource {

        private final BufferedImage image;
        private final int left;
        private final int top;

        public BufferedImageLuminanceSource(BufferedImage image) {
            this(image, 0, 0, image.getWidth(), image.getHeight());
        }

        public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
            super(width, height);

            int sourceWidth = image.getWidth();
            int sourceHeight = image.getHeight();
            if (left + width > sourceWidth || top + height > sourceHeight) {
                throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
            }

            for (int y = top; y < top + height; y++) {
                for (int x = left; x < left + width; x++) {
                    if ((image.getRGB(x, y) & 0xFF000000) == 0) {
                        // = white
                        image.setRGB(x, y, 0xFFFFFFFF);
                    }
                }
            }

            this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
            this.image.getGraphics().drawImage(image, 0, 0, null);
            this.left = left;
            this.top = top;
        }

        @Override
        public byte[] getRow(int y, byte[] row) {
            if (y < 0 || y >= getHeight()) {
                throw new IllegalArgumentException("Requested row is outside the image: " + y);
            }
            int width = getWidth();
            if (row == null || row.length < width) {
                row = new byte[width];
            }
            image.getRaster().getDataElements(left, top + y, width, 1, row);
            return row;
        }

        @Override
        public byte[] getMatrix() {
            int width = getWidth();
            int height = getHeight();
            int area = width * height;
            byte[] matrix = new byte[area];
            image.getRaster().getDataElements(left, top, width, height, matrix);
            return matrix;
        }

        @Override
        public boolean isCropSupported() {
            return true;
        }

        @Override
        public LuminanceSource crop(int left, int top, int width, int height) {
            return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);
        }

        @Override
        public boolean isRotateSupported() {
            return true;
        }

        @Override
        public LuminanceSource rotateCounterClockwise() {
            int sourceWidth = image.getWidth();
            AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
            BufferedImage rotatedImage = new BufferedImage(image.getHeight(), sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g = rotatedImage.createGraphics();
            g.drawImage(image, transform, null);
            g.dispose();
            int width = getWidth();
            return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
        }

    }
}
