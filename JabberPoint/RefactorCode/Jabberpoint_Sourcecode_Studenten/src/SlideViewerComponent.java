import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * <p>SlideViewerComponent is a graphical component that can display Slides.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent {

    private Slide slide;
    private Font labelFont;
    private Presentation presentation;
    private JFrame frame;

    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTHEIGHT = 10;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;

    public SlideViewerComponent(Presentation pres, JFrame frame) {
        this.setBackground(BGCOLOR);
        presentation = pres;
        labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
        this.frame = frame;
    }

    /**
     * gets the size of the slide
     * @return dimension
     */
    public Dimension getPreferredSize() {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    public void update(Presentation presentation, Slide data) {
        if (data != null) {
            this.presentation = presentation;
            this.slide = data;
            repaint();
            frame.setTitle(presentation.getTitle());
        }
        repaint();
    }

    /**
     * @param g Graphics sets the bg color and paint it on the window
     */
    public void paintComponent(Graphics g) {
        g.setColor(BGCOLOR);
        g.fillRect(0, 0, getSize().width, getSize().height);
        this.drawText(g);
    }

    /**
     * Method for drawing text
     * @param g Graphics sets the color of the font
     */
    private void drawText(Graphics g) {
        if (presentation.getSlideNumber() < 0 || slide == null) {
            return;
        }
        g.setFont(labelFont);
        g.setColor(COLOR);
        g.drawString("Slide " + getSlide() + " of " + presentation.getSize(), XPOS, YPOS);
        Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
        slide.draw(g, area, this);
    }

    private int getSlide() {
        return (1 + presentation.getSlideNumber());
    }
}
