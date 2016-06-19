package xyz.codevomit.ctulu.strings;

import java.util.Random;

/**
 *
 * @author merka, CodeVomit Productions
 */
public class StringsGenerator
{
    private static Random random = new Random(System.currentTimeMillis());
    
    /**
     * Generates a (UTF-16){@link String} of length {@code stringLength}
     * whose characters are randomly chosen between {@code 0x0000}
     * and {@code 0xFFFF} (uniform distribution).
     * @param stringLength The length of the generated string
     * @return The randomly generated {@link String}
     */
    public static String randomString(int stringLength)
    {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < stringLength; i++)
        {
            int randomCharCode = random.nextInt(0xFFFF);
            builder.append((char)randomCharCode);
        }
        return builder.toString();
    }
}
