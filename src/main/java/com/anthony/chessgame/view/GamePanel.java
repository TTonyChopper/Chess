package com.anthony.chessgame.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.anthony.chessgame.piece.Piece;
import com.anthony.chessgame.view.components.BoardPanel;
import com.anthony.chessgame.view.components.MovesPanel;
import com.anthony.chessgame.view.components.PiecesPanel;
import com.anthony.chessgame.view.components.StatusPanel;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7287116735250363314L;
	
	private BoardPanel board;
	private PiecesPanel piecesPanel;
	private StatusPanel statusPanel;
	
	/**
	 * 
	 * @param B
	 */
	public GamePanel(Piece[] B,String p1, String p2) {
		super();
		initComponents(B,p1,p2);
	}
	
	/**
	 * 
	 * @param B
	 */
	private void initComponents(Piece[] B,String p1, String p2) {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder("Chess V1.0"));
		GridBagConstraints c = new GridBagConstraints();

		
		//TOP view
		JPanel boardView = new JPanel();
		BoxLayout boxLayout = new BoxLayout(boardView,BoxLayout.Y_AXIS);
		boardView.setLayout(boxLayout);
		board = new BoardPanel(B);
		piecesPanel = new PiecesPanel();
		piecesPanel.setPreferredSize(new Dimension(board.getPreferredSize().width,0));
		boardView.add(board);
		boardView.add(piecesPanel);
		
		c.gridx=0;
		c.gridy=0;
		add(boardView,c);
		c.gridx=1;
		c.gridy=0;
		c.weightx=1;
		c.fill=GridBagConstraints.BOTH;
		add(new MovesPanel(),c);
		
		//BOT view
		c.gridx=0;
		c.gridy=1;
		c.weightx=0;
		c.weighty=1; 
		statusPanel = new StatusPanel(p1,p2);
		add(statusPanel,c);
		c.fill=GridBagConstraints.NONE;
		c.gridx=1;
		c.gridy=1;
		add(new JLabel("menu"),c);
	}
	/**
	 * 
	 * @return
	 */
	public BoardPanel getBoardPanel() {
		return board;
	}
	public void printText(String s) {
		SwingUtilities.invokeLater(() -> statusPanel.appendTextln(s));
	}
}
