package me.dan.emeraldeco.configuration;

import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;

public enum Config implements Configuration {

    SAVE_INTERVAL("save-interval", 300),
    SERVER_NAME("server-name", "ServerName"),
    EXCHANGE_RATE("exchange-rate", 1);

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
