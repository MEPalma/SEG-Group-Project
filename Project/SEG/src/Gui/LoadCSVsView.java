package Gui;

import DatabaseManager.DataExchange;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Marco-Edoardo Palma
 */
public class LoadCSVsView extends RPanel
{
    public static Color BACKGROUND = GuiColors.LIGHT;
    private final DataExchange dataExchange;
    private final BreadCrumbs breadCrumbs;

    public LoadCSVsView(DataExchange dataExchange, BreadCrumbs breadCrumbs)
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
            JScrollPane listview;
            JPanel addSellersPanel;

            @Override
            protected Void doInBackground() throws Exception
            {
                
                return null;
            }
        };
        breadCrumbs.updateBackgroundTask(backgroundTask);
        backgroundTask.execute();
    }
    
}
