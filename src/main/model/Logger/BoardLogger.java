package main.model.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class BoardLogger {
    private static BoardLogger logger = new BoardLogger();
    private List<Map<ChessHex, ChessHex>> history;
    private List<BoardEvent> events;

    private BoardLogger() {
        history = new ArrayList<>();
        events = new ArrayList<>();
    }

    public static BoardLogger getInstance() {
        return logger;
    }

    public Map<ChessHex, ChessHex> getPrevBoard() {
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

    public void logBoard(ChessBoard cb) {
        history.add(cb.duplicateBoard());
    }

    public void addEvent(BoardEvent event) {
        events.add(event);
    }
}
