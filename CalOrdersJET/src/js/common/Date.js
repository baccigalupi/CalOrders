/* 
 * The OnCore Consulting LLC License
 *
 * Copyright 2016 OnCore Consulting LLC, All Rights Reserved.
 *
 * Permission IS NOT GRANTED to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, unless the following conditions are met:
 *
 * Written permission is obtained from  
 * OnCore Consulting LLC and the above copyright 
 * notice and this permission notice shall be included in
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
define(function () {

// Utility function to append a 0 to single-digit numbers
    Date.LZ = function (x) {
        return(x < 0 || x > 9 ? "" : "0") + x;
    };
// Full month names. Change this for local month names
    Date.monthNames = new Array('January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December');
// Month abbreviations. Change this for local month names
    Date.monthAbbreviations = new Array('Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec');
// Full day names. Change this for local month names
    Date.dayNames = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');
// Day abbreviations. Change this for local month names
    Date.dayAbbreviations = new Array('Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat');
// Used for parsing ambiguous dates like 1/2/2000 - default to preferring 'American' format meaning Jan 2.
// Set to false to prefer 'European' format meaning Feb 1
    Date.preferAmericanFormat = true;

// If the getFullYear() method is not defined, create it
    if (!Date.prototype.getFullYear) {
        Date.prototype.getFullYear = function () {
            var yy = this.getYear();
            return (yy < 1900 ? yy + 1900 : yy);
        };
    }

// Check if a date string is valid
    Date.isValid = function (val, format) {
        return (Date.parseString(val, format) !== null);
    };

// Check if a date object is before another date object
    Date.prototype.isBefore = function (date2) {
        if (date2 === null) {
            return false;
        }
        return (this.getTime() < date2.getTime());
    };

// Check if a date object is after another date object
    Date.prototype.isAfter = function (date2) {
        if (date2 === null) {
            return false;
        }
        return (this.getTime() > date2.getTime());
    };

// Check if two date objects have equal dates and times
    Date.prototype.equals = function (date2) {
        if (date2 === null) {
            return false;
        }
        return (this.getTime() === date2.getTime());
    };

// Check if two date objects have equal dates, disregarding times
    Date.prototype.equalsIgnoreTime = function (date2) {
        if (date2 === null) {
            return false;
        }
        var d1 = new Date(this.getTime()).clearTime();
        var d2 = new Date(date2.getTime()).clearTime();
        return (d1.getTime() === d2.getTime());
    };

// Format a date into a string using a given format string
    Date.prototype.format = function (format) {
        format = format + "";
        var result = "";
        var i_format = 0;
        var c = "";
        var token = "";
        var y = this.getYear() + "";
        var M = this.getMonth() + 1;
        var d = this.getDate();
        var E = this.getDay();
        var H = this.getHours();
        var m = this.getMinutes();
        var s = this.getSeconds();
        var yyyy, yy, MMM, MM, dd, hh, h, mm, ss, ampm, HH, H, KK, K, kk, k;
        // Convert real date parts into formatted versions
        var value = new Object();
        if (y.length < 4) {
            y = "" + (+y + 1900);
        }
        value["y"] = "" + y;
        value["yyyy"] = y;
        value["yy"] = y.substring(2, 4);
        value["M"] = M;
        value["MM"] = Date.LZ(M);
        value["MMM"] = Date.monthNames[M - 1];
        value["NNN"] = Date.monthAbbreviations[M - 1];
        value["d"] = d;
        value["dd"] = Date.LZ(d);
        value["E"] = Date.dayAbbreviations[E];
        value["EE"] = Date.dayNames[E];
        value["H"] = H;
        value["HH"] = Date.LZ(H);
        if (H === 0) {
            value["h"] = 12;
        } else if (H > 12) {
            value["h"] = H - 12;
        } else {
            value["h"] = H;
        }
        value["hh"] = Date.LZ(value["h"]);
        value["K"] = value["h"] - 1;
        value["k"] = value["H"] + 1;
        value["KK"] = Date.LZ(value["K"]);
        value["kk"] = Date.LZ(value["k"]);
        if (H > 11) {
            value["a"] = "PM";
        } else {
            value["a"] = "AM";
        }
        value["m"] = m;
        value["mm"] = Date.LZ(m);
        value["s"] = s;
        value["ss"] = Date.LZ(s);
        while (i_format < format.length) {
            c = format.charAt(i_format);
            token = "";
            while ((format.charAt(i_format) === c) && (i_format < format.length)) {
                token += format.charAt(i_format++);
            }
            if (typeof (value[token]) !== "undefined") {
                result = result + value[token];
            } else {
                result = result + token;
            }
        }
        return result;
    };

// Get the full name of the day for a date
    Date.prototype.getDayName = function () {
        return Date.dayNames[this.getDay()];
    };

// Get the abbreviation of the day for a date
    Date.prototype.getDayAbbreviation = function () {
        return Date.dayAbbreviations[this.getDay()];
    };

// Get the full name of the month for a date
    Date.prototype.getMonthName = function () {
        return Date.monthNames[this.getMonth()];
    };

// Get the abbreviation of the month for a date
    Date.prototype.getMonthAbbreviation = function () {
        return Date.monthAbbreviations[this.getMonth()];
    };

// Clear all time information in a date object
    Date.prototype.clearTime = function () {
        this.setHours(0);
        this.setMinutes(0);
        this.setSeconds(0);
        this.setMilliseconds(0);
        return this;
    };

// Add an amount of time to a date. Negative numbers can be passed to subtract time.
    Date.prototype.add = function (interval, number) {
        if (typeof (interval) === "undefined" || interval === null || typeof (number) === "undefined" || number === null) {
            return this;
        }
        number = +number;
        if (interval === 'y') { // year
            this.setFullYear(this.getFullYear() + number);
        } else if (interval === 'M') { // Month
            this.setMonth(this.getMonth() + number);
        } else if (interval === 'd') { // Day
            this.setDate(this.getDate() + number);
        } else if (interval === 'w') { // Weekday
            var step = (number > 0) ? 1 : -1;
            while (number !== 0) {
                this.add('d', step);
                while (this.getDay() === 0 || this.getDay() === 6) {
                    this.add('d', step);
                }
                number -= step;
            }
        } else if (interval === 'h') { // Hour
            this.setHours(this.getHours() + number);
        } else if (interval === 'm') { // Minute
            this.setMinutes(this.getMinutes() + number);
        } else if (interval === 's') { // Second
            this.setSeconds(this.getSeconds() + number);
        }
        return this;
    };
    return   Date;
});
