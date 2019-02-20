package Commons;

import DatabaseManager.Stringifiable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Marco-Edoardo Palma
 */
public class ImpressionEntry implements Stringifiable
{
    public static enum Context {News, Shopping, Social, Media, BlockTagTree, Travel};
    
  
    //IN SAME ORDER AS IN DB TABLE
    private final int id;
    private final int userId;
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

    /*
        implementation of Stringifiable.java
    */
    @Override
    public String getDBContent()
    {
        String is = "', '";
        return ("'" + this.id + is + 
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
