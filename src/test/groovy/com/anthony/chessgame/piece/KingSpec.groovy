package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class KingSpec extends PieceSpec {
    Player P = new Player(1,true,'test')

    def "check moves from D4 to #a#b"() {
        int pos = FromNotToPos('d', '4')
        Piece p = new King(pos, Piece.colorPiece.WHITE)

        given:
        B = init(new String[]
                /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
                /*7*/ "  ","  ","  ","  ","  ","  ","  ","  ",
                /*6*/ "  ","  ","  ","  ","  ","  ","  ","  ",
                /*5*/ "  ","  ","  ","  ","Kb","  ","  ","  ",
                /*4*/ "  ","Qb","  ","Kw","  ","  ","  ","  ",
                /*3*/ "  ","  ","  ","Qb","Qw","  ","  ","  ",
                /*2*/ "  ","  ","  ","  ","  ","  ","  ","  ",
                /*1*/ "  ","  ","  ","  ","  ","  ","  ","  ",})
                     // a    b    c    d    e    f    g    h
        p.setThreats(B)

        expect:
        p.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], P, B)==c
        p.threatening.contains(B[FromNotToPos(a, b)]) == d

        where:
         a  |  b  |   c   |   d
        'c' | '5' | true  | true
        'd' | '5' | true  | true
        'e' | '5' | true  | true
        'e' | '4' | true  | true
        'e' | '3' | false | true
        'd' | '3' | true  | true
        'c' | '3' | true  | true
        'c' | '4' | true  | true
        'b' | '4' | false | false
        'a' | '9' | false | false
    }
}
