package Commons;

import java.util.LinkedList;
import java.util.List;

public class FilterSpecs {
    private String startDate;
    private String endDate;
    private List<UserEntry.Gender> genders;
    private List<UserEntry.Age> ages;
    private List<UserEntry.Income> incomes;
    private List<ImpressionEntry.Context> contexts;

    public FilterSpecs() {
        this.startDate = "";
        this.endDate = "";
        this.genders = new LinkedList<>();
        this.ages = new LinkedList<>();
        this.incomes = new LinkedList<>();
        this.contexts = new LinkedList<>();
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public List<UserEntry.Gender> getGenders() {
        return genders;
    }

    public List<UserEntry.Age> getAges() {
        return ages;
    }

    public List<UserEntry.Income> getIncomes() {
        return incomes;
    }

    public List<ImpressionEntry.Context> getContexts() {
        return contexts;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setGenders(List<UserEntry.Gender> genders) {
        this.genders = genders;
    }

    public void setAges(List<UserEntry.Age> ages) {
        this.ages = ages;
    }

    public void setIncomes(List<UserEntry.Income> incomes) {
        this.incomes = incomes;
    }

    public void setContexts(List<ImpressionEntry.Context> contexts) {
        this.contexts = contexts;
    }

    public boolean containsFilters() {
        return (getGenders().size() > 0 ||
                getIncomes().size() > 0 ||
                getAges().size() > 0 ||
                getContexts().size() > 0);
    }
}