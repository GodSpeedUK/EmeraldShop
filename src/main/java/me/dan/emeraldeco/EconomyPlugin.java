package me.dan.emeraldeco;

import lombok.Getter;
import me.dan.emeraldeco.account.AccountManager;
import me.dan.emeraldeco.command.BalanceCommand;
import me.dan.emeraldeco.command.EconomyCommand;
import me.dan.emeraldeco.command.PayCommand;
import me.dan.emeraldeco.configuration.Config;
import me.dan.emeraldeco.configuration.Message;
import me.dan.emeraldeco.economy.EconomyHolder;
import me.dan.emeraldeco.exchange.Exchange;
import me.dan.emeraldeco.exchange.ExchangeList;
import me.dan.emeraldeco.listener.PlayerJoinListener;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.configuration.Serialization;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.plugin.CustomPlugin;
import me.dan.pluginapi.util.Text;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

@Getter
public final class EconomyPlugin extends CustomPlugin {

    @Getter
    private static EconomyPlugin instance;

    private AccountManager accountManager;

    private EconomyHolder economyHolder;

    @Override
    public void enable() {
        instance = this;
        Configuration.loadConfig(new YamlFile("config.yml", this.getDataFolder().getAbsolutePath(), null, this), Config.values());
        Configuration.loadConfig(new YamlFile("messages.yml", this.getDataFolder().getAbsolutePath(), null, this), Message.values());
        Serialization.register(Exchange.class);
        Serialization.register(ExchangeList.class);
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            sendConsoleMessage("Could not find vault! Disabling...");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }


        this.accountManager = new AccountManager();
        this.economyHolder = new EconomyHolder();
        getServer().getServicesManager().register(Economy.class, economyHolder, this, ServicePriority.Highest);

        registerEvents(new PlayerJoinListener());
        registerCommands(new BalanceCommand(), new EconomyCommand(), new PayCommand());
    }

    @Override
    public void disable() {

    }


    public void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(Text.c(Message.PREFIX.getString() + message));
    }

}
