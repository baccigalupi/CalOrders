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

/**
 * The Dates object defines common utility methods for date comparisons.
 * 
 * @author Royce Owens
 * 
 * @returns {Dates}
 */
'use strict';
function Dates()
{

}

Dates.prototype = {

    /**
     * Is date1 before date2.  This method removes the 
     * time compoonent and only compares the date.
     * 
     * @param {type} date1 a date to compare
     * @param {type} date2 a date to compare
     * @returns true if date1 is before date2, false othewise
     */
    before: function (date1, date2)
    {

        var month = date1.getUTCMonth();
        var day = date1.getUTCDate();
        var year = date1.getUTCFullYear();

        date1 = new Date(year, month, day);

        month = date2.getUTCMonth();
        day = date2.getUTCDate();
        year = date2.getUTCFullYear();


        date2 = new Date(year, month, day);

        if (date1 < date2)
        {
            return true;
        } else
        {
            return false;
        }

    },

    /**
     * Is date1 after date2.  This method removes the 
     * time compoonent and only compares the date.
     * 
     * @param {type} date1 a date to compare
     * @param {type} date2 a date to compare
     * @returns true if date1 is before date2, false othewise
     */
    after: function (date1, date2)
    {

        var month = date1.getUTCMonth();
        var day = date1.getUTCDate();
        var year = date1.getUTCFullYear();

        date1 = new Date(year, month, day);

        month = date2.getUTCMonth();
        day = date2.getUTCDate();
        year = date2.getUTCFullYear();


        date2 = new Date(year, month, day);

        if (date1 > date2)
        {
            return true;
        } else
        {
            return false;
        }

    }

};
