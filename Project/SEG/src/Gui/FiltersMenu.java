package Gui;

import DatabaseManager.DataExchange;
import DatabaseManager.Stringifiable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class FiltersMenu extends RPanel
{
    private final DataExchange dataExchange;//todo change with controller
    private final BreadCrumbs breadCrumbs;

    public FiltersMenu(DataExchange dataExchange, BreadCrumbs breadCrumbs) {
        super(GuiColors.RED, new BorderLayout());
        this.dataExchange = dataExchange;
        this.breadCrumbs = breadCrumbs;
        refresh();
    }

    @Override
    public void refresh()
    {
        List<Component> menus = new LinkedList<>();

        //DATE
        menus.add(getTimeSpanSelection());

        add(new ListView(getBackground(), menus).getWrappedInScroll(true), BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private JPanel getTimeSpanSelection()
    {
        TitleLabel titleLabel = new TitleLabel("Date range", TitleLabel.LEFT, 20);
        titleLabel.setForeground(GuiColors.LIGHT);

        DateBrowser startDate = new DateBrowser(getBackground(), Stringifiable.globalDateFormat, new Date());
        startDate.setDateChangedListener(new DateChangedListener()
        {
            @Override
            public void takeAction()
            {
                Calendar tmpC = Calendar.getInstance();
                tmpC.setTime(startDate.getDate());
                if (tmpC.get(Calendar.DAY_OF_MONTH) > 15)
                {
                    tmpC.add(Calendar.MONTH, 1);
                    tmpC.set(Calendar.DAY_OF_MONTH, tmpC.getActualMaximum(Calendar.DAY_OF_MONTH));
                } else tmpC.set(Calendar.DAY_OF_MONTH, tmpC.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
        });

        DateBrowser endDate = new DateBrowser(getBackground(), Stringifiable.globalDateFormat, new Date());
        endDate.setDateChangedListener(new DateChangedListener()
        {
            @Override
            public void takeAction()
            {
                Calendar tmpC = Calendar.getInstance();
                tmpC.setTime(endDate.getDate());
                if (tmpC.get(Calendar.DAY_OF_MONTH) > 15)
                {
                    tmpC.add(Calendar.MONTH, 1);
                    tmpC.set(Calendar.DAY_OF_MONTH, tmpC.getActualMaximum(Calendar.DAY_OF_MONTH));
                } else tmpC.set(Calendar.DAY_OF_MONTH, tmpC.getActualMaximum(Calendar.DAY_OF_MONTH));
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

    private JPanel getAudianceSegments()
    {
        TitleLabel titleLabel = new TitleLabel("Audience segments", TitleLabel.LEFT, 20);
        titleLabel.setForeground(GuiColors.LIGHT);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(titleLabel, BorderLayout.NORTH);
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        wrapper.setBackground(getBackground());


        // GENDER
        RadioButton maleOption = new RadioButton("Male");
        maleOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (maleOption.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton femaleOption = new RadioButton("Female");
        femaleOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (femaleOption.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        JPanel genders = wrapInRow(new Component[]{maleOption, femaleOption});

        //AGE
        RadioButton opAge_less_than_25 = new RadioButton("<25");
        opAge_less_than_25.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opAge_less_than_25.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton opAge_25_34 = new RadioButton("25-34");
        opAge_25_34.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opAge_25_34.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton opAge_35_44 = new RadioButton("35-44");
        opAge_35_44.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opAge_35_44.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton opAge_45_54 = new RadioButton("45-54");
        opAge_45_54.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opAge_45_54.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton opAge_more_than_54 = new RadioButton(">54");
        opAge_more_than_54.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opAge_more_than_54.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });
        JPanel ages = wrapInRow(new Component[] {opAge_less_than_25, opAge_25_34, opAge_35_44, opAge_45_54, opAge_more_than_54});


        // Low, Medium, High,
        RadioButton opLowIncome = new RadioButton("Low");
        opLowIncome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opLowIncome.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton opMediumIncome = new RadioButton("Medium");
        opMediumIncome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opMediumIncome.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton highIncome = new RadioButton("High");
        highIncome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (highIncome.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });
        JPanel incomes = wrapInRow(new Component[]{opLowIncome, opMediumIncome, highIncome});

        //INCOME
        RadioButton opNews = new RadioButton("News");
        opNews.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opNews.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton opShopping = new RadioButton("Shopping");
        opShopping.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opShopping.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton opSocialMedia = new RadioButton("Social Media");
        opSocialMedia.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opSocialMedia.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton opTravels = new RadioButton("Travels");
        opTravels.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opTravels.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton opHobbies = new RadioButton("Hobbies");
        opHobbies.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opHobbies.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        RadioButton opBlog = new RadioButton("Blog");
        opBlog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opBlog.isSelected())
                {
                    //todo remove from pool
                }
                else
                {
                    //todo
                }
            }
        });

        JPanel contexts = wrapInRow(new Component[]{opNews, opShopping, opSocialMedia, opTravels, opHobbies, opBlog});

        TitleLabel ageTitle = new TitleLabel("Age", TitleLabel.LEFT, 18);
        ageTitle.setForeground(GuiColors.LIGHT);

        TitleLabel audienceSegmentsTitle = new TitleLabel("Audience segments", TitleLabel.LEFT, 18);
        audienceSegmentsTitle.setForeground(GuiColors.LIGHT);

        TitleLabel contextTitle = new TitleLabel("Context", TitleLabel.LEFT, 18);
        contextTitle.setForeground(GuiColors.LIGHT);

        JPanel subWrapper = new JPanel(new GridLayout(8, 1, 4, 4));
        subWrapper.add(audienceSegmentsTitle);
        subWrapper.add(ageTitle);
        subWrapper.add(ages);


        return wrapper;
    }

    private JPanel wrapInRow(Component[] components)
    {
        JPanel optionsPanel = new JPanel(new GridLayout(1, components.length, 4, 4));
        optionsPanel.setBackground(getBackground());
        optionsPanel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, getBackground()));
        for (int i = 0; i < components.length; i++)
            optionsPanel.add(components[i]);

        return optionsPanel;
    }
}
