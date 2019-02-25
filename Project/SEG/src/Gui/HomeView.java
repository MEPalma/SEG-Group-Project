package Gui;


import DatabaseManager.DataExchange;
import Gui.BreadCrumbs;
import Gui.RPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

//                        add(new TitleLabel(" Venditori", JLabel.LEFT), BorderLayout.NORTH);
//                        add(listview, BorderLayout.CENTER);
//                        add(addSellersPanel, BorderLayout.SOUTH);

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
