package Gui;

import Commons.Tuple;
import DatabaseManager.DataExchange;
import Gui.GraphManager.GraphManager;
import Gui.GraphManager.GraphManager.ChartType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author Marco-Edoardo Palma
 */
public class HomeView extends RPanel
{

    public static Color BACKGROUND = GuiColors.LIGHT;
    private final DataExchange dataExchange;
    private final BreadCrumbs breadCrumbs;

    private final Collection<Tuple<Number, Number>> chartData;
    private final ChartType chartType;
    private final String chartTitle, xAxisLabel, yAxisLabel;

    public HomeView(DataExchange dataExchange, BreadCrumbs breadCrumbs)
    {
        super(BACKGROUND, new BorderLayout());
        this.dataExchange = dataExchange;
        this.breadCrumbs = breadCrumbs;

        this.chartData = new LinkedList<>();
        this.chartType = ChartType.LINECHART;
        this.xAxisLabel = "xAxisLabel";
        this.yAxisLabel = "yAxisLabel";
        this.chartTitle = "chartTitle";

        refresh();
    }

    @Override
    public void refresh()
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>()
        {
            //Ref data for graph

            @Override
            protected Void doInBackground() throws Exception
            {
                breadCrumbs.startProgressBar();

                try
                {
                    Thread.sleep(1000);//simulate loading

                } catch (Exception e)
                {
                }
                return null;
            }

            @Override
            protected void done()
            {
                if (!isCancelled())
                {
                    try
                    {
                        removeAll();

                        JPanel bottomFunctionsPanel = new JPanel(new BorderLayout());
                        bottomFunctionsPanel.setBorder(BorderFactory.createEmptyBorder());
                        bottomFunctionsPanel.setBackground(GuiColors.WHITE);

                        TitleLabel showFieldsButton = new TitleLabel("<icon>", TitleLabel.CENTER, 20);
                        showFieldsButton.setForeground(Color.DARK_GRAY);
                        showFieldsButton.addMouseListener(new MouseAdapter()
                        {
                            @Override
                            public void mousePressed(MouseEvent e)
                            {
                                JDialog dialogFrame = new JDialog();
                                dialogFrame.setUndecorated(true);
                                dialogFrame.getContentPane().setLayout(new BorderLayout());

                                dialogFrame.getContentPane().add(new FieldChooser(dialogFrame, new JPanel()), BorderLayout.CENTER);
                                dialogFrame.addFocusListener(new FocusAdapter()
                                {
                                    @Override
                                    public void focusLost(FocusEvent e)
                                    {
                                        dialogFrame.setVisible(false);
                                    }
                                });

                                int dfWidth = 180;
                                int dfHeight = 480;
                                dialogFrame.setSize(new Dimension(dfWidth, dfHeight));

                                int centerXtmp = e.getXOnScreen() - dfWidth;
                                int centerYtmp = e.getYOnScreen() - dfHeight;
                                dialogFrame.setLocation(centerXtmp, centerYtmp);
                                dialogFrame.setVisible(true);
                            }

                            @Override
                            public void mouseEntered(MouseEvent e)
                            {
                                setForeground(Color.DARK_GRAY);
                            }

                            @Override
                            public void mouseExited(MouseEvent e)
                            {
                                setForeground(Color.BLACK);
                            }
                        });
                        bottomFunctionsPanel.add(showFieldsButton, BorderLayout.EAST);

                        TitleLabel changeChartType = new TitleLabel("<icon>", TitleLabel.CENTER, 20);
                        changeChartType.setForeground(Color.DARK_GRAY);
                        changeChartType.addMouseListener(new MouseAdapter()
                        {
                            @Override
                            public void mouseClicked(MouseEvent e)
                            {
                                JDialog dialogFrame = new JDialog();
                                dialogFrame.setUndecorated(true);
                                dialogFrame.getContentPane().setLayout(new BorderLayout());

                                dialogFrame.getContentPane().add(new ChartTypeChooser(dialogFrame, chartData), BorderLayout.CENTER);
                                dialogFrame.addFocusListener(new FocusAdapter()
                                {
                                    @Override
                                    public void focusLost(FocusEvent e)
                                    {
                                        dialogFrame.setVisible(false);
                                        refresh();
                                    }
                                });

                                int dfWidth = 180;
                                int dfHeight = 120;
                                dialogFrame.setSize(new Dimension(dfWidth, dfHeight));

                                int centerXtmp = e.getXOnScreen();
                                int centerYtmp = e.getYOnScreen() - dfHeight;
                                dialogFrame.setLocation(centerXtmp, centerYtmp);
                                dialogFrame.setVisible(true);
                            }

                        });
                        bottomFunctionsPanel.add(changeChartType, BorderLayout.WEST);

                        System.out.println(chartData.size());
                        add(new TitleLabel(chartTitle, TitleLabel.CENTER), BorderLayout.NORTH);
                        add(GraphManager.createChart(chartType, chartData, xAxisLabel, yAxisLabel), BorderLayout.CENTER);
                        add(bottomFunctionsPanel, BorderLayout.SOUTH);

                        breadCrumbs.stopProgressBar();

                        revalidate();
                        repaint();
                    } catch (Exception e)
                    {
                    }
                }
            }
        };
        breadCrumbs.updateBackgroundTask(backgroundTask);
        backgroundTask.execute();
    }

    class FieldChooser extends JPanel
    {

        public FieldChooser(JDialog dialogFrame, JPanel dummyChart)
        {
            super(new GridLayout(11, 1, 4, 4));
            setBackground(GuiColors.RED);
            setBorder(BorderFactory.createLineBorder(GuiColors.RED, 10, true));

            MenuLabel nImpressionsButton = new MenuLabel("N. Impressions", MenuLabel.LEFT, 16);
            nImpressionsButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(nImpressionsButton);

            MenuLabel nClicksButton = new MenuLabel("N. Clicks", MenuLabel.LEFT, 16);
            nClicksButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(nClicksButton);

            MenuLabel nUniquesButton = new MenuLabel("N. Uniques", MenuLabel.LEFT, 16);
            nUniquesButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(nUniquesButton);

            MenuLabel nBouncesButton = new MenuLabel("N. Bounces", MenuLabel.LEFT, 16);
            nBouncesButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(nBouncesButton);

            MenuLabel nConversionssButton = new MenuLabel("N. Conversions", MenuLabel.LEFT, 16);
            nConversionssButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(nConversionssButton);

            MenuLabel totalCostButton = new MenuLabel("Total Cost", MenuLabel.LEFT, 16);
            totalCostButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(totalCostButton);

            MenuLabel CTRButton = new MenuLabel("CTR", MenuLabel.LEFT, 16);
            CTRButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(CTRButton);

            MenuLabel CPAButton = new MenuLabel("CPA", MenuLabel.LEFT, 16);
            CPAButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(CPAButton);

            MenuLabel CPCButton = new MenuLabel("CPC", MenuLabel.LEFT, 16);
            CPCButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(CPCButton);

            MenuLabel CPMButton = new MenuLabel("CPM", MenuLabel.LEFT, 16);
            CPMButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(CPMButton);

            MenuLabel bounceRateButton = new MenuLabel("Bounce Rate", MenuLabel.LEFT, 16);
            bounceRateButton.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                    dummyForChart();
                }
            });
            add(bounceRateButton);
        }

        private void dummyForChart()
        {

        }
    }

    class ChartTypeChooser extends JPanel
    {

        public ChartTypeChooser(JDialog dialogFrame, Collection<Tuple<Number, Number>> chartData)
        {
            super(new GridLayout(2, 1, 4, 4));
            setBackground(GuiColors.RED);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            MenuLabel histoLabel = new MenuLabel("Histogram", MenuLabel.LEFT, 16);
            histoLabel.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    dialogFrame.setVisible(false);
                }
            });
            add(histoLabel);

            MenuLabel lineLabel = new MenuLabel("LineChart", MenuLabel.LEFT, 16);
            lineLabel.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    //load from db here
                    //TODO this is sample data -> change with query to database
                    chartData.clear();
                    chartData.add(new Tuple<>(1, 1));
                    chartData.add(new Tuple<>(2, 2));
                    chartData.add(new Tuple<>(3, 3));
                    chartData.add(new Tuple<>(4, 4));
                    chartData.add(new Tuple<>(5, 5));
                    chartData.add(new Tuple<>(6, 6));
                    chartData.add(new Tuple<>(7, 7));
                    chartData.add(new Tuple<>(8, 8));
                    System.out.println(chartData.size());
                    dialogFrame.setVisible(false);
                }
            });
            add(lineLabel);
        }

    }

}
