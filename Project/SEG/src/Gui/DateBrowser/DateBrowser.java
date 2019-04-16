package Gui.DateBrowser;

import DatabaseManager.Stringifiable;
import Gui.GuiColors;
import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.TextBox;
import Gui.GuiComponents.TitleLabel;
import Gui.TakeActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateBrowser extends JPanel {

    private SimpleDateFormat dateFormat;
    private Date date;
    private TakeActionListener listener;

    private GuiColors guiColors;

    public DateBrowser(GuiColors guiColors) {
        this(guiColors, guiColors.getGuiPrimeColor(), Stringifiable.globalDateFormat, new Date());
    }

    public DateBrowser(GuiColors guiColors, Color background, SimpleDateFormat dateFormat, Date date) {
        super(new BorderLayout(4, 4));
        setBackground(background);
        this.guiColors = guiColors;
        this.dateFormat = dateFormat;
        this.date = date;

        refresh();
    }

    private void refresh() {
        removeAll();

        DateBrowser thisBrowser = this;

        TextBox dateBox = new TextBox(guiColors);
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

    public void setDate(Date date) {
        this.date = date;
        refresh();
        notifyListener();
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getText() {
        return this.dateFormat.format(this.date);
    }

    public TakeActionListener getDateChangedListener() {
        return listener;
    }

    public void setDateChangedListener(TakeActionListener listener) {
        this.listener = listener;
    }

    public GuiColors getGuiColors() {
        return this.guiColors;
    }
}

class DateBrowserFrame extends JDialog {

    public static int HEIGHT = 230;
    public static int WIDTH = 250;
    private DateBrowser dateBrowser;

    public DateBrowserFrame(DateBrowser dateBrowser, int x, int y) throws HeadlessException {
        super();
        this.dateBrowser = dateBrowser;

        setLocation(x, y);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setUndecorated(true);
        setSize(WIDTH, HEIGHT);
        setBackground(dateBrowser.getGuiColors().getGuiPrimeColor());
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
        getContentPane().setLayout(new GridLayout(1, 1));

        JPanel mainWrapper = new JPanel(new BorderLayout());
        mainWrapper.setBackground(getBackground());
        mainWrapper.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, getBackground(), Color.LIGHT_GRAY, dateBrowser.getGuiColors().getGuiBackgroundColor()));
        getContentPane().add(mainWrapper);

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateBrowser.getDate());

        //these will need to be updated
        TitleLabel lspYearAndMonthAndDayLabel = new TitleLabel(dateBrowser.getDateFormat().format(dateBrowser.getDate()), TitleLabel.CENTER, 16, dateBrowser.getGuiColors().getGuiTextColor());

        //CENTER VIEW --> Date Browser
        JPanel centerViewPanel = new JPanel(new BorderLayout(10, 10));
        centerViewPanel.setBorder(BorderFactory.createEmptyBorder());
        centerViewPanel.setBackground(getBackground());
        centerViewPanel.setPreferredSize(new Dimension(400, 400));

        ////NORTH VIEW with month and name
        JPanel cvpNorthPanel = new JPanel(new BorderLayout(10, 10));
        cvpNorthPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        cvpNorthPanel.setBackground(getBackground());

        MenuLabel goBackOneMonthLabel = new MenuLabel("<", MenuLabel.CENTER, 14, dateBrowser.getGuiColors());
        goBackOneMonthLabel.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
        goBackOneMonthLabel.dropAllListeners();
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

            @Override
            public void mouseEntered(MouseEvent e) {
                goBackOneMonthLabel.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                goBackOneMonthLabel.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
            }
        });
        MenuLabel goForewordOneMonthLabel = new MenuLabel(">", MenuLabel.CENTER, 14, dateBrowser.getGuiColors());
        goForewordOneMonthLabel.dropAllListeners();
        goForewordOneMonthLabel.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
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

            @Override
            public void mouseEntered(MouseEvent e) {
                goForewordOneMonthLabel.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                goForewordOneMonthLabel.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
            }
        });

        cvpNorthPanel.add(goBackOneMonthLabel, BorderLayout.WEST);
        cvpNorthPanel.add(lspYearAndMonthAndDayLabel, BorderLayout.CENTER);
        cvpNorthPanel.add(goForewordOneMonthLabel, BorderLayout.EAST);

        mainWrapper.add(cvpNorthPanel, BorderLayout.NORTH);

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

        cvpDaysViewPanel.add(new TitleLabel("MO", TitleLabel.CENTER, 8, dateBrowser.getGuiColors().getGuiTextColor()));
        cvpDaysViewPanel.add(new TitleLabel("TU", TitleLabel.CENTER, 8, dateBrowser.getGuiColors().getGuiTextColor()));
        cvpDaysViewPanel.add(new TitleLabel("WE", TitleLabel.CENTER, 8, dateBrowser.getGuiColors().getGuiTextColor()));
        cvpDaysViewPanel.add(new TitleLabel("TH", TitleLabel.CENTER, 8, dateBrowser.getGuiColors().getGuiTextColor()));
        cvpDaysViewPanel.add(new TitleLabel("FR", TitleLabel.CENTER, 8, dateBrowser.getGuiColors().getGuiTextColor()));
        cvpDaysViewPanel.add(new TitleLabel("SA", TitleLabel.CENTER, 8, dateBrowser.getGuiColors().getGuiTextColor()));
        cvpDaysViewPanel.add(new TitleLabel("SU", TitleLabel.CENTER, 8, dateBrowser.getGuiColors().getGuiTextColor()));

        int added = 0;
        int lastDayNumber = 1;
        for (Date d = startDate; d.before(endDate); d.setTime(d.getTime() + 86400000)) {
            final Calendar ctmp = Calendar.getInstance();
            ctmp.setTime(d);

            int dayNumTMP = ctmp.get(Calendar.DAY_OF_WEEK);

            for (int i = lastDayNumber; i < dayNumTMP - 1; i++) {
                cvpDaysViewPanel.add(new TitleLabel("", TitleLabel.CENTER, 12, dateBrowser.getGuiColors()));
                added++;
            }
            lastDayNumber = dayNumTMP;

            MenuLabel dayChooser = new MenuLabel(Integer.toString(ctmp.get(Calendar.DAY_OF_MONTH)), TitleLabel.CENTER, 12, dateBrowser.getGuiColors());
            dayChooser.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
            dayChooser.dropAllListeners();
            dayChooser.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    dayChooser.setForeground(GuiColors.DARK_GRAY);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    dayChooser.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
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
            cvpDaysViewPanel.add(new TitleLabel("", TitleLabel.CENTER, 8, dateBrowser.getGuiColors()));
        }

        centerViewPanel.add(cvpDaysViewPanel, BorderLayout.CENTER);


        /*
            TIME EDITOR
         */

        SimpleDateFormat timeDate = new SimpleDateFormat("hh:mm:ss");


        JPanel timeEditorPanel = new JPanel(new BorderLayout());
        timeEditorPanel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
        timeEditorPanel.setBackground(getBackground());

        TitleLabel timeDisplayBottomEditor = new TitleLabel(timeDate.format(dateBrowser.getDate()), TitleLabel.CENTER, 12, dateBrowser.getGuiColors().getGuiTextColor());
        timeDisplayBottomEditor.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
        timeEditorPanel.add(timeDisplayBottomEditor, BorderLayout.CENTER);

        JPanel leftButtonsTimeEditor = new JPanel(new GridLayout(1, 2, 4, 0));
        leftButtonsTimeEditor.setBorder(BorderFactory.createEmptyBorder());
        leftButtonsTimeEditor.setBackground(getBackground());
        timeEditorPanel.add(leftButtonsTimeEditor, BorderLayout.WEST);

        MenuLabel addHourMB = new MenuLabel("+h", MenuLabel.CENTER, 12, dateBrowser.getGuiColors());
        addHourMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
        addHourMB.dropAllListeners();
        addHourMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addHourMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addHourMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
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

        MenuLabel addMinuteMB = new MenuLabel("+m", MenuLabel.CENTER, 12, dateBrowser.getGuiColors());
        addMinuteMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
        addMinuteMB.dropAllListeners();
        addMinuteMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addMinuteMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addMinuteMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
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

        MenuLabel addSecondMB = new MenuLabel("+s", MenuLabel.CENTER, 12, dateBrowser.getGuiColors());
        addSecondMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
        addSecondMB.dropAllListeners();
        addSecondMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addSecondMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addSecondMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
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


        MenuLabel rmHourMB = new MenuLabel("-h", MenuLabel.CENTER, 12, dateBrowser.getGuiColors());
        rmHourMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
        rmHourMB.dropAllListeners();
        rmHourMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rmHourMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rmHourMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
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

        MenuLabel rmMinuteMB = new MenuLabel("-m", MenuLabel.CENTER, 12, dateBrowser.getGuiColors());
        rmMinuteMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
        rmMinuteMB.dropAllListeners();
        rmMinuteMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rmMinuteMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rmMinuteMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
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

        MenuLabel rmSecondMB = new MenuLabel("-s", MenuLabel.CENTER, 12, dateBrowser.getGuiColors());
        rmSecondMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
        rmSecondMB.dropAllListeners();
        rmSecondMB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rmSecondMB.setForeground(GuiColors.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rmSecondMB.setForeground(dateBrowser.getGuiColors().getGuiTextColor());
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

        mainWrapper.add(centerViewPanel, BorderLayout.CENTER);
    }
}
