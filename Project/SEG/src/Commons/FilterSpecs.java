package Commons;

import java.util.LinkedList;
import java.util.List;

public class FilterSpecs {
    private String startDate;
    private String endDate;
    private List<Enums.Gender> genders;
    private List<Enums.Age> ages;
    private List<Enums.Income> incomes;
    private List<Enums.Context> contexts;

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

    public List<Enums.Gender> getGenders() {
        return genders;
    }

    public void setGenders(List<Enums.Gender> genders) {
        this.genders = genders;
    }

    public List<Enums.Age> getAges() {
        return ages;
    }

    public void setAges(List<Enums.Age> ages) {
        this.ages = ages;
    }

    public List<Enums.Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Enums.Income> incomes) {
        this.incomes = incomes;
    }

    public List<Enums.Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<Enums.Context> contexts) {
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