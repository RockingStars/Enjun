package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.controllers.ReversiController;
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

    // Inspired by: https://gamedev.stackexchange.com/questions/45173/reversi-othello-early-game-evaluation-function
    public static final int[][] EVALUATION_MATRIX ={
            {50,  -25, 10, 5, 5, 10, -25,  50,},
            {-25, -25,  1, 1, 1,  1, -25, -25,},
            { 10,   1,  5, 2, 2,  5,   1,  10,},
            {  5,   1,  2, 1, 1,  2,   1,   5,},
            {  5,   1,  2, 1, 1,  2,   1,   5,},
            { 10,   1,  5, 2, 2,  5,   1,  10,},
            {-25, -25,  1, 1, 1,  1, -25, -25,},
            { 50, -25, 10, 5, 5, 10, -25,  50,}
    };

    private ReversiModel _reversiModel;
    private ReversiController _reversiController;
    private int counter = 0;

    public OverPoweredAI(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    public void setModel(ReversiModel reversiModel) {
        _reversiModel = reversiModel;
    }

    public void setController(ReversiController reversiController) {
        _reversiController = reversiController;
    }

    public void setCounter(int value){
        counter = value;
    }


    @Override
    public VectorXY getMove(Player player, ArrayList<Integer> possibleMoves) {
        int valueOfBestMove = Integer.MIN_VALUE;
        int bestMove = 0;

        if(counter++ < 10){
            bestMove = getPowerSpotMove(possibleMoves);
            if(bestMove == -1) {
                for(int move : possibleMoves){
                    for(int avoid : avoidSpots){
                        if(move != avoid){
                            bestMove = move;
                        }
                    }
                }
                if(bestMove == -1) {
                    bestMove = generateRandomMove(possibleMoves);
                }
            }
        } else {
            System.out.println(possibleMoves);
            for(int move : possibleMoves){
                int value = minimax(_reversiModel.getBoard(),4,true);
                if (value > valueOfBestMove) {
                    valueOfBestMove = value;
                    bestMove = move;
                }
            }
        }


        return new VectorXY(bestMove % 8, bestMove / 8);
    }
    /*private static final int[] powerSpots1 = {0,7,56,63}; // corners
    private static final int[] powerSpots2 = {2,5,16,23,40,47,58,61}; // corner avoiders
    private static final int[] powerSpots3 = {18,21,42,45}; // corner avoiders avoiders
    */
    public int getPowerSpotMove(ArrayList<Integer> possibleMoves){
        for(int i : powerSpots1) {
            if (possibleMoves.contains(i)) {
                return i;
            }
        }
        for (int j : powerSpots2) {
            if (possibleMoves.contains(j)) {
                return j;
            }
        }
        /*for (int k : powerSpots3) {
            if (possibleMoves.contains(k)) {
                return k;
            }
        }*/
        return -1;
    }

    public int generateRandomMove(ArrayList<Integer> possibleMoves){
        int move;
        Random random = new Random();

        move = possibleMoves.get(random.nextInt(possibleMoves.size()));

        return move;
    }

    public boolean posMoveContainsCorner(ArrayList<Integer> possibleMoves){
        int move = -1;
        if(possibleMoves.contains(0)) {
            move = 0;
        } else if (possibleMoves.contains(7)){
            move = 7;
        } else if (possibleMoves.contains(56)){
            move = 56;
        } else if (possibleMoves.contains(63)) {
            move = 63;
        }
        return !(move == -1);
    }

    public int measure(Player[][] board, Player player){
        int measure = 0;

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j] == player){
                    measure += EVALUATION_MATRIX[i][j];
                }
            }
        }
        return measure;
    }

    public int minimax(Player[][] board, int depth, boolean maximizingPlayer){
        //int bestMove = 0;
        Player currentPlayer = maximizingPlayer ? _reversiController.getPlayer1() : _reversiController.getPlayer2();

        if(depth == 0){
            return measure(board, currentPlayer);
        }

        if(maximizingPlayer){
            int bestValue = Integer.MIN_VALUE;
            ArrayList<Integer> moves = _reversiModel.getPossibleMoves(currentPlayer, board);
            for(int pos : moves){
                Player[][] newBoard = _reversiModel.cloneBoard(board);
                LinkedList<Integer> flippedTiles =_reversiModel.getFlippableTiles(pos % 8, pos / 8, currentPlayer, newBoard);
                _reversiModel.flipTiles(flippedTiles, currentPlayer, newBoard);

                newBoard[pos % 8][pos / 8] = currentPlayer;
                //System.out.println("Curr player:" + currentPlayer);
                int value = minimax(newBoard, depth-1, false);
                //System.out.println("Curr player na minimax:" + currentPlayer);
                if(value > bestValue){
                    bestValue = value;
                    //bestMove = pos;
                }
            }
            return bestValue;
        }
        else {
            int bestValue = Integer.MAX_VALUE;
            ArrayList<Integer> moves = _reversiModel.getPossibleMoves(currentPlayer, board);
            for(int pos : moves){
                Player[][] newBoard = _reversiModel.cloneBoard(board);
                LinkedList<Integer> flippedTiles =_reversiModel.getFlippableTiles(pos % 8, pos / 8, currentPlayer, newBoard);
                _reversiModel.flipTiles(flippedTiles, currentPlayer, newBoard);

                newBoard[pos % 8][pos / 8] = currentPlayer;
                int value = minimax(newBoard, depth-1, true);
                if(value < bestValue){
                    bestValue = value;
                    //bestMove = pos;
                }
            }
            return bestValue;
        }
    }


    /*public VectorXY mostTilesFlippedStrategy(Player player, ArrayList<Integer> possibleMoves){
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
*/

}