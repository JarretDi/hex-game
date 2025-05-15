package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import main.model.ChessGame;
import main.model.Logger.BoardLogger;

public class MapViewer extends JFrame implements WindowListener {
    private static final int WIDTH = 1366;
    private static final int HEIGHT = 768;
    private static final String STATUS_OK = "System OK";

    private JLabel statusLabel;
    private BoardPanel boardUI;

    public MapViewer(ChessGame cg) {
        super("Hexagon Chess");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        statusLabel = new JLabel(STATUS_OK);
        add(statusLabel, BorderLayout.NORTH);

        boardUI = new BoardPanel(cg);
        add(boardUI, BorderLayout.CENTER);
        addWindowListener(this);
        boardUI.setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        pack();
        setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        BoardLogger.getInstance().printEvents();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        BoardLogger.getInstance().printEvents();
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }
}
