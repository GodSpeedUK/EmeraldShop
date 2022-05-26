package me.dan.emeraldeco;

import lombok.Getter;
import me.dan.emeraldeco.account.AccountManager;
import me.dan.emeraldeco.account.interest.Interest;
import me.dan.emeraldeco.command.BalanceCommand;
import me.dan.emeraldeco.command.EconomyCommand;
import me.dan.emeraldeco.command.PayCommand;
import me.dan.emeraldeco.configuration.Config;
import me.dan.emeraldeco.configuration.Message;
import me.dan.emeraldeco.economy.EconomyHolder;
import me.dan.emeraldeco.listener.PlayerJoinListener;
import me.dan.emeraldeco.menu.DepositMenuHandler;
import me.dan.pluginapi.PluginAPI;
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


    private Economy econ;

    @Getter
    private static EconomyPlugin instance;

    private AccountManager accountManager;

    private EconomyHolder economyHolder;

    @Override
    public void enable() {
        instance = this;
        Serialization.register(Interest.class);
        Configuration.loadConfig(new YamlFile("messages", this.getDataFolder().getAbsolutePath(), null, this), Message.values());
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            sendConsoleMessage("Could not find vault! Disabling...");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.economyHolder = new EconomyHolder();

        this.accountManager = new AccountManager();
        getServer().getServicesManager().register(Economy.class, economyHolder, this, ServicePriority.Highest);

        registerEvents(new PlayerJoinListener());
        registerCommands(new EconomyCommand(), new PayCommand(), new BalanceCommand());

        setupEconomy();

        PluginAPI.getInstance().getMenuManager().registerPerformMethod(Config.DEPOSIT_MENU.getMenu(), new DepositMenuHandler());

        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            //TODO: Papi
        }

    }

    @Override
    public void disable() {

    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    public void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(Text.c(Message.PREFIX.getString() + message));
    }

}
