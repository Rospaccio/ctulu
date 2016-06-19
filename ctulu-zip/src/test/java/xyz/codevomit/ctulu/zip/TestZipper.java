package xyz.codevomit.ctulu.zip;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import xyz.codevomit.ctulu.zip.entity.NamedRawContent;

/**
 *
 * @author merka, CodeVomit Productions
 */
public class TestZipper
{

    public TestZipper()
    {
    }

    @Test
    public void testZip() throws IOException
    {
        // Prepares a zip with 10 entries
        Zipper zipper = new Zipper();
        int count = 10;
        String testString = "This is a test string content";
        for (int i = 0; i < count; i++)
        {
            byte[] content = testString.getBytes();
            zipper.addZipEntry("test" + i, content);
        }
        
        byte[] zipped = zipper.zip();
        
        ZipContent zippedWrapper = new ZipContent(zipped);
        
        Map<String, NamedRawContent> extractedBack = zippedWrapper.extractEntries();
        assertNotNull(extractedBack);
        assertEquals(count, extractedBack.size());
        
        for(NamedRawContent content : extractedBack.values())
        {
            assertTrue(content.getFileName().contains("test"));
            assertEquals(testString, new String(content.getRawContent()));
        }
    }
}
