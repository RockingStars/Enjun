package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.controllers.ReversiController;
import com.rockingstar.modules.Reversi.models.ReversiModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;

public class MinimaxAI extends Player implements AI {

    // Copied from: https://gamedev.stackexchange.com/questions/45173/reversi-othello-early-game-evaluation-function
    public static final int[][] EVALUATION_MATRIX ={
            {30,  -25, 10, 5, 5, 10, -25,  30,},
            {-25, -25,  1, 1, 1,  1, -25, -25,},
            { 10,   1,  5, 2, 2,  5,   1,  10,},
            {  5,   1,  2, 1, 1,  2,   1,   5,},
            {  5,   1,  2, 1, 1,  2,   1,   5,},
            { 10,   1,  5, 2, 2,  5,   1,  10,},
            {-25, -25,  1, 1, 1,  1, -25, -25,},
            { 30, -25, 10, 5, 5, 10, -25,  30,}
    };

    private Player[][] _boardCopy;

    private ReversiModel _reversiModel;
    private ReversiController _reversiController;

    public MinimaxAI(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    public void setController(ReversiController reversiController){_reversiController = reversiController;}

    public void setModel(ReversiModel reversiModel) {
        _reversiModel = reversiModel;
    }

    @Override
    public VectorXY getMove(Player player, ArrayList<Integer> possibleMoves) {
        int coordinatesOfBestMove = 0;
        int valueOfBestMove = Integer.MIN_VALUE;

        for(int move : possibleMoves){
            int value = minimax(_reversiModel.getBoard(),5,true);

            if (value > valueOfBestMove) {
                valueOfBestMove = value;
                coordinatesOfBestMove = move;
            }
        }

        return new VectorXY(coordinatesOfBestMove % 8, coordinatesOfBestMove / 8);
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
        int bestMove = 0;
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
                    bestMove = pos;
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
/*
                for (int x = 0; x < _reversiModel.getBoard().length; x++) {
                    for (int y = 0; y < _reversiModel.getBoard().length; y++) {
                        if (_reversiModel.getBoard()[x][y] == null) {
                            System.out.print(" n");
                        } else {
                            System.out.print(" " + _reversiModel.getBoard()[x][y].getCharacter());
                        }
                    }
                    System.out.println(" \n--------------");
                }
                System.out.println("Hahahaha we're done");*/
                newBoard[pos % 8][pos / 8] = currentPlayer;
                int value = minimax(newBoard, depth-1, true);
                if(value < bestValue){
                    bestValue = value;
                    bestMove = pos;
                }
            }
            return bestValue;
        }


    }

}

/*

                for (int x = 0; x < _reversiModel.getBoard().length; x++) {
                    for (int y = 0; y < _reversiModel.getBoard().length; y++) {
                        if (_reversiModel.getBoard()[x][y] == null) {
                            System.out.print(" n");
                        } else {
                            System.out.print(" " + _reversiModel.getBoard()[x][y].getCharacter());
                        }
                    }
                    System.out.println(" \n--------------");
                }
                System.out.println("Hahahaha we're done");
*/
