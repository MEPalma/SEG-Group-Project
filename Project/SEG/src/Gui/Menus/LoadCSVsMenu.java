package Gui.Menus;

import DatabaseManager.CSVParser;
import Gui.GuiColors;
import Gui.GuiComponents.*;
import Gui.MainController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class LoadCSVsMenu extends RPanel {

    private enum FileType {IMPRESSION_LOGS, CLICK_LOGS, SERVER_LOGS, UNRECOCNISED}

    public static Color BACKGROUND = GuiColors.BASE_WHITE;
    private final MainController mainController;

    private String campaignName;

    public LoadCSVsMenu(MainController mainController) {
        super(BACKGROUND, new BorderLayout());
        this.mainController = mainController;
        setBorder(BorderFactory.createMatteBorder(4, 0, 4, 4, GuiColors.BASE_PRIME));

        this.campaignName = "Today's campaign";

        refresh();
    }

    @Override
    public void refresh() {
        RPanel thisView = this;

        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            File impressionLog, clickLog, serverLog;

            @Override
            protected Void doInBackground() {
                return null;
            }

            @Override
            protected void done() {
                removeAll();
                List<Component> components = new LinkedList<Component>();

                components.add(getImpressionLogFileFinderPanel());
                components.add(getClickLogFileFinderPanel());
                components.add(getServerLogFileFinderPanel());

                MenuLabel parseButton = new MenuLabel("LOAD", MenuLabel.CENTER);
                parseButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (impressionLog != null && clickLog != null && serverLog != null) {

                            if (campaignName.trim().equals("") || campaignName.trim().equals(" "))
                                campaignName = "Today's campaign";
                            else campaignName = campaignName.trim().replace("-", "").replace("'", "").replace("\"", "");

                            mainController.setCampaignName(campaignName);

                            //new background thread
                            SwingWorker<Void, Void> loadTask = new SwingWorker<Void, Void>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                    mainController.startProgressBar();
                                    CSVParser parser = new CSVParser(mainController, impressionLog, clickLog, serverLog);
                                    parser.parseAll();
                                    mainController.stopProgressBar();
                                    mainController.removeDataLoadingTask(this);

                                    return null;
                                }
                            };

                            mainController.addDataLoadingTask(loadTask);
                            loadTask.execute();


                        } else {
                            mainController.showErrorMessage("Invalid or missing files", "All files must be present and of the right format, for them to be parsed correctly.\nPlease try again.");
                        }
                    }

                });
                components.add(getChooseCampaignName());
                components.add(parseButton);

                ListView listView = new ListView(BACKGROUND, components);

                add(new TitleLabel(" Import data from CSV files", TitleLabel.LEFT), BorderLayout.NORTH);
                add(listView.getWrappedInScroll(true), BorderLayout.CENTER);

                mainController.removeDataLoadingTask(this);

                revalidate();
                repaint();
            }

            private JPanel getChooseCampaignName() {
                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                wrapper.setBackground(BACKGROUND);

                wrapper.add(new TitleLabel("Campaign Name", TitleLabel.LEFT, 14), BorderLayout.WEST);

                TextBox campaignChooser = new TextBox(BACKGROUND);
                campaignChooser.setText(campaignName);
                campaignChooser.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        campaignName = campaignChooser.getText();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        campaignName = campaignChooser.getText();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        campaignName = campaignChooser.getText();
                    }
                });
                wrapper.add(campaignChooser, BorderLayout.CENTER);

                return wrapper;
            }

            private JPanel getImpressionLogFileFinderPanel() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                panel.setBackground(BACKGROUND);

                TitleLabel titleLabel = new TitleLabel("Impression Log", TitleLabel.LEFT, 14);
                titleLabel.setPreferredSize(new Dimension(140, 20));
                panel.add(titleLabel, BorderLayout.WEST);

                JPanel pathFinderPanel = new JPanel(new BorderLayout());
                pathFinderPanel.setBorder(panel.getBorder());
                pathFinderPanel.setBackground(panel.getBackground());

                TitleLabel pathTextBox = new TitleLabel("", TitleLabel.LEFT, 14);
                pathTextBox.setForeground(GuiColors.DARK_GRAY);

                MenuLabel findFileButton = new MenuLabel(" choose", MenuLabel.CENTER, 14);
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
                            pathFinderPanel.removeAll();
                            pathFinderPanel.add(findFileButton, BorderLayout.EAST);
                            pathFinderPanel.add(pathTextBox, BorderLayout.CENTER);
                            pathFinderPanel.repaint();
                            pathFinderPanel.revalidate();

                            impressionLog = jfc.getSelectedFile().getAbsoluteFile();

                            FileType thisFileType = getFileType(impressionLog);
                            if (thisFileType != FileType.IMPRESSION_LOGS) {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                                pathTextBox.setText("INVALID FILE");
                                impressionLog = null;
                                mainController.showErrorMessage("Invalid File", getErrorMessage(FileType.IMPRESSION_LOGS, thisFileType));
                            } else {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
                                pathTextBox.setText(impressionLog.getName());
                            }
                        }
                    }

                });

                pathFinderPanel.add(findFileButton, BorderLayout.CENTER);
                panel.add(pathFinderPanel, BorderLayout.CENTER);

                return panel;
            }

            private JPanel getClickLogFileFinderPanel() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                panel.setBackground(BACKGROUND);

                TitleLabel titleLabel = new TitleLabel("Click Log", TitleLabel.LEFT, 14);
                titleLabel.setPreferredSize(new Dimension(140, 20));
                panel.add(titleLabel, BorderLayout.WEST);

                JPanel pathFinderPanel = new JPanel(new BorderLayout());
                pathFinderPanel.setBorder(panel.getBorder());
                pathFinderPanel.setBackground(panel.getBackground());

                TitleLabel pathTextBox = new TitleLabel("", TitleLabel.LEFT, 14);
                pathTextBox.setForeground(GuiColors.DARK_GRAY);

                MenuLabel findFileButton = new MenuLabel("choose", MenuLabel.CENTER, 14);
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
                            pathFinderPanel.removeAll();
                            pathFinderPanel.add(findFileButton, BorderLayout.EAST);
                            pathFinderPanel.add(pathTextBox, BorderLayout.CENTER);
                            pathFinderPanel.repaint();
                            pathFinderPanel.revalidate();
                            clickLog = jfc.getSelectedFile().getAbsoluteFile();

                            FileType thisFileType = getFileType(clickLog);
                            if (thisFileType != FileType.CLICK_LOGS) {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                                pathTextBox.setText("INVALID FILE");
                                clickLog = null;
                                mainController.showErrorMessage("Invalid File", getErrorMessage(FileType.CLICK_LOGS, thisFileType));
                            } else {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
                                pathTextBox.setText(clickLog.getName());
                            }
                        }

                    }

                });

                pathFinderPanel.add(findFileButton, BorderLayout.CENTER);
                panel.add(pathFinderPanel, BorderLayout.CENTER);

                return panel;
            }

            private JPanel getServerLogFileFinderPanel() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                panel.setBackground(BACKGROUND);

                TitleLabel titleLabel = new TitleLabel("Server Log", TitleLabel.LEFT, 14);
                titleLabel.setPreferredSize(new Dimension(140, 20));
                panel.add(titleLabel, BorderLayout.WEST);

                JPanel pathFinderPanel = new JPanel(new BorderLayout());
                pathFinderPanel.setBorder(panel.getBorder());
                pathFinderPanel.setBackground(panel.getBackground());

                TitleLabel pathTextBox = new TitleLabel("", TitleLabel.LEFT, 14);
                pathTextBox.setForeground(GuiColors.DARK_GRAY);

                MenuLabel findFileButton = new MenuLabel("choose", MenuLabel.CENTER, 14);
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
                            pathFinderPanel.removeAll();
                            pathFinderPanel.add(findFileButton, BorderLayout.EAST);
                            pathFinderPanel.add(pathTextBox, BorderLayout.CENTER);
                            pathFinderPanel.repaint();
                            pathFinderPanel.revalidate();
                            serverLog = jfc.getSelectedFile().getAbsoluteFile();

                            FileType thisFileType = getFileType(serverLog);
                            if (thisFileType != FileType.SERVER_LOGS) {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
                                pathTextBox.setText("INVALID FILE");
                                serverLog = null;
                                mainController.showErrorMessage("Invalid File", getErrorMessage(FileType.SERVER_LOGS, thisFileType));
                            } else {
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GREEN));
                                pathTextBox.setText(serverLog.getName());
                            }
                        }
                    }

                });

                pathFinderPanel.add(findFileButton, BorderLayout.CENTER);
                panel.add(pathFinderPanel, BorderLayout.CENTER);

                return panel;
            }

            private String getErrorMessage(FileType expected, FileType given) {
                return "Expected file '" + expected.toString().toLowerCase().replace("_", " ") + "' but the file given was '" + given.toString().toLowerCase().replace("_", " ") + "'";
            }

            private FileType getFileType(File file) {
                String[] impressionsFileStructure = {"Date", "ID", "Gender", "Age", "Income", "Context", "Impression Cost"};
                if (checkFirstRow(file, impressionsFileStructure)) return FileType.IMPRESSION_LOGS;

                String[] clicksFileStructure = {"Date", "ID", "Click Cost"};
                if (checkFirstRow(file, clicksFileStructure)) return FileType.CLICK_LOGS;

                String[] serverFileStructure = {"Entry Date", "ID", "Exit Date", "Pages Viewed", "Conversion"};
                if (checkFirstRow(file, serverFileStructure)) return FileType.SERVER_LOGS;

                else return FileType.UNRECOCNISED;
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
                } catch (Exception ex) {
                    return false;
                }
                return false;
            }


        };
        mainController.addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
    }
}
