/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */
package fr.rqndomhax.narutouhc.role.solos;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.inventories.role.ISasuke;
import fr.rqndomhax.narutouhc.managers.MGamePublicRoles;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.tasks.role.solos.TSasuke;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Sasuke extends RoleInfo {

    boolean hasKilled = false;
    public int usagesLeft = 0;
    public final List<GamePlayer> list = new ArrayList<>();
    private TSasuke task = null;

    public Team selectedTeam = null;

    public Sasuke(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.SASUKE);
        hasClaimed = true;
    }

    @Override
    public void onPlayerDeath(GamePlayer gamePlayer) {
        if (gamePlayer.role == null)
            return;

        if (gamePlayer.role.getRole().equals(Roles.DANZO) && task != null)
            task.cancel();
    }

    @Override
    public void onInit(Setup setup) {
        task = new TSasuke(setup, this);
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 0, false, false));
        if (getRole().getTeam().equals(Team.SASUKE))
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 0, false, false));
    }

    @Override
    public void onCommand(Setup setup) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        if (usagesLeft == 0) {
            player.sendMessage(Messages.ROLE_NO_MORE_USES);
            return;
        }

        Set<GamePlayer> players = new HashSet<>();

        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers()) {
            if (gamePlayer.equals(getGamePlayer()))
                continue;

            if (gamePlayer.isDead)
                continue;

            if (gamePlayer.role == null)
                return;

            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null)
                continue;

            players.add(gamePlayer);
        }

        int size = 0;
        int inventory_size = 1;

        for (GamePlayer ignored : players) {
            if (size == 9) {
                inventory_size++;
                size = 0;
            }
            size++;
        }

        player.openInventory(new ISasuke(player, players, inventory_size*9, this).getInventory());
    }

    @Override
    public void onClaim() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        if (hasClaimed) {
            player.sendMessage(Messages.ROLE_NO_ITEMS);
            return;
        }

        long nSpace = Arrays.stream(player.getInventory().getContents()).filter(Objects::isNull).count();
        if (nSpace < 1) {
            player.sendMessage(Messages.ROLE_ITEMS_NEED_SPACE.replace("%n%", "1").replace("objets", "objet"));
            return;
        }

        player.getInventory().addItem(new ItemBuilder(Material.ENCHANTED_BOOK).addStoredEnchant(Enchantment.DAMAGE_ALL, 3).addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addStoredEnchant(Enchantment.ARROW_DAMAGE, 2).toItemStack());
        hasClaimed = true;
        player.sendMessage(Messages.ROLE_ITEMS_OBTAINED);

    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);

        if (player == null) return;

        if (selectedTeam == null) {
            int random = new Random().nextInt(101);

            if (random >= 90)
                selectedTeam = Team.SASUKE;
            if (random >= 70 && random < 90)
                selectedTeam = Team.OROCHIMARU;
            if (random >= 35 && random < 70)
                selectedTeam = Team.AKATSUKI;
            if (random < 35)
                selectedTeam = Team.SHINOBI;
            getRole().setTeam(selectedTeam);
        }

        player.sendMessage(Messages.SEPARATORS);
        switch (selectedTeam) {
            case SASUKE:
                player.sendMessage(ChatColor.BLUE + "Vous êtes Sasuke Solo.");
                player.sendMessage(ChatColor.BLUE + "Votre but est de gagner seul.");
                player.sendMessage(ChatColor.BLUE + "Si vous tuez Danzo, vous connaîtrez tous les rôles des joueurs de la partie grâce à la commande /na sasuke.");
                player.sendMessage(ChatColor.BLUE + "De plus vous obtenez un livre \"sharpness 3\", \"protection 3\" et \"power 2\".");
                player.sendMessage(ChatColor.BLUE + "Vous avez également l'effet strength 1 permanent.");
                player.sendMessage(ChatColor.BLUE + "20 minutes après l'annonce des rôles, 3 pseudos aléatoires vous seront envoyés, l'un des trois sera celui de Danzo.");
                break;
            case AKATSUKI:
            case SHINOBI:
            case OROCHIMARU:
                if (selectedTeam.equals(Team.AKATSUKI))
                    player.sendMessage(ChatColor.BLUE + "Vous êtes Sasuke de l'Akatsuki.");
                else if (selectedTeam.equals(Team.SHINOBI))
                    player.sendMessage(ChatColor.BLUE + "Vous êtes Sasuke de l'alliance Shinobi.");
                else
                    player.sendMessage(ChatColor.BLUE + "Vous êtes Sasuke du camp Orochimaru.");
                player.sendMessage(ChatColor.BLUE + "Si vous tuez Danzo, vous aurez accès à inventaire avec la tête de tous les joueurs restants.");
                player.sendMessage(ChatColor.BLUE + "Vous pourrez connaître le rôle de 2 personnes, grâce à la commande /na sasuke.");
                player.sendMessage(ChatColor.BLUE + "Vous avez également l'effet strength 1 permanent.");
                player.sendMessage(ChatColor.BLUE + "20 minutes après l'annonce des rôles, 3 pseudos aléatoires vous seront envoyés, l'un des trois sera celui de Danzo.");
                break;
            default:
                break;
        }
        showList();
    }

    @Override
    public void onTeam() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        if (getRole().getTeam().equals(Team.OROCHIMARU))
            Messages.showList(player, MGamePublicRoles.orochimarus);
        else if (getRole().getTeam().equals(Team.AKATSUKI))
            Messages.showList(player, MGamePublicRoles.akatsukis);
    }

    public void showList() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;
        Messages.showList(player, list);
    }

    @Override
    public void onKill(GamePlayer killed) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);

        if (player == null)
            return;
        if (killed.role == null)
            return;

        if (hasKilled)
            return;

        if (!killed.role.getRole().equals(Roles.DANZO))
            return;

        if (getRole().getTeam().equals(Team.SASUKE)) {
            hasKilled = true;
            hasClaimed = false;
            player.sendMessage(Messages.ROLE_CLAIM);
            usagesLeft = 999;
            player.sendMessage(Messages.ROLE_CAPACITY);
        }

        else if (getRole().getTeam().equals(Team.AKATSUKI) || getRole().getTeam().equals(Team.OROCHIMARU) || getRole().getTeam().equals(Team.SHINOBI)) {
            hasKilled = true;
            usagesLeft = 2;
            player.sendMessage(Messages.ROLE_CAPACITY);
        }

    }
}
