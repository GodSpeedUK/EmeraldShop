package me.dan.emeraldeco.menu;

import me.dan.pluginapi.menu.MenuItem;
import me.dan.pluginapi.menu.MenuPerform;
import me.dan.pluginapi.user.User;

public class DepositMenuHandler extends MenuPerform {
    @Override
    public boolean perform(MenuItem menuItem, User user) {
        return false;
    }

    @Override
    public void onClose(User user) {
    }
}
