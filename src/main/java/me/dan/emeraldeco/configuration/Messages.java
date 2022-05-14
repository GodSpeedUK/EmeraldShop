package me.dan.emeraldeco.configuration;

import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.message.Message;

import java.util.List;

public enum Messages implements Configuration, Message {

    PREFIX("prefix", "&8[&aEmeraldEco&8] &2");

    @Getter
    private final String path;

    @Getter
    @Setter
    private Object value;

    Messages(String path, Object value) {
        this.path = path;
        this.value = value;
    }


    @Override
    public String getPrefix() {
        return PREFIX.getString();
    }

    @Override
    public List<String> getStringList() {
        return Configuration.super.getStringList();
    }

    @Override
    public String getString() {
        return Configuration.super.getString();
    }
}
