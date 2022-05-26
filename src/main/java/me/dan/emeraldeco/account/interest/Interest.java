package me.dan.emeraldeco.account.interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.dan.pluginapi.configuration.Serializable;
import me.dan.pluginapi.file.YamlFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class Interest extends Serializable {

    private final DayOfWeek dayOfWeek;
    private final int hour;
    private final double rate;


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("day", dayOfWeek.name());
        map.put("hour", hour);
        map.put("rate", rate);
        return map;
    }

    public static Interest deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(c.getString(path + ".day").toUpperCase());
        double rate = c.getDouble(path + ".rate");
        int hour = c.getInt(path + ".hour");
        return new Interest(dayOfWeek, hour, rate);
    }

}
