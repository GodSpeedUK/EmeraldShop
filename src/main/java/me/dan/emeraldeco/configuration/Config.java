package me.dan.emeraldeco.configuration;

import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;

public enum Config implements Configuration {

    SAVE_INTERVAL("save-interval", 300);

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
