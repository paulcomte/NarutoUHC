/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.role.shinobi;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Hinata extends RoleInfo {

    public int commandUses = 2;
    public boolean isNarutoDead = false;

    public Hinata(MPlayer mPlayer) {
        super(mPlayer, Roles.HINATA);
    }

    @Override
    public void onPlayerDeath(MPlayer mPlayer) {
        if (mPlayer.role == null) return;
        if (!mPlayer.role.getRole().equals(Roles.NARUTO)) {
            isNarutoDead = true;
            giveEffects();
        }
    }

    @Override
    public void onPlayerJoin() {
        giveEffects();
    }

    @Override
    public void giveEffects() {
        if (!isNarutoDead) return;

        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 0, false, false));
    }

    @Override
    public void onCommand() {
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        if (commandUses == 0) {
            player.sendMessage(Messages.ROLE_NO_MORE_USES);
            return;
        }

        commandUses--;
    }
}
