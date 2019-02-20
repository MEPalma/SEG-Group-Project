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
//        DataExchange dataExchange = new DataExchange(new DatabaseManager());
//        
//        UserEntry u = new UserEntry(0, UserEntry.Gender.Male, UserEntry.Age.Age_25_34, UserEntry.Income.Low);
//        dataExchange.insertUserStmt(u);
//        
//        ServerEntry s = new  ServerEntry(0, 0, new Date(), new Date(),123,Conversion.Yes);
//        dataExchange.insertServerStmt(s);
//        
//        ImpressionEntry i = new ImpressionEntry(0, 0, new Date(), ImpressionEntry.Context.News, 123);
//        dataExchange.insertImpressionStmt(i);
//        
//        ClickEntry c = new ClickEntry(0, 0, new Date(), 123);
//        dataExchange.insertClickStmt(c);
    }

}
