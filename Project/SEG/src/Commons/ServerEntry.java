
package Commons;

import DatabaseManager.Stringifiable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco-Edoardo Palma
 */
public class ServerEntry implements Stringifiable
{
    public static enum Conversion {Yes, No, Unknown}
  
    private int id;
    private int userId;
    private Date entryDate;
    private Date exitDate;
    private Number pagesViewed;
    private Conversion conversion;

    public ServerEntry(int id, int userId, Date entryDate, Date exitDate, Number pagesViewed, Conversion conversion)
    {
        this.id = id;
        this.userId = userId;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.pagesViewed = pagesViewed;
        this.conversion = conversion;
    }

    public ServerEntry()
    {
        this(-1, -1, new Date(), new Date(), 0, Conversion.Unknown);
    }
    
    /*
        implementation of Stringifiable.java
    */
    @Override
    public String getDBContent()
    {
        String is = "', '";
        String tmp;
        if (this.id < 0) tmp = "NULL, '";
        else tmp = "'" + this.id + is;
        return (tmp +
                this.userId + is + 
                simpleDateFormat.format(this.entryDate) + is + 
                simpleDateFormat.format(this.exitDate) + is +
                this.pagesViewed.intValue() + is +
                this.conversion +
                "'");
    }

    @Override
    public Object parseDBContent(ResultSet resultSet)
    {
        try
        {
            if (!resultSet.isClosed())
            {
                ServerEntry tmp = new ServerEntry(
                    resultSet.getInt("id"),
                    resultSet.getInt("userId"),
                    simpleDateFormat.parse(resultSet.getString("entryDate")),
                    simpleDateFormat.parse(resultSet.getString("exitDate")),
                    resultSet.getInt("pagesViewed"),
                    Conversion.valueOf(resultSet.getString("conversion"))
                );
                return tmp;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ParseException ex)
        {
            Logger.getLogger(ServerEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public String toString()
    {
        return this.getDBContent();
    }
    
     @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ServerEntry)
            if (this.id == ((ServerEntry) obj).id) return true;
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.id;
    }

    public Date getEntryDate()
    {
        return entryDate;
    }

    public Date getExitDate()
    {
        return exitDate;
    }

    public int getUserId()
    {
        return userId;
    }

    public Number getPagesViewed()
    {
        return pagesViewed;
    }

    public Conversion getConversion()
    {
        return conversion;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
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
