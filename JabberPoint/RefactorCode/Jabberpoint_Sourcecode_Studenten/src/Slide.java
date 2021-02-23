import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

/**
 * <p>A slide. This class has drawing functionality.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class Slide {
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;
	protected String title;
	protected Vector<SlideItem> items;

	public Slide() {
		items = new Vector<>();
	}

	public void append(SlideItem anItem) {
		items.addElement(anItem);
	}

	public String getTitle() { return this.title; }


	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	public void append(int level, String message) {
		append(new TextItem(level, message));
	}

	public Vector<SlideItem> getSlideItems() {
		return this.items;
	}

	public int getSize() {
		return this.items.size();
	}

	private int drawTheItems(Graphics g, SlideItem slideItem, Rectangle area, ImageObserver view, int y, float scale) {
		Style style = StyleCreation.getStyle(slideItem.getLevel());
		slideItem.draw(area.x, y, scale, g, style, view);
		return slideItem.getBoundingBox(g, view, scale, style).height;
	}
	/**
	 * Draws the header and the text.The y is for iterating on a new line.
	 * this draws the textItem to the screen.
	 * Draws the slide
	 * @param g abstract class for drawing onto components
	 * @param area area of an rectangle
	 * @param view update notification for image construction
	 */
	public void draw(Graphics g, Rectangle area, ImageObserver view) {
		float scale = getScale(area);
		int y = area.y;
		SlideItem slideItem = new TextItem(0, getTitle());
		y += this.drawTheItems(g, slideItem, area, view, y, scale);
		for (int number = 0; number < getSize(); number++) {
			slideItem = getSlideItems().elementAt(number);
			y += this.drawTheItems(g, slideItem, area, view, y, scale);
		}
	}

	/**
	 * @param area Rectangle area
	 * @return Returns the scale to draw a slide
	 */
	private float getScale(Rectangle area) {
		return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
	}
}
