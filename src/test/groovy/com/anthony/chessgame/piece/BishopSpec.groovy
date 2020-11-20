package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class BishopSpec extends PieceSpec {
    Player P = new Player(1,true,'test')

    def "check moves from D4 to #a#b"() {
        int pos = FromNotToPos('d', '4')
        Piece p = new Bishop(pos, Piece.colorPiece.WHITE)

        given:
        B = init(new String[]
                /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
                /*7*/ "  ","  ","  ","  ","  ","  ","  ","  ",
                /*6*/ "  ","  ","  ","  ","  ","  ","  ","  ",
                /*5*/ "  ","Bb","  ","  ","  ","  ","  ","  ",
                /*4*/ "  ","  ","  ","Bw","  ","  ","  ","  ",
                /*3*/ "  ","  ","Bw","  ","  ","  ","  ","  ",
                /*2*/ "  ","  ","Bb","  ","  ","  ","  ","  ",
                /*1*/ "Bb","  ","  ","  ","  ","  ","Bb","  ",})
                     // a    b    c    d    e    f    g    h
        p.setThreats(B)

        expect:
        p.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], P, B)==c
        p.threatening.contains(B[FromNotToPos(a, b)]) == d

        where:
        a   |  b  |   c   |   d
        'b' | '6' | true  | false
        'e' | '5' | true  | false
        'h' | '8' | true  | false
        'e' | '3' | true  | false
        'g' | '1' | true  | true
        'c' | '3' | false | true
        'b' | '2' | false | false
        'a' | '1' | false | false
        'g' | '8' | false | false
        'd' | '8' | false | false
        'a' | '9' | false | true
    }
}
