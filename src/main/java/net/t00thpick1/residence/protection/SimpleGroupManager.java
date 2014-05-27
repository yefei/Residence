package net.t00thpick1.residence.protection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.t00thpick1.residence.api.Group;
import net.t00thpick1.residence.protection.yaml.YAMLGroup;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SimpleGroupManager {
    private FileConfiguration config;
    private Group defaul;
    private Map<String, Group> groups;

    public SimpleGroupManager(FileConfiguration groupConfig) {
        config = groupConfig;
        groups = new HashMap<String, Group>();
        for (String group : config.getKeys(false)) {
            Group g = new YAMLGroup(config.getConfigurationSection(group));
            if (group.equalsIgnoreCase("Default")) {
                defaul = g;
            } else {
                groups.put(g.getPermission(), g);
            }
        }
    }

    public Group getGroup(Player player) {
        if (player == null) {
            return defaul;
        }
        for (Entry<String, Group> perm : groups.entrySet()) {
            if (player.hasPermission(perm.getKey())) {
                return perm.getValue();
            }
        }
        return defaul;
    }
}
