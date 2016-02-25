package com.anthony.chessgame.view.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.anthony.chessgame.game.ChessGame;
import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.piece.Piece;
import com.anthony.chessgame.view.ChessFrame;

public class BoardPanel extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3945785575519400790L;
	
	private static final String BASE_PATH = "pieces/";
	private static final String VOID_PATH = "Void.png";
	private static final String PAWNW_PATH = "PawnW.png";
	private static final String BISHOPW_PATH = "BishopW.png";
	private static final String KNIGHTW_PATH = "KnightW.png";
	private static final String ROOKW_PATH = "RookW.png";
	private static final String QUEENW_PATH = "QueenW.png";
	private static final String KINGW_PATH = "KingW.png";
	private static final String PAWNB_PATH = "PawnB.png";
	private static final String BISHOPB_PATH = "BishopB.png";
	private static final String KNIGHTB_PATH = "KnightB.png";
	private static final String ROOKB_PATH = "RookB.png";
	private static final String QUEENB_PATH = "QueenB.png";
	private static final String KINGB_PATH = "KingB.png";
	private PLabel[] labels;
	private Piece[] board;
	private int[] mW;
	private Player waitingPlayer;
	private PLabel lastPieceLabel;
	
	/**
	 * 
	 * @param B
	 */
	public BoardPanel(Piece[] B) {
		super();
		board = B;
		labels = new PLabel[64];
		initComponents();
	}
	
	/**
	 * 
	 */
	private void initComponents() {
		GridLayout gl = new GridLayout(8,8,1,1);
		setLayout(gl);
		setBorder(BorderFactory.createEmptyBorder(20,20,10,10));
		updateBoard();
		Dimension d = new Dimension((labels[0].getIcon().getIconWidth()+22)*8,(labels[0].getIcon().getIconHeight()+22)*8);
		setPreferredSize(d);
		setMinimumSize(d);
	}
	/**
	 * 
	 * @param B
	 */
	public void updateBoard(Piece[] B) {
		board = B;
		clearBoard();
		updateBoard();
		revalidate();
	}
	/**
	 * 
	 */
	private void clearBoard() {
		for (PLabel l : labels) {
			remove(l);
		}
	}
	/**
	 * 
	 */
	private void updateBoard() {
		for (int i=0;i<64;i++) {
			BufferedImage img = null;
			Piece p = board[i];
			String path = null;
			try {
			path = getImagePath(p.getType(),p.isWhite());
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			try {
				BufferedInputStream is = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(path));
				img = ImageIO.read(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        ImageIcon t = new ImageIcon(img);
			labels[i] = new PLabel(t,p,i);
			labels[i].unselect();
			add(labels[i]);
			labels[i].addMouseListener(this);
		}
	}
	/**
	 * 
	 * @param t
	 * @param w
	 * @return
	 */
	private String getImagePath(Piece.TypePiece t, Boolean w) {
		String result = BASE_PATH;
		switch (t) {
			case O :
			case N :
			default : result += VOID_PATH;
				break;
			case P: result += (w) ? PAWNW_PATH : PAWNB_PATH;
				break;
			case Bb :
			case Bw: result += (w) ? BISHOPW_PATH : BISHOPB_PATH;
				break;
			case Kn: result += (w) ? KNIGHTW_PATH : KNIGHTB_PATH;
				break;
			case R: result += (w) ? ROOKW_PATH : ROOKB_PATH;
				break;
			case Q: result += (w) ? QUEENW_PATH : QUEENB_PATH;
				break;
			case K: result += (w) ? KINGW_PATH : KINGB_PATH;
				break;	
		}
		return result;
	}
	/**
	 * 
	 * @param parent
	 * @param M
	 * @param Board
	 * @param mW
	 * @param G
	 */
	public void readyForMove(ChessFrame parent,Player M,Piece[] Board,int[] mW,ChessGame G) {
		waitingPlayer = M;
		this.mW = mW;
	}

	@Override
	/**
	 * Selection of pieces 
	 */
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o instanceof PLabel) {
			PLabel l = (PLabel) o;
			Piece lastPieceSelected=null;
			if (lastPieceLabel != null) {
				lastPieceSelected = lastPieceLabel.getPiece();
			}
			Piece nextPiece = l.getPiece();
			if (lastPieceLabel == l) return;
			Boolean isWhite = nextPiece.isWhite();
			//Move
			if((lastPieceLabel != null) && !((isWhite!=null) && (lastPieceSelected.isWhite()==isWhite))) {
				lastPieceLabel.unselect();
				int pos = l.getPos();
				int posx = pos%8;
				int posy = pos/8;
				boolean moveok = lastPieceSelected.checkMove(posx,posy,waitingPlayer,board);
				if (moveok) {
					mW[0] = lastPieceLabel.getPos();
					mW[1] = pos;
					lastPieceLabel = null;
					synchronized(waitingPlayer) {
						waitingPlayer.setMovingPiece(lastPieceSelected);
						waitingPlayer.notifyAll();
						waitingPlayer = null;
					}			
				}
			//Reselection another piece
			} else if ((lastPieceLabel != null) && (isWhite!=null)){
				if(isWhite == waitingPlayer.isWhite()) {
					lastPieceLabel.unselect();
					lastPieceLabel = l;
					l.select();
				}
			//First selection
			} else {
				if (lastPieceLabel != null) {
					lastPieceLabel.unselect();
					lastPieceLabel = null;
				}
				if ((isWhite!=null) && (isWhite == waitingPlayer.isWhite())) {
					l.select();
					lastPieceLabel = l;
				}
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
