/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils.tools;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.List;

public enum Banners {
    NEGATIVE_BLUE,
    NEGATIVE_CYAN,
    NEGATIVE_LIGHT,
    POSITIVE_YELLOW,
    POSITIVE_LIME,
    POSITIVE_GREEN;

    Banners() {
    }

    public ItemStack getPattern() {
        switch (this) {
            case NEGATIVE_BLUE:
                return getNegative(DyeColor.BLUE, DyeColor.PURPLE);
            case NEGATIVE_CYAN:
                return getNegative(DyeColor.CYAN);
            case NEGATIVE_LIGHT:
                return getNegative(DyeColor.LIGHT_BLUE);
            case POSITIVE_YELLOW:
                return getPositive(DyeColor.YELLOW);
            case POSITIVE_LIME:
                return getPositive(DyeColor.LIME);
            case POSITIVE_GREEN:
                return getPositive(DyeColor.GREEN);
        }
        return null;
    }

    public static Banners[] getNegatives() {
        return new Banners[]{NEGATIVE_BLUE, NEGATIVE_CYAN, NEGATIVE_LIGHT};
    }

    public static Banners[] getPositives() {
        return new Banners[]{POSITIVE_YELLOW, POSITIVE_LIME, POSITIVE_GREEN};
    }

    private ItemStack getPositive(DyeColor color) {
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        bannerMeta.setBaseColor(color);

        List<Pattern> patterns = new ArrayList<>();
        patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
        patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
        patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
        patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
        patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
        patterns.add(new Pattern(color, PatternType.BORDER));
        patterns.add(new Pattern(color, PatternType.BORDER));
        bannerMeta.setPatterns(patterns);

        bannerMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner.setItemMeta(bannerMeta);
        return banner;
    }

    private ItemStack getNegative(DyeColor baseColor, DyeColor color) {
        ItemStack banner = new ItemStack(Material.BANNER);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        bannerMeta.setBaseColor(baseColor);

        List<Pattern> patterns = new ArrayList<>();
        patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
        patterns.add(new Pattern(color, PatternType.BORDER));
        patterns.add(new Pattern(color, PatternType.BORDER));
        bannerMeta.setPatterns(patterns);

        bannerMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        banner.setItemMeta(bannerMeta);
        return banner;
    }

    private ItemStack getNegative(DyeColor color) {
        return getNegative(color, color);
    }
}
