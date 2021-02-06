/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils.tools;

public class DateManager {

    private final long after;
    public DateManager(long after){
        this.after = after;
    }

    public String getTimeLeft(){
        long now = System.currentTimeMillis();
        long timeLeft = (after - now) / 1000;
        int years = 0;
        int months = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        while(timeLeft >= TimeUnit.YEAR.getToSecond()){
            years++;
            timeLeft -= TimeUnit.YEAR.getToSecond();
        }

        while(timeLeft >= TimeUnit.MONTH.getToSecond()){
            months++;
            timeLeft -= TimeUnit.MONTH.getToSecond();
        }

        while(timeLeft >= TimeUnit.DAY.getToSecond()){
            days++;
            timeLeft -= TimeUnit.DAY.getToSecond();
        }

        while(timeLeft >= TimeUnit.HOUR.getToSecond()){
            hours++;
            timeLeft -= TimeUnit.HOUR.getToSecond();
        }

        while(timeLeft >= TimeUnit.MINUTE.getToSecond()){
            minutes++;
            timeLeft -= TimeUnit.MINUTE.getToSecond();
        }

        while(timeLeft >= TimeUnit.SECOND.getToSecond()){
            seconds++;
            timeLeft -= TimeUnit.SECOND.getToSecond();
        }

        if (years != 0) {
            return years + (years < 1 ? " année" : " années") + (months < 1 ? "" : " et " + months + " mois");
        }

        if (months != 0) {
            return months + (months < 1 ? " mois" : " mois") + (days < 1 ? "" : " et " + days + " jours");
        }

        if (days != 0) {
            return days + (days < 1 ? " jour" : " jours") + (hours < 1 ? "" : " et " + hours + " heures");
        }

        if (hours != 0) {
            return hours + (hours < 1 ? " heure" : " heures") + (minutes < 1 ? "" : " et " + minutes + " minutes");
        }

        if (minutes == 0) {
            return seconds + (seconds < 1 ? " seconde" : " secondes");
        }

        if (minutes >= 1) {
            return minutes + " minutes" + (seconds < 1 ? "" : " et " + seconds + " secondes");
        }

        return null;
    }
}
