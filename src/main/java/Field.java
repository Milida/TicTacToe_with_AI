package main.java;

enum FIELD_STATE {
    X,
    O,
    EMPTY;

    @Override
    public String toString() {
        if(this.equals(X)) {
            return "X";
        } else if (this.equals(O)) {
            return "O";
        } else {
            return "_";
        }
    }
}

public class Field {
    FIELD_STATE state;

    public Field() {
        state = FIELD_STATE.EMPTY;
    }

    /**
     * Is this field empty
     * @return true if empty, false if not
     */
    boolean isFree() {
        return state.equals(FIELD_STATE.EMPTY);
    }

    /**
     * Checks if state is equal to the expected one
     * @param state expected state of field
     * @return true if state is equal to given one, false if not
     */
    boolean stateEquals(FIELD_STATE state) {
        return this.state.equals(state);
    }

    public void setState(FIELD_STATE state) {
        this.state = state;
    }
}
