package main.persistenceJson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import main.model.InvalidMoveException;
import main.model.Board.ChessBoard;

// Represents a reader that reads inventory from JSON data stored in file
// Method implementations mostly taken from workroom demo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads inventory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ChessBoard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseChessboard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses inventory from JSON object and returns it
    private ChessBoard parseChessboard(JSONObject jsonObject) {
        

        return new ChessBoard(5,5,5);
    }

    // MODIFIES: inventory
    // EFFECTS: parses items from JSON object and adds them to inventory
    private void recreateBoard(ChessBoard cb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            try {
                //
            } catch (Exception e) {

            }
        }
    }

    // MODIFIES: inventory
    // EFFECTS: parses item from JSON object and adds it to inventory
    private void makeMove(ChessBoard cb, JSONObject jsonObject) throws InvalidMoveException {
        
    }
}
