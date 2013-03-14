package lee.won.hcv1.img;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Won Lee
 * @version 1.0 b3032355
 * b3031255:This class is to retrieve images from img package.
 * b3032355:Minor bugs fixed
 *
 */
public class ImageUtility {

	/**
	 * 
	 * @param name image's name in the img package
	 * @return return BufferedImage that matches the name if exists
	 * @throws IOException
	 */
	public static BufferedImage retrieveImage(String name) throws IOException{
		BufferedImage img = ImageIO.read(lee.won.hcv1.img.ImageUtility.class.getResourceAsStream(name));
		return img;
	}
	
	/**
	 * 
	 * @param img the image that you want to resize
	 * @param width
	 * @param height
	 * @return resized image
	 */
	public static BufferedImage resizeImage(BufferedImage img, int width, int height){
		BufferedImage resizedImage = new BufferedImage(width, height, img.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}
	
	/**
	 * 
	 * @param name the image's name in the img package
	 * @param width 
	 * @param height
	 * @return resized image
	 * @throws IOException
	 */
	public static BufferedImage retrieveImageWithResize(String name, int width, int height) throws IOException{
		return resizeImage(retrieveImage(name), width, height);
	}
	
	public static BufferedImage resizeWithRatio(BufferedImage img, double ratio){
		return resizeImage(img, (int) (img.getWidth()*ratio), (int) (img.getHeight()*ratio));
	}
	
	/**
	 * 
	 * @param img 
	 * @param size how long you want to change
	 * @return image that is resized but keeps aspect ratio
	 */
	public static BufferedImage resizeImageKeepRatioWithW(BufferedImage img, int size){
		double ratio = (double) size/img.getWidth();
		return resizeImage(img, size, (int) (img.getHeight()*ratio));
	}
	
	public static BufferedImage resizeImageKeepRatioWithH(BufferedImage img, int size){
		double ratio = (double) size/img.getHeight();
		return resizeImage(img, (int) (img.getWidth()*ratio), size);
	}
	
	public static double calculateAspectRatio(int width, int height){
		return (double) height/width;
	}
	
}
