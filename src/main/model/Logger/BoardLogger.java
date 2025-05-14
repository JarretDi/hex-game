package main.model.Logger;

import java.util.ArrayList;
import java.util.List;

import main.model.Board.ChessBoard;

public class BoardLogger {
    private static BoardLogger logger = new BoardLogger();
    private List<ChessBoard> history;
    private List<BoardEvent> events;

    private BoardLogger() {
        history = new ArrayList<>();
        events = new ArrayList<>();
    }

    public static BoardLogger getInstance() {
        return logger;
    }

    public ChessBoard getPrevBoard() {
        return history.getLast();
    }

    public List<BoardEvent> getEvents() {
        return events;
    }

    public void printEvents() {
        int count = 1;
        for (BoardEvent event : events) {
            System.out.println(count + ". " + event.getMsg());
            count++;
        }
    }

    public void addEvent(BoardEvent event) {
        events.add(event);
    }
}
