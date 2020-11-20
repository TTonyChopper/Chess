package com.anthony.chessgame.piece

import com.anthony.chessgame.game.Player

import static com.anthony.chessgame.util.Utils.FromNot
import static com.anthony.chessgame.util.Utils.FromNotToPos

class RookSpec extends PieceSpec {
    Player p = new Player(1,true,'test')

    def "check moves from D4 to #a#b"() {
        int pos = FromNotToPos('d', '4')
        Piece k = new Rook(pos, Piece.colorPiece.WHITE)

        given:
        B = init(new String[]
                /*8*/{"  ","  ","  ","  ","  ","  ","  ","  ",
                /*7*/"  ","  ","  ","Rb","  ","  ","  ","  ",
                /*6*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*5*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*4*/"  ","Rw","  ","Rw","  ","  ","  ","Rb",
                /*3*/"  ","  ","  ","  ","  ","  ","  ","  ",
                /*2*/"  ","  ","Rb","  ","  ","  ","  ","  ",
                /*1*/"  ","  ","  ","  ","  ","  ","  ","  ",})
                    // a    b    c    d    e    f    g    h

        expect:
        k.checkMove(FromNot(a, b)[0], FromNot(a, b)[1], p, B)==c

        where:
        a   |  b  | c
        'c' | '4' | true
        'b' | '4' | false
        'a' | '4' | false
        'd' | '8' | false
        'd' | '7' | true
        'f' | '4' | true
        'd' | '1' | true
        'b' | '4' | false
        'c' | '2' | false
        'd' | '8' | false
    }
}
