import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * <p>A text item.</p>
 * <p>A text item has drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
	private String text;

	public TextItem(int level, String string) {
		super(level);
		this.text = string;
	}

	public String getText() {
		return text == null ? "" : text;
	}

	/**
	 * This methods adds string to AttributedString collection which stores string and other attributes
	 * @param style the style for the text
	 * @param scale the scale for the text
	 * @return variable for AttributedString
	 */
	public AttributedString getAttributedString(Style style, float scale) {
		AttributedString attrStr = new AttributedString(getText());
		attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());
		return attrStr;
	}

	/**
	 * @param g
	 * @param observer
	 * @param scale
	 * @param myStyle
	 * @return Returns the bounding box of an Item
	 */
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		int xsize = 0, ysize = StyleCreation.getLeadingStyle(myStyle, scale);

		for (TextLayout layout : this.getLayouts(g, myStyle, scale)) {
			if (layout.getBounds().getWidth() > xsize && layout.getBounds().getHeight() > ysize) {
				xsize = (int) layout.getBounds().getWidth();
				ysize += layout.getBounds().getHeight();
			}
			ysize += layout.getLeading() + layout.getDescent();
		}
		return new Rectangle(StyleCreation.getIndentStyle(myStyle, scale), 0, xsize, ysize);
	}

	/**
	 * The method draws the item with pointer that creates the coordinates
	 * @param x
	 * @param y
	 * @param scale
	 * @param g
	 * @param myStyle
	 * @param o
	 */
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o) {
		if (text.isEmpty()) {
			return;
		}
		int xIndent = x + StyleCreation.getIndentStyle(myStyle, scale);
		int yLeading = y + StyleCreation.getLeadingStyle(myStyle, scale);
		Point pen = new Point(xIndent, yLeading);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(myStyle.color);

		for (TextLayout layout : getLayouts(g, myStyle, scale)) {
			pen.y += layout.getAscent();
			layout.draw(g2d, pen.x, pen.y);
			pen.y += layout.getDescent();
		}
	}

	private AttributedString getContextString(Style s, float scale) {
		return getAttributedString(s, scale);
	}

	private Graphics2D getGraphics(Graphics g) {
		return (Graphics2D) g;
	}

	private FontRenderContext getFontContext(Graphics g) {
		return this.getGraphics(g).getFontRenderContext();
	}

	/**
	 * Method for getting the collection of the layout
	 * @param g
	 * @param s
	 * @param scale
	 * @return Collection with the text
	 */
	public List<TextLayout> getLayouts(Graphics g, Style s, float scale) {
		List<TextLayout> layouts = new ArrayList<>();
		LineBreakMeasurer measurer = new LineBreakMeasurer(this.getContextString(s, scale).getIterator(), this.getFontContext(this.getGraphics(g)));
		float wrappingWidth = (Slide.WIDTH - s.indent) * scale;
		while (measurer.getPosition() < getText().length()) {
			TextLayout layout = measurer.nextLayout(wrappingWidth);
			layouts.add(layout);
		}
		return layouts;
	}

	public String toString() {
		return "TextItem[" + getLevel() + "," + getText() + "]";
	}
}
