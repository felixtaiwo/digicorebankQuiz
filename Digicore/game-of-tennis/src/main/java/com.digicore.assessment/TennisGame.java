package src.main.java.com.digicore.assessment;

import java.util.Arrays;

public class TennisGame {
    public static void main(String[] args) throws Exception {
        TennisGame tennisGame= new TennisGame();
        System.out.println("input [1,4,7,2,4], [2,4,2,4,4]");
        int[] p1 = {1,4,7,2,4};
        int[] p2 = {2,4,2,4,4};
        System.out.println("output "+ Arrays.toString(tennisGame.getGameResult(p1, p2)));

    }
    public int[] getGameResult(int[] player1, int[] player2) throws Exception {
        if(player1.length!=5||player2.length!=5){
            throw new Exception("Incorrect scores array");
        } else{
            return decideScore(player1,player2);
        }
    }

    private int[] decideScore(int[] player1, int[] player2) {
        int player1Point = 0,player2Point=0;
        for(int i=0;i<5;i++){
            if(player1[i]==player2[i]){
                continue;
            }
            if(player1[i]>player2[i]){
                player1Point++;
                continue;
            }
            player2Point++;
        }
        int[] score= {player1Point, player2Point};
        return score;
    }
}