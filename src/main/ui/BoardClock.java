package main.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import main.model.ChessGame;
import main.model.Observer;

public class BoardClock extends JInternalFrame implements Observer {
    private Timer timer;
    private boolean paused;

    private boolean turn;
    private int whiteTime;
    private int blackTime;
    private int increment;

    private JLabel whiteTimeLabel;
    private JLabel blackTimeLabel;
    private JButton startStopButton;
    private JButton timeSetButton;

    public BoardClock(ChessGame cg) {
        this.timer = new Timer(1000, new TimeTickAction());
        this.paused = false;
        this.turn = true;
        cg.addObserver(this);

        addClock();
    }

    private void addClock() {
        setLayout(new BorderLayout());

        whiteTimeLabel = new JLabel(getTime(this.whiteTime));
        whiteTimeLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 75));

        JLabel fill = new JLabel("                 ");

        blackTimeLabel = new JLabel(getTime(this.blackTime));
        blackTimeLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 75));

        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout());

        startStopButton = new JButton(new StartStopAction());
        startStopButton.setIcon(scaleImage(new ImageIcon("src/data/icons/play.png"), 30));

        timeSetButton = new JButton(new TimeSetAction());
        timeSetButton.setIcon(scaleImage(new ImageIcon("src/data/icons/clock.png"), 30));

        add(whiteTimeLabel, BorderLayout.WEST);
        add(fill, BorderLayout.CENTER);
        add(blackTimeLabel, BorderLayout.EAST);
        toolbar.add(startStopButton);
        toolbar.add(timeSetButton);
        add(toolbar, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    private ImageIcon scaleImage(ImageIcon icon, int dim) {
        Image img = icon.getImage();
        img = img.getScaledInstance(dim, dim, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public void setTimeControl(int time, int increment) {
        this.whiteTime = time * 60;
        this.blackTime = time * 60;
        this.increment = increment;
    }

    private String getTime(int time) {
        String front = String.valueOf(time / 60);
        String back = String.valueOf(time % 60);
        if (back.length() == 1) {
            back = "0" + back;
        }
        return front + ":" + back;
    }

    private void updateTime() {
        whiteTimeLabel.setText(getTime(whiteTime));
        blackTimeLabel.setText(getTime(blackTime));
    }

    @Override
    public void update(Object obj, String msg) {
        if (msg == "Piece moved") {
            if (!paused && !timer.isRunning()) {
                timer.start();
            }
            if (turn) {
                whiteTime += increment;
            } else {
                blackTime += increment;
            }
            turn = !turn;
            updateTime();
        }
    }

    private class TimeTickAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (paused) return;

            if (turn) {
                if (--whiteTime == 0) {
                    System.out.println("White out of time");
                    timer.stop();
                }
            } else {
                if (--blackTime == 0) {
                    System.out.println("Black out of time");
                    timer.stop();
                }
            }
            updateTime();
        }
    }

    private class StartStopAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            paused = !paused;
            startStopButton.setIcon(paused ? scaleImage(new ImageIcon("src/data/icons/play.png"), 30) : scaleImage(new ImageIcon("src/data/icons/pause.png"), 30));
        }

    }

    private class TimeSetAction extends AbstractAction {
        String[] options = {"1|0", "3|2", "5|3", "10|5", "15|10"};

        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showOptionDialog(null, "What time control?", "Set Time", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            String control = options[choice];
            String[] split = control.split("[|]");
            setTimeControl(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            updateTime();
        }

    }
}
