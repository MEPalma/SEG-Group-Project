
package Commons;

import DatabaseManager.Stringifiable;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Marco-Edoardo Palma
 */
public class ServerEntry implements Stringifiable
{
    public static enum Conversion {Yes, No}
    
    private Date entryDate;
    private Date exitDate;
    private final int id;
    private Number pagesViewed;
    private Conversion conversion;

    public ServerEntry(Date entryDate, Date exitDate, int id, Number pagesViewed, Conversion conversion)
    {
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.id = id;
        this.pagesViewed = pagesViewed;
        this.conversion = conversion;
    }
    
    /*
        implementation of Stringifiable.java
    */
    @Override
    public String getDBContent()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object parseDBContent(ResultSet resultSet)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Date getEntryDate()
    {
        return entryDate;
    }

    public Date getExitDate()
    {
        return exitDate;
    }

    public int getId()
    {
        return id;
    }

    public Number getPagesViewed()
    {
        return pagesViewed;
    }

    public Conversion getConversion()
    {
        return conversion;
    }

    public void setEntryDate(Date entryDate)
    {
        this.entryDate = entryDate;
    }

    public void setExitDate(Date exitDate)
    {
        this.exitDate = exitDate;
    }

    public void setPagesViewed(Number pagesViewed)
    {
        this.pagesViewed = pagesViewed;
    }

    public void setConversion(Conversion conversion)
    {
        this.conversion = conversion;
    }
    
}
