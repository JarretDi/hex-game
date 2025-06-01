package main.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import main.model.Board.ChessHex;
import main.model.GamePieces.GamePiece;
import main.model.GamePieces.Pawn;

public abstract class Observable {
    protected static Set<Observer> observers = new HashSet<>();

    public void notifyObservers(Object obj, String msg) {
        Iterator<Observer> it = observers.iterator();
        while (it.hasNext()) {
            Observer obs = it.next();

            // Clean up observer list to be safe
            if (obs instanceof GamePiece && ((GamePiece) obs).getPosition() == null) {
                it.remove();
            } else if (obs instanceof ChessHex && ((ChessHex) obs).getBoard() == null) {
                it.remove();
            } else {
                obs.update(obj, msg);
            }
        }
    }
}
