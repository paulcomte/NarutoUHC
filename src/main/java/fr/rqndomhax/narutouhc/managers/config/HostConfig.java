/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.config;

import fr.rqndomhax.narutouhc.game.GameRules;

import java.util.UUID;

public class HostConfig {

    private final GameRules rules;
    private String name;
    private ConfigLogos logo;
    private final String filePath;

    public HostConfig(GameRules rules, String name, ConfigLogos logo, String filePath) {
        this.rules = rules;
        this.name = name;
        this.logo = logo;
        this.filePath = filePath;
    }

    public GameRules getRules() {
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

    public HostConfig copy() {
        ConfigLogos newLogo = logo;

        if (newLogo.equals(ConfigLogos.DEFAULT))
            newLogo = ConfigLogos.APPLE;

        return new HostConfig(rules, name + " copy", newLogo, "configs/" + UUID.randomUUID().toString().substring(0, 8) + ".cfg");
    }
}
