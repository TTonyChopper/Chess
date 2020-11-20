package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class KingSpec extends PieceSpec {
    Player p = new Player(1,true,'test')

    def "check moves from D4 to #a#b"() {
        int pos = FromNotToPos('d', '4')
        Piece k = new King(pos, Piece.colorPiece.WHITE)

        given:
        B = init(new String[]
                /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
                /*7*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*6*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*5*/"  ","  ","  ","  ","Kb","  ","  ","  ",
                /*4*/"  ","Qb","  ","Kw","  ","  ","  ","  ",
                /*3*/"  ","  ","  ","Qb","Qw","  ","  ","  ",
                /*2*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*1*/"  ","  ","  ","  ","  ","  ","  ","  ",})
                    // a    b    c    d    e    f    g    h

        expect:
        k.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], p, B)==c

        where:
        a   |  b  | c
        'c' | '5' | true
        'd' | '5' | true
        'e' | '5' | true
        'e' | '4' | true
        'e' | '3' | false
        'd' | '3' | true
        'c' | '3' | true
        'c' | '4' | true
        'b' | '4' | false
        'a' | '9' | false
    }
}
