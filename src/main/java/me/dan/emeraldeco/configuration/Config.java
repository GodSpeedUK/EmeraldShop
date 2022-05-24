package me.dan.emeraldeco.configuration;

import lombok.Getter;
import lombok.Setter;
import me.dan.emeraldeco.exchange.Exchange;
import me.dan.emeraldeco.exchange.ExchangeList;
import me.dan.pluginapi.configuration.Configuration;
import org.bukkit.Material;

public enum Config implements Configuration {

    SAVE_INTERVAL("save-interval", 300),
    SERVER_NAME("server-name", "ServerName"),
    INTEREST_RATE("interest-rate", 10D),
    EXCHANGES("exchange-rates", new ExchangeList(
            new Exchange(2, Material.DIAMOND),
            new Exchange(3, Material.GOLD_INGOT)
    ));

    @Getter
    private final String path;

    @Getter
    @Setter
    private Object value;

    Config(String path, Object value) {
        this.path = path;
        this.value = value;
    }
}
