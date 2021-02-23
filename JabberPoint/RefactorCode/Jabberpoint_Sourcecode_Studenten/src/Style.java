import java.awt.Color;
import java.awt.Font;

/** <p>Style stands for Indent, Color, Font and Leading.</p>
 * <p>The link between a style number and a item level is hard-linked:
 * in Slide the style is grabbed for an item
 * with a style number the same as the item level.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Style {
	private static Style[] styles; // de styles
	private static final String FONTNAME = "Helvetica";
	public int indent;
	public Color color;
	public Font font;
	public int fontSize;
	public int leading;

	public Style(int indent, Color color, int points, int leading) {
		this.indent = indent;
		this.color = color;
		this.font = new Font(FONTNAME, Font.BOLD, fontSize=points);
		this.leading = leading;
	}

	public String toString() {
		return "["+ indent + "," + color + "; " + fontSize + " on " + leading +"]";
	}

	public Font getFont(float scale) {
		return font.deriveFont(fontSize * scale);
	}

}
