
package Commons;

import DatabaseManager.Stringifiable;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Marco-Edoardo Palma
 */
public class ClickEntry implements Stringifiable
{
    private Date date;
    private final int id;
    private Number clickCost;

    public ClickEntry(Date date, int id, Number clickCost)
    {
        this.date = date;
        this.id = id;
        this.clickCost = clickCost;
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

    public int getId()
    {
        return id;
    }

    public Number getClickCost()
    {
        return clickCost;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setClickCost(Number clickCost)
    {
        this.clickCost = clickCost;
    }
    
}
