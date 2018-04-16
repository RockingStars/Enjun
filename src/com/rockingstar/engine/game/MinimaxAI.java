package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.models.ReversiModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class MinimaxAI extends Player implements AI {

    private static final int[] powerSpots1 = {0,7,56,63}; // corners
    private static final int[] powerSpots2 = {2,5,16,23,40,47,58,61}; // corner avoiders
    private static final int[] powerSpots3 = {18,21,42,45}; // corner avoiders avoiders

    private static final int[] avoidSpots = {1, 8, 9, 6, 14, 15, 48, 49, 57, 62, 54, 55};

    private Player[][] _boardCopy;

    private ReversiModel _reversiModel;

    public MinimaxAI(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    public void setModel(ReversiModel reversiModel) {
        _reversiModel = reversiModel;
    }

    @Override
    public VectorXY getMove(Player player, ArrayList<Integer> possibleMoves) {
        /*//VectorXY move = getPowerSpotMove(player, possibleMoves);
        _boardCopy = _reversiModel.getBoard().clone();

        VectorXY bestMoveCoordinates = new VectorXY(-1, -1);
        int mostPoints = -1;

        for (int possibleMove : possibleMoves) {
            int tiles = resultOfMove(possibleMoves, 5, player, true);

            if (tiles > mostPoints)
                bestMoveCoordinates = new VectorXY(possibleMove % 8, possibleMove / 8);
        }

        return bestMoveCoordinates;*/
        return null;
    }

    public int resultOfMove(ArrayList<Integer> possibleMoves, int depth, Player localPlayer, Player opponent, boolean isLocalPlayer) {
        /*int bestValue;
        ArrayList<Integer> results = new ArrayList<>();

        if (isLocalPlayer) {
            bestValue = Integer.MIN_VALUE;

            for (int possibleMove : possibleMoves) {
                results.add(resultOfMove(possibleMove));
            }
        }
        else {
            bestValue = Integer.MAX_VALUE;
        }*/
        return 0;
    }

    public VectorXY mostTilesFlippedStrategy(Player player, ArrayList<Integer> possibleMoves){
        System.out.println(possibleMoves);

        LinkedList<Integer> goodMoves = new LinkedList<>();

        for (int possibleMove : possibleMoves) {
            for (int avoidSpot : avoidSpots) {
                if (possibleMove != avoidSpot)
                    goodMoves.add(possibleMove);
            }
        }

        VectorXY bestMoveCoordinates = null;
        int bestMoveNumberOfTiles = Integer.MAX_VALUE;

        if (goodMoves.size() > 0) {
            for (int i = 0; i < goodMoves.size(); i++) {
                System.out.println("loop wel");

                int pmove = goodMoves.get(i);
                System.out.println("getter wel");
                LinkedList<Integer> tilesFlipped = _reversiModel.getFlippableTiles(pmove % 8, pmove / 8, player);

                int amountTilesFlipped = tilesFlipped.size();
                System.out.println(tilesFlipped);

                if (amountTilesFlipped < bestMoveNumberOfTiles) {
                    System.out.println("hoi");
                    bestMoveNumberOfTiles = amountTilesFlipped;
                    bestMoveCoordinates = new VectorXY(pmove % 8, pmove / 8);
                }

            }
        }
        else {
            int move = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
            return new VectorXY(move % 8, move / 8);
        }

        return bestMoveCoordinates;
    }

    /*private static final int[] powerSpots1 = {0,7,56,63}; // corners
    private static final int[] powerSpots2 = {2,5,16,23,40,47,58,61}; // corner avoiders
    private static final int[] powerSpots3 = {18,21,42,45}; // corner avoiders avoiders
    */
    public VectorXY getPowerSpotMove(Player player, ArrayList<Integer> possibleMoves){
        for(Integer i : powerSpots1) {
            if (possibleMoves.contains(i)) {
                return new VectorXY(i % 8, i / 8);
            } else {
                for (Integer j : powerSpots2) {
                    if (possibleMoves.contains(j)) {
                        return new VectorXY(j % 8, j / 8);
                    } else {
                        for (Integer k : powerSpots3) {
                            if (possibleMoves.contains(k)) {
                                return new VectorXY(k % 8, k / 8);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
