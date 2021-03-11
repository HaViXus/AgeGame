package com.agegame.utils;

import com.badlogic.gdx.graphics.Pixmap;

public class PixmapModifier {
    public static Pixmap flipHorizontally(Pixmap src) {
        final int width = src.getWidth();
        final int height = src.getHeight();
        Pixmap flipped = new Pixmap(width, height, src.getFormat());

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                flipped.drawPixel(x, y, src.getPixel(width - x - 1, y));
            }
        }
        return flipped;
    }
}
