package com.project.barcodechecker.models;

/**
 * Created by lucky on 07-Oct-17.
 */

public class MenuItem {
    private int icon;
    private String name;

    public MenuItem(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
