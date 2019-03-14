package Gui.DateBrowser;

import DatabaseManager.Stringifiable;
import Gui.Gui;
import Gui.GuiColors;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.TextBox;
import Gui.GuiComponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateBrowser extends JPanel {

    private SimpleDateFormat dateFormat;
    private Date date;
    private DateChangedListener listener;

    public DateBrowser() {
        this(GuiColors.BASE_PRIME, Stringifiable.globalDateFormat, new Date());//TODO change to simple data format
    }

    public DateBrowser(Color background, SimpleDateFormat dateFormat, Date date) {
        super(new BorderLayout(4, 4));
        setBackground(background);

        this.dateFormat = dateFormat;
        this.date = date;

        refresh();
    }

    private void refresh() {
        removeAll();

        DateBrowser thisBrowser = this;

        TextBox dateBox = new TextBox(getBackground());
        dateBox.setEditable(false);
        dateBox.setPreferredSize(new Dimension(150, 26));
        dateBox.setHorizontalAlignment(TextBox.CENTER);
        dateBox.setText(dateFormat.format(date));
        dateBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //size of the screen
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                //height of the task bar
                Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
                int taskBarSize = scnMax.bottom;

                int x = dateBox.getLocationOnScreen().x;
                if (x + DateBrowserFrame.WIDTH > screenSize.width) {
                    x = screenSize.width - DateBrowserFrame.WIDTH;
                }
                int y = dateBox.getLocationOnScreen().y + dateBox.getHeight();
                if (y + DateBrowserFrame.HEIGHT > (screenSize.height - taskBarSize)) {
                    y = screenSize.height - taskBarSize - DateBrowserFrame.HEIGHT;
                }

                new DateBrowserFrame(thisBrowser, x, y).setVisible(true);
            }
        });

        add(dateBox, BorderLayout.CENTER);

        repaint();
        revalidate();
    }

    private void notifyListener() {
        if (this.listener != null) {
            this.listener.takeAction();
        }
    }

    public Date getDate() {
        return this.date;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public String getText() {
        return this.dateFormat.format(this.date);
    }

    public DateChangedListener getDateChangedListener() {
        return listener;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setDate(Date date) {
        this.date = date;
        refresh();
        notifyListener();
    }

    public void setDateChangedListener(DateChangedListener listener) {
        this.listener = listener;
    }
}

class DateBrowserFrame extends JFrame {

    public static int HEIGHT = 230;
    public static int WIDTH = 250;
    private DateBrowser dateBrowser;

    public DateBrowserFrame(DateBrowser dateBrowser, int x, int y) throws HeadlessException {
        super("Dashboard App");
        this.dateBrowser = dateBrowser;

        setLocation(x, y);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setUndecorated(true);
        setSize(WIDTH, HEIGHT);
        setBackground(GuiColors.BASE_PRIME);
        getContentPane().setBackground(getBackground());

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setVisible(false);
            }
        });

        refresh();
    }

    private void refresh() {
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateBrowser.getDate());

        //these will need to be updated
        TitleLabel lspYearAndMonthAndDayLabel = new TitleLabel(dateBrowser.getDateFormat().format(dateBrowser.getDate()), TitleLabel.CENTER, 16, GuiColors.BASE_WHITE);

        //CENTER VIEW --> Date Browser
        JPanel centerViewPanel = new JPanel(new BorderLayout(10, 10));
        centerViewPanel.setBorder(BorderFactory.createEmptyBorder());
        centerViewPanel.setBackground(getBackground());
        centerViewPanel.setPreferredSize(new Dimension(400, 400));

        ////NORTH VIEW with month and name
        JPanel cvpNorthPanel = new JPanel(new BorderLayout(10, 10));
        cvpNorthPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        cvpNorthPanel.setBackground(getBackground());

        MenuLabel goBackOneMonthLabel = new MenuLabel("<", MenuLabel.CENTER, 14);
        goBackOneMonthLabel.setForeground(GuiColors.BASE_WHITE);
        goBackOneMonthLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Calendar cTMP = Calendar.getInstance();
                cTMP.setTime(dateBrowser.getDate());
                cTMP.add(Calendar.MONTH, -1);
                dateBrowser.setDate(cTMP.getTime());
                refresh();
                repaint();
                revalidate();
            }
        });
        MenuLabel goForewordOneMonthLabel = new MenuLabel(">", MenuLabel.CENTER, 14);
        goForewordOneMonthLabel.setForeground(GuiColors.BASE_WHITE);
        goForewordOneMonthLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Calendar cTMP = Calendar.getInstance();
                cTMP.setTime(dateBrowser.getDate());
                cTMP.add(Calendar.MONTH, +1);
                dateBrowser.setDate(cTMP.getTime());
                refresh();
                repaint();
                revalidate();
            }
        });

        cvpNorthPanel.add(goBackOneMonthLabel, BorderLayout.WEST);
        cvpNorthPanel.add(lspYearAndMonthAndDayLabel, BorderLayout.CENTER);
        cvpNorthPanel.add(goForewordOneMonthLabel, BorderLayout.EAST);

        getContentPane().add(cvpNorthPanel, BorderLayout.NORTH);

        ////CENTER VIEW --> day of the months choosers
        JPanel cvpDaysViewPanel = new JPanel(new GridLayout(7, 7, 0, 0));
        cvpDaysViewPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        cvpDaysViewPanel.setBackground(getBackground());

        Calendar c = Calendar.getInstance();
        c.setTime(dateBrowser.getDate());

        c.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = (Date) c.getTime().clone();

        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date endDate = (Date) c.getTime().clone();

        cvpDaysViewPanel.add(new TitleLabel("MO", TitleLabel.CENTER, 8, GuiColors.BASE_WHITE));
        cvpDaysViewPanel.add(new TitleLabel("TU", TitleLabel.CENTER, 8, GuiColors.BASE_WHITE));
        cvpDaysViewPanel.add(new TitleLabel("WE", TitleLabel.CENTER, 8, GuiColors.BASE_WHITE));
        cvpDaysViewPanel.add(new TitleLabel("TH", TitleLabel.CENTER, 8, GuiColors.BASE_WHITE));
        cvpDaysViewPanel.add(new TitleLabel("FR", TitleLabel.CENTER, 8, GuiColors.BASE_WHITE));
        cvpDaysViewPanel.add(new TitleLabel("SA", TitleLabel.CENTER, 8, GuiColors.BASE_WHITE));
        cvpDaysViewPanel.add(new TitleLabel("SU", TitleLabel.CENTER, 8, GuiColors.BASE_WHITE));

        int added = 0;
        int lastDayNumber = 1;
        for (Date d = startDate; d.before(endDate); d.setTime(d.getTime() + 86400000)) {
            final Calendar ctmp = Calendar.getInstance();
            ctmp.setTime(d);

            int dayNumTMP = ctmp.get(Calendar.DAY_OF_WEEK);

            for (int i = lastDayNumber; i < dayNumTMP - 1; i++) {
                cvpDaysViewPanel.add(new TitleLabel("", TitleLabel.CENTER, 12));
                added++;
            }
            lastDayNumber = dayNumTMP;

            MenuLabel dayChooser = new MenuLabel(Integer.toString(ctmp.get(Calendar.DAY_OF_MONTH)), TitleLabel.CENTER, 12);
            dayChooser.setForeground(GuiColors.BASE_WHITE);
            dayChooser.dropAllListeners();
            dayChooser.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    dayChooser.setForeground(GuiColors.DARK_GRAY);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    dayChooser.setForeground(GuiColors.BASE_WHITE);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    dateBrowser.setDate(ctmp.getTime());
                    setVisible(false);
                }
            });

            if (ctmp.getTime().equals(dateBrowser.getDate())) {
                for (MouseListener ml : dayChooser.getMouseListeners()) {
                    dayChooser.removeMouseListener(ml);
                }

                dayChooser.setForeground(new Color(105, 175, 205));
            }
            cvpDaysViewPanel.add(dayChooser);
            added++;
        }

        for (int i = added; i < 42; i++) {
            cvpDaysViewPanel.add(new TitleLabel("", TitleLabel.CENTER, 8));
        }

        centerViewPanel.add(cvpDaysViewPanel, BorderLayout.CENTER);


        /*
            TIME EDITOR
         */

        SimpleDateFormat timeDate = new SimpleDateFormat("hh:mm:ss");


        JPanel timeEditorPanel = new JPanel(new BorderLayout());
        timeEditorPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
        timeEditorPanel.setBackground(getBackground());

        TitleLabel timeDisplayBottomEditor = new TitleLabel(timeDate.format(dateBrowser.getDate()), TitleLabel.CENTER, 12, GuiColors.BASE_WHITE);
        timeDisplayBottomEditor.setForeground(GuiColors.BASE_WHITE);
        timeEditorPanel.add(timeDisplayBottomEditor, BorderLayout.CENTER);

        JPanel leftButtonsTimeEditor = new JPanel(new GridLayout(1, 2, 4, 0));
        leftButtonsTimeEditor.setBorder(BorderFactory.createEmptyBorder());
        leftButtonsTimeEditor.setBackground(getBackground());
        timeEditorPanel.add(leftButtonsTimeEditor, BorderLayout.WEST);

        MenuLabel addHourMB = new MenuLabel("+h", MenuLabel.CENTER, 12);
        addHourMB.setForeground(GuiColors.BASE_WHITE);
        addHourMB.dropAllListeners();
        addHourMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addHourMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addHourMB.setForeground(GuiColors.BASE_WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Calendar cTMP = Calendar.getInstance();
                cTMP.setTime(dateBrowser.getDate());
                cTMP.add(Calendar.HOUR, +1);
                dateBrowser.setDate(cTMP.getTime());
                refresh();
                repaint();
                revalidate();
            }
        });
        leftButtonsTimeEditor.add(addHourMB);

        MenuLabel addMinuteMB = new MenuLabel("+m", MenuLabel.CENTER, 12);
        addMinuteMB.setForeground(GuiColors.BASE_WHITE);
        addMinuteMB.dropAllListeners();
        addMinuteMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addMinuteMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addMinuteMB.setForeground(GuiColors.BASE_WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Calendar cTMP = Calendar.getInstance();
                cTMP.setTime(dateBrowser.getDate());
                cTMP.add(Calendar.MINUTE, +1);
                dateBrowser.setDate(cTMP.getTime());
                refresh();
                repaint();
                revalidate();
            }
        });
        leftButtonsTimeEditor.add(addMinuteMB);

        MenuLabel addSecondMB = new MenuLabel("+s", MenuLabel.CENTER, 12);
        addSecondMB.setForeground(GuiColors.BASE_WHITE);
        addSecondMB.dropAllListeners();
        addSecondMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addSecondMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addSecondMB.setForeground(GuiColors.BASE_WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Calendar cTMP = Calendar.getInstance();
                cTMP.setTime(dateBrowser.getDate());
                cTMP.add(Calendar.SECOND, +1);
                dateBrowser.setDate(cTMP.getTime());
                refresh();
                repaint();
                revalidate();
            }
        });
        leftButtonsTimeEditor.add(addSecondMB);


        JPanel rightButtonsTimeEditor = new JPanel(new GridLayout(1, 2, 4, 0));
        rightButtonsTimeEditor.setBorder(BorderFactory.createEmptyBorder());
        rightButtonsTimeEditor.setBackground(getBackground());
        timeEditorPanel.add(rightButtonsTimeEditor, BorderLayout.EAST);


        MenuLabel rmHourMB = new MenuLabel("-h", MenuLabel.CENTER, 12);
        rmHourMB.setForeground(GuiColors.BASE_WHITE);
        rmHourMB.dropAllListeners();
        rmHourMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rmHourMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rmHourMB.setForeground(GuiColors.BASE_WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Calendar cTMP = Calendar.getInstance();
                cTMP.setTime(dateBrowser.getDate());
                cTMP.add(Calendar.HOUR, -1);
                dateBrowser.setDate(cTMP.getTime());
                refresh();
                repaint();
                revalidate();
            }
        });
        rightButtonsTimeEditor.add(rmHourMB);

        MenuLabel rmMinuteMB = new MenuLabel("-m", MenuLabel.CENTER, 12);
        rmMinuteMB.setForeground(GuiColors.BASE_WHITE);
        rmMinuteMB.dropAllListeners();
        rmMinuteMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rmMinuteMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rmMinuteMB.setForeground(GuiColors.BASE_WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Calendar cTMP = Calendar.getInstance();
                cTMP.setTime(dateBrowser.getDate());
                cTMP.add(Calendar.MINUTE, -1);
                dateBrowser.setDate(cTMP.getTime());
                refresh();
                repaint();
                revalidate();
            }
        });
        rightButtonsTimeEditor.add(rmMinuteMB);

        MenuLabel rmSecondMB = new MenuLabel("-s", MenuLabel.CENTER, 12);
        rmSecondMB.setForeground(GuiColors.BASE_WHITE);
        rmSecondMB.dropAllListeners();
        rmSecondMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rmSecondMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rmSecondMB.setForeground(GuiColors.BASE_WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Calendar cTMP = Calendar.getInstance();
                cTMP.setTime(dateBrowser.getDate());
                cTMP.add(Calendar.SECOND, -1);
                dateBrowser.setDate(cTMP.getTime());
                refresh();
                repaint();
                revalidate();
            }
        });
        rightButtonsTimeEditor.add(rmSecondMB);


        centerViewPanel.add(timeEditorPanel, BorderLayout.NORTH);

        getContentPane().add(centerViewPanel, BorderLayout.CENTER);
    }
}
