package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class RookSpec extends PieceSpec {
    Player P = new Player(1,true,'test')

    def "check moves from D4 to #a#b"() {
        int pos = FromNotToPos('d', '4')
        Piece p = new Rook(pos, Piece.colorPiece.WHITE)

        given:
        B = init(new String[]
                /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
                /*7*/ "  ","  ","  ","Rb","  ","  ","  ","  ",
                /*6*/ "  ","  ","  ","  ","  ","  ","  ","  ",
                /*5*/ "  ","  ","  ","  ","  ","  ","  ","  ",
                /*4*/ "  ","Rw","  ","Rw","  ","  ","  ","Rb",
                /*3*/ "  ","  ","  ","  ","  ","  ","  ","  ",
                /*2*/ "  ","  ","Rb","  ","  ","  ","  ","  ",
                /*1*/ "  ","  ","  ","  ","  ","  ","  ","  ",})
                     // a    b    c    d    e    f    g    h
        p.setThreats(B)

        expect:
        p.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], P, B)==c
        p.threatening.contains(B[FromNotToPos(a, b)]) == d

        where:
        a   |  b  |   c   |   d
        'c' | '4' | true  | false
        'b' | '4' | false | true
        'a' | '4' | false | false
        'd' | '8' | false | false
        'd' | '7' | true  | true
        'f' | '4' | true  | false
        'd' | '1' | true  | false
        'c' | '2' | false | false
        'd' | '8' | false | false
        'h' | '4' | true  | true
        'a' | '9' | false | true
    }
}
