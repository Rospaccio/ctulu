package xyz.codevomit.ctulu.strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author merka, CodeVomit Productions
 */
@Slf4j
public class AutoFilteringStringTest
{
    public static final char[] charactersToRemove = {0x00, 0x01, 0x1B, 0x1C, 0x7F};
    public static final String referenceInvalidString = String.copyValueOf(charactersToRemove);
    
    public static final Random rand = new Random(System.currentTimeMillis());
    
    @Test
    public void testRemoveAllSimple()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("a very plain string")
                .append(0x342F)
                .append(0x342E)
                .append(0x346E);
        String originalValidString = builder.toString();
        log.info("Initial valid String ==> " + originalValidString);
        for(char toRemove : charactersToRemove)
        {
            builder.insert(5, toRemove);
        }
        String invalidString = builder.toString();
        log.info("Test String ==> " + invalidString);
        
        AutoFilteringString autoFiltering = new AutoFilteringString(invalidString);
        String filtered = autoFiltering.removeAll(charactersToRemove);
        
        assertEquals(originalValidString, filtered);
    }
    
    @Test
    public void testRemoveAllRandomStrings()
    {
        List<String> randomsStrings = buildValidStrings();
        for(String random : randomsStrings)
        {
            AutoFilteringString autoFiltering = new AutoFilteringString(random);
            String filtered = autoFiltering.removeAll(charactersToRemove);
            if(StringUtils.containsAny(random, charactersToRemove))
            {
                log.info("testing invalid string ==> " + random);
                log.info("after filtering ==> " + filtered);
            }
            assertFalse(StringUtils.containsAny(filtered, charactersToRemove));
        }
    }
    
    private List<String> buildValidStrings()
    {
        int testStringLength = 128;
        int testStringCount = 1024 * 4;
        List<String> randomStrings = new ArrayList<>(testStringCount);
        int invalidCount = 0;
        int validCount = 0;
        for(int i = 0; i < testStringCount; i++)
        {
            String random = StringsGenerator.randomString(testStringLength);
            randomStrings.add(random);
            if(StringUtils.containsAny(random, charactersToRemove))
            {
                invalidCount++;
            }
            else
            {
                validCount++;
            }
        }  
        log.info("Valid Strings: " + validCount + "; Invalid Strings: " + invalidCount);
        return randomStrings;
    }
}
