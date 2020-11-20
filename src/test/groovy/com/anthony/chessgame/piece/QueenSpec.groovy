package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class QueenSpec extends PieceSpec {
    Player p = new Player(1,true,'test')

    def "check moves from D4 to #a#b"() {
        int pos = FromNotToPos('d', '4')
        Piece k = new Queen(pos, Piece.colorPiece.WHITE)

        given:
        B = init(new String[]
                /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
                /*7*/"  ","  ","  ","Rb","  ","  ","  ","  ",
                /*6*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*5*/"  ","Bb","  ","  ","  ","  ","  ","  ",
                /*4*/"  ","Rw","  ","Qw","  ","  ","  ","Rb",
                /*3*/"  ","  ","Bw","  ","  ","  ","  ","  ",
                /*2*/"  ","  ","Rb","  ","  ","  ","  ","  ",
                /*1*/"Bb","  ","  ","  ","  ","  ","Bb","  ",})
                    // a    b    c    d    e    f    g    h

        expect:
        k.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], p, B)==c

        where:
        a   |  b  | c
        //from bishop
        'b' | '6' | true
        'e' | '5' | true
        'h' | '8' | true
        'e' | '3' | true
        'g' | '1' | true
        'c' | '3' | false
        'b' | '2' | false
        'a' | '1' | false
        'g' | '8' | false
        'd' | '8' | false
        //from rook
        'c' | '4' | true
        'b' | '4' | false
        'a' | '4' | false
        'd' | '8' | false
        'd' | '7' | true
        'f' | '4' | true
        'd' | '1' | true
        'b' | '4' | false
        'c' | '2' | false
        'd' | '8' | false
    }
}
