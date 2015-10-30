package dameo;

/**
 *
 * @author Wim
 */
public class Constants {
    
    public static final int PLAYER_WHITE = 1;
    public static final int PLAYER_BLACK = 2;
    
    public static final int PIECES_PER_PLAYER = 18;
    
    public enum PlayerColors {
        WHITE(1), BLACK(2);
        private int value;
        
        private PlayerColors(int value) {
            this.value = value;
        }
    }
    
    public enum PlayerTypes {
        HUMAN(1), AI(2);
        private int value;

        private PlayerTypes(int value) {
            this.value = value;
        }
        
        public static PlayerTypes getPlayerType(int value) {
            if (value == PlayerTypes.HUMAN.value) {
                return HUMAN;
            }
            else return AI;
        }
    }
}
