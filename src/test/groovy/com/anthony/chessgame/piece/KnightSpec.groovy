package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class KnightSpec extends PieceSpec {
    Player P = new Player(1,true,'test')

    def "check moves from C3 to #a#b"() {
        int pos = FromNotToPos('c', '3')
        Piece p = new Knight(pos, Piece.colorPiece.WHITE)

        given:
        setPiece(p, pos)

        expect:
        p.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], P, B)==c

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
        Piece p = new Knight(pos, Piece.colorPiece.WHITE)

        given:
        B = init(new String[]
            /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
            /*7*/ "  ","  ","  ","  ","  ","  ","  ","  ",
            /*6*/ "  ","  ","  ","  ","  ","  ","  ","  ",
            /*5*/ "  ","Nb","  ","  ","  ","  ","  ","  ",
            /*4*/ "  ","  ","  ","Nb","  ","  ","  ","  ",
            /*3*/ "  ","  ","Nw","  ","  ","  ","  ","  ",
            /*2*/ "  ","  ","Nb","  ","  ","  ","  ","  ",
            /*1*/ "  ","  ","  ","  ","  ","  ","  ","  ",})
                 // a    b    c    d    e    f    g    h
        p.setThreats(B)

        expect:
        p.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], P, B) == c
        p.threatening.size() == 8
        p.threatening.contains(B[FromNotToPos(a, b)]) == d

        where:
         a  |  b  |   c   |  d
        'b' | '1' | true  | true
        'd' | '1' | true  | true
        'a' | '2' | true  | true
        'e' | '2' | true  | true
        'a' | '4' | true  | true
        'e' | '4' | true  | true
        'b' | '5' | true  | true
        'd' | '5' | true  | true
        'g' | '8' | false | false
        'a' | '9' | false | false
    }
}
