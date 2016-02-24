package com.anthony.chessgame.view.components;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.anthony.chessgame.piece.Piece;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7287116735250363314L;
	private BoardPanel board;
	
	/**
	 * 
	 * @param B
	 */
	public GamePanel(Piece[] B) {
		super();
		initComponents(B);
	}
	
	/**
	 * 
	 * @param B
	 */
	private void initComponents(Piece[] B) {
		JPanel boardView = new JPanel();
		BoxLayout boxLayout = new BoxLayout(boardView,BoxLayout.Y_AXIS);
		boardView.setLayout(boxLayout);
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		board = new BoardPanel(B);
		boardView.add(board);
		boardView.add(new PiecesPanel());
		add(boardView);
		add(new JPanel());
	}
	/**
	 * 
	 * @return
	 */
	public BoardPanel getBoardPanel() {
		return board;
	}
}
