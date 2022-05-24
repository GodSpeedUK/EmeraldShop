package me.dan.emeraldeco.listener;

import me.dan.emeraldeco.EconomyPlugin;
import me.dan.emeraldeco.account.Account;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Account account = EconomyPlugin.getInstance().getAccountManager().get(e.getPlayer().getUniqueId());
        if (account == null) {
            EconomyPlugin.getInstance().sendConsoleMessage("No account detected for " + e.getPlayer().getName() + "! Creating...");
            EconomyPlugin.getInstance().getAccountManager().create(e.getPlayer().getUniqueId());
        }
    }

}
