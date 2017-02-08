/*
 * The MIT License
 *
 * Copyright 2017 OnCore Consulting LLC, All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.oncore.calorders.core.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The FormatHelper class provides common utility methods for formatting and
 * comparing various data types and patterns, utility methods for dumping stack
 * traces, and other useful methods.
 *
 * @author OnCore
 *
 */
public final class FormatHelper {

    /**
     * The <code>formatPhoneNumber</code> method takes as input a string that it
     * expects to be numeric and places a period after every third character no
     * matter what the length of the String.
     *
     * @param phone a String that could be a phone number
     *
     * @return a String with a period inserted after every third character
     */
    public static String formatPhoneNumber(final String phone) {
        String value = phone;
        @SuppressWarnings("UnusedAssignment")
        StringBuilder builder = null;

        if (StringUtils.isNotBlank(value)) {
            value = value.replace(".", "");
            value = value.replace("-", "");
            value = value.replace("(", "");
            value = value.replace(")", "");

            if (value.length() >= 10) {
                builder = new StringBuilder();
                builder.append(value.substring(0, 3)).append(".");
                builder.append(value.substring(3, 6)).append(".");
                builder.append(value.substring(6, value.length()));
                value = builder.toString();
            } else if (value.length() == 7) {
                builder = new StringBuilder();
                builder.append(value.substring(0, 3)).append(".");
                builder.append(value.substring(3, value.length()));
                value = builder.toString();
            } else {

                builder = new StringBuilder();
                char[] chars = value.toCharArray();
                int index = 0;

                for (int i = 0; i < value.length(); i++) {
                    if (index == 2) {
                        builder.append(chars[i]).append(".");
                        index = 0;
                    } else {
                        builder.append(chars[i]);
                        index++;
                    }

                }

                value = builder.toString();

                if (value.endsWith(".")) {
                    value = value.substring(0, value.length() - 1);
                }
            }
        }

        return value;
    }

    /**
     * <pre>
     * The <code>formatSocialSecurityNumber</code> method takes as
     * input an ssn or itin and returns the number in masked format.
     *
     * Example:  123456789 returns XXX-XX-6789
     * </pre>
     *
     * @param ssnitin a String that could be an ssn or itin
     * @param isMasked if true apply masking, otherwise no masking will be
     * provided
     *
     * @return null if the input is null, empty string if the input is empty
     * string, value if the input is less than 9 digits, or the masked ssn/itin
     * if the passed value is a valid ssn/itin
     */
    public static String formatSocialSecurityNumber(final String ssnitin, final Boolean isMasked) {
        String value = ssnitin;

        if (StringUtils.isNotBlank(value)) {
            value = value.trim();

            if (value.length() == 9) {
                if (isMasked) {
                    value = "XXX-XX-" + value.substring(5, value.length());
                } else {
                    value = value.substring(0, 3) + "-" + value.substring(3, 5) + "-" + value.substring(5, value.length());
                }
            }
        }
        return value;
    }

    /**
     * This method returns the stack trace of an exception
     *
     * @param ex The exception
     * @return The stack trace in string
     */
    public static String getStackTrace(Exception ex) {
        StringBuilder trace = new StringBuilder();
        trace.append(ex.getClass().getCanonicalName());
        trace.append("\n\t");

        trace.append(ex.getMessage());
        trace.append("\n");

        for (StackTraceElement stackTrace : ex.getStackTrace()) {
            trace.append(stackTrace.toString());
            trace.append("\n\t");
        }

        return trace.toString();
    }

    /**
     * This method returns the stack trace of an exception
     *
     * @param throwable The exception
     * @return The stack trace in string
     */
    public static String getStackTrace(Throwable throwable) {
        StringBuilder trace = new StringBuilder();
        trace.append(throwable.getClass().getCanonicalName());
        trace.append("\n\t");

        trace.append(throwable.getMessage());
        trace.append("\n");

        for (StackTraceElement stackTrace : throwable.getStackTrace()) {
            trace.append(stackTrace.toString());
            trace.append("\n\t");
        }

        return trace.toString();
    }

    public static String getStringOrValueNotSet(String str) {
        return str == null ? VALUE_NOT_SET : str;
    }

    public static String getStringValueOrValueNotSet(Object obj) {
        return obj == null ? VALUE_NOT_SET : obj.toString();
    }

    /**
     * The <code>getValueOrEmptyString</code> method checks the value for null
     * or empty and returns and empty string if that is detected otherwise it
     * returns the value.
     *
     * @param value a String value to check
     *
     * @return the value or empty String
     */
    public static String getValueOrEmptyString(String value) {
        if (StringUtils.isBlank(value)) {
            return StringUtils.EMPTY;
        } else {
            return value;
        }
    }

    /**
     * The <code>isNameValid</code> method checks if the CharSequence contains
     * only unicode letters, spaces, apostrophes, and reverse apostrophes
     *
     * <pre>
     * PageUtilities.isNameValid(null) = false
     * PageUtilities.isNameValid("") = false
     * PageUtilities.isNameValid(" ") = false
     * PageUtilities.isNameValid("abc") = true
     * PageUtilities.isNameValid("ab c") = true
     * PageUtilities.isNameValid("ab2c") = false
     * PageUtilities.isNameValid("ab-c") = false
     * PageUtilities.isNameValid("9999") = false
     * PageUtilities.isNameValid("For instance, this is bla") = false
     * PageUtilities.isNameValid("John Doe") = true
     * PageUtilities.isNameValid("John O'Brien") = true
     * PageUtilities.isNameValid("John O`Brien") = true
     * PageUtilities.isNameValid("John") = true
     * PageUtilities.isNameValid("Smith") = true
     * PageUtilities.isNameValid("John O@Brien") = false
     * </pre>
     *
     * @author OnCore Consulting LLC
     *
     * @param cs the CharSequence to check, may be null
     *
     * @return true if only unicode letters, spaces, apostrophes, and reverse
     * apostrophes, false otherwise
     */
    @SuppressWarnings("empty-statement")
    public static Boolean isNameValid(final CharSequence cs) {
        if (StringUtils.isBlank(String.valueOf(cs))) {
            return Boolean.FALSE;
        }

        final int size = cs.length();

        for (int i = 0; i < size; i++) {
            if ('\'' == cs.charAt(i) || '`' == cs.charAt(i) || cs.charAt(i) == ' ' || (Character.isLetter(cs.charAt(i)) == true)) {
                ; // do nothing
            } else {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

    //Pattern.compile("^([a-zA-Z0-9]+[ ]{0,1}[.,'&-]{0,1}[ ]{0,1})+[^ ]$");
    /**
     * Method used to validate Business Names. If field is blank, the logic will
     * return value will be dictated by if the value should be treated as
     * required or not. If the field is not blank, then the regex pattern
     * {@link #BUSINESS_NAME_PATTERN} will be used to validate the field.
     *
     * <ul>
     * <li>All letters (uppercase and lower case)</li>
     * <li>All digits</li>
     * <li>period(.), comma(,), apostrophe('), ampersand(&), and hyphen(-)</li>
     * <li>Spaces can appear in between letters and symbols</li>
     * <li>A single symbol can appear only between letters, spaces, or at the
     * end</li>
     * <li>Space cannot appear at the end</li>
     * </ul>
     *
     * @param cs The character sequence to be tested against
     * @param required Whether this sequence is required or not
     * @return True if sequence is valid or not. False otherwise.
     */
    public static Boolean isBusinessNameValid(final CharSequence cs, boolean required) {
        if (StringUtils.isBlank(String.valueOf(cs))) {
            return !required;
        }

        Matcher match = BUSINESS_NAME_PATTERN.matcher(cs);
        return match.matches();
    }

    public static Boolean isNullOrNotSelected(Integer value) {
        if (value == null || value == -1) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public static Boolean isValidDecimal(String value) {
        Boolean result = Boolean.FALSE;

        try {
            value = StringUtils.replace(value, ",", "");
            @SuppressWarnings("unused")
            BigDecimal bd = new BigDecimal(value);
            result = Boolean.TRUE;
        } catch (Exception ex) {
            Logger.warn(LOG, getStackTrace(ex));
        }

        return result;
    }

    /**
     * Checks if the CharSequence is a valid email address format
     *
     * <pre>
     * PageUtilities.isValidEmailAddress(null) = false
     * PageUtilities.isValidEmailAddress("") = false
     * PageUtilities.isValidEmailAddress(" ") = false
     * PageUtilities.isValidEmailAddress("abc") = false
     * PageUtilities.isValidEmailAddress("ab@") = false
     * PageUtilities.isValidEmailAddress("ab@c") = false
     * PageUtilities.isValidEmailAddress("ab@xyz.com") = true
     * </pre>
     *
     * @author OnCore Consulting LLC
     *
     * @param address an email address to validate
     *
     *
     * @return true if the value is a valid address, false otherwise
     */
    public static Boolean isValidEmailAddress(final String address) {
        Boolean result = Boolean.FALSE;

        if (StringUtils.isNotBlank(address)) {

            try {
                result = Pattern.matches("[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}", address);
            } catch (PatternSyntaxException px) {
                Logger.warn(LOG, "Invalid email pattern detected");
            } catch (Exception ex) {
                Logger.warn(LOG, "Fatal error occurred during email validation check");
            }
        }

        return result;

    }

    /**
     * Checks if the CharSequence is a valid web URL
     *
     * <pre>
     * PageUtilities.isValidURL(null) = false
     * PageUtilities.isValidURL("") = false
     * PageUtilities.isValidURL(" ") = false
     * PageUtilities.isValidURL("abc") = false
     * PageUtilities.isValidURL("www.yahoo.com") = true
     * </pre>
     *
     * @author OnCore Consulting LLC
     *
     * @param url a URL to validate
     *
     *
     * @return true if the value is a valid address, false otherwise
     */
    public static Boolean isValidURL(final String url) {
        Boolean result = Boolean.FALSE;

        if (StringUtils.isNotBlank(url)) {
            try {
                result = Pattern.matches("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?", url);
            } catch (PatternSyntaxException px) {
                Logger.warn(LOG, "Invalid URL pattern detected");
            } catch (Exception ex) {
                Logger.warn(LOG, "Fatal error occurred during email validation check");
            }
        }

        return result;
    }

    public static Double getGpsDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        Double distance;

        if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
            //throw new IllegalArgumentException("Coordinates passed can not be null");
            distance = 1000d;
        } else {

            double radius = 6371 * 0.621371;
            double dLat = (lat2 - lat1) * Math.PI / 180;
            double dLon = (lon2 - lon1) * Math.PI / 180;

            double arc = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180)
                    * Math.sin(dLon / 2) * Math.sin(dLon / 2);

            double curve = 2 * Math.atan2(Math.sqrt(arc), Math.sqrt(1 - arc));

            distance = radius * curve;

        }

        return distance;
    }

    /**
     * The <code>retrieveBaseUrl</code> method returns the base URL for any ETF
     * application URL.
     *
     * @author OnCore Consulting LLC
     *
     * @param requestUrl a fully qualified URL
     *
     * @return the base portion of the request URL
     */
    public static String retrieveBaseUrl(String requestUrl) {
        String url = StringUtils.EMPTY;

        if (StringUtils.isNotBlank(requestUrl)) {
            String[] values = requestUrl.split("/");

            if (values != null && values.length > 0) {

                for (int i = 0; i < 4; i++) {
                    url += values[i] + "/";
                }

                url = url.substring(0, url.length() - 1);
            }
        }

        return url;
    }

    /**
     * The <code>retrievePageName</code> method will examine a valid URL and
     * return just the page name portion.
     *
     * @author OnCore Consulting LLC
     *
     * @param url a fully qualified URL
     *
     * @return the page name from the URL
     */
    public static String retrievePageName(String url) {
        Logger.debug(LOG, "retrievePageName for " + url);

        @SuppressWarnings("UnusedAssignment")
        String[] values = null;
        String value = url;

        if (StringUtils.isNotBlank(url) && StringUtils.contains(url, "/")) {
            values = url.split("/");

            value = values[values.length - 1];
        }

        if (StringUtils.contains(value, "?faces-redirect")) {
            values = value.split("/?faces");
            value = values[0];
            value = value.substring(0, value.length() - 1);
        }

        if (StringUtils.contains(value, ".xhtml")) {
            values = value.split("\\.");
            value = values[0];
        }

        Logger.debug(LOG, "retrievePageName value is " + value);

        return value;
    }

    /**
     * This method checks the strings in input fields are same or not. The null
     * value and empty string "" are treated as same.
     *
     * @param obj1 The first string to compare
     * @param obj2 The second string to compare
     * @return true if the two strings are same, e.g. no change.
     */
    public static Boolean sameAfterTrimToNull(String obj1, String obj2) {
        return StringUtils.equals(StringUtils.trimToNull(obj1), StringUtils.trimToNull(obj2));
    }

    /**
     * This method checks the chars in input fields are same or not. The
     * '\uffff' value and blankspace ' ' are treated as same.
     *
     * @param char1 char to compare
     * @param char2 char to compare
     * @return true if the two chars are same, e.g. no change.
     */
    public static Boolean sameCharValues(char char1, char char2) {
        if (char1 == '\uffff') {
            char1 = ' ';
        }

        if (char2 == '\uffff') {
            char2 = ' ';
        }

        return sameAfterTrimToNull(char1 + "", char2 + "");
    }

    /**
     * This method checks the selected values in a drop down list are same or
     * not. The unknown value in drop down "-1" is treated as null. The
     * parameters can be any type. This method should be used to compare drop
     * down values only, not values on input fields.
     *
     * @param obj1 The first value to compare
     * @param obj2 The second value to compare
     * @return true if the two values are same, e.g. no change.
     */
    public static Boolean sameSelectedValues(Object obj1, Object obj2) {
        String value1 = null;
        String value2 = null;

        if (obj1 != null) {
            value1 = obj1.toString();
        }

        if (obj2 != null) {
            value2 = obj2.toString();
        }

        if (StringUtils.equals(value1, UNKNOWN_VALUE + "")) {
            value1 = null;
        }

        if (StringUtils.equals(value2, UNKNOWN_VALUE + "")) {
            value2 = null;
        }

        return StringUtils.equals(StringUtils.trimToNull(value1), StringUtils.trimToNull(value2));
    }

    /**
     * This method checks the two values are same or not. Both null values are
     * treated same. Time is ignored.
     *
     * @param obj1 The first object to compare
     * @param obj2 The second object to compare
     * @return true if the two objects have same values, e.g. no change.
     */
    public static Boolean sameValueOrBothNull(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }

        if (obj1 instanceof String && obj2 == null) {
            return StringUtils.isBlank((String) obj1);
        } else if (obj1 == null && obj2 instanceof String) {
            return StringUtils.isBlank((String) obj2);
        } else if (obj1 != null && obj2 != null) {
            return StringUtils.equals(obj1.toString(), obj2.toString());
        }

        return false;
    }

    /**
     * The <code>stripCodesfromPhoneNumber</code> method removes the country and
     * area codes from a phone number in order to be matched in MDM
     *
     * @author Glenn Ryan
     *
     * @param phoneNumber the phone number string to be stripped
     * @param isForeign true if phone number is a foreign phone number
     * @return a string with the country and area codes stripped
     */
    public static String stripCodesfromPhoneNumber(String phoneNumber, Boolean isForeign) {
        String result = null;

        if (phoneNumber != null) {
            String[] phoneNumberElements = phoneNumber.split("[-\\(\\)\\. ]");

            if (isForeign) {
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < phoneNumberElements.length; i++) {
                    builder.append(phoneNumberElements[i]);
                }
                result = builder.toString();
            } else {
                result = phoneNumberElements[phoneNumberElements.length - 2] + phoneNumberElements[phoneNumberElements.length - 1];
            }
        }

        return result;
    }

    /**
     * The <code>stripCurrencyChars</code> method removes all $ and , characters
     * from a String
     *
     * @author OnCore Consulting LLC
     *
     * @param currencyValue a currency value
     *
     * @return a new String with any $ or , characters removed
     */
    public static String stripCurrencyChars(String currencyValue) {
        String result = StringUtils.replace(currencyValue, "$", "");
        result = StringUtils.replace(result, ",", "");

        return result;
    }

    /**
     * The <code>stripCharacters</code> method strips out characters that would
     * not be conducive to the database such as single quotes and double quotes
     *
     * <pre>
     * PageUtilities.stripCharacters(null) = Empty String
     * PageUtilities.stripCharacters("") = Empty String
     * PageUtilities.stripCharacters("It doesn't matter") = It doesnt matter
     * PageUtilities.stripCharacters("\"She said it wasn't so\"") = She said it wasnt so
     * </pre>
     *
     * @author OnCore Consulting LLC
     *
     *
     * @param str a String to modify
     *
     * @return a new String with any invalid characters removed
     */
    public static String stripCharacters(String str) {

        String result = StringUtils.EMPTY;

        if (StringUtils.isNotBlank(str)) {

            result = str;

            result = result.replaceAll("'", "");
            result = result.replaceAll("\"", "");

        }

        return result;

    }

    /**
     * <code>convertNullToNullString</code> method checks if the string value
     * passed is NULL or empty string and returns a string with the literal
     * value of "NULL".
     *
     * @author OnCore Consulting LLC
     *
     * @param str a string value to examine
     *
     * @return "NULL" if the string passed is null or empty string, otherwise
     * return the passed string value
     */
    public static String convertNullToNullString(String str) {
        String result = str;

        if (StringUtils.isBlank(str)) {
            result = "NULL";
        }

        return result;
    }

    /**
     * Use this method to remove dollar signs and comma separators from dollar
     * amount fields and dashes and dots from phone numbers and zip codes
     *
     * @param value A dollar amount such as $392,302
     * @return value without the $ and , characters such as 392302
     */
    public static String stripNonNumerics(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        } else {
            value = StringUtils.replace(value, "$", "");
            value = StringUtils.replace(value, ",", "");
            value = StringUtils.replace(value, "-", "");
            value = StringUtils.replace(value, ".", "");

            if (StringUtils.contains(value, "(")) {
                value = StringUtils.replace(value, "(", "");
                value = StringUtils.replace(value, ")", "");
                value = "-" + value;
            }

            return value;
        }
    }

    /**
     * Determines if the passed parameter is a valid US tax ID.
     *
     * @param taxID specifies the tax ID to validate
     * @return true if it is, false otherwise
     */
    public static Boolean isValidTaxID(String taxID) {

        if (taxID == null || taxID.isEmpty()) {
            return false;
        }

        if (taxID.length() != 9) {
            return false;
        }

        try {

            Long.parseLong(taxID);

        } catch (NumberFormatException e) {

            return false;

        }

        return true;
    }

    /**
     * This method trims the formatting strings from a phone number. It is null
     * safe and the result can be null.
     *
     * @param phoneNumber The phone number to trim
     * @return The phone number after being trimmed
     */
    public static String trimPhoneNumberToNull(String phoneNumber) {
        return StringUtils.replaceEach(StringUtils.trimToNull(phoneNumber), PHONE_NUMBER_FORMATTING_ARRAY, PHONE_NUMBER_REPLACE_ARRAY);
    }

    /**
     * Method to compare Strings evaluating values such that null == empty
     * strings
     *
     * @param str1 First String
     * @param str2 Second String
     * @return True if both are equal evaluating nulls == empty strings
     */
    public static boolean compareStringsNullEqualsBlank(String str1, String str2) {
        return StringUtils.equals(StringUtils.trimToEmpty(str1), StringUtils.trimToEmpty(str2));
    }

    /**
     * The <code>isEmpty</code> method is a helper for determining if a list is
     * empty or null and is null safe to boot!
     *
     * @param aList a list of objects to check
     *
     * @return true if null or empty, false otherwise.
     */
    public static Boolean isEmpty(List<?> aList) {
        if (aList == null || aList.isEmpty()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * The <code>isNotEmpty</code> method is a helper for determining if a list
     * is empty or null and is null safe to boot!
     *
     * @param aList a list of objects to check
     *
     * @return true not null or not empty, false otherwise.
     */
    public static Boolean isNotEmpty(List<?> aList) {
        return !isEmpty(aList);
    }

    public static final int COMMENT_MAX_LENGTH = 350;

    public static final Log LOG = LogFactory
            .getLog(FormatHelper.class);

    public static final int UNKNOWN_VALUE = -1;

    public static final String VALUE_NOT_SET = "EMPTY";

    static private final String[] PHONE_NUMBER_FORMATTING_ARRAY = new String[]{"-", "(", ")", ".", " "};

    static private final String[] PHONE_NUMBER_REPLACE_ARRAY = new String[]{"", "", "", "", ""};

    private static final Pattern BUSINESS_NAME_PATTERN = Pattern.compile("^([a-zA-Z0-9 .,'&-]+)$");

}
