package Gui.Menus;

import Commons.ImpressionEntry;
import Commons.UserEntry;
import Gui.DateBrowser.DateBrowser;
import Gui.DateBrowser.DateChangedListener;
import Gui.GuiColors;
import Gui.GuiComponents.*;
import Gui.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static DatabaseManager.Stringifiable.globalDateFormat;

public class FiltersMenu extends RPanel {
    private final MainController mainController;

    public FiltersMenu(MainController mainController) {
        super(GuiColors.BASE_WHITE, new BorderLayout());
        this.mainController = mainController;
        refresh();
    }

    @Override
    public void refresh() {
        removeAll();

        List<Component> menus = new LinkedList<>();

        menus.add(getDateRange());
        menus.add(getAudianceSegments());
        menus.add(getIncome());

        MenuLabel clearFiltersLabel = new MenuLabel("Clear all filters", MenuLabel.LEFT, 16);
        clearFiltersLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        clearFiltersLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //TODO Are you sure message
                mainController.clearFiltersSpecs();
                mainController.refreshGraphs();
                refresh();
            }
        });
        menus.add(clearFiltersLabel);

        add(new ListView(getBackground(), menus).getWrappedInScroll(true), BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private JPanel getDateRange() {
        TitleLabel titleLabel = new TitleLabel("Date range", TitleLabel.LEFT, 20);

        DateBrowser startDate = new DateBrowser(getBackground(), globalDateFormat, new Date());
        try {
            startDate.setDate(globalDateFormat.parse(mainController.getFilterSpecs().getStartDate()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("INIT THIS FROM THE LOADING!!!!!!!!!!!!!");
        }
        startDate.setDateChangedListener(new DateChangedListener() {
            @Override
            public void takeAction() {
                mainController.getFilterSpecs().setStartDate(globalDateFormat.format(startDate.getDate()));
                mainController.refreshGraphs();
            }
        });

        DateBrowser endDate = new DateBrowser(getBackground(), globalDateFormat, new Date());
        try {
            endDate.setDate(globalDateFormat.parse(mainController.getFilterSpecs().getEndDate()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("INIT THIS FROM THE LOADING!!!!!!!!!!!!!");
        }
        endDate.setDateChangedListener(new DateChangedListener() {
            @Override
            public void takeAction() {
                mainController.getFilterSpecs().setEndDate(globalDateFormat.format(endDate.getDate()));
                mainController.refreshGraphs();
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

    private JPanel getAudianceSegments() {
        TitleLabel titleLabel = new TitleLabel("Audience segments", TitleLabel.LEFT, 20);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        wrapper.setBackground(getBackground());


        // GENDER
        CheckBox maleOption = new CheckBox("Male");
        maleOption.setSelected(mainController.getFilterSpecs().getGenders().contains(UserEntry.Gender.Male));
        maleOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (maleOption.isSelected()) {
                    mainController.getFilterSpecs().getGenders().add(UserEntry.Gender.Male);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getGenders().remove(UserEntry.Gender.Male);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox femaleOption = new CheckBox("Female");
        femaleOption.setSelected(mainController.getFilterSpecs().getGenders().contains(UserEntry.Gender.Female));
        femaleOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (femaleOption.isSelected()) {
                    mainController.getFilterSpecs().getGenders().add(UserEntry.Gender.Female);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getGenders().remove(UserEntry.Gender.Female);
                    mainController.refreshGraphs();
                }
            }
        });

        JPanel genders = wrapInRow(new Component[]{maleOption, femaleOption});

        //AGE
        CheckBox opAge_less_than_25 = new CheckBox("<25");
        opAge_less_than_25.setSelected(mainController.getFilterSpecs().getAges().contains(UserEntry.Age.Age_less_than_25));
        opAge_less_than_25.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opAge_less_than_25.isSelected()) {
                    mainController.getFilterSpecs().getAges().add(UserEntry.Age.Age_less_than_25);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getAges().remove(UserEntry.Age.Age_less_than_25);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox opAge_25_34 = new CheckBox("25-34");
        opAge_25_34.setSelected(mainController.getFilterSpecs().getAges().contains(UserEntry.Age.Age_25_34));
        opAge_25_34.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opAge_25_34.isSelected()) {
                    mainController.getFilterSpecs().getAges().add(UserEntry.Age.Age_25_34);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getAges().remove(UserEntry.Age.Age_25_34);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox opAge_35_44 = new CheckBox("35-44");
        opAge_35_44.setSelected(mainController.getFilterSpecs().getAges().contains(UserEntry.Age.Age_35_44));
        opAge_35_44.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opAge_35_44.isSelected()) {
                    mainController.getFilterSpecs().getAges().add(UserEntry.Age.Age_35_44);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getAges().remove(UserEntry.Age.Age_35_44);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox opAge_45_54 = new CheckBox("45-54");
        opAge_45_54.setSelected(mainController.getFilterSpecs().getAges().contains(UserEntry.Age.Age_45_54));
        opAge_45_54.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opAge_45_54.isSelected()) {
                    mainController.getFilterSpecs().getAges().add(UserEntry.Age.Age_45_54);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getAges().remove(UserEntry.Age.Age_45_54);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox opAge_more_than_54 = new CheckBox(">54");
        opAge_more_than_54.setSelected(mainController.getFilterSpecs().getAges().contains(UserEntry.Age.Age_more_than_54));
        opAge_more_than_54.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opAge_more_than_54.isSelected()) {
                    mainController.getFilterSpecs().getAges().add(UserEntry.Age.Age_more_than_54);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getAges().remove(UserEntry.Age.Age_more_than_54);
                    mainController.refreshGraphs();
                }
            }
        });
        JPanel ages = wrapInRow(new Component[]{opAge_less_than_25, opAge_25_34, opAge_35_44, opAge_45_54, opAge_more_than_54});


        //INCOME
        CheckBox opNews = new CheckBox("News");
        opNews.setSelected(mainController.getFilterSpecs().getContexts().contains(ImpressionEntry.Context.News));
        opNews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opNews.isSelected()) {
                    mainController.getFilterSpecs().getContexts().add(ImpressionEntry.Context.News);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getContexts().remove(ImpressionEntry.Context.News);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox opShopping = new CheckBox("Shopping");
        opShopping.setSelected(mainController.getFilterSpecs().getContexts().contains(ImpressionEntry.Context.Shopping));
        opShopping.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opShopping.isSelected()) {
                    mainController.getFilterSpecs().getContexts().add(ImpressionEntry.Context.Shopping);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getContexts().remove(ImpressionEntry.Context.Shopping);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox opSocialMedia = new CheckBox("Social Media");
        opSocialMedia.setSelected(mainController.getFilterSpecs().getContexts().contains(ImpressionEntry.Context.SocialMedia));
        opSocialMedia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opSocialMedia.isSelected()) {
                    mainController.getFilterSpecs().getContexts().add(ImpressionEntry.Context.SocialMedia);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getContexts().remove(ImpressionEntry.Context.SocialMedia);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox opTravels = new CheckBox("Travels");
        opTravels.setSelected(mainController.getFilterSpecs().getContexts().contains(ImpressionEntry.Context.Travel));
        opTravels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opTravels.isSelected()) {
                    mainController.getFilterSpecs().getContexts().add(ImpressionEntry.Context.Travel);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getContexts().remove(ImpressionEntry.Context.Travel);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox opHobbies = new CheckBox("Hobbies");
        opHobbies.setSelected(mainController.getFilterSpecs().getContexts().contains(ImpressionEntry.Context.Hobbies));
        opHobbies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opHobbies.isSelected()) {
                    mainController.getFilterSpecs().getContexts().add(ImpressionEntry.Context.Hobbies);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getContexts().remove(ImpressionEntry.Context.Hobbies);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox opBlog = new CheckBox("Blog");
        opBlog.setSelected(mainController.getFilterSpecs().getContexts().contains(ImpressionEntry.Context.Blog));
        opBlog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opBlog.isSelected()) {
                    mainController.getFilterSpecs().getContexts().add(ImpressionEntry.Context.Blog);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getContexts().remove(ImpressionEntry.Context.Blog);
                    mainController.refreshGraphs();
                }
            }
        });

        JPanel contexts = wrapInRow(new Component[]{opNews, opShopping, opSocialMedia, opTravels, opHobbies, opBlog});

        TitleLabel genderTitle = new TitleLabel("Gender", TitleLabel.LEFT, 16);

        TitleLabel ageTitle = new TitleLabel("Age", TitleLabel.LEFT, 16);

        TitleLabel contextTitle = new TitleLabel("Context", TitleLabel.LEFT, 16);

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

        wrapper.add(new ListView(wrapper.getBackground(), sections, false), BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel getIncome() {
        TitleLabel contextTitle = new TitleLabel("Income", TitleLabel.LEFT, 20);

        // Low, Medium, High,
        CheckBox opLowIncome = new CheckBox("Low");
        opLowIncome.setSelected(mainController.getFilterSpecs().getIncomes().contains(UserEntry.Income.Low));
        opLowIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opLowIncome.isSelected()) {
                    mainController.getFilterSpecs().getIncomes().add(UserEntry.Income.Low);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getIncomes().remove(UserEntry.Income.Low);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox opMediumIncome = new CheckBox("Medium");
        opMediumIncome.setSelected(mainController.getFilterSpecs().getIncomes().contains(UserEntry.Income.Medium));
        opMediumIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opMediumIncome.isSelected()) {
                    mainController.getFilterSpecs().getIncomes().add(UserEntry.Income.Medium);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getIncomes().remove(UserEntry.Income.Medium);
                    mainController.refreshGraphs();
                }
            }
        });

        CheckBox highIncome = new CheckBox("High");
        highIncome.setSelected(mainController.getFilterSpecs().getIncomes().contains(UserEntry.Income.High));
        highIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (highIncome.isSelected()) {
                    mainController.getFilterSpecs().getIncomes().add(UserEntry.Income.High);
                    mainController.refreshGraphs();
                } else {
                    mainController.getFilterSpecs().getIncomes().remove(UserEntry.Income.High);
                    mainController.refreshGraphs();
                }
            }
        });
        JPanel contexts = wrapInRow(new Component[]{opLowIncome, opMediumIncome, highIncome});

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        wrapper.setBackground(GuiColors.BASE_WHITE);
        wrapper.add(contextTitle, BorderLayout.NORTH);
        wrapper.add(contexts, BorderLayout.CENTER);

        return wrapper;
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
