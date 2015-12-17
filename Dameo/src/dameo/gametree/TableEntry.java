package dameo.gametree;

import dameo.move.Move;
import java.util.BitSet;

/**
 *
 * @author Wim
 */
public class TableEntry {
    private int stateValue;
        private TranspositionTable.TableValueFlagTypes valueType;
        private Move bestMove;
        private int searchDepth;
        private BitSet hashkey;

        public TableEntry() {
        }

        public Move getBestMove() {
            return bestMove;
        }

        public int getStateValue() {
            return stateValue;
        }

        public TranspositionTable.TableValueFlagTypes getValueFlag() {
            return valueType;
        }

        public int getSearchDepth() {
            return searchDepth;
        }

        public BitSet getHashkey() {
            return hashkey;
        }

        public void setBestMove(Move bestMove) {
            this.bestMove = bestMove;
        }

        public void setHashkey(BitSet hashkey) {
            this.hashkey = hashkey;
        }

        public void setSearchDepth(int searchDepth) {
            this.searchDepth = searchDepth;
        }

        public void setStateValue(int stateValue) {
            this.stateValue = stateValue;
        }

        public void setValueType(TranspositionTable.TableValueFlagTypes valueType) {
            this.valueType = valueType;
        }
        
        
        
}
