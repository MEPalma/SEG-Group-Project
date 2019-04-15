package Gui.Menus;

import Commons.*;
import Gui.DateBrowser.DateBrowser;
import Gui.GuiColors;
import Gui.GuiComponents.*;
import Gui.MainController;
import Gui.TakeActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static DatabaseManager.Stringifiable.globalDateFormat;

public class FiltersMenu extends RPanel {
    private final MainController mainController;
    private FilterSpecs originalActiveFilters, cloneOfActiveFilters;

    public FiltersMenu(MainController mainController) {
        super(mainController.getGuiColors().getGuiTextColor(), new BorderLayout());
        this.mainController = mainController;

        Object selectedGraph = this.mainController.getSelectedGraph();
        if (selectedGraph instanceof GraphSpecs) this.originalActiveFilters = ((GraphSpecs) selectedGraph).getFilterSpecs();
        else if (selectedGraph instanceof CompareGraphSpec)
            this.originalActiveFilters = ((CompareGraphSpec) selectedGraph).getFilterSpecs();

        this.cloneOfActiveFilters = this.originalActiveFilters.clone();
        refresh();
    }

    @Override
    public void refresh() {
        removeAll();

        List<Component> menus = new LinkedList<>();

        menus.add(getDateRange());
        menus.add(getAudienceSegments());
        menus.add(getIncome());
        menus.add(getApplyOrClearButtons());

        add(new ListView(mainController.getGuiColors(), menus).getWrappedInScroll(true), BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private JPanel getDateRange() {
        TitleLabel titleLabel = new TitleLabel("Date range", TitleLabel.LEFT, 20, mainController.getGuiColors());

        DateBrowser startDate = new DateBrowser(mainController.getGuiColors(), getBackground(), globalDateFormat, new Date());
        try {
            startDate.setDate(globalDateFormat.parse(cloneOfActiveFilters.getStartDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        startDate.setDateChangedListener(new TakeActionListener() {
            @Override
            public void takeAction() {
                cloneOfActiveFilters.setStartDate(globalDateFormat.format(startDate.getDate()));
            }
        });

        DateBrowser endDate = new DateBrowser(mainController.getGuiColors(), getBackground(), globalDateFormat, new Date());
        try {
            endDate.setDate(globalDateFormat.parse(cloneOfActiveFilters.getEndDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        endDate.setDateChangedListener(new TakeActionListener() {
            @Override
            public void takeAction() {
                cloneOfActiveFilters.setEndDate(globalDateFormat.format(endDate.getDate()));
            }
        });


        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        wrapper.setBackground(getBackground());

        wrapper.add(titleLabel, BorderLayout.NORTH);

        JPanel dateWrapper = new JPanel(new GridLayout(1, 2, 4, 4));
        dateWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        dateWrapper.setBackground(getBackground());
        dateWrapper.add(startDate);
        dateWrapper.add(endDate);

        wrapper.add(dateWrapper, BorderLayout.SOUTH);


        return wrapper;
    }

    private JPanel getAudienceSegments() {
        TitleLabel titleLabel = new TitleLabel("Audience segments", TitleLabel.LEFT, 20, mainController.getGuiColors());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        wrapper.setBackground(getBackground());


        // GENDER
        CheckBox maleOption = new CheckBox("Male", Color.DARK_GRAY);
        maleOption.setSelected(cloneOfActiveFilters.getGenders().contains(Enums.Gender.Male));
        maleOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maleOption.isSelected()) {
                    cloneOfActiveFilters.getGenders().add(Enums.Gender.Male);
                } else {
                    cloneOfActiveFilters.getGenders().remove(Enums.Gender.Male);
                }
            }
        });

        CheckBox femaleOption = new CheckBox("Female", Color.DARK_GRAY);
        femaleOption.setSelected(cloneOfActiveFilters.getGenders().contains(Enums.Gender.Female));
        femaleOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (femaleOption.isSelected()) {
                    cloneOfActiveFilters.getGenders().add(Enums.Gender.Female);
                } else {
                    cloneOfActiveFilters.getGenders().remove(Enums.Gender.Female);
                }
            }
        });

        JPanel genders = wrapInRow(new Component[]{maleOption, femaleOption});

        //AGE
        CheckBox opAge_less_than_25 = new CheckBox("<25", Color.DARK_GRAY);
        opAge_less_than_25.setSelected(cloneOfActiveFilters.getAges().contains(Enums.Age.Age_less_than_25));
        opAge_less_than_25.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opAge_less_than_25.isSelected()) {
                    cloneOfActiveFilters.getAges().add(Enums.Age.Age_less_than_25);
                } else {
                    cloneOfActiveFilters.getAges().remove(Enums.Age.Age_less_than_25);
                }
            }
        });

        CheckBox opAge_25_34 = new CheckBox("25-34", Color.DARK_GRAY);
        opAge_25_34.setSelected(cloneOfActiveFilters.getAges().contains(Enums.Age.Age_25_34));
        opAge_25_34.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opAge_25_34.isSelected()) {
                    cloneOfActiveFilters.getAges().add(Enums.Age.Age_25_34);
                } else {
                    cloneOfActiveFilters.getAges().remove(Enums.Age.Age_25_34);
                }
            }
        });

        CheckBox opAge_35_44 = new CheckBox("35-44", Color.DARK_GRAY);
        opAge_35_44.setSelected(cloneOfActiveFilters.getAges().contains(Enums.Age.Age_35_44));
        opAge_35_44.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opAge_35_44.isSelected()) {
                    cloneOfActiveFilters.getAges().add(Enums.Age.Age_35_44);
                } else {
                    cloneOfActiveFilters.getAges().remove(Enums.Age.Age_35_44);
                }
            }
        });

        CheckBox opAge_45_54 = new CheckBox("45-54", Color.DARK_GRAY);
        opAge_45_54.setSelected(cloneOfActiveFilters.getAges().contains(Enums.Age.Age_45_54));
        opAge_45_54.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opAge_45_54.isSelected()) {
                    cloneOfActiveFilters.getAges().add(Enums.Age.Age_45_54);
                } else {
                    cloneOfActiveFilters.getAges().remove(Enums.Age.Age_45_54);
                }
            }
        });

        CheckBox opAge_more_than_54 = new CheckBox(">54", Color.DARK_GRAY);
        opAge_more_than_54.setSelected(cloneOfActiveFilters.getAges().contains(Enums.Age.Age_more_than_54));
        opAge_more_than_54.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opAge_more_than_54.isSelected()) {
                    cloneOfActiveFilters.getAges().add(Enums.Age.Age_more_than_54);
                } else {
                    cloneOfActiveFilters.getAges().remove(Enums.Age.Age_more_than_54);
                }
            }
        });
        JPanel ages = wrapInRow(new Component[]{opAge_less_than_25, opAge_25_34, opAge_35_44, opAge_45_54, opAge_more_than_54});


        //INCOME
        CheckBox opNews = new CheckBox("News", Color.DARK_GRAY);
        opNews.setSelected(cloneOfActiveFilters.getContexts().contains(Enums.Context.News));
        opNews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opNews.isSelected()) {
                    cloneOfActiveFilters.getContexts().add(Enums.Context.News);
                } else {
                    cloneOfActiveFilters.getContexts().remove(Enums.Context.News);
                }
            }
        });

        CheckBox opShopping = new CheckBox("Shopping", Color.DARK_GRAY);
        opShopping.setSelected(cloneOfActiveFilters.getContexts().contains(Enums.Context.Shopping));
        opShopping.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opShopping.isSelected()) {
                    cloneOfActiveFilters.getContexts().add(Enums.Context.Shopping);
                } else {
                    cloneOfActiveFilters.getContexts().remove(Enums.Context.Shopping);
                }
            }
        });

        CheckBox opSocialMedia = new CheckBox("Social Media", Color.DARK_GRAY);
        opSocialMedia.setSelected(cloneOfActiveFilters.getContexts().contains(Enums.Context.SocialMedia));
        opSocialMedia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opSocialMedia.isSelected()) {
                    cloneOfActiveFilters.getContexts().add(Enums.Context.SocialMedia);
                } else {
                    cloneOfActiveFilters.getContexts().remove(Enums.Context.SocialMedia);
                }
            }
        });

        CheckBox opTravels = new CheckBox("Travels", Color.DARK_GRAY);
        opTravels.setSelected(cloneOfActiveFilters.getContexts().contains(Enums.Context.Travel));
        opTravels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opTravels.isSelected()) {
                    cloneOfActiveFilters.getContexts().add(Enums.Context.Travel);
                } else {
                    cloneOfActiveFilters.getContexts().remove(Enums.Context.Travel);
                }
            }
        });

        CheckBox opHobbies = new CheckBox("Hobbies", Color.DARK_GRAY);
        opHobbies.setSelected(cloneOfActiveFilters.getContexts().contains(Enums.Context.Hobbies));
        opHobbies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opHobbies.isSelected()) {
                    cloneOfActiveFilters.getContexts().add(Enums.Context.Hobbies);
                } else {
                    cloneOfActiveFilters.getContexts().remove(Enums.Context.Hobbies);
                }
            }
        });

        CheckBox opBlog = new CheckBox("Blog", Color.DARK_GRAY);
        opBlog.setSelected(cloneOfActiveFilters.getContexts().contains(Enums.Context.Blog));
        opBlog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opBlog.isSelected()) {
                    cloneOfActiveFilters.getContexts().add(Enums.Context.Blog);
                } else {
                    cloneOfActiveFilters.getContexts().remove(Enums.Context.Blog);
                }
            }
        });

        JPanel contexts = wrapInRow(new Component[]{opNews, opShopping, opSocialMedia, opTravels, opHobbies, opBlog});

        TitleLabel genderTitle = new TitleLabel("Gender", TitleLabel.LEFT, 16, mainController.getGuiColors());

        TitleLabel ageTitle = new TitleLabel("Age", TitleLabel.LEFT, 16, mainController.getGuiColors());

        TitleLabel contextTitle = new TitleLabel("Context", TitleLabel.LEFT, 16, mainController.getGuiColors());

        JPanel subWrapperGender = new JPanel(new GridLayout(2, 1, 4, 4));
        subWrapperGender.setBorder(BorderFactory.createEmptyBorder());
        subWrapperGender.setBackground(wrapper.getBackground());
        subWrapperGender.add(genderTitle);
        subWrapperGender.add(genders);

        JPanel subWrapperAge = new JPanel(new GridLayout(2, 1, 4, 4));
        subWrapperAge.setBorder(BorderFactory.createEmptyBorder());
        subWrapperAge.setBackground(wrapper.getBackground());
        subWrapperAge.add(ageTitle);
        subWrapperAge.add(ages);

        JPanel subWrapperContext = new JPanel(new GridLayout(2, 1, 4, 4));
        subWrapperContext.setBorder(BorderFactory.createEmptyBorder());
        subWrapperContext.setBackground(wrapper.getBackground());
        subWrapperContext.add(contextTitle);
        subWrapperContext.add(contexts);

        List<Component> sections = new LinkedList<>();
        sections.add(subWrapperGender);
        sections.add(subWrapperAge);
        sections.add(subWrapperContext);

        wrapper.add(new ListView(mainController.getGuiColors(), sections, false), BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getIncome() {
        TitleLabel contextTitle = new TitleLabel("Income", TitleLabel.LEFT, 20, mainController.getGuiColors());

        // Low, Medium, High,
        CheckBox opLowIncome = new CheckBox("Low", Color.DARK_GRAY);
        opLowIncome.setSelected(cloneOfActiveFilters.getIncomes().contains(Enums.Income.Low));
        opLowIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opLowIncome.isSelected()) {
                    cloneOfActiveFilters.getIncomes().add(Enums.Income.Low);
                } else {
                    cloneOfActiveFilters.getIncomes().remove(Enums.Income.Low);
                }
            }
        });

        CheckBox opMediumIncome = new CheckBox("Medium", Color.DARK_GRAY);
        opMediumIncome.setSelected(cloneOfActiveFilters.getIncomes().contains(Enums.Income.Medium));
        opMediumIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opMediumIncome.isSelected()) {
                    cloneOfActiveFilters.getIncomes().add(Enums.Income.Medium);
                } else {
                    cloneOfActiveFilters.getIncomes().remove(Enums.Income.Medium);
                }
            }
        });

        CheckBox highIncome = new CheckBox("High", Color.DARK_GRAY);
        highIncome.setSelected(cloneOfActiveFilters.getIncomes().contains(Enums.Income.High));
        highIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (highIncome.isSelected()) {
                    cloneOfActiveFilters.getIncomes().add(Enums.Income.High);
                } else {
                    cloneOfActiveFilters.getIncomes().remove(Enums.Income.High);
                }
            }
        });
        JPanel contexts = wrapInRow(new Component[]{opLowIncome, opMediumIncome, highIncome});

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        wrapper.add(contextTitle, BorderLayout.NORTH);
        wrapper.add(contexts, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getApplyOrClearButtons() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());
        wrapper.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, mainController.getGuiColors().getGuiTextColor()));

        JPanel choicesSplitter = new JPanel(new GridLayout(1, 2));
        choicesSplitter.setBackground(wrapper.getBackground());
        choicesSplitter.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        MenuLabel applyFiltersLabel = new MenuLabel("Apply", MenuLabel.CENTER, 16, mainController.getGuiColors());
        applyFiltersLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        applyFiltersLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                originalActiveFilters.updateFrom(cloneOfActiveFilters);
                refreshGraphOnController();
                refresh();
            }
        });
        choicesSplitter.add(applyFiltersLabel);

        MenuLabel clearFiltersLabel = new MenuLabel("Clear", MenuLabel.CENTER, 14, mainController.getGuiColors());
        clearFiltersLabel.dropAllListeners();
        clearFiltersLabel.setForeground(GuiColors.RED_ERROR);
        clearFiltersLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        clearFiltersLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO Are you sure message
                mainController.clearFilter(originalActiveFilters);
                cloneOfActiveFilters = originalActiveFilters.clone();
                refreshGraphOnController();
                refresh();
            }
        });
        choicesSplitter.add(clearFiltersLabel);

        wrapper.add(choicesSplitter);

        return wrapper;
    }

    private void refreshGraphOnController() {
        Object selectedGraph = mainController.getSelectedGraph();
        if (selectedGraph instanceof GraphSpecs)
            mainController.refreshGraph((GraphSpecs) selectedGraph);
        else mainController.refreshGraph((CompareGraphSpec) selectedGraph);
    }

    private JPanel wrapInRow(Component[] components) {
        int columns = (components.length > 4) ? (components.length / 2) : components.length;
        int rows = components.length / columns;

        JPanel optionsPanel = new JPanel(new GridLayout(rows, columns, 0, 2));
        optionsPanel.setBackground(getBackground());
        optionsPanel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, getBackground()));
        for (int i = 0; i < components.length; i++)
            optionsPanel.add(components[i]);

        return optionsPanel;
    }
}
