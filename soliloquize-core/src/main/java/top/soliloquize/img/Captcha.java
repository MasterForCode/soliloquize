package top.soliloquize.img;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @author wb
 * @date 2020/7/3
 */
public class Captcha {
    private static final Logger logger = LoggerFactory.getLogger(Captcha.class);
    private static Integer width = 80;
    private static Integer height = 32;
    private static char[] mapTable = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '0', '1',
            '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static Map<String, String> getImageCode(Integer width, Integer height, int num, String filePath) {
        if (width == null || width <= 0) {
            width = Captcha.width;
        }
        if (height == null || height <= 0) {
            height = Captcha.height;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics graphics = image.getGraphics();
        //生成随机类
        Random random = new Random();
        // 设定背景色
        graphics.setColor(getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);
        //设定字体
        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.SIZE, 18);
        attributes.put(TextAttribute.FONT, "Times New Roman");

        Font font = new Font(attributes);
        graphics.setFont(font);
        // 随机产生168条干扰线，使图象中的认证码不易被其它程序探测到
        graphics.setColor(getRandColor(160, 200));
        for (int i = 0; i < 168; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, x + xl, y + yl);
        }
        //取随机产生的码
        StringBuilder strSource = new StringBuilder();
        //num代表num位验证码,如果要生成更多位的认证码,则加大数值
        for (int i = 0; i < num; ++i) {
            strSource.append(mapTable[(int) (mapTable.length * Math.random())]);
            // 将认证码显示到图象中
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            // 直接生成
            String str = strSource.substring(i, i + 1);
            // 设置验证码在背景图图片上的位置
            graphics.drawString(str, i * font.getSize(), font.getSize() + 5);
        }

        if (font.getSize() * num > width) {
            logger.error("The width of the image is too small, and the generated verification code cannot be displayed");
        }

        // 释放图形上下文
        graphics.dispose();
        // 生成图片
        String fileName = UUID.randomUUID().toString() + ".jpg";
        new File(filePath).mkdirs();
        String absolutePath = filePath + File.separator + fileName;
        File outputfile = new File(absolutePath);
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            logger.error("Error generating verification code");
            throw new RuntimeException();
        }
        Map<String, String> returnMap = new HashMap<>(2);
        returnMap.put("fileName", fileName);
        returnMap.put("strSource", strSource.toString());
        return returnMap;
    }

    //给定范围获得随机颜色
    static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }


    public static void main(String[] args) {
//        System.out.println(Captcha.getImageCode(null, null, 4, Singleton.captchaDirPath));
        System.out.println(Captcha.getImageCode(90, null, 5, "./"));
    }
}
