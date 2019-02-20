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
    public static enum Gender {Male, Famale};
    public static enum Age {Age_less_than_25, Age_25_34, Age_35_44, Age_45_54, Age_more_than_54};
    public static enum Income {Low, Medium, Hight};
    public static enum Context {News, Shopping, Social, Media, BlockTagTree, Travel};
    
    private Date date;
    private final int id;
    private Gender gender;
    private Age age;
    private Income income;
    private Context context;
    private Number impressionCose;

    public ImpressionEntry(Date date, int id, Gender gender, Age age, Income income, Context context, Number impressionCose)
    {
        this.date = date;
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.income = income;
        this.context = context;
        this.impressionCose = impressionCose;
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

    public Gender getGender()
    {
        return gender;
    }

    public Age getAge()
    {
        return age;
    }

    public Income getIncome()
    {
        return income;
    }

    public Context getContext()
    {
        return context;
    }

    public Number getImpressionCose()
    {
        return impressionCose;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public void setAge(Age age)
    {
        this.age = age;
    }

    public void setIncome(Income income)
    {
        this.income = income;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public void setImpressionCose(Number impressionCose)
    {
        this.impressionCose = impressionCose;
    }
    
    
}
