/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils.tools;

import java.util.HashMap;

public enum TimeUnit {

    SECOND("Seconds", "sec", 1),
    MINUTE("Minuts", "min", 60),
    HOUR("Hours", "h", 60 * 60),
    DAY("Days", "d", 60 * 60 * 24),
    MONTH("Months", "m", 60 * 60 * 24 * 30),
    YEAR("Years", "y", 60 * 60 * 24 * 365);

    private String name;
    private String shortcut;
    private long toSecond;

    private static HashMap<String, TimeUnit> ID_SHORTCUT = new HashMap<>();

    private TimeUnit(String name, String shortcut, long toSecond) {
        this.name = name;
        this.shortcut = shortcut;
        this.toSecond = toSecond;
    }

    static {
        for(TimeUnit units : values()){
            ID_SHORTCUT.put(units.shortcut, units);
        }
    }

    /**
     * Get timeunit of schortcut
     * @param shortcut
     * @return TimeUnit
     */
    public static TimeUnit getFromShortcut(String shortcut){
        return ID_SHORTCUT.get(shortcut);
    }

    public String getName(){
        return name;
    }

    public String getShortcut(){
        return shortcut;
    }

    public long getToSecond() {
        return toSecond;
    }

}