package Commons;

import DatabaseManager.Stringifiable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marco-Edoardo Palma
 */
public class UserEntry implements Stringifiable
{
    public static enum Gender {Male, Famale, Unknown};
    public static enum Age {Age_less_than_25, Age_25_34, Age_35_44, Age_45_54, Age_more_than_54, Unknown};
    public static enum Income {Low, Medium, Hight, Unknown};
    
    //IN SAME ORDER AS IN DB TABLE
    private int id;
    private Gender gender;
    private Age age;
    private Income income;

    public UserEntry(int id, Gender gender, Age age, Income income)
    {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.income = income;
    }
    
    public UserEntry()
    {
        this(-1, Gender.Unknown, Age.Unknown, Income.Unknown);
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
        else tmp = "'" + this.id + is;
        return (tmp + this.gender + is + this.age + is + this.income + "'");
    }

    @Override
    public Object parseDBContent(ResultSet resultSet)
    {
        try
        {
            if (!resultSet.isClosed())
            {
                UserEntry tmp = new UserEntry(
                    resultSet.getInt("id"),
                    Gender.valueOf(resultSet.getString("gender")),
                    Age.valueOf(resultSet.getString("age")),
                    Income.valueOf(resultSet.getString("income"))
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
    
    @Override
    public String toString()
    {
        return this.getDBContent();
    }
    
     @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof UserEntry)
            if (this.id == ((UserEntry) obj).id) return true;
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.id;
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

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    
    public void setAge(Age age)
    {
        this.age = age;
    }

    public void setIncome(Income income)
    {
        this.income = income;
    }
    
}
