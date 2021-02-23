import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


/**
 * XMLAccessor, reads and writes XML files
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

/**
 * THIS CLASS READS AND WRITES XML FILES
 */
public class XMLAccessor extends Accessor {
    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    private DocumentBuilder createBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    private Document createDocument(DocumentBuilder builder, String filename) throws IOException, SAXException {
        return builder.parse(new File(filename));
    }

    /**
     * Method for loading a file
     * @param presentation The presentation itself
     * @param filename the file that needs to be loaded
     */
    public void loadFile(Presentation presentation, String filename) {
        try {
            this.createBuilder();
            Document document = this.createDocument(this.createBuilder(), filename);//Create a JDOM document
            Element doc = document.getDocumentElement();
            presentation.setTitle(getTitle(doc, XMLtags.SHOWTITLE));
            NodeList slides = doc.getElementsByTagName(XMLtags.SLIDE);
            for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide();
                slide.setTitle(getTitle(xmlSlide, XMLtags.SLIDETITLE));
                presentation.append(slide);
                NodeList slideItems = xmlSlide.getElementsByTagName(XMLtags.ITEM);
                for (int itemNumber = 0; itemNumber < slideItems.getLength(); itemNumber++) {
                    Element item = (Element) slideItems.item(itemNumber);
                    loadSlideItem(slide, item);
                }
            }

        } catch (IOException iox) {
            System.err.println(iox.toString());
        } catch (SAXException sax) {
            System.err.println(sax.getMessage());
        } catch (ParserConfigurationException pcx) {
            System.err.println(XMLtags.PCE);
        }
    }

    private void loadSlideItem(Slide slide, Element item) {
        int level = 1; // default
        NamedNodeMap attributes = item.getAttributes();
        String leveltext = attributes.getNamedItem(XMLtags.LEVEL).getTextContent();
        if (!leveltext.isEmpty()) {
            try {
                level = Integer.parseInt(leveltext);
            } catch (NumberFormatException x) {
                System.err.println(XMLtags.NFE);
            }
            this.checkType(level, attributes, slide, item);
        }
    }

    /**
     * Method for checking the type of the item and adding it to the slide
     * @param level the level of the text item
     * @param attributes coolection of nodes accessed by name
     * @param slide the slide
     * @param item xml element
     */
    private void checkType(int level, NamedNodeMap attributes, Slide slide, Element item) {
        String type = attributes.getNamedItem(XMLtags.KIND).getTextContent();
        if (XMLtags.TEXT.equals(type)) {
            slide.append(new TextItem(level, item.getTextContent()));
        } else if (XMLtags.IMAGE.equals(type)) {
            slide.append(new BitmapItem(level, item.getTextContent()));
        } else {
            System.err.println(XMLtags.UNKNOWNTYPE);
        }
    }

    /**
     * Method for saving xml file
     * @param presentation the presentation
     * @param filename the name of the file
     * @throws IOException
     */
    public void saveFile(Presentation presentation, String filename) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        out.println("<presentation>");
        out.print("<showtitle>");
        out.print(presentation.getTitle());
        out.println("</showtitle>");
        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
            Slide slide = presentation.getSlide(slideNumber);
            out.println("<slide>");
            out.println("<title>" + slide.getTitle() + "</title>");
            Vector<SlideItem> slideItems = slide.getSlideItems();
            for (int itemNumber = 0; itemNumber < slideItems.size(); itemNumber++) {
                SlideItem slideItem = slideItems.elementAt(itemNumber);
                out.print("<item kind=");
                if (slideItem instanceof TextItem) {
                    out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
                    out.print(((TextItem) slideItem).getText());
                } else if (slideItem instanceof BitmapItem) {
                    out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
                    out.print(((BitmapItem) slideItem).getName());
                }
                out.println("</item>");
            }
            out.println("</slide>");
        }
        out.println("</presentation>");
        out.close();
    }
}
