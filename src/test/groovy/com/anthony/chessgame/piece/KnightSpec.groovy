package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class KnightSpec extends PieceSpec {
    Player p = new Player(1,true,'test')

    def "check moves from C3 to #a#b"() {
        int pos = FromNotToPos('c', '3')
        Piece k = new Knight(pos, Piece.colorPiece.WHITE)

        given:
        setPiece(k, pos)

        expect:
        k.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], p, B)==c

        where:
         a  |  b  | c
        'b' | '1' | true
        'd' | '1' | true
        'a' | '2' | true
        'e' | '2' | true
        'a' | '4' | true
        'e' | '4' | true
        'b' | '5' | true
        'd' | '5' | true
        'g' | '8' | false
        'a' | '9' | false
    }

    def "check moves v2 from C3 to #a#b"() {
        int pos = FromNotToPos('c', '3')
        Piece k = new Knight(pos, Piece.colorPiece.WHITE)

        given:
        B = init(new String[]
            /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
            /*7*/"  ","  ","  ","  ","  ","  ","  ","  ",
            /*6*/"  ","  ","  ","  ","  ","  ","  ","  ",
            /*5*/"  ","Nb","  ","  ","  ","  ","  ","  ",
            /*4*/"  ","  ","  ","Nb","  ","  ","  ","  ",
            /*3*/"  ","  ","Nw","  ","  ","  ","  ","  ",
            /*2*/"  ","  ","Nb","  ","  ","  ","  ","  ",
            /*1*/"  ","  ","  ","  ","  ","  ","  ","  ",})
                // a    b    c    d    e    f    g    h

        expect:
        k.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], p, B)==c

        where:
        a  |  b  | c
        'b' | '1' | true
        'd' | '1' | true
        'a' | '2' | true
        'e' | '2' | true
        'a' | '4' | true
        'e' | '4' | true
        'b' | '5' | true
        'd' | '5' | true
        'g' | '8' | false
        'a' | '9' | false
    }
}
