package com.anthony.chessgame;
//Class  PLAYER
public class Player{
	
//NUMBER of the PLAYER
int number;
//number of games won
int win;
//number of game lost
int loss;
//name of the PLAYER
String pname;
//COLOR of the PIECE the PLAYER plays
boolean white;

//CONSTRUCTOR : Player 
public Player(int i,boolean W,String N) {
pname = N;
number = i;
win=0;
loss=0;
white = W;	  
}

//GETTER
public boolean isWhite() {return white;}
public String getPname() {return pname;}
//Adds one WIN on this PLAYER and one LOSS on the other PLAYER
public void Victory(Player opp){
win++;	
opp.Defeat();
}
//Adds one LOSS to this PLAYER
public void Defeat(){loss++;}

}