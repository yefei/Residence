package net.t00thpick1.residence.protection.yaml;

import java.util.HashMap;
import java.util.Map;

import net.t00thpick1.residence.api.Group;
import net.t00thpick1.residence.api.flags.Flag;
import net.t00thpick1.residence.api.flags.FlagManager;
import net.t00thpick1.residence.utils.parser.Equation;
import net.t00thpick1.residence.utils.parser.EquationParser;

import org.bukkit.configuration.ConfigurationSection;

public class YAMLGroup implements Group {
    private final String permission;
    private final Equation costEquation;
    private final int maxHeight;
    private final int maxY;
    private final int minY;
    private final int minHeight;
    private final int maxWidth;
    private final int minWidth;
    private final int maxLength;
    private final int minLength;
    private final int maxResidences;
    private final Map<Flag, Boolean> defaultAreaFlags;
    private final Map<Flag, Boolean> defaultOwnerFlags;
    private final String defaultEnterMessage;
    private final String defaultLeaveMessage;
    private final int weight;
    
    public YAMLGroup(ConfigurationSection section) {
        permission = "residence.groups." + section.getName();
        costEquation = EquationParser.parse(section.getString("CostEquation", "XSize * YSize * ZSize"));
        maxHeight = section.getInt("MaxHeight", 255);
        maxY = section.getInt("MaxY", 255);
        minY = section.getInt("MinY", 0);
        minHeight = section.getInt("MinHeight", 1);
        maxWidth = section.getInt("MaxWidth", 500);
        minWidth = section.getInt("MinWidth", 1);
        minLength = section.getInt("MinLength", 1);
        maxLength = section.getInt("MaxLength", 500);
        maxResidences = section.getInt("MaxResidences", 10);
        defaultAreaFlags = new HashMap<Flag, Boolean>();
        ConfigurationSection flags = section.getConfigurationSection("AreaFlags");
        for (String key : flags.getKeys(false)) {
            Flag flag = FlagManager.getFlag(key);
            if (flag != null) {
                defaultAreaFlags.put(flag, flags.getBoolean(key));
            }
        }
        defaultOwnerFlags = new HashMap<Flag, Boolean>();
        flags = section.getConfigurationSection("OwnerFlags");
        for (String key : flags.getKeys(false)) {
            Flag flag = FlagManager.getFlag(key);
            if (flag != null) {
                defaultOwnerFlags.put(flag, flags.getBoolean(key));
            }
        }
        defaultEnterMessage = section.getString("DefaultEnterMessage", "You have entered %area%");
        defaultLeaveMessage = section.getString("DefaultLeaveMessage", "You have left %area%");
        weight = section.getInt("Weight", 1);
    }

    public String getPermission() {
        return permission;
    }

    public Equation getCostEquation() {
        return costEquation;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getDefaultLeaveMessage() {
        return defaultLeaveMessage;
    }

    public String getDefaultEnterMessage() {
        return defaultEnterMessage;
    }

    public int getMaxResidences() {
        return maxResidences;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMinY() {
        return minY;
    }

    @Override
    public Map<Flag, Boolean> getDefaultAreaFlags() {
        return defaultAreaFlags;
    }

    @Override
    public Map<Flag, Boolean> getDefaultOwnerFlags() {
        return defaultOwnerFlags;
    }
}
