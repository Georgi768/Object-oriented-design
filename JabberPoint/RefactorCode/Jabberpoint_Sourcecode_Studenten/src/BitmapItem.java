import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;

import java.io.IOException;


/** <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class BitmapItem extends SlideItem {
  private BufferedImage bufferedImage;
  private String imageName;
  
  protected static final String FILE = "File ";
  protected static final String NOTFOUND = " not found";

	/**
	 * This method reads the image from the bufferImage class
	 * @param level level indicates the item-level
	 * @param name name indicates the name of the file with the image
	 */
	public BitmapItem(int level, String name) {
		super(level);
		this.imageName = name;
		try {
			this.bufferedImage = ImageIO.read(new File(this.imageName));
		}
		catch (IOException e) {
			System.err.println(FILE + imageName + NOTFOUND) ;
		}
	}

	public String getName() {
		return this.imageName;
	}

	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		int indent = (int) (myStyle.indent * scale);
		int width = (int) (bufferedImage.getWidth(observer) * scale);
		int height = (int) (bufferedImage.getHeight(observer) * scale);

		return new Rectangle(indent, 0,width,height);
	}

	/**
	 * Draws the image by setting up the size and height
	 * @param x
	 * @param y
	 * @param scale
	 * @param g
	 * @param myStyle
	 * @param observer
	 */
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
		int width = x + StyleCreation.getIndentStyle(myStyle,scale);
		int height = y +StyleCreation.getLeadingStyle(myStyle,scale);
		int bufferImageWidth = (int) (bufferedImage.getWidth(observer)*scale);
		int bufferImageHeight = (int) (bufferedImage.getHeight(observer)*scale);

		g.drawImage(bufferedImage, width, height,bufferImageWidth,bufferImageHeight,observer);
	}

	public String toString() {
		return "BitmapItem[" + getLevel() + "," + imageName + "]";
	}
}
