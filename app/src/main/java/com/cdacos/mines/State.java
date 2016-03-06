package com.cdacos.mines;

/**
 * Created by carlos on 06/03/2016.
 */
public enum State {
    UNKNOWN,
    FLAGGED,
    TENTATIVE;

    public static State[] createArray(int size) {
        State[] array = new State[size];
        for (int i = 0; i < size; i++) {
            array[i] = UNKNOWN;
        }
        return array;
    }
}
