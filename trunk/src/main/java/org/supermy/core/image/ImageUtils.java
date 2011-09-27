package org.supermy.core.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang.StringUtils;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import javax.imageio.ImageIO;

public final class ImageUtils {

	public enum Direction {
		LEFT, RIGHT, BLANK
	}

	/**
	 * 直接高度計算
	 */
	private static int AUTOCALC = -1;

	/**
	 * 分3份高度計算
	 */
	private static int CENTER_31 = 1;
	private static int CENTER_32 = 2;
	private static int CENTER_33 = 3;

	/**
	 * 图片水印
	 * 
	 * @param pressImg
	 *            水印图片
	 * @param targetImg
	 *            目标图片
	 * @param x
	 *            修正值 默认在中间
	 * @param y
	 *            修正值 默认在中间
	 * @param alpha
	 *            透明度
	 */
	public final static void pressImage(String pressImg, String targetImg,
			int x, int y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2,
					(height - height_biao) / 2, wideth_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "png", img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param targetImg
	 *            目标图片
	 * @param fontName
	 *            字体名称
	 * @param fontStyle
	 *            字体样式
	 * @param color
	 *            字体颜色
	 * @param fontSize
	 *            字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度
	 */
	public static void pressText(String pressText, String targetImg,
			String fontName, int fontStyle, Color color, int fontSize, int x,
			int y, int left, int top, float alpha) {
		try {

			File img = new File(targetImg);

			// ImageInputStream img = ImageIO.createImageInputStream(img1);

			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));

			if (left == -1) {
				left = (width - (getLength(pressText) * fontSize)) / 2 + x;
			}

			if (top == AUTOCALC) {
				top = (height - fontSize) / 2 + y;
			}
			if (top == CENTER_31) {
				top = (height / 3 - fontSize) + y;

				// top = (height - fontSize)/CENTER_31 / 2 + y;
			}
			if (top == CENTER_32) {
				top = (height / 3 * 2 - fontSize) + y;

				// top = (height - fontSize)/ 2+CENTER_32 + y;
			}
			if (top == CENTER_33) {
				top = (height - fontSize) + y;
			}

			g.drawString(pressText, left, top);
			g.dispose();
			ImageIO.write((BufferedImage) image, "png", img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缩放
	 * 
	 * @param filePath
	 *            图片路径
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 * @param bb
	 *            比例不对时是否需要补白
	 */
	public static void resize(String filePath, int height, int width, boolean bb) {
		try {
			double ratio = 0.0; // 缩放比例
			File f = new File(filePath);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue()
							/ bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(
						AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 */
	public static void cut(String srcImageFile, String descDir,
			Direction direction, int destWidth) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth < destWidth) {
				destWidth = srcWidth;
			}
			// if (srcHeight < destHeight) {
			int destHeight = srcHeight;
			// }
			Image image = bi.getScaledInstance(srcWidth, srcHeight,
					Image.SCALE_DEFAULT);
			// destWidth = 200; // 切片宽度
			// destHeight = 150; // 切片高度
			int cols = 0; // 切片横向数量
			int rows = 0; // 切片纵向数量
			// 计算切片的横向和纵向数量
			if (srcWidth % destWidth == 0) {
				cols = srcWidth / destWidth;
			} else {
				cols = (int) Math.floor(srcWidth / destWidth) + 1;
			}
			if (srcHeight % destHeight == 0) {
				rows = srcHeight / destHeight;
			} else {
				rows = (int) Math.floor(srcHeight / destHeight) + 1;
			}

			System.out.println("cols:" + cols);
			System.out.println("rows:" + rows);

			// 循环建立切片
			// 改进的想法:是否可用多线程加快切割速度
			// for (int i = 0; i < rows; i++) {
			// for (int j = 0; j < cols; j++) {
			// 四个参数分别为图像起点坐标和宽高
			// 即: CropImageFilter(int x,int y,int width,int height)
			// cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
			// destWidth, destHeight);

			int left = 0;
			if (Direction.RIGHT == direction) {
				left = srcWidth - destWidth;
			}
			if (Direction.BLANK == direction) {
				left = 5;
			}

			System.out.println("left:" + left);

			cropFilter = new CropImageFilter(left, 0, destWidth, destHeight);

			img = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(destWidth, destHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			// 输出为文件
			// ImageIO.write(tag, "PNG", new File(descDir
			// + "pre_map_" + i + "_" + j + ".jpg"));
			ImageIO.write(tag, "PNG", new File(
					(descDir + "MENU_" + direction + ".png").toLowerCase()));

			// }
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Java拼接多张图片
	 * TODO
	 * 
	 * @param pics
	 * @param type
	 * @param dst_pic
	 * @return
	 */
	public static boolean merge(String dst_pic, String[] pics) {
		int len = pics.length;
		System.out.println("pics len：" + len);

		if (len < 1) {
			System.out.println("pics len < 1");
			return false;
		}

		File[] src = new File[len];
		BufferedImage[] images = new BufferedImage[len];
		int[][] ImageArrays = new int[len][];
		for (int i = 0; i < len; i++) {
			try {
				src[i] = new File(pics[i]);
				// System.out.println("pics len ... ："+src[i]);
				images[i] = ImageIO.read(src[i]);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			int width = images[i].getWidth();
			int height = images[i].getHeight();
			ImageArrays[i] = new int[width * height];// 从图片中读取RGB
			ImageArrays[i] = images[i].getRGB(0, 0, width, height,
					ImageArrays[i], 0, width);
		}

		int dst_height = 0;
		int dst_width = images[0].getWidth();
		for (int i = 0; i < images.length; i++) {
			dst_width = dst_width > images[i].getWidth() ? dst_width
					: images[i].getWidth();

			dst_height += images[i].getHeight();
		}
		System.out.println(dst_width);
		System.out.println(dst_height);
		if (dst_height < 1) {
			System.out.println("dst_height < 1");
			return false;
		}

		// 生成新图片
		try {
			// dst_width = images[0].getWidth();
			BufferedImage ImageNew = new BufferedImage(dst_width, dst_height,
					BufferedImage.TYPE_INT_RGB);
			int height_i = 0;
			for (int i = 0; i < images.length; i++) {

				System.out.println("images at:" + images[i].getHeight());
				System.out.println("images at:" + ImageArrays[i]);

				ImageNew.setRGB(0, height_i, dst_width, images[i].getHeight(),ImageArrays[i], 0, dst_width);

				height_i += images[i].getHeight();
				System.out.println("images at:" + i);
			}

			File outFile = new File(dst_pic);
			ImageIO.write(ImageNew, "png", outFile);// 写图片
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	// /////////////////////

	public static void main(String[] args) throws IOException {
		String srcImgPath = "D:/env_myweb/mywebV0.3/myweb-rest-extjs/src/main/webapp/resources/menu/nav_products.png";
		String descDir = "D:/env_myweb/mywebV0.3/myweb-rest-extjs/src/main/webapp/resources/menu/";
		String logoText = "商店位置";
		String targetPath = "D:/env_myweb/mywebV0.3/myweb-rest-extjs/src/main/webapp/resources/menu/merge.png";
		
		String blank = "D:/env_myweb/mywebV0.3/myweb-rest-extjs/src/main/webapp/resources/menu/menu_blank.png";
		String left = "D:/env_myweb/mywebV0.3/myweb-rest-extjs/src/main/webapp/resources/menu/menu_left.png";
		String right = "D:/env_myweb/mywebV0.3/myweb-rest-extjs/src/main/webapp/resources/menu/menu_right.png";
		
		// String targerPath2 =
		// "D:/env_myweb/mywebV0.3/myweb-rest-extjs/src/main/webapp/resources/menu/nav_store_marked_text_rotate.jpg";

		// pressImage(srcImgPath, targerPath, 0, 0, 0.5f);
		pressText(logoText, srcImgPath, "黑体", 36, Color.white, 14, 0, 0,
				AUTOCALC, CENTER_31, 1.0f);
		pressText(logoText, srcImgPath, "黑体", 36, Color.white, 14, 0, 0,
				AUTOCALC, CENTER_32, 1.0f);
		pressText(logoText, srcImgPath, "黑体", 36, Color.white, 14, 0, 0,
				AUTOCALC, CENTER_33, 1.0f);

		cut(srcImgPath, descDir, Direction.LEFT, 10);
		cut(srcImgPath, descDir, Direction.RIGHT, 30);
		cut(srcImgPath, descDir, Direction.BLANK, 10);

		//String[] mergefiles = { left, blank, right };
		//merge(targetPath, mergefiles);
//		merge2(blank,right,targetPath);
		
		// resize(srcImgPath, 500, 500, true);
	}

	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
}