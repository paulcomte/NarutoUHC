/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.config;

import fr.rqndomhax.narutouhc.infos.BorderCenter;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.managers.rules.DayCycle;
import fr.rqndomhax.narutouhc.managers.rules.Drops;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public class HostConfigBuilder {

    private final MRules mRules = new MRules();
    private String name = "Default";
    private ConfigLogos logo = ConfigLogos.DEFAULT;

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
        mRules.mBorder.defaultSize = Math.max(defaultSize, 50);
        return this;
    }

    public HostConfigBuilder setBorderFinalSize(int finalSize) {
        mRules.mBorder.defaultSize = Math.max(finalSize, 50);
        return this;
    }

    public HostConfigBuilder setBorderSpeed(double speed) {
        if (speed >= 20)
            mRules.mBorder.speed = 20;
        else
            mRules.mBorder.speed = Math.max(speed, 0.5d);
        return this;
    }

    public HostConfigBuilder setBorderDamages(double damages) {
        if (damages >= 20)
            mRules.mBorder.damages = 20;
        else
            mRules.mBorder.damages = Math.max(damages, 0.5d);
        return this;
    }

    public HostConfigBuilder setBorderCenter(BorderCenter center) {
        if (center == null)
            mRules.mBorder.center = BorderCenter.KONOHA;
        else
            mRules.mBorder.center = center;
        return this;
    }

    public HostConfigBuilder setBorderTimeBeforeResize(int timeBeforeResize) {
        mRules.mBorder.timeBeforeResize = Math.max(timeBeforeResize, 60);
        return this;
    }

    public HostConfigBuilder showDeathMessages(boolean b) {
        mRules.showDeathMessages = b;
        return this;
    }

    public HostConfigBuilder showRolesOnDeaths(boolean b) {
        mRules.showRoleOnDeath = b;
        return this;
    }

    public HostConfigBuilder allowSpectators(boolean b) {
        mRules.allowSpectators = b;
        return this;
    }

    public HostConfigBuilder allowSpectatorsAfterBorder(boolean b) {
        mRules.spectatorsAfterBorder = b;
        return this;
    }

    public HostConfigBuilder activateWhitelist(boolean b) {
        mRules.hasWhitelist = b;
        return this;
    }

    public HostConfigBuilder allowAdminsSetPlayersRole(boolean b) {
        mRules.adminRoles = b;
        return this;
    }

    public HostConfigBuilder activateNaturalRegeneration(boolean b) {
        mRules.adminRoles = b;
        return this;
    }

    public HostConfigBuilder allowWhispers(boolean b) {
        mRules.allowWhispers = b;
        return this;
    }

    public HostConfigBuilder allowChat(boolean b) {
        mRules.allowChat = b;
        return this;
    }

    public HostConfigBuilder setStartInventory(ItemStack[] items) {
        mRules.startInventory = items;
        return this;
    }

    public HostConfigBuilder setDeathInventory(ItemStack[] items) {
        mRules.deathInventory = items;
        return this;
    }

    public HostConfigBuilder setActivatedRoles(Set<Roles> activatedRoles) {
        mRules.activatedRoles = activatedRoles;
        return this;
    }

    public HostConfigBuilder setActivatedScenarios(Set<Scenarios> activatedScenarios) {
        mRules.activatedScenarios = activatedScenarios;
        return this;
    }

    public HostConfigBuilder setDrops(Set<Drops> drops) {
        mRules.drops = drops;
        return this;
    }

    public HostConfigBuilder setDayCycle(DayCycle dayCycle) {
        mRules.dayCycle = dayCycle;
        return this;
    }

    public HostConfigBuilder setGameTitle(String gameTitle) {
        mRules.gameTitle = gameTitle;
        return this;
    }

    public HostConfigBuilder setStartDuration(int startDuration) {
        mRules.startDuration = Math.max(startDuration, 1);
        return this;
    }

    public HostConfigBuilder setInvincibilityDuration(int invincibilityDuration) {
        mRules.invincibilityDuration = Math.max(invincibilityDuration, 1);
        return this;
    }

    public HostConfigBuilder setPreparationDuration(int preparationDuration) {
        mRules.preparationDuration = Math.max(preparationDuration, 60);
        return this;
    }

    public HostConfigBuilder setRoleAnnounce(int roleAnnounce) {
        mRules.rolesAnnounce = Math.max(roleAnnounce, 60);
        return this;
    }

    public HostConfigBuilder setTeleportingDuration(int teleportingDuration) {
        mRules.teleportingDuration = Math.max(teleportingDuration, 1);
        return this;
    }

    public HostConfigBuilder setNarutoTeleportingDuration(int narutoTeleportingDuration) {
        mRules.narutoTeleportingDuration = Math.max(narutoTeleportingDuration, 1);
        return this;
    }

    public HostConfigBuilder setTimeBeforeDeath(int timeBeforeDeath) {
        mRules.timeBeforeDeath = Math.max(timeBeforeDeath, 1);
        return this;
    }

    public HostConfig build() {
        return new HostConfig(mRules, name, logo, "configs/" + UUID.randomUUID().toString().substring(0, 8) + ".cfg");
    }

}
