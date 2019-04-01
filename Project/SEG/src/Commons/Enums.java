package Commons;

public class Enums {
    /*
        Gender
     */
    public static enum Gender {
        Male, Female, Unknown
    }

    public static int GenderToInt(Gender g) {
        if (g == Gender.Male) return 0;
        else if (g == Gender.Female) return 1;
        return 2;
    }

    public static Gender IntToGender(int i) {
        return Gender.values()[i];
    }


    /*
        Age
     */

    public static enum Age {
        Age_less_than_25, Age_25_34, Age_35_44, Age_45_54, Age_more_than_54, Unknown
    }

    public static int AgeToInt(Enums.Age a) {
        if (a == Enums.Age.Age_less_than_25) return 0;
        else if (a == Enums.Age.Age_25_34) return 1;
        else if (a == Enums.Age.Age_35_44) return 2;
        else if (a == Enums.Age.Age_45_54) return 3;
        else if (a == Enums.Age.Age_more_than_54) return 4;
        return 5;
    }

    public static Enums.Age IntToAge(int i) {
        return Enums.Age.values()[i];
    }


    /*
        Income
     */
    public static enum Income {
        Low, Medium, High, Unknown
    }

    public static int IncomeToInt(Enums.Income i) {
        if (i == Enums.Income.Low) return 0;
        else if (i == Enums.Income.Medium) return 1;
        else if (i == Enums.Income.High) return 2;
        return 3;
    }

    public static Enums.Income IntToIncome(int i) {
        return Enums.Income.values()[i];
    }


    /*
        Context
     */
    public static enum Context {
        News, Shopping, SocialMedia, Travel, Hobbies, Blog, Unknown
    }

    public static int ContextToInt(Context c) {
        if (c == Context.News) return 0;
        else if (c == Context.Shopping) return 1;
        else if (c == Context.SocialMedia) return 2;
        else if (c == Context.Travel) return 3;
        else if (c == Context.Hobbies) return 4;
        else if (c == Context.Blog) return 5;
        return 6;
    }

    public static Context IntToContext(int i) {
        return Context.values()[i];
    }

    /*
        Conversion
     */
    public static enum Conversion {No, Yes, Unknown}

    public static int ConversionToInt(Conversion c) {
        if (c == Conversion.Yes) return 1;
        else if (c == Conversion.No) return 0;
        return 2;
    }

    public static Conversion IntToConversion(int i) {
        return Conversion.values()[i];
    }
}
