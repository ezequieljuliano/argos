/*
 * Copyright 2013 Ezequiel Juliano Müller - ezequieljuliano@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.ezequieljuliano.argos.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    public static String dateToString(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

    public static String dateTimeToString(Date dateTime) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(dateTime);
    }

    public static String timeToString(Date time) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(time);
    }

    public static Date stringToDate(String date) {
        try {
            TimeZone tz = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            sdf.setTimeZone(tz);
            return sdf.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static Date stringToDateTime(String dateTime) {
        try {
            TimeZone tz = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(tz);
            return sdf.parse(dateTime);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static Date stringToTime(String time) {
        try {
            TimeZone tz = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(tz);
            return sdf.parse(time);
        } catch (ParseException ex) {
            return null;
        }
    }

}
