package com.vbuecker.chucknorrisio;

import android.graphics.Color;

import java.util.Random;

public class Colors {
    public static int randomColor(){
        Random random = new Random();
        int r = random.nextInt(0xFF);
        int g = random.nextInt(0xFF);
        int b = random.nextInt(0xFF);

        return Color.argb(0x50, r, g, b);

    }
}
