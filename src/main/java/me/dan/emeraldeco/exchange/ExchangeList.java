package me.dan.emeraldeco.exchange;

import lombok.Getter;
import me.dan.pluginapi.configuration.Serializable;
import me.dan.pluginapi.file.YamlFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

@Getter
public class ExchangeList extends Serializable {

    private final List<Exchange> exchangeList;

    public ExchangeList(Exchange... exchanges) {
        this.exchangeList = new ArrayList<>();
        exchangeList.addAll(Arrays.asList(exchanges));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < exchangeList.size(); i++) {
            Exchange exchange = exchangeList.get(i);
            Map<String, Object> serialized = exchange.serialize();
            for (String key : serialized.keySet()) {
                map.put(i + "." + key, serialized.get(key));
            }
        }
        return map;
    }

    public static ExchangeList deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        List<Exchange> exchanges = new ArrayList<>();
        for (String key : c.getConfigurationSection(path).getKeys(false)) {
            Exchange exchange = Exchange.deserialize(yamlFile, path + "." + key);
            exchanges.add(exchange);
        }

        return new ExchangeList(exchanges.toArray(new Exchange[0]));
    }

}
