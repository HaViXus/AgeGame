package com.agegame;

public class Direction {
    public static enum direction {LEFT(-1), RIGHT(1);
        private int directionValue;

        direction(int directionValue){ this.directionValue = directionValue; };

        public int getIntValue(){ return directionValue; }

    };

}
