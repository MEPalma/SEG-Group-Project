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
public class ImpressionEntry implements Stringifiable
{
    public static enum Context {News, Shopping, Social, Media, BlockTagTree, Travel, Unknown};
    
  
    //IN SAME ORDER AS IN DB TABLE
    private int id;
    private int userId;
    private Date date;
    private Context context;
    private Number impressionCost;

    public ImpressionEntry(int id, int userId, Date date, Context context, Number impressionCost)
    {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.context = context;
        this.impressionCost = impressionCost;
    }
    
    public ImpressionEntry()
    {
        this(-1, -1, new Date(), Context.Unknown, 0);
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
                simpleDateFormat.format(this.date) + is +
                this.context + is +
                this.impressionCost.doubleValue() +
                "'");
    }

    @Override
    public Object parseDBContent(ResultSet resultSet)
    {
        try
        {
            if (!resultSet.isClosed())
            {
                ImpressionEntry tmp = new ImpressionEntry(
                    resultSet.getInt("id"),
                    resultSet.getInt("userId"),
                    resultSet.getDate("date"), //TODO check me!!!!!!!
                    Context.valueOf(resultSet.getString("context")),
                    resultSet.getDouble("impressionCost")
                );
                resultSet.close();
                return tmp;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
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
        if (obj instanceof ImpressionEntry)
            if (this.id == ((ImpressionEntry) obj).id) return true;
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.id;
    }
    
    public Date getDate()
    {
        return date;
    }

    public int getUserId()
    {
        return userId;
    }
    
    public int getId()
    {
        return id;
    }

    public Context getContext()
    {
        return context;
    }

    public Number getImpressionCost()
    {
        return impressionCost;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public void setImpressionCost(Number impressionCost)
    {
        this.impressionCost = impressionCost;
    }
    
}
