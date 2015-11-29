package dameo;

/**
 *
 * @author Wim
 */
public class KingPiece extends Piece {
    
    public KingPiece(Piece p) {
        super(p);
    }
    
    @Override
    public int getBoardValue() {
        return super.getBoardValue() * 3;
    }
    
}
