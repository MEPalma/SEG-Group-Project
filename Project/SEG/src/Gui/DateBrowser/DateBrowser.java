package Gui.DateBrowser;

import DatabaseManager.Stringifiable;
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
        this(GuiColors.BASE_LIGHT, Stringifiable.globalDateFormat, new Date());//TODO change to simple data format
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
        dateBox.setForeground(GuiColors.TEXT_UNSELECTED);
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
        setBackground(GuiColors.BASE_LIGHT);
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
        TitleLabel lspYearAndMonthAndDayLabel = new TitleLabel(dateBrowser.getDateFormat().format(dateBrowser.getDate()), TitleLabel.CENTER, 16);

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

        centerViewPanel.add(cvpNorthPanel, BorderLayout.NORTH);

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

        cvpDaysViewPanel.add(new TitleLabel("MO", TitleLabel.CENTER, 8));
        cvpDaysViewPanel.add(new TitleLabel("TU", TitleLabel.CENTER, 8));
        cvpDaysViewPanel.add(new TitleLabel("WE", TitleLabel.CENTER, 8));
        cvpDaysViewPanel.add(new TitleLabel("TH", TitleLabel.CENTER, 8));
        cvpDaysViewPanel.add(new TitleLabel("FR", TitleLabel.CENTER, 8));
        cvpDaysViewPanel.add(new TitleLabel("SA", TitleLabel.CENTER, 8));
        cvpDaysViewPanel.add(new TitleLabel("SU", TitleLabel.CENTER, 8));

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
            if (ctmp.getTime().equals(dateBrowser.getDate())) {
                for (MouseListener ml : dayChooser.getMouseListeners()) {
                    dayChooser.removeMouseListener(ml);
                }

                dayChooser.setForeground(new Color(105, 175, 205));
            }
            dayChooser.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dateBrowser.setDate(ctmp.getTime());
                    setVisible(false);
                }
            });
            cvpDaysViewPanel.add(dayChooser);
            added++;
        }

        for (int i = added; i < 42; i++) {
            cvpDaysViewPanel.add(new TitleLabel("", TitleLabel.CENTER, 8));
        }

        centerViewPanel.add(cvpDaysViewPanel, BorderLayout.CENTER);

        getContentPane().add(centerViewPanel, BorderLayout.CENTER);
    }
}
