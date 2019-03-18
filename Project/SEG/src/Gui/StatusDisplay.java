package Gui;

import Gui.GuiComponents.MenuLabel;
import Gui.GuiComponents.RPanel;
import Gui.GuiComponents.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StatusDisplay extends RPanel {

    private int nLoadingRequests;

    public StatusDisplay() {
        super(GuiColors.BASE_WHITE, new BorderLayout());
        this.nLoadingRequests = 0;
        refresh();
    }

    @Override
    public void refresh() {
        repaint();
        revalidate();
    }

    public synchronized void newProgressBar() {
        ++this.nLoadingRequests;

        removeAll();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GuiColors.BASE_WHITE);

        TitleLabel titleLabel = new TitleLabel("Loading...", TitleLabel.CENTER, 14);
        wrapper.add(titleLabel, BorderLayout.NORTH);

        JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
        progressBar.setPreferredSize(new Dimension(50, 20));
        progressBar.setIndeterminate(true);
        wrapper.add(progressBar, BorderLayout.CENTER);

        add(wrapper, BorderLayout.CENTER);

        refresh();
    }

    public synchronized void killProgressBar() {
        if (this.nLoadingRequests > 0) this.nLoadingRequests--;
        if (this.nLoadingRequests == 0) clear();
    }


    public void showErrorMessage(String title, String content) {
        clear();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, GuiColors.BASE_SMOKE));
        wrapper.setBackground(GuiColors.RED_ERROR);

        TitleLabel titleLabel = new TitleLabel(title, TitleLabel.CENTER, 16);
        titleLabel.setForeground(GuiColors.BASE_WHITE);
        wrapper.add(titleLabel, BorderLayout.NORTH);

        TitleLabel contentLabel = new TitleLabel(content, TitleLabel.CENTER, 14);
        contentLabel.setForeground(GuiColors.BASE_WHITE);
        wrapper.add(contentLabel, BorderLayout.CENTER);

        MenuLabel closeLabel = new MenuLabel("CLOSE", MenuLabel.CENTER, 14);
        closeLabel.dropAllListeners();
        closeLabel.setForeground(GuiColors.BASE_WHITE);
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clear();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setForeground(GuiColors.BASE_SMOKE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setForeground(GuiColors.BASE_WHITE);
            }
        });
        wrapper.add(closeLabel, BorderLayout.SOUTH);

        add(wrapper, BorderLayout.CENTER);
        refresh();
    }

    private void clear() {
        removeAll();
        refresh();
    }

}
