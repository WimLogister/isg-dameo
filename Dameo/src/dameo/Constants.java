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
        WHITE(1, 1), BLACK(2, -1);
        private final int value;
        private final int forward;

        public int getValue() {
            return value;
        }

        public int getForward() {
            return forward;
        }
        
        private PlayerColors(int value, int forward) {
            this.value = value;
            this.forward = forward;
        }
    }
    
    public enum PlayerTypes {
        HUMAN(1), AI(2), RANDOM(3);
        private int value;

        private PlayerTypes(int value) {
            this.value = value;
        }
        
        public static PlayerTypes getPlayerType(int value) {
            if (value == PlayerTypes.HUMAN.value) {
                return HUMAN;
            }
            if (value == PlayerTypes.AI.value) {
                return AI;
            }
            else return RANDOM;
        }
    }
}
