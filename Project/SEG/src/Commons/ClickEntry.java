
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
public class ClickEntry implements Stringifiable
{
    //IN THE SAME ORDER AS DECLARATION IN DB TABLE
    private int id;
    private int userId;
    private Date date;
    private Number clickCost;

    public ClickEntry(int id, int userId, Date date, Number clickCost)
    {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.clickCost = clickCost;
    }
    
    public ClickEntry()
    {
        this(-1, -1, new Date(), 0);
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
        else tmp = this.id + ", '";
        return (tmp + 
                this.userId + is +
                simpleDateFormat.format(this.date) + is +
                this.clickCost.doubleValue() +
                "'");
    }
    
    @Override
    public Object parseDBContent(ResultSet resultSet)
    {
        try
        {
            if (!resultSet.isClosed())
            {
                ClickEntry tmp = new ClickEntry(
                    resultSet.getInt("id"),
                    resultSet.getInt("userId"),
                    simpleDateFormat.parse(resultSet.getString("date")),
                    resultSet.getDouble("clickCost")
                );
                return tmp;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (Exception ex)
        {
            System.out.println("fuuuuuuck");
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
        if (obj instanceof ClickEntry)
            if (this.id == ((ClickEntry) obj).id) return true;
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

    public Number getClickCost()
    {
        return clickCost;
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

    public void setClickCost(Number clickCost)
    {
        this.clickCost = clickCost;
    }
    
}
