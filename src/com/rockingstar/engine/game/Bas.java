package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.models.ReversiModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Bas extends Player implements AI {

    private static final int[] powerSpots1 = {0,7,56,63}; // corners
    private static final int[] powerSpots2 = {2,5,16,23,40,47,58,61}; // corner avoiders
    private static final int[] powerSpots3 = {18,21,42,45}; // corner avoiders avoiders

    private ReversiModel _reversiModel;

    public Bas(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    @Override
    public void setModel(ReversiModel reversiModel) {
        _reversiModel = reversiModel;
    }

    @Override
    public VectorXY getMove(Player player) {
        VectorXY move = OPOPOPOPOP(player);

        if(move == null) {
            move = mostTilesFlippedStrategy(player);
        }
        return move;
        //return mostTilesFlippedStrategy(player);
    }

    public VectorXY randomCornerStrategy(Player player){
        ArrayList<Integer> possibleMoves = _reversiModel.getPossibleMoves(player);
        Random random = new Random();

        Integer move;

        if(possibleMoves.contains(0)) {
            move = 0;
        } else if (possibleMoves.contains(7)){
            move = 7;
        } else if (possibleMoves.contains(56)){
            move = 56;
        } else if (possibleMoves.contains(63)){
            move = 63;
        } else {
            move = possibleMoves.get(random.nextInt(possibleMoves.size()));
        }
        return new VectorXY(move % 8, move / 8);
    }


    public VectorXY mostTilesFlippedStrategy(Player player){

        ArrayList<Integer> possibleMoves = _reversiModel.getPossibleMoves(player);
        System.out.println(_reversiModel.getPossibleMoves(player));

        VectorXY bestMoveCoordinates = null;
        int bestMoveNumberOfTiles = 0;

        for( int i = 0; i < possibleMoves.size(); i++){
            System.out.println("loop wel");
            int pmove = possibleMoves.get(i);
            System.out.println("getter wel");
            LinkedList<Integer> tilesFlipped = _reversiModel.getFlippableTiles(pmove % 8, pmove / 8 ,player);
            int amountTilesFlipped = tilesFlipped.size();
            System.out.println(tilesFlipped);
            if(amountTilesFlipped > bestMoveNumberOfTiles){
                System.out.println("hoi");
                bestMoveNumberOfTiles = amountTilesFlipped;
                bestMoveCoordinates = new VectorXY(pmove % 8, pmove / 8);
            }

        }
        return bestMoveCoordinates;
    }

    /*private static final int[] powerSpots1 = {0,7,56,63}; // corners
    private static final int[] powerSpots2 = {2,5,16,23,40,47,58,61}; // corner avoiders
    private static final int[] powerSpots3 = {18,21,42,45}; // corner avoiders avoiders
    */
    public VectorXY OPOPOPOPOP(Player player){

        ArrayList<Integer> possibleMoves = _reversiModel.getPossibleMoves(player);

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
