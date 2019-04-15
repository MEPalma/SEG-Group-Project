package Gui.Menus;

import Commons.Tuple;
import DatabaseManager.CSVParser;
import Gui.GuiColors;
import Gui.GuiComponents.*;
import Gui.MainController;
import Gui.TakeActionListener;

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
    private final MainController mainController;
    private TakeActionListener onLoaded;
    private String campaignName;

    public LoadCSVsMenu(MainController mainController) {
        super(mainController.getGuiColors().getGuiTextColor(), new BorderLayout());
        this.mainController = mainController;
        setBorder(BorderFactory.createMatteBorder(4, 0, 4, 4, mainController.getGuiColors().getGuiPrimeColor()));

        this.campaignName = "Today's campaign";

        refresh();
    }

    public void setOnLoaded(TakeActionListener onLoaded) {
        this.onLoaded = onLoaded;
    }

    @Override
    public void refresh() {
        RPanel thisView = this;

        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            File impressionLog, clickLog, serverLog;

            Component centerComponent;

            @Override
            protected Void doInBackground() {
                removeAll();
                List<Component> components = new LinkedList<Component>();

                components.addAll(getManageCampaignsPanels());

                components.add(getTitleAddNewCampaign());
                components.add(getImpressionLogFileFinderPanel());
                components.add(getClickLogFileFinderPanel());
                components.add(getServerLogFileFinderPanel());

                MenuLabel parseButton = new MenuLabel("LOAD", MenuLabel.CENTER, 18, mainController.getGuiColors());
                parseButton.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                parseButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (impressionLog != null && clickLog != null && serverLog != null) {

                            parseButton.setText("LOADING..");
                            parseButton.setEnabled(false);
                            parseButton.setVisible(true);

                            if (campaignName.trim().equals("") || campaignName.trim().equals(" "))
                                campaignName = "Today's campaign";
                            else campaignName = campaignName.trim().replace("-", "").replace("'", "").replace("\"", "");

                            //new background thread
                            SwingWorker<Void, Void> loadTask = new SwingWorker<Void, Void>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                    mainController.startProgressBar();
                                    CSVParser parser = new CSVParser(mainController, impressionLog, clickLog, serverLog);
                                    parser.parseAll(campaignName);
                                    mainController.stopProgressBar();
                                    mainController.removeDataLoadingTask(this);

                                    if (onLoaded != null) onLoaded.takeAction();

                                    parseButton.setText("LOAD AGAIN");
                                    parseButton.setEnabled(true);
                                    parseButton.setForeground(mainController.getGuiColors().getGuiPrimeColor());

                                    mainController.refreshGraphs();

                                    return null;
                                }

                                @Override
                                public void done() {
                                    refresh();
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

                centerComponent = new ListView(mainController.getGuiColors(), components).getWrappedInScroll(true);

                return null;
            }

            @Override
            protected void done() {
                TitleLabel titleLabel = new TitleLabel(" Campaigns", TitleLabel.LEFT, 18, mainController.getGuiColors());
                titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 10, 0));
                add(titleLabel, BorderLayout.NORTH);
                add(centerComponent, BorderLayout.CENTER);
                revalidate();
                repaint();
                mainController.removeDataLoadingTask(this);
            }

            private JPanel getTitleAddNewCampaign() {
                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setBorder(BorderFactory.createEmptyBorder(12, 8, 8, 8));
                wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());

                wrapper.add(new TitleLabel("Add New Campaign", TitleLabel.LEFT, 18, mainController.getGuiColors()), BorderLayout.WEST);

                return wrapper;
            }

            private JPanel getChooseCampaignName() {
                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());

                wrapper.add(new TitleLabel("Campaign Name", TitleLabel.LEFT, 16, mainController.getGuiColors()), BorderLayout.WEST);

                TextBox campaignChooser = new TextBox(mainController.getGuiColors());
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
                panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                panel.setBackground(mainController.getGuiColors().getGuiTextColor());

                TitleLabel titleLabel = new TitleLabel("Impression Log", TitleLabel.LEFT, 16, mainController.getGuiColors());
                titleLabel.setPreferredSize(new Dimension(140, 20));
                panel.add(titleLabel, BorderLayout.WEST);

                JPanel pathFinderPanel = new JPanel(new BorderLayout());
                pathFinderPanel.setBorder(panel.getBorder());
                pathFinderPanel.setBackground(panel.getBackground());

                TitleLabel pathTextBox = new TitleLabel("", TitleLabel.LEFT, 14, mainController.getGuiColors());
                pathTextBox.setForeground(GuiColors.DARK_GRAY);

                MenuLabel findFileButton = new MenuLabel(" choose", MenuLabel.CENTER, 14, mainController.getGuiColors());
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
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, mainController.getGuiColors().getGuiPrimeColor()));
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
                panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                panel.setBackground(mainController.getGuiColors().getGuiTextColor());

                TitleLabel titleLabel = new TitleLabel("Click Log", TitleLabel.LEFT, 16, mainController.getGuiColors());
                titleLabel.setPreferredSize(new Dimension(140, 20));
                panel.add(titleLabel, BorderLayout.WEST);

                JPanel pathFinderPanel = new JPanel(new BorderLayout());
                pathFinderPanel.setBorder(panel.getBorder());
                pathFinderPanel.setBackground(panel.getBackground());

                TitleLabel pathTextBox = new TitleLabel("", TitleLabel.LEFT, 14, mainController.getGuiColors());
                pathTextBox.setForeground(GuiColors.DARK_GRAY);

                MenuLabel findFileButton = new MenuLabel(" choose", MenuLabel.CENTER, 14, mainController.getGuiColors());
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
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, mainController.getGuiColors().getGuiPrimeColor()));
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
                panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                panel.setBackground(mainController.getGuiColors().getGuiTextColor());

                TitleLabel titleLabel = new TitleLabel("Server Log", TitleLabel.LEFT, 16, mainController.getGuiColors());
                titleLabel.setPreferredSize(new Dimension(140, 20));
                panel.add(titleLabel, BorderLayout.WEST);

                JPanel pathFinderPanel = new JPanel(new BorderLayout());
                pathFinderPanel.setBorder(panel.getBorder());
                pathFinderPanel.setBackground(panel.getBackground());

                TitleLabel pathTextBox = new TitleLabel("", TitleLabel.LEFT, 14, mainController.getGuiColors());
                pathTextBox.setForeground(GuiColors.DARK_GRAY);

                MenuLabel findFileButton = new MenuLabel(" choose", MenuLabel.CENTER, 14, mainController.getGuiColors());
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
                                pathFinderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, mainController.getGuiColors().getGuiPrimeColor()));
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

            private List<Component> getManageCampaignsPanels() {
                List<Component> panels = new LinkedList<>();

                List<Tuple<Integer, String>> camps = mainController.getDataExchange().selectAllCampaigns();

                if (camps.size() > 0) {
                    TitleLabel titleLabel = new TitleLabel("Campaigns in the system", TitleLabel.LEFT, 16, mainController.getGuiColors());
                    titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                    panels.add(titleLabel);

                    for (Tuple<Integer, String> tuple : camps) {
                        JPanel wrapper = new JPanel(new BorderLayout());
                        wrapper.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                        wrapper.setBackground(mainController.getGuiColors().getGuiTextColor());

                        wrapper.add(new TitleLabel(tuple.getY(), TitleLabel.LEFT, 16, mainController.getGuiColors()), BorderLayout.WEST);

                        JPanel rightWrapper = new JPanel(new GridLayout(1, 2, 2, 2));
                        rightWrapper.setBackground(wrapper.getBackground());
                        rightWrapper.setBorder(BorderFactory.createEmptyBorder());

                        MenuLabel changeNameLabel = new MenuLabel("nameEdit", MenuLabel.CENTER, 8, mainController.getGuiColors());
                        changeNameLabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent mouseEvent) {

                                String newName = JOptionPane.showInputDialog("Type a new campaign name", tuple.getY());

                                if (newName != null) {
                                    if (!newName.trim().equals(tuple.getY())) {

                                        new SwingWorker<Void, Void>() {
                                            @Override
                                            public Void doInBackground() {

                                                mainController.startProgressBar();
                                                mainController.getDataExchange().setCampaignName(tuple.getX(), newName.trim().replace("'", "''").replace("-", "--"));

                                                return null;
                                            }

                                            @Override
                                            public void done() {
                                                mainController.stopProgressBar();
                                                refresh();
                                            }
                                        }.execute();
                                    }
                                }
                            }
                        });
                        rightWrapper.add(changeNameLabel);

                        TitleLabel deleteLabel = new TitleLabel("x", MenuLabel.CENTER, 16, mainController.getGuiColors());
                        deleteLabel.setForeground(GuiColors.RED_ERROR);
                        deleteLabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent mouseEvent) {

                                int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to permanently delete this campaign?","Delete",JOptionPane.WARNING_MESSAGE);
                                if(dialogResult == JOptionPane.YES_OPTION) {
                                    changeNameLabel.setEnabled(false);
                                    deleteLabel.setEnabled(false);

                                    // THIS PROCESS CANNOT BE KILLED BY THE CONTROLLER! -> DO NOT ADD TO mainController's pool!
                                    new SwingWorker<Void, Void>() {
                                        @Override
                                        public Void doInBackground() {

                                            mainController.startProgressBar();
                                            mainController.getDataExchange().deleteCampaign(tuple.getX());

                                            return null;
                                        }

                                        @Override
                                        public void done() {
                                            mainController.stopProgressBar();
                                            refresh();
                                        }
                                    }.execute();
                                }
                            }
                        });
                        rightWrapper.add(deleteLabel);

                        wrapper.add(rightWrapper, BorderLayout.EAST);

                        panels.add(wrapper);
                    }

                    JPanel spacer = new JPanel(new BorderLayout());
                    spacer.setBorder(BorderFactory.createEmptyBorder());
                    spacer.setPreferredSize(new Dimension(50, 24));
                    spacer.setBackground(mainController.getGuiColors().getGuiTextColor());
                    panels.add(spacer);
                }

                return panels;
            }

        };
        mainController.addDataLoadingTask(backgroundTask);
        backgroundTask.execute();
    }

    private enum FileType {IMPRESSION_LOGS, CLICK_LOGS, SERVER_LOGS, UNRECOCNISED}
}
