package me.dan.emeraldeco.configuration;

import lombok.Getter;
import lombok.Setter;
import me.dan.emeraldeco.account.interest.Interest;
import me.dan.emeraldeco.exchange.Exchange;
import me.dan.emeraldeco.exchange.ExchangeList;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.menu.Menu;
import org.bukkit.Material;

import java.time.DayOfWeek;

public enum Config implements Configuration {

    SAVE_INTERVAL("save-interval", 300),
    SERVER_NAME("server-name", "ServerName"),
    INTEREST_RATE("interest-rate", 10D),
    EXCHANGES("exchange-rates", new ExchangeList(
            new Exchange(2, Material.DIAMOND),
            new Exchange(3, Material.GOLD_INGOT)
    )),
    INTEREST("interest", new Interest(DayOfWeek.SATURDAY, 7, 10)),
    DEPOSIT_MENU("deposit-menu", new Menu()
            .setName("&7Deposit")
            .setSize(27));

    @Getter
    private final String path;

    @Getter
    @Setter
    private Object value;

    Config(String path, Object value) {
        this.path = path;
        this.value = value;
    }

    public Interest getInterest() {
        return (Interest) value;
    }

}
