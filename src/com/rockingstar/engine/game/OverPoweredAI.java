package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.models.ReversiModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class OverPoweredAI extends Player implements AI {

    private static final int[] powerSpots1 = {0,7,56,63}; // corners
    private static final int[] powerSpots2 = {2,5,16,23,40,47,58,61}; // corner avoiders
    private static final int[] powerSpots3 = {18,21,42,45}; // corner avoiders avoiders

    private static final int[] avoidSpots = {1, 8, 9, 6, 14, 15, 48, 49, 57, 62, 54, 55};

    private ReversiModel _reversiModel;

    public OverPoweredAI(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    public void setModel(ReversiModel reversiModel) {
        _reversiModel = reversiModel;
    }

    @Override
    public VectorXY getMove(Player player, ArrayList<Integer> possibleMoves) {
        VectorXY move = getPowerSpotMove(possibleMoves);

        if(move == null) {
            move = mostTilesFlippedStrategy(player, possibleMoves);
        }
        System.out.println("move in getmove method" + move);
        return move;
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
        int bestMoveNumberOfTiles = 0;

        if (goodMoves.size() > 0) {
            for (int i = 0; i < goodMoves.size(); i++) {
                //System.out.println("loop wel");
                int pmove = goodMoves.get(i);
                //System.out.println("getter wel");
                LinkedList<Integer> tilesFlipped = _reversiModel.getFlippableTiles(pmove % 8, pmove / 8, player);
                int amountTilesFlipped = tilesFlipped.size();
                //System.out.println(tilesFlipped);

                if (amountTilesFlipped > bestMoveNumberOfTiles) {
                    //System.out.println("hoi");
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
    public VectorXY getPowerSpotMove(ArrayList<Integer> possibleMoves){
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