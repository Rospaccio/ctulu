package xyz.codevomit.ctulu.zip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import xyz.codevomit.ctulu.zip.entity.NamedRawContent;
import xyz.codevomit.ctulu.exception.CtuluException;

/**
 *
 * @author merka, CodeVomit Productions
 */
@Slf4j
public class Zipper
{
    @Getter
    private List<NamedRawContent> contents;
    
    private ZipArchiveOutputStream currentZipArchiveOutputStream;

    public Zipper()
    {
        this.contents = new ArrayList<>();
    }

    public void addZipEntry(String filename, byte[] rawContent)
    {
        addZipEntry(new NamedRawContent(filename, rawContent));
    }
    
    public void addZipEntry(NamedRawContent content)
    {
        this.contents.add(content);
    }

    public void clear()
    {
        this.contents.clear();
    }

    public byte[] zip()
    {
        ByteArrayOutputStream byteStream = null;
        currentZipArchiveOutputStream = null;
        try
        {
            byteStream = new ByteArrayOutputStream();
            currentZipArchiveOutputStream = new ZipArchiveOutputStream(byteStream);

            zipAllElements();

            currentZipArchiveOutputStream.flush();
            currentZipArchiveOutputStream.close();
            return byteStream.toByteArray();
        }
        catch (Exception e)
        {
            String message = "Impossible to zip the contained elements";
            log.error(message, e);
            throw new CtuluException(message, e);
        }
        finally
        {
            closeSilently(byteStream, currentZipArchiveOutputStream);
            // we do not want this reference to be valid after this point.
            currentZipArchiveOutputStream = null;
        }
    }

    private void zipAllElements() throws IOException
    {
        for (NamedRawContent namedContent : contents)
        {
            zipElement(namedContent);
        }
    }

    private void zipElement(NamedRawContent namedContent) throws IOException
    {
        File supportFile = null;
        try
        {
            supportFile = File.createTempFile(namedContent.getFileName(), null);
            ArchiveEntry firstArchiveEntry = currentZipArchiveOutputStream
                    .createArchiveEntry(supportFile, namedContent.getFileName());
            currentZipArchiveOutputStream.putArchiveEntry(firstArchiveEntry);
            currentZipArchiveOutputStream.write(namedContent.getRawContent());
            currentZipArchiveOutputStream.closeArchiveEntry();
        }
        finally
        {
            if (supportFile != null)
            {
                supportFile.delete();
            }
        }
    }

    private void closeSilently(OutputStream... streams)
    {
        for (OutputStream stream : streams)
        {
            try
            {
                if (stream != null)
                {
                    stream.close();
                }
            }
            catch (Exception e)
            {
                log.error("Impossible to close the stream", e);
            }
        }
    }
}
