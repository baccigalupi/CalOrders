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
 * The ServiceEndPoints object defines all Service End points used
 * by the application.  This file should be altered on deployment
 * by the CM scripts to alter the endpoints based on target environment.
 * 
 * @author Royce Owens
 * 
 * @returns {ServiceEndPoints}
 */
'use strict';
function ServiceEndPoints()
{

}

ServiceEndPoints.prototype = {

    /**
     * Given a service name, return the fully
     * qualified service URL.
     * 
     * @param {type} key a service name
     * @returns {String} a fully qualified URL
     */
    getEndPoint: function (key)
    {
        // ====== CM TOUCH POINT =================
        var calOrdersHost = "http://localhost:8080";
        // =======================================


        if (key === "findNavBarPrivilegesByEmployeeId")
        {
            return calOrdersHost + "/CalOrdersServices/webresources/com.oncore.calorders.services.users/findNavBarPrivilegesByUserId";
        } else if (key === "findNavMenuPrivilegesByEmployeeId")
        {
            return calOrdersHost + "/CalOrdersServices/webresources/com.oncore.calorders.services.users/findNavMenuPrivilegesByUserId";
        }
        else
        {
            return "UNDEFINED";
        }
    }
};