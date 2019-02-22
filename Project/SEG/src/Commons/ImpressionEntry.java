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
    public static int AUTO_INDEX = -1;

    public static enum Context {News, Shopping, SocialMedia, BlockTagTree, Travel, Hobbies, Blog, Unknown};

    //IN SAME ORDER AS IN DB TABLE
    private int id;
    private String userId;
    private Date date;
    private Context context;
    private Number impressionCost;

    public ImpressionEntry(int id, String userId, Date date, Context context, Number impressionCost)
    {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.context = context;
        this.impressionCost = impressionCost;
    }

    public ImpressionEntry()
    {
        this(AUTO_INDEX, "", new Date(), Context.Unknown, 0);
    }

    /*
        implementation of Stringifiable.java
     */
    @Override
    public String getDBContent()
    {
        String is = "', '";
        String tmp;
        if (this.id == AUTO_INDEX)
        {
            tmp = "NULL, '";
        } else
        {
            tmp = "'" + this.id + is;
        }
        return (tmp
                + this.userId + is
                + globalDateFormat.format(this.date) + is
                + this.context + is
                + this.impressionCost.doubleValue()
                + "'");
    }

    @Override
    public Object parseDBContent(ResultSet resultSet)
    {
        try
        {
            if (!resultSet.isClosed())
            {
                return new ImpressionEntry(
                        resultSet.getInt("id"),
                        resultSet.getString("userId"),
                        globalDateFormat.parse(resultSet.getString("date")),
                        Context.valueOf(resultSet.getString("context")),
                        resultSet.getDouble("impressionCost")
                );
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ParseException ex)
        {
            Logger.getLogger(ImpressionEntry.class.getName()).log(Level.SEVERE, null, ex);
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
        {
            if (this.id == ((ImpressionEntry) obj).id)
            {
                return true;
            }
        }
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

    public String getUserId()
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

    public void setUserId(String userId)
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
