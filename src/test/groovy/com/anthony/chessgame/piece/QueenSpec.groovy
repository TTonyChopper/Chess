package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class QueenSpec extends PieceSpec {
    Player P = new Player(1,true,'test')

    def "check moves from D4 to #a#b"() {
        int pos = FromNotToPos('d', '4')
        Piece p = new Queen(pos, Piece.colorPiece.WHITE)

        given:
        B = init(new String[]
                /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
                /*7*/ "  ","  ","  ","Rb","  ","  ","  ","  ",
                /*6*/ "  ","  ","  ","  ","  ","  ","  ","  ",
                /*5*/ "  ","Bb","  ","  ","  ","  ","  ","  ",
                /*4*/ "  ","Rw","  ","Qw","  ","  ","  ","Rb",
                /*3*/ "  ","  ","Bw","  ","  ","  ","  ","  ",
                /*2*/ "  ","  ","Rb","  ","  ","  ","  ","  ",
                /*1*/ "Bb","  ","  ","  ","  ","  ","Bb","  ",})
                     // a    b    c    d    e    f    g    h
        p.setThreats(B)

        expect:
        p.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], P, B) == c
        p.threatening.size() == 8
        p.threatening.contains(B[FromNotToPos(a, b)]) == d

        where:
        a   |  b  |   c   |  d
        //from bishop
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
        //from rook
        'c' | '4' | true  | false
        'b' | '4' | false | true
        'a' | '4' | false | false
        'd' | '8' | false | false
        'f' | '4' | true  | false
        'd' | '1' | true  | false
        'b' | '4' | false | true
        'g' | '1' | true  | true
        'd' | '7' | true  | true
        'c' | '2' | false | false
        'd' | '8' | false | false
        'a' | '9' | false | true
    }
}
