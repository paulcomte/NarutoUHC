/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.config;

import fr.rqndomhax.narutouhc.managers.MRules;

public class HostConfig {

    private final MRules rules;
    private String name;
    private ConfigLogos logo;
    private final String filePath;

    public HostConfig(MRules rules, String name, ConfigLogos logo, String filePath) {
        this.rules = rules;
        this.name = name;
        this.logo = logo;
        this.filePath = filePath;
    }

    public MRules getRules() {
        return rules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConfigLogos getLogo() {
        return logo;
    }

    public void setLogo(ConfigLogos logo) {
        this.logo = logo;
    }

    public String getFilePath() {
        return filePath;
    }
}
