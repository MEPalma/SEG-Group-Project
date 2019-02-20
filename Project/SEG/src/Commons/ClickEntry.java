
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
    private final int id;
    private final int userId;
    private Date date;
    private Number clickCost;

    public ClickEntry(int id, int userId, Date date, Number clickCost)
    {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.clickCost = clickCost;
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
                    simpleDateFormat.parse(Stringifiable.parseDateBack(resultSet.getString("date"))),//TODO check this is in the right format!!!!!!
                    resultSet.getDouble("clickCost")
                );
                resultSet.close();
                return tmp;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ParseException ex)
        {
            Logger.getLogger(ClickEntry.class.getName()).log(Level.SEVERE, null, ex);
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
