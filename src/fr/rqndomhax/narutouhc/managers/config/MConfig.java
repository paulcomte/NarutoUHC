/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.config;

import com.google.common.collect.Sets;
import fr.rqndomhax.narutouhc.infos.BorderCenter;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.managers.rules.DayCycle;
import fr.rqndomhax.narutouhc.managers.rules.Drops;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import fr.rqndomhax.narutouhc.utils.FileManager;
import fr.rqndomhax.narutouhc.utils.tools.BukkitSerializer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class MConfig {

    public static final List<HostConfig> configurations = new ArrayList<>();
    private static FileManager manager;

    public static void init(FileManager fileManager, File dataFolder) {
        if (fileManager == null)
            return;

        File configs = new File(dataFolder.getPath(), "configs/");

        if (!configs.exists() && !configs.mkdirs())
            return;

        manager = fileManager;

        saveConfig(new HostConfig(new MRules(), "Default Configuration", ConfigLogos.DEFAULT, "configs/default.cfg"), true);

        File[] entries = dataFolder.listFiles();

        for (File entry : entries) {
            if (entry == null || !entry.exists() || !entry.isDirectory())
                continue;
            if (entry.getName().equals("configs")) {
                File[] files = entry.listFiles();
                for (File file : files) {
                    if (file == null || !file.exists() || file.isDirectory())
                        continue;
                    if (file.getName().equals("default.cfg"))
                        continue;
                    if (file.getName().endsWith(".cfg"))
                        loadConfig("configs/" + file.getName());
                }
            }
        }
    }

    public static HostConfig loadConfig(String path) {
        if (path == null || manager == null)
            return null;
        YamlConfiguration config = manager.getConfig(path).get();

        if (config == null)
            return null;
        HostConfigBuilder configBuilder = new HostConfigBuilder();
        String tmp = "";

        configBuilder.setConfigName(config.getString("configName"));
        tmp = config.getString("configLogo");
        configBuilder.setConfigLogo(tmp == null ? null : ConfigLogos.valueOf(tmp));


        configBuilder.setBorderDefaultSize(config.getInt("border.defaultSize"));
        configBuilder.setBorderFinalSize(config.getInt("border.finalSize"));
        configBuilder.setBorderSpeed(config.getDouble("border.speed"));
        configBuilder.setBorderDamages(config.getDouble("border.damages"));
        tmp = config.getString("border.center");
        configBuilder.setBorderCenter(tmp == null ? null : BorderCenter.valueOf(tmp));
        configBuilder.setBorderTimeBeforeResize(config.getInt("border.timeBeforeResize"));

        configBuilder.showDeathMessages(config.getBoolean("config.showDeathMessages"));
        configBuilder.showRolesOnDeaths(config.getBoolean("config.showRolesOnDeaths"));
        configBuilder.allowSpectators(config.getBoolean("config.allowSpectators"));
        configBuilder.allowSpectatorsAfterBorder(config.getBoolean("config.allowSpectatorsAfterBorder"));
        configBuilder.activateWhitelist(config.getBoolean("config.activateWhitelist"));
        configBuilder.allowAdminsSetPlayersRole(config.getBoolean("config.allowAdminsSetPlayersRole"));
        configBuilder.activateNaturalRegeneration(config.getBoolean("config.activateNaturalRegeneration"));
        configBuilder.allowWhispers(config.getBoolean("config.allowWhispers"));


        configBuilder.setStartInventory(BukkitSerializer.stringToItemArray(config.getString("inventories.startInventory")));
        configBuilder.setDeathInventory(BukkitSerializer.stringToItemArray(config.getString("inventories.deathInventory")));

        Set<Roles> roles = new HashSet<>();
        tmp = config.getString("activatedRoles");
        tmp = tmp.replace("[", "").replace("]", "");

        for (String o : tmp.split(", "))
            Arrays.stream(Roles.values()).filter(e -> e.name().equals(o)).findFirst().ifPresent(roles::add);

        configBuilder.setActivatedRoles(roles);

        Set<Scenarios> scenarios = new HashSet<>();
        tmp = config.getString("activatedScenarios");
        tmp = tmp.replace("[", "").replace("]", "");

        for (String o : tmp.split(", "))
            Arrays.stream(Scenarios.values()).filter(e -> e.name().equals(o)).findFirst().ifPresent(scenarios::add);

        configBuilder.setActivatedScenarios(scenarios);

        Set<Drops> drops = new HashSet<>();
        tmp = config.getString("drops");
        tmp = tmp.replace("[", "").replace("]", "");
        for (String o : tmp.split(", "))
            Arrays.stream(Drops.values()).filter(e -> e.name().equals(o)).findFirst().ifPresent(drops::add);

        configBuilder.setDrops(drops);

        tmp = config.getString("dayCycle");
        configBuilder.setDayCycle(tmp == null ? null : DayCycle.valueOf(tmp));

        configBuilder.setGameTitle(config.getString("gameTitle"));

        configBuilder.setStartDuration(config.getInt("timers.startDuration"));
        configBuilder.setInvincibilityDuration(config.getInt("timers.invincibilityDuration"));
        configBuilder.setPreparationDuration(config.getInt("timers.preparationDuration"));
        configBuilder.setRoleAnnounce(config.getInt("timers.roleAnnounce"));
        configBuilder.setTeleportingDuration(config.getInt("timers.teleportingDuration"));
        configBuilder.setNarutoTeleportingDuration(config.getInt("timers.narutoTeleportingDuration"));
        configBuilder.setTimeBeforeDeath(config.getInt("timers.timerBeforeDeath"));

        HostConfig newConfig = configBuilder.build();
        configurations.add(newConfig);
        return newConfig;
}

    public static void deleteConfig(HostConfig config) {
        if (manager == null || config == null)
            return;
        configurations.remove(config);
        manager.getConfig(config.getFilePath()).delete();
    }

    public static void saveConfig(HostConfig config, boolean save) {
        if (manager == null || config == null)
            return;
        if (!configurations.contains(config))
            configurations.add(config);

        FileManager.Config configuration = manager.getConfig(config.getFilePath());
        configuration.set("configName", config.getName());
        configuration.set("configLogo", config.getLogo().name());

        configuration.set("border.defaultSize", config.getRules().mBorder.defaultSize);
        configuration.set("border.finalSize", config.getRules().mBorder.finalSize);
        configuration.set("border.speed", config.getRules().mBorder.speed);
        configuration.set("border.damages", config.getRules().mBorder.damages);
        configuration.set("border.center", config.getRules().mBorder.center.name());
        configuration.set("border.timeBeforeResize", config.getRules().mBorder.timeBeforeResize);

        configuration.set("config.showDeathMessages", config.getRules().showDeathMessages);
        configuration.set("config.allowSpectators", config.getRules().allowSpectators);
        configuration.set("config.allowSpectatorsAfterBorder", config.getRules().spectatorsAfterBorder);
        configuration.set("config.activateWhitelist", config.getRules().hasWhitelist);
        configuration.set("config.allowAdminsSetPlayersRole", config.getRules().adminRoles);
        configuration.set("config.activateNaturalRegeneration", config.getRules().naturalRegeneration);
        configuration.set("config.allowWhispers", config.getRules().allowWhispers);
        configuration.set("config.allowChat", config.getRules().allowChat);

        configuration.set("inventories.startInventory", BukkitSerializer.inventoryToString(config.getRules().startInventory));
        configuration.set("inventories.deathInventory", BukkitSerializer.inventoryToString(config.getRules().startInventory));

        configuration.set("activatedRoles", config.getRules().activatedRoles.toString());
        configuration.set("activatedScenarios", config.getRules().activatedScenarios.toString());
        configuration.set("drops", config.getRules().drops.toString());

        configuration.set("dayCycle", config.getRules().dayCycle.name());

        configuration.set("gameTitle", config.getRules().gameTitle);

        configuration.set("timers.startDuration", config.getRules().startDuration);
        configuration.set("timers.invincibilityDuration", config.getRules().invincibilityDuration);
        configuration.set("timers.preparationDuration", config.getRules().preparationDuration);
        configuration.set("timers.roleAnnounce", config.getRules().rolesAnnounce);
        configuration.set("timers.teleportingDuration", config.getRules().teleportingDuration);
        configuration.set("timers.narutoTeleportingDuration", config.getRules().narutoTeleportingDuration);
        configuration.set("timers.timerBeforeDeath", config.getRules().timeBeforeDeath);

        if (save)
            configuration.save();
    }

}
