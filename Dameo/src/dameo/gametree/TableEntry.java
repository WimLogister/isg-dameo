package dameo.gametree;

import dameo.move.Move;
import java.util.BitSet;

/**
 *
 * @author Wim
 */
public class TableEntry {
    private long stateValue;
    private TranspositionTable.TableValueFlagTypes valueType;
    private Move bestMove;
    private int searchDepth;
    private BitSet hashkey;

    public TableEntry() {
    }

    public TableEntry(long stateValue, TranspositionTable.TableValueFlagTypes valueType, Move bestMove, int searchDepth, BitSet hashkey) {
        this.stateValue = stateValue;
        this.valueType = valueType;
        this.bestMove = bestMove;
        this.searchDepth = searchDepth;
        this.hashkey = hashkey;
    }
        
        

        public Move getBestMove() {
            return bestMove;
        }

        public long getStateValue() {
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

        public void setStateValue(long stateValue) {
            this.stateValue = stateValue;
        }

        public void setValueType(TranspositionTable.TableValueFlagTypes valueType) {
            this.valueType = valueType;
        }
        
        
        
}
