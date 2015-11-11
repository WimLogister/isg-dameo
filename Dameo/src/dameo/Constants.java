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
        WHITE(1, 0, 7, 7, 0, 1, 2), BLACK(2, -7, 0, 0, -7, -1, 1);
        private final int value;
        private final int boardLeftEdge;
        private final int boardRightEdge;
        private final int boardTopEdge;
        private final int boardBottomEdge;
        private final int direction;
        private final int opponent;

        private PlayerColors(int value, int boardLeftEdge, int boardRightEdge,
                int boardTopEdge, int boardBottomEdge, int direction, int opponent) {
            this.value = value;
            this.boardLeftEdge = boardLeftEdge;
            this.boardRightEdge = boardRightEdge;
            this.boardTopEdge = boardTopEdge;
            this.boardBottomEdge = boardBottomEdge;
            this.direction = direction;
            this.opponent = opponent;
        }
        
        public int getValue() {
            return value;
        }

        public int getBoardBottomEdge() {
            return boardBottomEdge;
        }

        public int getBoardLeftEdge() {
            return boardLeftEdge;
        }

        public int getBoardRightEdge() {
            return boardRightEdge;
        }

        public int getBoardTopEdge() {
            return boardTopEdge;
        }

        public int getDirection() {
            return direction;
        }

        public int getOpponent() {
            return opponent;
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
