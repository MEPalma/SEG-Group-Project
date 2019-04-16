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

    private final GuiColors guiColors;

    public StatusDisplay(GuiColors guiColors) {
        super(guiColors.getGuiTextColor(), new BorderLayout());
        this.guiColors = guiColors;
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
        wrapper.setBackground(guiColors.getGuiTextColor());

        TitleLabel titleLabel = new TitleLabel("Loading...", TitleLabel.CENTER, 14, guiColors);
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
        wrapper.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, guiColors.getGuiBackgroundColor()));
        wrapper.setBackground(GuiColors.RED_ERROR);

        TitleLabel titleLabel = new TitleLabel(title, TitleLabel.CENTER, 16, guiColors);
        titleLabel.setForeground(guiColors.getGuiTextColor());
        wrapper.add(titleLabel, BorderLayout.NORTH);

        TitleLabel contentLabel = new TitleLabel(content, TitleLabel.CENTER, 14, guiColors);
        contentLabel.setForeground(guiColors.getGuiTextColor());
        wrapper.add(contentLabel, BorderLayout.CENTER);

        MenuLabel closeLabel = new MenuLabel("CLOSE", MenuLabel.CENTER, 14, guiColors);
        closeLabel.dropAllListeners();
        closeLabel.setForeground(guiColors.getGuiTextColor());
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clear();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setForeground(guiColors.getGuiBackgroundColor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setForeground(guiColors.getGuiTextColor());
            }
        });
        wrapper.add(closeLabel, BorderLayout.SOUTH);

        add(wrapper, BorderLayout.CENTER);
        refresh();

        Thread timeout = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(30 * 1000);
                    remove(wrapper);
                    repaint();
                    revalidate();
                } catch (InterruptedException e) {

                }

            }
        });
        timeout.start();
    }

    private void clear() {
        removeAll();
        refresh();
    }

}
