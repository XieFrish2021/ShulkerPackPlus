package me.coderfrish.util;

import com.moandjiezana.toml.Toml;

import java.io.File;

@SuppressWarnings("ALL")
public class TomlUtil {
    private final Toml toml;

    public TomlUtil(File file) {
        this.toml = new Toml();
        this.read(file);
    }

    public void read(File file) {
        this.toml.read(file);
    }

    public String getString(String key) {
        return toml.getString(key);
    }

    public boolean getBoolean(String key) {
        return toml.getBoolean(key);
    }
}
