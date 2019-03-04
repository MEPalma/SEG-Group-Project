package Gui.SideMenus;

import DatabaseManager.CSVParser;
import Gui.GuiColors;
import Gui.GuiComponents.*;
import Gui.MainController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadCSVsMenu extends RPanel {

    public static Color BACKGROUND = GuiColors.BASE_WHITE;
    private final MainController mainController;

    public LoadCSVsMenu(MainController mainController) {
        super(BACKGROUND, new BorderLayout());
        this.mainController = mainController;

        refresh();
    }

    @Override
    public void refresh() {
        JPanel thisView = this;

        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            File impressionLog, clickLog, serverLog;

            @Override
            protected Void doInBackground() throws Exception {
                return null;
            }

            @Override
            protected void done() {
                removeAll();
                List<Component> components = new LinkedList<Component>();

                components.add(getImpressionLogFileFinderPanel());
                components.add(getClickLogFileFinderPanel());
                components.add(getServerLogFileFinderPanel());

                ListView listView = new ListView(BACKGROUND, components);

                add(new TitleLabel(" Import data from CSV files", TitleLabel.LEFT), BorderLayout.NORTH);
                add(listView.getWrappedInScroll(true), BorderLayout.CENTER);

                MenuLabel parseButton = new MenuLabel("LOAD", MenuLabel.CENTER);
                parseButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (impressionLog != null && clickLog != null && serverLog != null) {
                            //new background thread
                            SwingWorker<Void, Void> loadTask = new SwingWorker<Void, Void>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                    mainController.startProgressBar();
                                    CSVParser parser = new CSVParser(mainController.getDataExchange(), impressionLog, clickLog, serverLog);
                                    parser.parseAll();
                                    mainController.stopProgressBar();
                                    return null;
                                }
                            };

                            mainController.setMainBackgroundTask(loadTask);
                            loadTask.execute();
                        } else {
                            //TODO error message
                        }
                    }

                });

                add(parseButton, BorderLayout.SOUTH);

                revalidate();
                repaint();
            }

            private JPanel getImpressionLogFileFinderPanel() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                panel.setBackground(BACKGROUND);

                TitleLabel titleLabel = new TitleLabel("Impression Log", TitleLabel.LEFT, 14);
                titleLabel.setPreferredSize(new Dimension(160, 20));
                panel.add(titleLabel, BorderLayout.WEST);

                JPanel pathFinderPanel = new JPanel(new BorderLayout());
                pathFinderPanel.setBorder(panel.getBorder());
                pathFinderPanel.setBackground(panel.getBackground());

                TextBox pathTextBox = new TextBox(Color.WHITE);
                pathTextBox.setEditable(false);
                pathFinderPanel.add(pathTextBox, BorderLayout.CENTER);

                MenuButton findFileButton = new MenuButton("...", 14);
                findFileButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                        jfc.setDialogTitle("Find the Impression Log file");
                        jfc.setAcceptAllFileFilterUsed(false);
                        jfc.setMultiSelectionEnabled(false);
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "csv");
                        jfc.addChoosableFileFilter(filter);

                        int returnValue = jfc.showOpenDialog(thisView);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            impressionLog = jfc.getSelectedFile().getAbsoluteFile();

                            //check the first row to verify it is the right file
                            String[] entries =
                                    {
                                            "Date", "ID", "Gender", "Age", "Income", "Context", "Impression Cost"
                                    };
                            if (checkFirstRow(impressionLog, entries)) {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
                                pathTextBox.setText(impressionLog.getAbsolutePath());
                            } else {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                                pathTextBox.setText("INVALID FILE");
                                impressionLog = null;
                            }
                        }
                    }

                });

                pathFinderPanel.add(findFileButton, BorderLayout.EAST);
                panel.add(pathFinderPanel, BorderLayout.CENTER);

                return panel;
            }

            private JPanel getClickLogFileFinderPanel() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                panel.setBackground(BACKGROUND);

                TitleLabel titleLabel = new TitleLabel("Click Log", TitleLabel.LEFT, 14);
                titleLabel.setPreferredSize(new Dimension(160, 20));
                panel.add(titleLabel, BorderLayout.WEST);

                JPanel pathFinderPanel = new JPanel(new BorderLayout());
                pathFinderPanel.setBorder(panel.getBorder());
                pathFinderPanel.setBackground(panel.getBackground());

                TextBox pathTextBox = new TextBox(Color.WHITE);
                pathTextBox.setEditable(false);
                pathFinderPanel.add(pathTextBox, BorderLayout.CENTER);

                MenuButton findFileButton = new MenuButton("...", 14);
                findFileButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                        jfc.setDialogTitle("Find the Click Log file");
                        jfc.setAcceptAllFileFilterUsed(false);
                        jfc.setMultiSelectionEnabled(false);
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "csv");
                        jfc.addChoosableFileFilter(filter);

                        int returnValue = jfc.showOpenDialog(thisView);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            clickLog = jfc.getSelectedFile().getAbsoluteFile();

                            //check the first row to verify it is the right file
                            String[] entries =
                                    {
                                            "Date", "ID", "Click Cost"
                                    };
                            if (checkFirstRow(clickLog, entries)) {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
                                pathTextBox.setText(clickLog.getAbsolutePath());
                            } else {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                                pathTextBox.setText("INVALID FILE");
                                clickLog = null;
                            }
                        }
                    }

                });

                pathFinderPanel.add(findFileButton, BorderLayout.EAST);
                panel.add(pathFinderPanel, BorderLayout.CENTER);

                return panel;
            }

            private JPanel getServerLogFileFinderPanel() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                panel.setBackground(BACKGROUND);

                TitleLabel titleLabel = new TitleLabel("Server Log", TitleLabel.LEFT, 14);
                titleLabel.setPreferredSize(new Dimension(160, 20));
                panel.add(titleLabel, BorderLayout.WEST);

                JPanel pathFinderPanel = new JPanel(new BorderLayout());
                pathFinderPanel.setBorder(panel.getBorder());
                pathFinderPanel.setBackground(panel.getBackground());

                TextBox pathTextBox = new TextBox(Color.WHITE);
                pathTextBox.setEditable(false);
                pathFinderPanel.add(pathTextBox, BorderLayout.CENTER);

                MenuButton findFileButton = new MenuButton("...", 14);
                findFileButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                        jfc.setDialogTitle("Find the Server Log file");
                        jfc.setAcceptAllFileFilterUsed(false);
                        jfc.setMultiSelectionEnabled(false);
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "csv");
                        jfc.addChoosableFileFilter(filter);

                        int returnValue = jfc.showOpenDialog(thisView);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            serverLog = jfc.getSelectedFile().getAbsoluteFile();

                            //check the first row to verify it is the right file
                            String[] entries =
                                    {
                                            "Entry Date", "ID", "Exit Date", "Pages Viewed", "Conversion"
                                    };
                            if (checkFirstRow(serverLog, entries)) {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
                                pathTextBox.setText(serverLog.getAbsolutePath());
                            } else {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                                pathTextBox.setText("INVALID FILE");
                                serverLog = null;
                            }
                        }
                    }

                });

                pathFinderPanel.add(findFileButton, BorderLayout.EAST);
                panel.add(pathFinderPanel, BorderLayout.CENTER);

                return panel;
            }

            private boolean checkFirstRow(File file, String[] entries) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line = br.readLine();
                    br.close();

                    String[] headers = line.split(",");

                    if (headers.length == entries.length) {
                        for (int i = 0; i < headers.length; ++i) {
                            if (!headers[i].equals(entries[i])) {
                                return false;
                            }
                        }
                        return true;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LoadCSVsMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                return false;
            }
        };
        mainController.setMainBackgroundTask(backgroundTask);
        backgroundTask.execute();
    }

}
