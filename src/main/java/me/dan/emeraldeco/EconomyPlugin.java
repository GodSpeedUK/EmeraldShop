package me.dan.emeraldeco;

import lombok.Getter;
import me.dan.emeraldeco.account.AccountManager;
import me.dan.emeraldeco.configuration.Messages;
import me.dan.emeraldeco.listener.PlayerJoinListener;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.plugin.CustomPlugin;
import me.dan.pluginapi.util.Text;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public final class EconomyPlugin extends CustomPlugin {


    private Economy econ;

    @Getter
    private static EconomyPlugin instance;

    private AccountManager accountManager;

    @Override
    public void enable() {
        instance = this;
        Configuration.loadConfig(new YamlFile("messages", this.getDataFolder().getAbsolutePath(), null, this), Messages.values());
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            sendConsoleMessage("Could not find vault! Disabling...");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }


        this.accountManager = new AccountManager();
        registerEvents(new PlayerJoinListener());

    }

    @Override
    public void disable() {

    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(Text.c(Messages.PREFIX.getString() + message));
    }

}
