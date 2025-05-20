package main.model.GamePieces;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import main.model.Board.ChessBoard;
import main.model.Board.ChessHex;

public class Knight extends GamePiece {
    private static final int[] KV1  = ChessBoard.addV(ChessBoard.VECTOR_DIA_NE, ChessBoard.VECTOR_ADJ_N);
    private static final int[] KV2  = ChessBoard.addV(ChessBoard.VECTOR_DIA_NE, ChessBoard.VECTOR_ADJ_NE);
    private static final int[] KV3  = ChessBoard.addV(ChessBoard.VECTOR_DIA_E, ChessBoard.VECTOR_ADJ_NE);
    private static final int[] KV4  = ChessBoard.addV(ChessBoard.VECTOR_DIA_E, ChessBoard.VECTOR_ADJ_SE);
    private static final int[] KV5  = ChessBoard.addV(ChessBoard.VECTOR_DIA_SE, ChessBoard.VECTOR_ADJ_SE);
    private static final int[] KV6  = ChessBoard.addV(ChessBoard.VECTOR_DIA_SE, ChessBoard.VECTOR_ADJ_S);
    private static final int[] KV7  = ChessBoard.addV(ChessBoard.VECTOR_DIA_SW, ChessBoard.VECTOR_ADJ_S);
    private static final int[] KV8  = ChessBoard.addV(ChessBoard.VECTOR_DIA_SW, ChessBoard.VECTOR_ADJ_SW);
    private static final int[] KV9  = ChessBoard.addV(ChessBoard.VECTOR_DIA_W, ChessBoard.VECTOR_ADJ_SW);
    private static final int[] KV10 = ChessBoard.addV(ChessBoard.VECTOR_DIA_W, ChessBoard.VECTOR_ADJ_NW);
    private static final int[] KV11 = ChessBoard.addV(ChessBoard.VECTOR_DIA_NW, ChessBoard.VECTOR_ADJ_NW);
    private static final int[] KV12 = ChessBoard.addV(ChessBoard.VECTOR_DIA_NW, ChessBoard.VECTOR_ADJ_N);
    private static final int[][] KNIGHT_VECTORS = {KV1, KV2, KV3, KV4, KV5, KV6, KV7, KV8, KV9, KV10, KV11, KV12};

    public Knight(boolean colour, ChessHex position) {
        super(colour, position);
        try {
            if (getColour() == GamePiece.WHITE) {
                image = ImageIO.read(new File("./src/data/pieces/white-knight.png"));
            } else {
                image = ImageIO.read(new File("./src/data/pieces/black-knight.png"));
            }
        } catch (Exception e) {
            // Really should not have got here
        }
    }

    public Knight(Knight other, ChessHex position) {
        super(other, position);
        image = other.image;
    }

    @Override
    public Set<ChessHex> getMovableHexes() {
        ChessBoard cb = getBoard();

        Set<ChessHex> ret = new HashSet<>();

        for (int i = 0; i < KNIGHT_VECTORS.length; i++) {
            ChessHex tileToAdd = cb.getTile(ChessBoard.addV(getPosition().getCoords(), KNIGHT_VECTORS[i]));
            if (tileToAdd != null && !tileToAdd.containsAlliedPiece(this)) {
                ret.add(tileToAdd);
            }
        }

        filterCriticals(ret);
        managePins(ret);

        return ret;
    }

    @Override
    public Set<ChessHex> getThreatenedHexes() {
        if (!isOnMap()) {
            return new HashSet<>();
        }
        ChessBoard cb = getBoard();

        Set<ChessHex> ret = new HashSet<>();

        for (int i = 0; i < KNIGHT_VECTORS.length; i++) {
            ChessHex tileToAdd = cb.getTile(ChessBoard.addV(getPosition().getCoords(), KNIGHT_VECTORS[i]));
            if (tileToAdd != null) {
                ret.add(tileToAdd);
            }
        }

        return ret;
    }

    @Override
    public Set<ChessHex> getCriticalHexes() {
        Set<ChessHex> ret = new HashSet<>();
        ret.add(getPosition());
        return ret;
    }

    @Override
    public String getType() {
        return "N";
    }
}
