//    KrOS POS  - Open Source Point Of Sale
//    Copyright (c) 2009-2018 uniCenta & previous Openbravo POS works
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.
package com.openbravo.format;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.openbravo.basic.BasicException;

/**
 *
 * @author JG uniCenta
 */
public abstract class Formats {

    public final static Formats NULL = new FormatsNULL();
    public final static Formats INT = new FormatsINT();
    public final static Formats STRING = new FormatsSTRING();
    public final static Formats DOUBLE = new FormatsDOUBLE();
    public final static Formats CURRENCY = new FormatsCURRENCY();
    public final static Formats PERCENT = new FormatsPERCENT();
    public final static Formats BOOLEAN = new FormatsBOOLEAN();
    public final static Formats TIMESTAMP = new FormatsTIMESTAMP();
    public final static Formats DATE = new FormatsDATE();
    public final static Formats TIME = new FormatsTIME();
    public final static Formats BYTEA = new FormatsBYTEA();
    public final static Formats HOURMIN = new FormatsHOURMIN();
    public final static Formats SIMPLEDATE = new FormatsSIMPLEDATE();

    private static NumberFormat m_integerformat = NumberFormat.getIntegerInstance();
    private static NumberFormat m_doubleformat = NumberFormat.getNumberInstance();
    private static NumberFormat m_currencyformat = NumberFormat.getCurrencyInstance();
    private static NumberFormat m_percentformat = new DecimalFormat("#,##0.##%");
    private static DateFormat m_dateformat = DateFormat.getDateInstance();
    private static DateFormat m_timeformat = DateFormat.getTimeInstance();
    private static DateFormat m_datetimeformat = DateFormat.getDateTimeInstance();

    private static final DateFormat m_hourminformat = new SimpleDateFormat("H:mm:ss");
    private static final DateFormat m_simpledate = new SimpleDateFormat("dd-MM-yyyy");

    protected Formats() {
    }

    public static int getCurrencyDecimals() {
        return m_currencyformat.getMaximumFractionDigits();
    }

    public String formatValue(Object value) {
        if (value == null) {
            return "";
        } else {
            return formatValueInt(value);
        }
    }

    public Object parseValue(String value, Object defvalue) throws BasicException {
        if (value == null || "".equals(value)) {
            return defvalue;
        } else {
            try {
                return parseValueInt(value);
            } catch (ParseException e) {
                throw new BasicException(e.getMessage(), e);
            }
        }
    }

    public Object parseValue(String value) throws BasicException {
        return parseValue(value, null);
    }

    public static void setIntegerPattern(String pattern) {
        if (pattern == null || pattern.equals("")) {
            m_integerformat = NumberFormat.getIntegerInstance();
        } else {
            m_integerformat = new DecimalFormat(pattern);
        }
    }

    public static void setDoublePattern(String pattern) {
        if (pattern == null || pattern.equals("")) {
            m_doubleformat = NumberFormat.getNumberInstance();
        } else {
            m_doubleformat = new DecimalFormat(pattern);
        }
    }

    public static void setCurrencyPattern(String pattern) {
        if (pattern == null || pattern.equals("")) {
            m_currencyformat = NumberFormat.getCurrencyInstance();
        } else {
            m_currencyformat = new DecimalFormat(pattern);
        }
    }

    public static void setPercentPattern(String pattern) {
        if (pattern == null || pattern.equals("")) {
            m_percentformat = new DecimalFormat("#,##0.##%");
        } else {
            m_percentformat = new DecimalFormat(pattern);
        }
    }

    public static void setDatePattern(String pattern) {
        if (pattern == null || pattern.equals("")) {
            m_dateformat = DateFormat.getDateInstance();
        } else {
            m_dateformat = new SimpleDateFormat(pattern);
        }
    }

    public static void setTimePattern(String pattern) {
        if (pattern == null || pattern.equals("")) {
            m_timeformat = DateFormat.getTimeInstance();
        } else {
            m_timeformat = new SimpleDateFormat(pattern);
        }
    }

    public static void setDateTimePattern(String pattern) {
        if (pattern == null || pattern.equals("")) {
            m_datetimeformat = DateFormat.getDateTimeInstance();
        } else {
            m_datetimeformat = new SimpleDateFormat(pattern);
        }
    }

    protected abstract String formatValueInt(Object value);

    protected abstract Object parseValueInt(String value) throws ParseException;

    public abstract int getAlignment();

    private static final class FormatsNULL extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return null;
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return null;
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.LEFT;
        }
    }

    private static final class FormatsINT extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return m_integerformat.format(((Number) value).longValue());
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return m_integerformat.parse(value).intValue();
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.RIGHT;
        }
    }

    private static final class FormatsSTRING extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return (String) value;
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return value;
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.LEFT;
        }
    }

    private static final class FormatsDOUBLE extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return m_doubleformat.format(DoubleUtils.fixDecimals((Number) value)); // quickfix for 3838
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return m_doubleformat.parse(value).doubleValue();
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.RIGHT;
        }
    }

    private static final class FormatsPERCENT extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return m_percentformat.format(DoubleUtils.fixDecimals((Number) value)); // quickfix for 3838
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            try {
                return m_percentformat.parse(value).doubleValue();
            } catch (ParseException e) {
                // Segunda oportunidad como numero normalito
                return m_doubleformat.parse(value).doubleValue() / 100;
            }
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.RIGHT;
        }
    }

    private static final class FormatsCURRENCY extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return m_currencyformat.format(DoubleUtils.fixDecimals((Number) value)); // quickfix for 3838
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            try {
                return m_currencyformat.parse(value).doubleValue();
            } catch (ParseException e) {
                // Segunda oportunidad como numero normalito
                return m_doubleformat.parse(value).doubleValue();
            }
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.RIGHT;
        }
    }

    private static final class FormatsBOOLEAN extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return ((Boolean) value).toString();
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return Boolean.valueOf(value);
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.CENTER;
        }
    }

    private static final class FormatsTIMESTAMP extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return m_datetimeformat.format((Date) value);
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            try {
                return m_datetimeformat.parse(value);
            } catch (ParseException e) {
                // segunda oportunidad como fecha normalita
                return m_dateformat.parse(value);
            }
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.CENTER;
        }
    }

    private static final class FormatsDATE extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return m_dateformat.format((Date) value);
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return m_dateformat.parse(value);
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.CENTER;
        }
    }

    private static final class FormatsTIME extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return m_timeformat.format((Date) value);
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return m_timeformat.parse(value);
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.CENTER;
        }
    }

    private static final class FormatsBYTEA extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            try {
                return new String((byte[]) value, "UTF-8");
            } catch (java.io.UnsupportedEncodingException eu) {
                return "";
            }
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            try {
                return value.getBytes("UTF-8");
            } catch (java.io.UnsupportedEncodingException eu) {
                return new byte[0];
            }
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.LEADING;
        }
    }

    private static final class FormatsHOURMIN extends Formats {

        @Override
        protected String formatValueInt(Object value) {
            return m_hourminformat.format(value);
        }

        @Override
        protected Date parseValueInt(String value) throws ParseException {
            return m_hourminformat.parse(value);
        }

        @Override
        public int getAlignment() {

            return javax.swing.SwingConstants.CENTER;

        }

    }

    private static final class FormatsSIMPLEDATE extends Formats {

        @Override
        protected String formatValueInt(Object value) {

            return m_simpledate.format(value);
        }

        @Override
        protected Date parseValueInt(String value) throws ParseException {

            return m_simpledate.parse(value);
        }

        @Override
        public int getAlignment() {

            return javax.swing.SwingConstants.CENTER;
        }

    }

    private static final class FormatsRESOURCE extends Formats {

        private ResourceBundle m_rb;
        private String m_sPrefix;

        public FormatsRESOURCE(ResourceBundle rb, String sPrefix) {
            m_rb = rb;
            m_sPrefix = sPrefix;
        }

        @Override
        protected String formatValueInt(Object value) {
            try {
                return m_rb.getString(m_sPrefix + (String) value);
            } catch (MissingResourceException e) {
                return (String) value;
            }
        }

        @Override
        protected Object parseValueInt(String value) throws ParseException {
            return value;
        }

        @Override
        public int getAlignment() {
            return javax.swing.SwingConstants.LEFT;
        }
    }
}
