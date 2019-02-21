package Main;

import Commons.ClickEntry;
import Commons.ImpressionEntry;
import Commons.ServerEntry;
import Commons.ServerEntry.Conversion;
import Commons.UserEntry;
import DatabaseManager.DataExchange;
import DatabaseManager.DatabaseManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Group 31
 */
public class Main 
{

    public static void main(String[] args) throws ParseException 
    {
//        String pattern = "yyyy-MM-dd hh:mm:ss";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        Date d = simpleDateFormat.parse("2015-01-20 16:12:47");
//        System.out.println(d.toString());
//        System.out.println(simpleDateFormat.format(new Date()));
//        
        DataExchange dataExchange = new DataExchange(new DatabaseManager());
        
        UserEntry u = new UserEntry();
        dataExchange.insertUserStmt(u);
        
        ServerEntry s = new  ServerEntry();
        s.setUserId(u.getId());
        dataExchange.insertServerStmt(s);
        
        ImpressionEntry i = new ImpressionEntry();
        i.setUserId(u.getId());
        dataExchange.insertImpressionStmt(i);
        
        ClickEntry c = new ClickEntry();
        c.setUserId(u.getId());
        dataExchange.insertClickStmt(c);
    }

}
