/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.config;

import fr.rqndomhax.narutouhc.infos.BorderCenter;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.managers.rules.DayCycle;
import fr.rqndomhax.narutouhc.managers.rules.Drops;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public class HostConfigBuilder {

    private final GameRules gameRules = new GameRules();
    private String name = "Default";
    private ConfigLogos logo = ConfigLogos.XP;

    public HostConfigBuilder setConfigName(String name) {
        if (name != null)
            this.name = name;
        return this;
    }

    public HostConfigBuilder setConfigLogo(ConfigLogos logo) {
        if (logo != null)
            this.logo = logo;
        return this;
    }

    public HostConfigBuilder setBorderDefaultSize(int defaultSize) {
        gameRules.gameBorder.defaultSize = Math.max(defaultSize, 50);
        return this;
    }

    public HostConfigBuilder setBorderFinalSize(int finalSize) {
        gameRules.gameBorder.defaultSize = Math.max(finalSize, 50);
        return this;
    }

    public HostConfigBuilder setBorderSpeed(double speed) {
        if (speed >= 20)
            gameRules.gameBorder.speed = 20;
        else
            gameRules.gameBorder.speed = Math.max(speed, 0.5d);
        return this;
    }

    public HostConfigBuilder setBorderDamages(double damages) {
        if (damages >= 20)
            gameRules.gameBorder.damages = 20;
        else
            gameRules.gameBorder.damages = Math.max(damages, 0.5d);
        return this;
    }

    public HostConfigBuilder setBorderCenter(BorderCenter center) {
        if (center == null)
            gameRules.gameBorder.center = BorderCenter.KONOHA;
        else
            gameRules.gameBorder.center = center;
        return this;
    }

    public HostConfigBuilder setBorderTimeBeforeResize(int timeBeforeResize) {
        gameRules.gameBorder.timeBeforeResize = Math.max(timeBeforeResize, 60);
        return this;
    }

    public HostConfigBuilder showDeathMessages(boolean b) {
        gameRules.showDeathMessages = b;
        return this;
    }

    public HostConfigBuilder showRolesOnDeaths(boolean b) {
        gameRules.showRoleOnDeath = b;
        return this;
    }

    public HostConfigBuilder allowSpectators(boolean b) {
        gameRules.allowSpectators = b;
        return this;
    }

    public HostConfigBuilder allowSpectatorsAfterBorder(boolean b) {
        gameRules.spectatorsAfterBorder = b;
        return this;
    }

    public HostConfigBuilder activateWhitelist(boolean b) {
        gameRules.hasWhitelist = b;
        return this;
    }

    public HostConfigBuilder allowAdminsSetPlayersRole(boolean b) {
        gameRules.adminRoles = b;
        return this;
    }

    public HostConfigBuilder activateNaturalRegeneration(boolean b) {
        gameRules.adminRoles = b;
        return this;
    }

    public HostConfigBuilder allowWhispers(boolean b) {
        gameRules.allowWhispers = b;
        return this;
    }

    public HostConfigBuilder allowChat(boolean b) {
        gameRules.allowChat = b;
        return this;
    }

    public HostConfigBuilder setStartInventory(ItemStack[] items) {
        gameRules.startInventory = items;
        return this;
    }

    public HostConfigBuilder setDeathInventory(ItemStack[] items) {
        gameRules.deathInventory = items;
        return this;
    }

    public HostConfigBuilder setActivatedRoles(Set<Roles> activatedRoles) {
        gameRules.activatedRoles = activatedRoles;
        return this;
    }

    public HostConfigBuilder setActivatedScenarios(Set<Scenarios> activatedScenarios) {
        gameRules.activatedScenarios = activatedScenarios;
        return this;
    }

    public HostConfigBuilder setDrops(Set<Drops> drops) {
        gameRules.drops = drops;
        return this;
    }

    public HostConfigBuilder setDayCycle(DayCycle dayCycle) {
        gameRules.dayCycle = dayCycle;
        return this;
    }

    public HostConfigBuilder setGameTitle(String gameTitle) {
        gameRules.gameTitle = gameTitle;
        return this;
    }

    public HostConfigBuilder setStartDuration(int startDuration) {
        gameRules.startDuration = Math.max(startDuration, 1);
        return this;
    }

    public HostConfigBuilder setInvincibilityDuration(int invincibilityDuration) {
        gameRules.invincibilityDuration = Math.max(invincibilityDuration, 1);
        return this;
    }

    public HostConfigBuilder setPreparationDuration(int preparationDuration) {
        gameRules.preparationDuration = Math.max(preparationDuration, 60);
        return this;
    }

    public HostConfigBuilder setRoleAnnounce(int roleAnnounce) {
        gameRules.rolesAnnounce = Math.max(roleAnnounce, 60);
        return this;
    }

    public HostConfigBuilder setTeleportingDuration(int teleportingDuration) {
        gameRules.teleportingDuration = Math.max(teleportingDuration, 1);
        return this;
    }

    public HostConfigBuilder setNarutoTeleportingDuration(int narutoTeleportingDuration) {
        gameRules.narutoTeleportingDuration = Math.max(narutoTeleportingDuration, 1);
        return this;
    }

    public HostConfigBuilder setTimeBeforeDeath(int timeBeforeDeath) {
        gameRules.timeBeforeDeath = Math.max(timeBeforeDeath, 1);
        return this;
    }

    public HostConfig build() {
        return new HostConfig(gameRules, name, logo, "configs/" + UUID.randomUUID().toString().substring(0, 8) + ".cfg");
    }

}
