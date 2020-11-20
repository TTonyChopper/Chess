package com.anthony.chessgame.piece

import spock.lang.Specification

import static com.anthony.chessgame.util.Utils.FromNot

/*
final static int[] PIECES ={
     0, 1, 2, 3, 4, 5, 6, 7,
     8, 9,10,11,12,13,14,15,
    16,17,18,19,20,21,22,23,
    24,25,26,27,28,29,30,31,
    32,33,34,35,36,37,38,39,
    40,41,42,43,44,45,46,47,
    48,49,50,51,52,53,54,55,
    56,57,58,59,60,61,62,63,64}*/
class PieceSpec extends Specification {
    protected static Piece[] B = new Piece[65]

    static void setPiece(Piece p,int P){
        B[P] = p
        p.setPos(P)
        p.setCoord()
        p.setLCoord()
    }

    static Piece getNewPiece(String L, int pos){
        L=L.toUpperCase()
        char letter = L.charAt(0)
        char y = L.charAt(1)
        def color
        if (y == (char)'B')
        {
            color = Piece.colorPiece.BLACK
        }
        else if (y == (char)'W') {
            color = Piece.colorPiece.WHITE
        }
        else {
            color = Piece.colorPiece.NONE
        }
        switch (letter)
        {
            case (char)' ':
                return new Nothing(pos,color)
            case (char)'P':
                return new Pawn(pos,color)
            case (char)'N':
                return new Knight(pos,color)
            case (char)'B':
                return new Bishop(pos,color)
            case (char)'R':
                return new Rook(pos,color)
            case (char)'Q':
                return new Queen(pos,color)
            case (char)'K':
                return new King(pos,color)
            default:
                return new OutOfBoard()
        }
    }

    static Piece[] init(String[] s) {
        for (int i = 0;i<s.size();i++) {
            setPiece(getNewPiece(s[i], i), i)
        }
        return B
    }

    def setup() {           // run before every feature method
        B = new Piece[65]
        Arrays.fill(B, new Nothing(0, Piece.colorPiece.NONE))
        B[64]=new OutOfBoard()
    }
    def cleanup() {}        // run after every feature method
    def setupSpec() {}      // run before the first feature method
    def cleanupSpec() {}    // run after the last feature method

    def "conversions"() {
        expect:
        FromNot(a,b)[0]==c
        FromNot(a,b)[1]==d

        where:
         a | b | c | d
        'c'|'3'| 2 | 5
        'e'|'8'| 4 | 0
        'h'|'5'| 7 | 3
        'f'|'1'| 5 | 7
    }
}
