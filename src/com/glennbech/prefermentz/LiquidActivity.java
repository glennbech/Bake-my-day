package com.glennbech.prefermentz;

/**
 * Created by IntelliJ IDEA.
 * User: glenn
 * Date: 5/21/11
 * Time: 6:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class LiquidActivity {
    private static LiquidActivity ourInstance = new LiquidActivity();

    public static LiquidActivity getInstance() {
        return ourInstance;
    }

    private LiquidActivity() {
    }
}
