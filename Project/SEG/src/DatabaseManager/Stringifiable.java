package DatabaseManager;

/*
 * Created by Marco-Edoardo Palma.
 */

import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public interface Stringifiable
{
    public SimpleDateFormat globalDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public String getDBContent();

    public Object parseDBContent(ResultSet resultSet);
}
