import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@RunWith(Parameterized.class)
public class StaticPageTest extends AuthenticatedTestBase {

    static class PageEntry {
        String url;
        String content;
        String title;
        int headingLevel;
        String heading;
    }

    @Parameters
    public static List<PageEntry> readPagesConfig() throws IOException, ParserConfigurationException, SAXException {
        List<PageEntry> pages = new ArrayList<>();
        InputStream xml = StaticPageTest.class.getClassLoader().getResourceAsStream("static-pages.xml");
        if (xml == null) {
            throw new RuntimeException("Could not find static-pages.xml");
        }

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(xml);

        NodeList pageNodes = doc.getElementsByTagName("page");

        for (int i = 0; i < pageNodes.getLength(); i++) {
            Element pageElem = (Element) pageNodes.item(i);
            PageEntry entry = new PageEntry();
            entry.url = pageElem.getElementsByTagName("url").item(0).getTextContent().trim();
            entry.content = pageElem.getElementsByTagName("content").item(0).getTextContent().trim();
            entry.title = pageElem.getElementsByTagName("title").item(0).getTextContent().trim();
            entry.headingLevel = Integer.parseInt(pageElem.getElementsByTagName("heading-level").item(0).getTextContent());
            entry.heading = pageElem.getElementsByTagName("heading").item(0).getTextContent().trim();
            pages.add(entry);
        }
        return pages;
    }

    PageEntry entry;

    public StaticPageTest(PageEntry entry) {
        this.entry = entry;
    }

    @Test
    public void testStaticPages() {
        StaticPage page = new StaticPage(entry.url, driver);
        assertTrue(page.getBodyText().contains(entry.content));
        assertEquals(entry.title, page.getTitle());
        assertEquals(entry.heading, page.getHeading(entry.headingLevel));
    }
}