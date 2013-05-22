package br.com.ezequieljuliano.argos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Data {

    public Date fromString(String dataString) {
        String[] splitData = dataString.split("/");

        int year = Integer.parseInt(splitData[2]);
        int month = Integer.parseInt(splitData[1]);
        int day = Integer.parseInt(splitData[0]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);

        return calendar.getTime();
    }

    public Date firstTimeOfMonth(int mes, int ano) {
        GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
        calendar.set(Calendar.YEAR, ano);
        calendar.set(Calendar.MONTH, mes - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public Date lastTimeOfMonth(int mes, int ano) {
        GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
        calendar.set(Calendar.YEAR, ano);
        calendar.set(Calendar.MONTH, mes - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date firstTimeOfDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public Date firstDayOfCurrentMonth() {
        Date today = new Date();
        Date firstDay = firstTimeOfMonth(extractMonthFrom(today), extractYearFrom(today));
        return firstDay;
    }

    public Date lastDayOfCurrentMonth() {
        Date today = new Date();
        Date lastDay = lastTimeOfMonth(extractMonthFrom(today), extractYearFrom(today));
        return lastDay;
    }

    public Date lastTimeOfDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static String monthName(Integer month) {
        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
            default:
                return "S/N";
        }
//        Calendar calendar = Calendar.getInstance(Locale.getDefault());
//        calendar.set(Calendar.MONTH, month - 1);
//        return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

    }

    public static Date decreaseMonthsFrom(Date date, Integer monthsToDecrease) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(firstTimeOfDay(date));
        calendar.add(Calendar.MONTH, -monthsToDecrease);
        return calendar.getTime();
    }
    
    public static Date decreaseDaysFrom(Date date, Integer daysToDecrease) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(firstTimeOfDay(date));
        calendar.add(Calendar.DAY_OF_MONTH, -daysToDecrease);
        return calendar.getTime();
    }

    public Date decreaseMonthsFromToday(Integer monthsToDecrease) {
        return decreaseMonthsFrom(new Date(), monthsToDecrease);
    }

    public static Integer extractMonthFrom(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static Integer extractYearFrom(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static boolean isTheSameMonth(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        calendar.setTime(date1);
        Integer mes1 = calendar.get(Calendar.MONTH);
        Integer ano1 = calendar.get(Calendar.YEAR);

        calendar.setTime(date2);
        Integer mes2 = calendar.get(Calendar.MONTH);
        Integer ano2 = calendar.get(Calendar.YEAR);

        return (ano1.equals(ano2)) && (mes1.equals(mes2));
    }

    public class InvalidDateException extends Exception {

        public InvalidDateException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static String dateToString(Date data) {
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    public static String timeToString(Date time) {
        return new SimpleDateFormat("HH:mm:ss").format(time);
    }

    public static String timestampToString(Date timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp);
    }

    public static Date stringToTimeStamp(String s) {
        try {
            TimeZone tz = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(tz);
            return sdf.parse(s);
        } catch (ParseException ex) {
            return null;
        }
    }
}
