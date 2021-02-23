import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {

	private Frame parent; //The frame, only used as parent for the Dialogs
	private Presentation presentation; //Commands are given to the presentation

	public MenuController(Frame frame, Presentation pres) {
		parent = frame;
		presentation = pres;
		this.presentationMenu();
		this.slidesMenu();
		this.aboutPage();
	}

	//Creating a menu-item
	public MenuItem mkMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}
	private void presentationMenu()
	{
		Menu viewMenu = new Menu(MenuInstructions.FILE);
		add(viewMenu);
		this.openFunction(viewMenu);
		this.clearFunction(viewMenu);
		this.newFunction(viewMenu);
		this.saveFunction(viewMenu);
		this.exitFunction(viewMenu);
	}

	/**
	 * This methods saves the current presentation slide
	 * @param fileMenu
	 *
	 */
	private void saveFunction(Menu fileMenu) {
		MenuItem menuItem;
		fileMenu.add(menuItem = mkMenuItem(MenuInstructions.SAVE));
		menuItem.addActionListener(e -> {
		   Accessor xmlAccessor = new XMLAccessor();
			   try {
				   xmlAccessor.saveFile(presentation, MenuInstructions.SAVEFILE);
				   } catch (IOException exc) {
				   JOptionPane.showMessageDialog(parent, MenuInstructions.IOEX + exc, MenuInstructions.SAVEERR, JOptionPane.ERROR_MESSAGE);
				   }
			   }
		);
	}

	/**
	 * Method for adding help menu and about button for the about page
	 */
	private void aboutPage() {
		Menu helpMenu = new Menu(MenuInstructions.HELP);
		MenuItem menuItem;
		helpMenu.add(menuItem = mkMenuItem(MenuInstructions.ABOUT));
		menuItem.addActionListener(actionEvent -> AboutBox.show(parent));
		setHelpMenu(helpMenu);
	}

	/**
	 * Event which clears the presentation
	 * @param menuItem
	 */
	private void clearFunction(MenuItem menuItem) {
		menuItem.addActionListener(actionEvent -> {
			presentation.clear();
			parent.repaint();
		});
	}

	/**
	 * Method for adding exit button
	 * @param fileMenu
	 *
	 */
	private void exitFunction(Menu fileMenu) {
		fileMenu.addSeparator();
		MenuItem menuItem;
		fileMenu.add(menuItem = mkMenuItem(MenuInstructions.EXIT));
		menuItem.addActionListener(actionEvent -> presentation.exit(0));
	}

	/**
	 * Method for creating a button which opens the default presentation
	 * @param fileMenu
	 *
	 */
	private void openFunction(Menu fileMenu) {
		MenuItem menuItem;
		fileMenu.add(menuItem = mkMenuItem(MenuInstructions.OPEN));
		menuItem.addActionListener(actionEvent -> {
			presentation.clear();
			Accessor xmlAccessor = new XMLAccessor();
			try {
				xmlAccessor.loadFile(presentation, MenuInstructions.TESTFILE);
				presentation.setSlideNumber(0);
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(parent, MenuInstructions.IOEX + exc,
						MenuInstructions.LOADERR, JOptionPane.ERROR_MESSAGE);
			}
			parent.repaint();
		});
	}

	/**
	 * method for creating new slide
	 * @param fileMenu
	 *
	 */
	private void newFunction(Menu fileMenu) {
		MenuItem menuItem;
		fileMenu.add(menuItem = mkMenuItem(MenuInstructions.NEW));
		menuItem.addActionListener(actionEvent -> {
			presentation.clear();
			parent.repaint();
		});
	}

	/**
	 * Method for slide navigation
	 */
	private void slidesMenu()
	{
		Menu viewMenu = new Menu(MenuInstructions.VIEW);
		add(viewMenu);
		this.getNextSlide(viewMenu);
		this.getPrevSlide(viewMenu);
		this.goToSlide(viewMenu);
	}

	/**
	 * Method for selecting next slide
	 * @param viewMenu
	 */
	private void getNextSlide(Menu viewMenu)
	{
		MenuItem menuItem;
		viewMenu.add(menuItem = mkMenuItem(MenuInstructions.NEXT));
		menuItem.addActionListener(actionEvent -> presentation.nextSlide());
	}
	/**
	 * Method for selecting previous slide
	 * @param viewMenu
	 */
	private void getPrevSlide(Menu viewMenu)
	{
		MenuItem menuItem;
		viewMenu.add(menuItem = mkMenuItem(MenuInstructions.PREV));
		menuItem.addActionListener(actionEvent -> presentation.prevSlide());;
	}

	/**
	 * Method for selecting specific slide
	 * @param viewMenu
	 */
	private void goToSlide(Menu viewMenu)
	{
		MenuItem menuItem;
		viewMenu.add(menuItem = mkMenuItem(MenuInstructions.GOTO));
		menuItem.addActionListener(actionEvent -> {
			String pageNumberStr = JOptionPane.showInputDialog(MenuInstructions.PAGENR);
			int pageNumber = Integer.parseInt(pageNumberStr);
			presentation.setSlideNumber(pageNumber - 1);
		});
	}
}
