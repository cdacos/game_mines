package com.cdacos.mines;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carlos on 06/03/2016.
 */
public class Data implements Parcelable {
    public int[] map;
    public boolean[] revealed;
    public State[] flagged;
    public int boardColumns;
    public int totalMines = 0;
    public int foundMines = 0;
    public boolean hasExploded;

    public Data(int boardColumns) {
        this.boardColumns = boardColumns;
        map = Logic.makeMap(boardColumns);
        revealed = new boolean[map.length];
        flagged = State.createArray(map.length);
        totalMines = Logic.getTotalMines(boardColumns);
    }

    public Data(Parcel in) {
        Object[] parceled = in.readArray(Data.class.getClassLoader());
        map = (int[])parceled[0];
        revealed = (boolean[])parceled[1];
        flagged = (State[])parceled[2];
        boardColumns = (int)parceled[3];
        totalMines = (int)parceled[4];
        foundMines = (int)parceled[5];
        hasExploded = (boolean)parceled[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(new Object[] {
                map,
                revealed,
                flagged,
                boardColumns,
                totalMines,
                foundMines,
                hasExploded
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
