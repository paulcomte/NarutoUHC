/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils;

import org.bukkit.potion.PotionEffectType;

public class TranslateEffect {

    public static String translate(PotionEffectType potionEffectType) {
        if (potionEffectType.equals(PotionEffectType.SPEED)) {
            return "Vitesse";
        } else if (potionEffectType.equals(PotionEffectType.SLOW)) {
            return "Lenteur";
        } else if (potionEffectType.equals(PotionEffectType.FAST_DIGGING)) {
            return "Minage accéléré";
        } else if (potionEffectType.equals(PotionEffectType.SLOW_DIGGING)) {
            return "Minage ralenti";
        } else if (potionEffectType.equals(PotionEffectType.INCREASE_DAMAGE)) {
            return "Force";
        } else if (potionEffectType.equals(PotionEffectType.HEAL)) {
            return "Soin";
        } else if (potionEffectType.equals(PotionEffectType.HARM)) {
            return "Dégâts";
        } else if (potionEffectType.equals(PotionEffectType.JUMP)) {
            return "Saut amélioré";
        } else if (potionEffectType.equals(PotionEffectType.CONFUSION)) {
            return "Nausée";
        } else if (potionEffectType.equals(PotionEffectType.REGENERATION)) {
            return "Régénération";
        } else if (potionEffectType.equals(PotionEffectType.DAMAGE_RESISTANCE)) {
            return "Résistance aux dégâts";
        } else if (potionEffectType.equals(PotionEffectType.FIRE_RESISTANCE)) {
            return "Résistance au feu";
        } else if (potionEffectType.equals(PotionEffectType.WATER_BREATHING)) {
            return "Respiration";
        } else if (potionEffectType.equals(PotionEffectType.INVISIBILITY)) {
            return "Invisibilité";
        } else if (potionEffectType.equals(PotionEffectType.BLINDNESS)) {
            return "Aveuglement";
        } else if (potionEffectType.equals(PotionEffectType.NIGHT_VISION)) {
            return "Vision nocturne";
        } else if (potionEffectType.equals(PotionEffectType.HUNGER)) {
            return "Faim";
        } else if (potionEffectType.equals(PotionEffectType.WEAKNESS)) {
            return "Faiblesse";
        } else if (potionEffectType.equals(PotionEffectType.POISON)) {
            return "Poison";
        } else if (potionEffectType.equals(PotionEffectType.WITHER)) {
            return "Wither";
        } else if (potionEffectType.equals(PotionEffectType.HEALTH_BOOST)) {
            return "Boost de coeur";
        } else if (potionEffectType.equals(PotionEffectType.ABSORPTION)) {
            return "Absorption";
        } else if (potionEffectType.equals(PotionEffectType.SATURATION)) {
            return "Saturation";
        }
        return null;
    }

}
