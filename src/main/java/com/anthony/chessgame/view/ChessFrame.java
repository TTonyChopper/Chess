package com.anthony.chessgame.view;

import java.awt.Dimension;
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
import com.anthony.chessgame.view.components.PlayerDialog;

public class ChessFrame extends JFrame implements IPrint{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7457878922842570019L;
	
	//Default height window app
	public final static int HEIGHT = 950; 
	//Default width window app
	public final static int WIDTH = 820; 
	//Players names
	private String p1;
	private String p2;
	//Current displayed panel
	private JPanel currentPanel=null;
	//Current GamePanel
	private GamePanel gP;
	//Current Board view
	private BoardPanel bP;
	
	/**
	 * 
	 * @param margin
	 */
	public ChessFrame(int margin) {
		super("Chess Game");
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
	@SuppressWarnings("unused")
	private JPanel replaceCurrentPanel(JPanel p) {
		removeCurrentPanel();
		addCurrentPanel(p);
		return p;
	}
	
	@Override
	public void printTitle() {setTitle("CHESS v1.0");}
	@Override
	public void printIsCheck(boolean check) {}
	@Override
	public void printLine() {}
	@Override
	public void setDest(String L) {
		// TODO Auto-generated method stub
	}
	@Override
	public String askName(boolean W, ChessGame C) {
		SwingUtilities.invokeLater(() ->
		{
			PlayerDialog pd = new PlayerDialog(W,C);
			pd.setVisible(true);
		});
		synchronized(C) {
			try {
				C.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Object o = C.getHolder();
		String result;
		if (!(o instanceof String)) {
			return null;
		} else {
			result = (String) o;
			if (W) {
				p1 = result;
			} else {
				p2 = result;
			}
		}
		return  result; 
	}
	@Override
	public Piece askMove(final Player J,final Piece[] Board,final int[] mW,final ChessGame G) {
		SwingUtilities.invokeLater(() -> bP.readyForMove(ChessFrame.this,J,Board,mW,G));
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
		return TypePiece.Q;
	}
	@Override
	public void printThreateningOnBoard(Piece[] B) {}
	@Override
	public void printThreatenOnBoard(Piece[] B) {}
	@Override
	public void printPlayersPiecesOnBoard(Player... P) {}
	@Override
	public void oppInCheck(Piece[] B, Player J2, boolean moveFound) {
		gP.printText("The other player is in Check!");
	}
	@Override
	public void printCheck() {
		SwingUtilities.invokeLater(() ->
		{
			if (gP!=null) {
				gP.printText("You sir are in trouble!");
			}
		});
	}
	@Override
	public void printBoardState(Piece[] B, Player J, int N) {
		SwingUtilities.invokeLater(() -> gP.printText("Your time to play : "+J.getPname()));
	}
	@Override
	public void printCaptures(ArrayList<String> wcaptures,
			ArrayList<String> bcaptures) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void printBoard(final Piece[] B) {
		SwingUtilities.invokeLater(() ->
		{
			if (bP == null) {
				currentPanel = new GamePanel(B,p1,p2);
				gP = (GamePanel)currentPanel;
				bP = gP.getBoardPanel();
				add(currentPanel);
				setPreferredSize(new Dimension(HEIGHT,WIDTH));
				pack();
				setLocationRelativeTo(null);
				setVisible(true);
			} else {
				bP.updateBoard(B);
			}
		});
	}
	@Override
	public void printGameOver() {
		SwingUtilities.invokeLater(() ->
		{
			if (gP!=null) {
				gP.printText("The Game is over!");
			}
		});
	}
	@Override
	public void printStats() {
		// TODO Auto-generated method stub
	}	
}
