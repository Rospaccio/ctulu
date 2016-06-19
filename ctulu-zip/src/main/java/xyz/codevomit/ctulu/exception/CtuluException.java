package xyz.codevomit.ctulu.exception;

/**
 *
 * @author merka, CodeVomit Productions
 */
public class CtuluException extends RuntimeException
{

    public CtuluException()
    {
    }

    public CtuluException(String message)
    {
        super(message);
    }

    public CtuluException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CtuluException(Throwable cause)
    {
        super(cause);
    }
    
}
