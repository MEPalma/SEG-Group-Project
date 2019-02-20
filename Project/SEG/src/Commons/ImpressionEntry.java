package Commons;

import DatabaseManager.Stringifiable;
import java.sql.ResultSet;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object parseDBContent(ResultSet resultSet)
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
