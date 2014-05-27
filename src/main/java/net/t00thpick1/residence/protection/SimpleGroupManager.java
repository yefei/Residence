package net.t00thpick1.residence.protection;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.t00thpick1.residence.api.Group;
import net.t00thpick1.residence.protection.yaml.YAMLGroup;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SimpleGroupManager {
    private FileConfiguration config;
    private Group defaul;
    private List<Group> groups;

    public SimpleGroupManager(FileConfiguration groupConfig) {
        config = groupConfig;
        groups = new LinkedList<Group>();
        for (String group : config.getKeys(false)) {
            Group g = new YAMLGroup(config.getConfigurationSection(group));
            if (group.equalsIgnoreCase("Default")) {
                defaul = g;
            } else {
                groups.add(g);
            }
        }
        Collections.sort(groups, new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
                return o1.getWeight().compare(o2.getWeight());
            }});
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
