package main.persistenceJson;

import org.json.JSONObject;

import main.model.Board.ChessBoard;
import main.model.Logger.BoardLogger;
import main.modelOldRpg.Inventory.Inventory;

import java.io.*;

// Represents a writer that writes JSON representation of inventory to file
// All method specifications are from workroom demo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a writer to save an inventory under given character
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of game history to file
    public void write(ChessBoard cb) {
        JSONObject json = BoardLogger.getInstance().toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
