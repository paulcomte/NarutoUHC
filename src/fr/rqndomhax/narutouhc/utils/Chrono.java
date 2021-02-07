/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils;

public class Chrono {

    public static Integer[] timeToHMS(long tempsS) {

        int h = (int) (tempsS / 3600);
        int m = (int) ((tempsS % 3600) / 60);
        int s = (int) (tempsS % 60);
        Integer[] i = {h,m,s};
        return i;
    }

    public static String timeToString(long tempsS) {

        Integer[] i = timeToHMS(tempsS);

        int h = i[0];
        int m = i[1];
        int s = i[2];

        StringBuilder r= new StringBuilder();

        if(h>0) r.append(h + "h ");
        if(m>0) r.append(m + "min ");
        if(s>0) r.append(s + " s");
        if(h<=0 && m<=0 && s<=0) {
            r = new StringBuilder();
            r.append("0 s");
        }

        return r.toString();
    }

    public static String timeToDigitalString(long tempsS) {
        Integer[] i = timeToHMS(tempsS);

        int h = i[0];
        int m = i[1];
        int s = i[2];

        StringBuilder r= new StringBuilder();

        if(h > 0){
            r.append(h + ":");
        }

        if(tempsS < 0)return "00h00";

        r.append((m>9? (m) : ("0" + m)) +":");
        r.append((s>9? (s) : ("0" + s)));

        return r.toString();
    }

    public static int getCycleDurationTime(long value){
        Double b = value/60.0;
        Integer result = b.intValue();
        return result;
    }

}
