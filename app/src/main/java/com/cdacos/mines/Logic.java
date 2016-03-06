package com.cdacos.mines;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by carlos on 06/03/2016.
 */

public class Logic {
    public static final int HAS_MINE = 999;

    private static Random random = new Random();

    public static int[] getCoordsFromPosition(int position, int width) {
        return new int[] { position % width, position / width };
    }

    public static int getPositionFromCoords(int[] coord, int width) {
        return coord[1] * width + coord[0];
    }

    private static int getRandomPosition(int width) {
        return random.nextInt(width*width);
    }

    public static int getTotalMines(int width) {
        return (int)(width * 1.25f);
    }

    public static int[] makeMap(int width) {
        int[] map = new int[width * width];
        int totalMines = getTotalMines(width);

        for (int i = 0; i < totalMines; i++) {
            int p;
            do {
                p = getRandomPosition(width);
            } while (map[p] != 0);
            map[p] = HAS_MINE;
        }

        for (int i = 0; i < map.length; i++) {
            if (map[i] != HAS_MINE) {
                int[] neighbours = getNeighbours(i, width);
                int mines = 0;
                for (int neighbour : neighbours) {
                    mines += map[neighbour] == HAS_MINE ? 1 : 0;
                }
                map[i] = mines;
            }
        }

        return map;
    }

    public static int[] getNeighbours(int position, int width) {
        int[] coord = getCoordsFromPosition(position, width);
        ArrayList<Integer> temp = new ArrayList<>();

        // Starting from top left walk clockwise round this point
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int x = coord[0] + dx;
                int y = coord[1] + dy;
                if (x >= 0 && x < width && y >= 0 && y < width &&
                        !(x == coord[0] && y == coord[1])) {
                    temp.add(getPositionFromCoords(new int[]{x, y}, width));
                }
            }
        }

        int[] neighbours = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            neighbours[i] = temp.get(i);
        }

        return neighbours;
    }

    public static boolean hasWon(Data data) {
        if (data.foundMines == data.totalMines) {
            for (int i = 0; i < data.map.length; i++) {
                if (!data.revealed[i] && data.flagged[i] != State.FLAGGED) return false;
            }
            return true;
        }
        return false;
    }


    public static  void reveal(Data data, int position) {
        if (data.flagged[position] == State.UNKNOWN) {
            data.revealed[position] = true;
            if (data.map[position] == Logic.HAS_MINE) {
                // Boom!
                data.hasExploded = true;
                // Reveal everything
                for (int i = 0; i < data.map.length; i++) {
                    data.revealed[i] = true;
                }
            } else {
                int[] neighbours = Logic.getNeighbours(position, data.boardColumns);
                if (data.map[position] == 0) {
                    for (int neighbour : neighbours) {
                        if (!data.revealed[neighbour]) {
                            reveal(data, neighbour);
                        }
                    }
                }
            }
        }
    }

    public static  void toggleFlaggedState(Data data, int position) {
        switch (data.flagged[position]) {
            case FLAGGED:
                data.flagged[position] = State.TENTATIVE;
                data.foundMines--;
                break;
            case TENTATIVE:
                data.flagged[position] = State.UNKNOWN;
                break;
            default:
                data.flagged[position] = State.FLAGGED;
                data.foundMines++;
        }
    }
}
