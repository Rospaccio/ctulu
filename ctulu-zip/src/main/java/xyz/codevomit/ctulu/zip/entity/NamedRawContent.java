package xyz.codevomit.ctulu.zip.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a raw content associated with a filename: it can be used to
 * temporarily store in memory an extracted {@link ArchiveEntry} (a zip file entry).
 * @author merka, CodeVomit productions
 */
@Data
@AllArgsConstructor
public class NamedRawContent
{
    private String fileName;
    private byte[] rawContent;
}
