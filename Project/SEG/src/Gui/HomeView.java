package Gui;

import DatabaseManager.DataExchange;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    
    public HomeView(DataExchange dataExchange, BreadCrumbs breadCrumbs)
    {
        super(BACKGROUND, new BorderLayout());
        this.dataExchange = dataExchange;
        this.breadCrumbs = breadCrumbs;
        
        refresh();
    }
    
    @Override
    public void refresh()
    {
        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>()
        {
            //Ref to graph

            @Override
            protected Void doInBackground() throws Exception
            {
                breadCrumbs.startProgressBar();
                try
                {
                    Thread.sleep(3000);
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
                        
                        TitleLabel showFieldsButton = new TitleLabel("$$", MenuLabel.CENTER, 26);
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
                                
                                int dfWidth = 250;
                                int dfHeight = 500;
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
                        
                        add(new TitleLabel("<Chart Name>", TitleLabel.CENTER), BorderLayout.NORTH);
                        add(new TitleLabel("CHHHHHAAART", TitleLabel.CENTER, 30), BorderLayout.CENTER);
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
}

class FieldChooser extends JPanel
{
    
    public FieldChooser(JDialog dialogFrame, JPanel dummyChart)
    {
        super(new GridLayout(11, 1, 4, 4));
        setBackground(GuiColors.RED);
        setBorder(BorderFactory.createLineBorder(GuiColors.LIGHT, 1, true));
        
        MenuLabel nImpressionsButton = new MenuLabel("N. Impressions", MenuLabel.CENTER, 16);
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
        
        MenuLabel nClicksButton = new MenuLabel("N. Clicks", MenuLabel.CENTER, 16);
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
        
        MenuLabel nUniquesButton = new MenuLabel("N. Uniques", MenuLabel.CENTER, 16);
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
        
        MenuLabel nBouncesButton = new MenuLabel("N. Bounces", MenuLabel.CENTER, 16);
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
        
        MenuLabel nConversionssButton = new MenuLabel("N. Conversions", MenuLabel.CENTER, 16);
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
        
        MenuLabel totalCostButton = new MenuLabel("Total Cost", MenuLabel.CENTER, 16);
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
        
        MenuLabel CTRButton = new MenuLabel("CTR", MenuLabel.CENTER, 16);
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
        
        MenuLabel CPAButton = new MenuLabel("CPA", MenuLabel.CENTER, 16);
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
        
        MenuLabel CPCButton = new MenuLabel("CPC", MenuLabel.CENTER, 16);
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
        
        MenuLabel CPMButton = new MenuLabel("CPM", MenuLabel.CENTER, 16);
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
        
        MenuLabel bounceRateButton = new MenuLabel("Bounce Rate", MenuLabel.CENTER, 16);
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
