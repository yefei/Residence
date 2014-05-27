package net.t00thpick1.residence.api;

import java.util.Map;

import net.t00thpick1.residence.api.flags.Flag;
import net.t00thpick1.residence.utils.parser.Equation;

public interface Group {
    public String getPermission();

    public Equation getCostEquation();

    public int getMaxHeight();

    public int getMaxY();

    public int getMinHeight();

    public int getMinY();

    public int getMaxWidth();

    public int getMaxLength();

    public int getMinWidth();

    public int getMinLength();

    public int getMaxResidences();

    public Map<Flag, Boolean> getDefaultAreaFlags();

    public Map<Flag, Boolean> getDefaultOwnerFlags();

    public String getDefaultEnterMessage();

    public String getDefaultLeaveMessage();

    public Integer getWeight();
}
