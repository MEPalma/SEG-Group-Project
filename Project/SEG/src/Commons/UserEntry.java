package Commons;

import DatabaseManager.Stringifiable;

public class UserEntry implements Stringifiable {
    public static int AUTO_INDEX = -1;

    //IN SAME ORDER AS IN DB TABLE
    private long id;
    private int campaignId;
    private Enums.Gender gender;
    private Enums.Age age;
    private Enums.Income income;

    public UserEntry(long id, int campaignId, Enums.Gender gender, Enums.Age age, Enums.Income income) {
        this.id = id;
        this.campaignId = campaignId;
        this.gender = gender;
        this.age = age;
        this.income = income;
    }

    public UserEntry() {
        this(AUTO_INDEX, AUTO_INDEX, Enums.Gender.Unknown, Enums.Age.Unknown, Enums.Income.Unknown);
    }

    @Override
    public String stringify() {
        if (this.campaignId == AUTO_INDEX) {
            return null;//id needs to be specified!
        }
        String is = "', '";
        return ("'" + this.id + is + this.campaignId + is + this.gender + is + this.age + is + this.income + "'");
    }

    @Override
    public String toString() {
        return stringify();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserEntry) {
            return (this.id == ((UserEntry) obj).id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (new Long(this.id).hashCode() + new Integer(this.campaignId).hashCode());
    }//TODO test

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Enums.Gender getGender() {
        return gender;
    }

    public void setGender(Enums.Gender gender) {
        this.gender = gender;
    }

    public Enums.Age getAge() {
        return age;
    }

    public void setAge(Enums.Age age) {
        this.age = age;
    }

    public Enums.Income getIncome() {
        return income;
    }

    public void setIncome(Enums.Income income) {
        this.income = income;
    }
}
