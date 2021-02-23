import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

/**
 * <p>The applicatiewindow for a slideviewcomponent</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerFrame extends JFrame {
	private static final String JABTITLE = "Jabberpoint 1.6 - OU";
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;

	public SlideViewerFrame(String title, Presentation presentation) {
		super(title);
		new SlideViewerFrame(presentation);
	}

	private SlideViewerFrame(Presentation presentation) {
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
		presentation.setShowView(slideViewerComponent);
		setupWindow(slideViewerComponent, presentation);
	}

	private void exitApplication() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation) {
		setTitle(JABTITLE);
		exitApplication();
		getContentPane().add(slideViewerComponent);
		this.addController(presentation);
		this.setSize();
	}

	private void addController(Presentation presentation) {
		addKeyListener(new KeyController(presentation));
		setMenuBar(new MenuController(this, presentation));//Add another controller
	}

	private void setSize()
	{
		setSize(new Dimension(WIDTH, HEIGHT)); //Same sizes a slide has
		setVisible(true);
	}
}
