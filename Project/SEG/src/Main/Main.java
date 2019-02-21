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
import java.util.List;

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
       
//        UserEntry u = new UserEntry();
//        dataExchange.insertUserStmt(u);
//        
//        ServerEntry s = new  ServerEntry();
//        s.setUserId(u.getId());
//        dataExchange.insertServerStmt(s);
//        
//        ImpressionEntry i = new ImpressionEntry();
//        i.setUserId(u.getId());
//        dataExchange.insertImpressionStmt(i);
//        
//        ClickEntry c = new ClickEntry();
//        c.setUserId(u.getId());
//        dataExchange.insertClickStmt(c);
        
//        List<UserEntry> users = dataExchange.selectAllFrom_USERS();
//        for (UserEntry x : users)
//            System.out.println(x.toString());
//        
//        List<ClickEntry> clicks = dataExchange.selectAllFrom_CLICK_LOGS();
//        for (ClickEntry cl : clicks)
//            System.out.println(cl.toString());
//        
//        List<ImpressionEntry> impressions = dataExchange.selectAllFrom_IMPRESSION_LOGS();
//        for (ImpressionEntry im : impressions)
//            System.out.println(im.toString());
//        
//        List<ServerEntry> server = dataExchange.selectAllFrom_SERVER_LOGS();
//        for (ServerEntry x : server)
//            System.out.println(x.toString());
//        
//        System.out.println(dataExchange.selectByIdFrom_USERS(1).toString());
//        System.out.println(dataExchange.selectByIdFrom_CLICK_LOGS(1).toString());
//        System.out.println(dataExchange.selectByIdFrom_IMPRESSION_LOGS(1).toString());
//        System.out.println(dataExchange.selectByIdFrom_SERVER_LOGS(1).toString());
//        
//        System.out.println(dataExchange.selectByUserIdFrom_CLICK_LOGS(1).toString());
//        System.out.println(dataExchange.selectByUserIdFrom_IMPRESSION_LOGS(1).toString());
//        System.out.println(dataExchange.selectByUserIdFrom_SERVER_LOGS(1).toString());
        
        dataExchange.close();
    }

}
