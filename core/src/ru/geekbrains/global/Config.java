package ru.geekbrains.global;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Config {

    private static TextureAtlas
            mainAtlas,
            menuAtlas;

    public static TextureAtlas getMainATLAS() {
        return mainAtlas != null ? mainAtlas : (mainAtlas = new TextureAtlas("textures/mainAtlas.tpack"));
    }

    public static void disposeMainAtlas() {
        mainAtlas.dispose();
        mainAtlas = null;
    }

    public static TextureAtlas getMenuATLAS() {
        return menuAtlas != null ? menuAtlas : (menuAtlas = new TextureAtlas("textures/menuAtlas.tpack"));
    }

    public static void disposeMenutlas() {
        menuAtlas.dispose();
        menuAtlas = null;
    }
}
