package me.dan.emeraldeco.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.dan.pluginapi.configuration.Serializable;
import me.dan.pluginapi.file.YamlFile;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Exchange extends Serializable {

    private final int rate;
    private final Material material;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("rate", rate);
        map.put("material", material.name());
        return map;
    }

    public static Exchange deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        int rate = c.getInt(path + ".rate");
        Material material = Material.getMaterial(c.getString(path + ".material").toUpperCase());
        if (material == null) {
            return null;
        }

        return new Exchange(rate, material);
    }
    


}
