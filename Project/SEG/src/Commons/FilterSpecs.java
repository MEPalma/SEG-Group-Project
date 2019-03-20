package Commons;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Filter;

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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<UserEntry.Gender> getGenders() {
        return genders;
    }

    public void setGenders(List<UserEntry.Gender> genders) {
        this.genders = genders;
    }

    public List<UserEntry.Age> getAges() {
        return ages;
    }

    public void setAges(List<UserEntry.Age> ages) {
        this.ages = ages;
    }

    public List<UserEntry.Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<UserEntry.Income> incomes) {
        this.incomes = incomes;
    }

    public List<ImpressionEntry.Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<ImpressionEntry.Context> contexts) {
        this.contexts = contexts;
    }

    public FilterSpecs clone() {
        FilterSpecs clone = new FilterSpecs();
        clone.getGenders().addAll(this.genders);
        clone.getContexts().addAll(this.contexts);
        clone.getAges().addAll(this.ages);
        clone.getIncomes().addAll(this.incomes);
        clone.setStartDate(this.getStartDate());
        clone.setEndDate(this.getEndDate());
        return clone;
    }

    private void clearAll() {
        this.genders.clear();
        this.contexts.clear();
        this.ages.clear();
        this.incomes.clear();
    }

    public void updateFrom(FilterSpecs filterSpecs) {
        clearAll();
        this.genders.addAll(filterSpecs.getGenders());
        this.contexts.addAll(filterSpecs.getContexts());
        this.ages.addAll(filterSpecs.getAges());
        this.incomes.addAll(filterSpecs.getIncomes());
        this.startDate = filterSpecs.getStartDate();
        this.endDate = filterSpecs.getEndDate();
    }
}