import java.util.ArrayList;


/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
	private String showTitle;
	private ArrayList<Slide> showList = null;
	private int currentSlideNumber = 0;
	private SlideViewerComponent slideViewComponent;

	public Presentation() {
		slideViewComponent = null;
		clear();
	}

	public Presentation(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
		clear();
	}

	public int getSize() {
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
	}

	public void setShowView(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
	}

	public int getSlideNumber() {
		return currentSlideNumber;
	}

	//Change the current slide number and report it the the window
	public void setSlideNumber(int number) {
		this.currentSlideNumber = number;
		if (slideViewComponent != null) {
			slideViewComponent.update(this, getCurrentSlide());
		}
	}

	//Navigate to the previous slide unless we are at the first slide
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
	    }
	}

	//Navigate to the next slide unless we are at the last slide
	public void nextSlide() {
		if (this.currentSlideNumber < (showList.size()-1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	public void clear() {
		this.showList = new ArrayList<>();
		setSlideNumber(-1);
	}

	public void append(Slide slide) {
		this.showList.add(slide);
	}

	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
	    }
			return this.showList.get(number);
	}

	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
}
