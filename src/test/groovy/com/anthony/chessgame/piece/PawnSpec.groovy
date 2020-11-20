package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class PawnSpec extends PieceSpec {
    Player p = new Player(1,false,'test')

    def "check moves from D4 to #a#b"() {
        int pos = FromNotToPos('d', '4')
        Piece k = new Pawn(pos, Piece.colorPiece.BLACK)

        given:
        B = init(new String[]
                /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
                /*7*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*6*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*5*/"  ","  ","Pb","  ","Pw","  ","  ","  ",
                /*4*/"  ","Pw","  ","Pb","  ","  ","  ","  ",
                /*3*/"  ","  ","  ","Pw","Pb","  ","  ","  ",
                /*2*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*1*/"  ","  ","  ","  ","  ","  ","  ","  ",})
                    // a    b    c    d    e    f    g    h

        expect:
        k.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], p, B)==c

        where:
        a   |  b  | c
        'c' | '5' | false
        'd' | '5' | true
        'e' | '5' | true
        'e' | '4' | false
        'e' | '3' | false
        'd' | '3' | false
        'c' | '3' | false
        'c' | '4' | false
        'b' | '4' | false
        'a' | '9' | false
    }
}
