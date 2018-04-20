package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.models.ReversiModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * This class contains methods that create and update a Easy AI
 * @author Rockingstars
 * @since 1.0 Beta 1
 */
public class EasyAI extends Player implements AI {

    private ReversiModel _reversiModel;
    private static final int[] avoidSpots = {1, 8, 9, 6, 14, 15, 48, 49, 57, 62, 54, 55};
    private static final int[] powerSpots1 = {2,5,16,23,40,47,58,61}; // corner avoiders
    private static final int[] powerSpots2 = {18,21,42,45}; // corner avoiders avoiders

    /**
     * Method to instantiate the EasyAI
     * @param username
     * @param color
     */
    public EasyAI(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    /**
     * Method to set the model of the class
     * @param reversiModel
     */
    public void setModel(ReversiModel reversiModel) {
        _reversiModel = reversiModel;
    }

    /**
     * Method to get the possible moves and a check for corners.
     * @param player
     * @param possibleMoves
     * @return Returns the vector to make a move
     */
    @Override
    public VectorXY getMove(Player player, ArrayList<Integer> possibleMoves) {
        Integer move;
        System.out.println("poss moves in get move" +_reversiModel.getPossibleMoves(player));
        Random random = new Random();
        ArrayList<Integer> goodmoves = new ArrayList<>();
        System.out.printf("Possible moves for EasyAI: %d\n", possibleMoves.size());

        if(possibleMoves.contains(0)) {
            move = 0;
        } else if (possibleMoves.contains(7)){
            move = 7;
        } else if (possibleMoves.contains(56)){
            move = 56;
        } else if (possibleMoves.contains(63)){
            move = 63;
        } else {
            move = getPowerSpotMove(possibleMoves);
            if(move == -1) {
                for (int posmove : possibleMoves) {
                    for (int avoid : avoidSpots) {
                        if (posmove != avoid) {
                            goodmoves.add(posmove);
                        }
                    }
                }

                if (goodmoves.size() > 0) {
                    move = goodmoves.get(random.nextInt(goodmoves.size()));
                } else {
                    move = mostTilesFlippedStrategy(player ,possibleMoves);
                }
            }
        }
        return new VectorXY(move % 8, move / 8);
    }

    public int getPowerSpotMove(ArrayList<Integer> possibleMoves){
        for (int j : powerSpots1) {
            if (possibleMoves.contains(j)) {
                return j;
            }
        }
        for (int k : powerSpots2) {
            if (possibleMoves.contains(k)) {
                return k;
            }
        }
        return -1;
    }

    public int mostTilesFlippedStrategy(Player player, ArrayList<Integer> possibleMoves){
        System.out.println(possibleMoves);

        LinkedList<Integer> goodMoves = new LinkedList<>();

        for (int possibleMove : possibleMoves) {
            for (int avoidSpot : avoidSpots) {
                if (possibleMove != avoidSpot)
                    goodMoves.add(possibleMove);
            }
        }

        int bestMoveCoordinates = -1;
        int bestMoveNumberOfTiles = 0;

        if (goodMoves.size() > 0) {
            for (int i = 0; i < goodMoves.size(); i++) {
                int pmove = goodMoves.get(i);
                LinkedList<Integer> tilesFlipped = _reversiModel.getFlippableTiles(pmove % 8, pmove / 8, player);
                int amountTilesFlipped = tilesFlipped.size();

                if (amountTilesFlipped > bestMoveNumberOfTiles) {
                    bestMoveNumberOfTiles = amountTilesFlipped;
                    bestMoveCoordinates = pmove;
                }

            }
        }
        else {
            int move = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
            return move;
        }

        return bestMoveCoordinates;
    }
}
