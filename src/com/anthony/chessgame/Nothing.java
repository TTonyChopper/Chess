package com.anthony.chessgame;
//Class representing VOID case
public class Nothing extends Piece {

//CONSTRUCTOR : create a PIECE of COLOR C=0[NEUTRAL] at POSITION P, and his NAME stays "  " from PIECE 
public Nothing(int P,int C) {
super(P,C);
}

//CONSTRUCTOR : create a PIECE of COLOR C=0[NEUTRAL] at COORDINATES(PX,PY), and his NAME stays "  " from PIECE 
public Nothing(int Px,int Py,int C) {
super(8*Py+Px,C);
}

}