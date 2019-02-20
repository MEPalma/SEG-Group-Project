
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
    public static enum Conversion {Yes, No}
  
    private final int id;
    private final int userId;
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

    /*
        implementation of Stringifiable.java
    */
    @Override
    public String getDBContent()
    {
        String is = "', '";
        return ("'" + this.id + is + 
                this.userId + is + 
                Stringifiable.parseDate(simpleDateFormat.format(this.entryDate)) + is + 
                Stringifiable.parseDate(simpleDateFormat.format(this.exitDate)) + is +
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
                    simpleDateFormat.parse(Stringifiable.parseDateBack(resultSet.getString("entryDate"))),//TODO check this is in the right format!!!!!!
                    simpleDateFormat.parse(Stringifiable.parseDateBack(resultSet.getString("exitDate"))),//TODO check this is in the right format!!!!!!
                    resultSet.getInt("pagesViewed"),
                    Conversion.valueOf(resultSet.getString("conversion"))
                );
                resultSet.close();
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
