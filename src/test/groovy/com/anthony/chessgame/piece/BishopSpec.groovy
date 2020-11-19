package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class BishopSpec extends PieceSpec {
    Player p = new Player(1,true,'test')

    def "check moves from D4 to #a#b"() {
        int pos = FromNotToPos('d', '4')
        Piece k = new Bishop(pos, Piece.colorPiece.WHITE)

        given:
        B = init(new String[]
                /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
                /*7*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*6*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*5*/"  ","Bb","  ","  ","  ","  ","  ","  ",
                /*4*/"  ","  ","  ","Bw","  ","  ","  ","  ",
                /*3*/"  ","  ","Bw","  ","  ","  ","  ","  ",
                /*2*/"  ","  ","Bb","  ","  ","  ","  ","  ",
                /*1*/"Bb","  ","  ","  ","  ","  ","Bb","  ",})
                    // a    b    c    d    e    f    g    h

        expect:
        k.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], p, B)==c

        where:
        a   |  b  | c
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
    }
}
