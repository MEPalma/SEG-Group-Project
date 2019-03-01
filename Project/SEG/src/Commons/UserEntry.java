package Commons;

import DatabaseManager.Stringifiable;

/**
 * @author Marco-Edoardo Palma
 */
public class UserEntry implements Stringifiable {

    public static enum Gender {
        Male, Female, Unknown
    }

    ;

    public static enum Age {
        Age_less_than_25, Age_25_34, Age_35_44, Age_45_54, Age_more_than_54, Unknown
    }

    ;

    public static enum Income {
        Low, Medium, High, Unknown
    }

    ;

    //IN SAME ORDER AS IN DB TABLE
    private String id;
    private Gender gender;
    private Age age;
    private Income income;

    public UserEntry(String id, Gender gender, Age age, Income income) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.income = income;
    }

    public UserEntry() {
        this("", Gender.Unknown, Age.Unknown, Income.Unknown);
    }

    @Override
    public String stringify() {
        if (this.id.equals("")) {
            return null;//id needs to be specified!
        }
        String is = "', '";
        return ("'" + this.id + is + this.gender + is + this.age + is + this.income + "'");
    }

    @Override
    public String toString() {
        return stringify();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserEntry) {
            if (this.id == ((UserEntry) obj).id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    public String getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
    }

    public Age getAge() {
        return age;
    }

    public Income getIncome() {
        return income;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public void setIncome(Income income) {
        this.income = income;
    }
}
