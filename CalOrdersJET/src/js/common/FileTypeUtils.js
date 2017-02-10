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
 * The FileTypeUtils object provides a common utility method which can be
 * used to determine the ImageTypeCd.CODE value for a given file MIME type.
 * 
 * @author Royce Owens
 * 
 * @returns {FileTypeUtils}
 */
'use strict';
function FileTypeUtils()
{

}

FileTypeUtils.prototype = {

    /**
     * Given a service name, return the fully
     * qualified service URL.
     * 
     * @param {type} key a service name
     * @returns {String} a fully qualified URL
     */
    determineTypeCode: function (theType)
    {
        var result = null;

        if (theType !== null)
        {
            if (theType === "image/jpeg")
            {
                result = "JPEG";
            } else if (theType === "image/png")
            {
                result = "PNG";
            } else if(theType === 'application/pdf')
            {
                result = "PDF";
            }
            else {
                result = "unknown";
            }
        }

        return result;
    }
};
