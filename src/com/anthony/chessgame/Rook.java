package com.anthony.chessgame;
import java.util.ArrayList;

//Class representing ROOK
public class Rook extends Piece {

	//false ; set to true after first move[condition for Castling]
	private boolean immobile;
	////1 for column a ; 2 for column h
	//private int number;

	//CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Rw"/"Rb", it is IMMOBILE(false) on creation 
	public Rook(int P,colorPiece C) {
		super(P);
		definePiece(typePiece.R,C);
		immobile = true;
	}

	//OVERRIDE
	public boolean isImmobile(){return immobile;}
	//One-way SETTER in case it moves
	public boolean isMobile(){return immobile=false;}

	//Movement allowed, stopping to the first obstacle[non VOID] : TOP-DOWN-LEFT-RIGHT
	//RETURNS true if the move is allowed
	@Override
	public boolean checkMove(int Px, int Py,boolean W,Player J,ArrayList<Piece> B){
		int Dx = Px - getPosx();
		int Dy = Py - getPosy();
		boolean moveok = false;
		//Looking for an obstacle(friendly or not[non VOID]) on the way
		if (Dx==0)
		{
			if (Dy>0) moveok=checkMoveColumnU(Px,Py,Dx,Dy,W,B);
			else if (Dy<0) moveok=checkMoveColumnD(Px,Py,Dx,Dy,W,B);
		}
		else if (Dy==0)
		{
			if (Dx<0) moveok=checkMoveLineL(Px,Py,Dx,Dy,W,B);
			else if (Dx>0) moveok=checkMoveLineR(Px,Py,Dx,Dy,W,B);
		}  

		//Test final : case de destination
		if (W&&(((Utils.getPieceN(B,Px,Py)).charAt(1))=='w')) moveok=false;
		else if (!W&&(((Utils.getPieceN(B,Px,Py)).charAt(1))=='b')) moveok=false;

		return moveok;
	}
	
	//SETTER for THREATENING : contains the first obstacle(friendly or not)
	//If no piece is on the way, puts an OutOfBoard object instead(NAME "XX") 
	//4 concrete PIECE threaten
	@Override
	public boolean setThreats(ArrayList <Piece> B)
	{
		clearThreatening();
		addThreatening(LineL(B));
		addThreatening(ColumnU(B));
		addThreatening(LineR(B));
		addThreatening(ColumnD(B)); 
		return false;
	}
	

	//Checks move Upward  
	private boolean checkMoveColumnU(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx==0 et Dy>0
		boolean obstacle = false;
		for (int i=1;i<Dy;i++)
		{
			if (!(Calc.isVoid(B,Px,Py-i))) obstacle=true;
		}
		return !obstacle;
	}
	//Checks move Downward  
	private boolean checkMoveColumnD(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx==0 et Dy<0
		boolean obstacle = false;
		for (int i=-1;i>Dy;i--)
		{
			if (!(Calc.isVoid(B,Px,Py-i))) obstacle=true;
		}
		return !obstacle;
	}	
	//Checks move on the left
	private boolean checkMoveLineL(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx<0 et Dy==0
		boolean obstacle = false;
		for (int i=-1;i>Dx;i--)
		{
			if (!(Calc.isVoid(B,Px-i,Py))) obstacle=true;
		}
		return !obstacle;
	}	
	//Checks move on the right
	private boolean checkMoveLineR(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx>0 et Dy==0
		boolean obstacle = false;
		for (int i=1;i<Dx;i++)
		{
			if (!(Calc.isVoid(B,Px-i,Py))) obstacle=true;
		}
		return !obstacle;
	}	

	//Checks obstacle upward
	private Piece ColumnU(ArrayList <Piece> B)
	{
		int i;
		for (i = getPosy()+1;i<8;i++)
		{
			if (!(Calc.isVoid(B,getPosx(),i))) return Utils.getPiece(B,getPosx(),i);
		}
		return Utils.getPiece(B,getPosx(),i);	
	}
	//Checks obstacle downward
	private Piece ColumnD(ArrayList <Piece> B)
	{
		int i;
		for (i = getPosy()-1;i>-1;i--)
		{ 
			if (!(Calc.isVoid(B,getPosx(),i))) return Utils.getPiece(B,getPosx(),i);
		}
		return Utils.getPiece(B,getPosx(),i);	
	}
	//Checks obstacle on the left
	private Piece LineL(ArrayList <Piece> B)
	{
		int i;
		for (i = getPosx()-1;i>-1;i--)
		{  
			if (!(Calc.isVoid(B,i,getPosy()))) return Utils.getPiece(B,i,getPosy());
		}
		return Utils.getPiece(B,i,getPosy());	
	}
	//Checks obstacle on the right
	private Piece LineR(ArrayList <Piece> B)
	{
		int i;
		for (i = getPosx()+1;i<8;i++)
		{ 
			if (!(Calc.isVoid(B,i,getPosy()))) return Utils.getPiece(B,i,getPosy());
		}
		return Utils.getPiece(B,i,getPosy());
	}

}