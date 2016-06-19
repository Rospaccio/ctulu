package xyz.codevomit.ctulu.strings;

import org.apache.commons.lang.StringUtils;

/**
 * A String adapter that is able to remove characters from the underlying
 * {@link String} instance.
 * @author merka, CodeVomit Productions
 */
public class AutoFilteringString
{
    private final String originalString;
    public AutoFilteringString(String originalString)
    {
        this.originalString = originalString;
    }

    public String removeAll(char[] charactersToRemove)
    {
        String filtered = originalString;
        for(char removing : charactersToRemove)
        {
            filtered = StringUtils.remove(filtered, removing);
        }
        return filtered;
    }
}
