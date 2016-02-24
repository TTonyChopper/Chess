package com.anthony.chessgame.util;

import com.anthony.chessgame.view.ChessFrame;
import com.anthony.chessgame.view.ConsolePrinter;

public class PrinterFactory {

	public static enum PrinterType{CONSOLE,SWING};
	
	/**
	 * 
	 */
	private PrinterFactory() {
		
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public static IPrint getPrinter(PrinterType type) {
		IPrint p = null;
		switch (type) {
			default : break;
			case CONSOLE : p = new ConsolePrinter();
				break;
			case SWING : p = new ChessFrame(4);
				break;
		}
		return p;
	}
}
