package org.supermy.core.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageDraw {
	public static void main(String[] args) {
		mark();
		//hbimg();
		
	}

	public static void mark() {
		try {
			// 读取模板图片内容
								
			String filename = "D:/env_myweb/mywebV0.3/myweb-rest-extjs/src/main/webapp/resources/menu/test.jpg";
			String filenamenew = "D:/env_myweb/mywebV0.3/myweb-rest-extjs/src/main/webapp/resources/menu/test_new.jpg";

			
			BufferedImage image = ImageIO.read(new File(filename));
			Graphics g = image.createGraphics();

			g.setColor(Color.WHITE); // 设置画笔颜色
			// 设置字体
			Font font = new Font("大标宋", Font.LAYOUT_LEFT_TO_RIGHT, 18);
			g.setFont(font);
			g.drawImage(image, 0, 0, null);
			g.drawString("016478中文", 270, 282);// 写入签名
			g.dispose();
//			// this writes the bufferedImage back to outputFile
			ImageIO.write(image,"png", new File(filenamenew));
			System.out.println("ok");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}