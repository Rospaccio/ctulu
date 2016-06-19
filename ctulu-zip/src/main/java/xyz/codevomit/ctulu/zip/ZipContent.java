package xyz.codevomit.ctulu.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import xyz.codevomit.ctulu.zip.entity.NamedRawContent;

/**
 *
 * @author merka, CodeVomit Productions
 */
@Slf4j
public class ZipContent
{
    byte[] rawContent;
    public ZipContent(byte[] rawContent)
    {
        this.rawContent = rawContent;
    }
    
    public Map<String, NamedRawContent> extractEntries() throws IOException
    {
        Map<String, NamedRawContent> contentsMap = new LinkedHashMap<>();

        ZipArchiveInputStream zipStream = new ZipArchiveInputStream(new ByteArrayInputStream(rawContent));
        ArchiveEntry archiveEntry;
        while ((archiveEntry = zipStream.getNextZipEntry()) != null)
        {
            if (archiveEntry.isDirectory())
            {
                continue;
            }
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            org.apache.commons.compress.utils.IOUtils.copy(zipStream, outStream);

            NamedRawContent namedContent = new NamedRawContent(archiveEntry.getName(), outStream.toByteArray());
            contentsMap.put(namedContent.getFileName(), namedContent);

        }
        return contentsMap;
    }
}
