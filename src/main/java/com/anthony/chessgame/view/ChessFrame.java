package com.anthony.chessgame.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.anthony.chessgame.game.ChessGame;
import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.piece.Piece;
import com.anthony.chessgame.piece.Piece.TypePiece;
import com.anthony.chessgame.util.IPrint;
import com.anthony.chessgame.view.components.BoardPanel;
import com.anthony.chessgame.view.components.GamePanel;

public class ChessFrame extends JFrame implements IPrint{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7457878922842570019L;
	
	//Default height window app
	public final static int HEIGHT = 860; 
	//Default width window app
	public final static int WIDTH = 640; 
	//Margin for window insets
	private int margin;
	//Current displayed panel
	private JPanel currentPanel=null;
	//Current Board view
	private BoardPanel boardPanel;
	
	/**
	 * 
	 * @param margin
	 */
	public ChessFrame(int margin) {
		super("Chess Game");
		setMargin(margin);
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
	}
	
	/**
	 * 
	 */
	public Insets getInsets() {
		Insets in = super.getInsets();
		return new Insets(in.top + margin, in.left + margin,
				in.bottom + margin, in.right + margin);
	}
	/**
	 * 
	 * @param m
	 */
	public void setMargin(int m) {
		margin = m;
	}
	/**
	 * 
	 * @param p
	 * @return
	 */
	private JPanel addCurrentPanel(JPanel p) {
		if (p != null) {
			currentPanel = p;
			add(p);
			revalidate();
		}
		return p;
	}
	/**
	 * 
	 * @return
	 */
	private JPanel removeCurrentPanel() {
		if (currentPanel != null) {
			remove(currentPanel);
		}
		JPanel removed = currentPanel;
		currentPanel = null;
		return removed;
	}
	/**
	 * 
	 * @param p
	 * @return
	 */
	private JPanel replaceCurrentPanel(JPanel p) {
		removeCurrentPanel();
		addCurrentPanel(p);
		return p;
	}
	
	@Override
	public void printTitle() {
		setTitle("CHESS v1.0");
	}
	@Override
	public void printIsCheck(boolean check) {
		// TODO Auto-generated method stub
	}
	@Override
	public void printLine() {
		// TODO Auto-generated method stub
	}
	@Override
	public void setDest(String L) {
		// TODO Auto-generated method stub
	}
	@Override
	public String askName(boolean W, ChessGame C) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Piece askMove(final Player J,final Piece[] Board,final int[] mW,final ChessGame G) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				boardPanel.readyForMove(ChessFrame.this,J,Board,mW,G);
			}
		});
		synchronized(J) {
			try {
				J.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return J.getMovingPiece();
	}
	@Override
	public TypePiece askPromotion(ChessGame G) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void printThreateningOnBoard(Piece[] B) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printThreatenOnBoard(Piece[] B) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printPlayersPiecesOnBoard(Player... P) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void oppInCheck(Piece[] B, Player J2, boolean moveFound) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printCheck() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printBoardState(Piece[] B, Player J, int N) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printCaptures(ArrayList<String> wcaptures,
			ArrayList<String> bcaptures) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printBoard(final Piece[] B) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if (boardPanel == null) {
					currentPanel = new GamePanel(B);
					boardPanel = ((GamePanel)currentPanel).getBoardPanel();
					add(currentPanel);
					setPreferredSize(new Dimension(HEIGHT,WIDTH));
					pack();
					setLocationRelativeTo(null);
					setVisible(true);
				} else {
					boardPanel.updateBoard(B);
				}
			}
		});
	}
	@Override
	public void printGameOver() {
		// TODO Auto-generated method stub
	}
	@Override
	public void printStats() {
		// TODO Auto-generated method stub
	}	
}
