package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class PawnSpec extends PieceSpec {
    Player P = new Player(1,false,'test')

    def "check moves from D4 to #a#b"() {
        int pos = FromNotToPos('d', '4')
        Piece p = new Pawn(pos, Piece.colorPiece.BLACK)

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
        p.setThreats(B)

        expect:
        p.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], P, B) == c
        p.threatening.size() == 2
        p.threatening.contains(B[FromNotToPos(a, b)]) == d

        where:
        a   |  b  |   c   |   d
        'c' | '5' | false | true
        'd' | '5' | true  | false
        'e' | '5' | true  | true
        'e' | '4' | false | false
        'e' | '3' | false | false
        'd' | '3' | false | false
        'c' | '3' | false | false
        'c' | '4' | false | false
        'b' | '4' | false | false
        'a' | '9' | false | false
    }
}
