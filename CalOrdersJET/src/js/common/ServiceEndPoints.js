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


        if (key === "findNavBarPrivilegesByPartyId")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.party/findNavBarPrivilegesByPartyId";
        } else if (key === "findNavMenuPrivilegesByPartyId")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.party/findNavMenuPrivilegesByPartyId";
        } else if (key === "findAllPrivilegesByPartyId")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.party/findAllPrivilegesByPartyId";
        } else if (key === "findPartyByUserIdAndPassword")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.party/findPartyByUserIdAndPassword";
        } else if (key === "findActiveProductsByProductType")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.product/findActiveProductsByProductType";
        } else if (key === "findProductsByProductType")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.product/findProductsByProductType";
        } else if (key === "createOrder")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.orderHistory/createOrder";
        } else if (key === "findAllVendors")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.vendor";
        } else if (key === "findAllCategories")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.prdcategorycd";
        } else if (key === "doesProductNameExist")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.product/doesProductNameExist";
        } else if (key === "createProduct")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.product/createProduct";
        } else if (key === "updateProduct")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.product/updateProduct";
        } else if (key === "findAllUnitCodes")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.prdunitcd";
        } else if (key === "findProductById")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.product";
        } else if (key === "findActiveProductsByProductTypeAndVendor")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.product/findActiveProductsByProductTypeAndVendor";
        } else if (key === "fetchOrdersByQuarter")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.orderHistory/fetchOrdersByQuarter";
        } else if (key === "fetchOrderStatusSummary")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.orderHistory/fetchOrderStatusSummary";
        } else if (key === "findAllOrderHistoryByPartyUid")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.orderHistory/findAllOrderHistoryByPartyUid";
        } else if (key === "findAllOrderHistory")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.orderHistory/findAllOrderHistory";
        } else if (key === "findOrderDetailById")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.orderHistory/findOrderDetailById";
        } else if (key === "cancelOrderHistory")
        {
            return calOrdersHost + "/CalOrdersRest/webresources/com.oncore.calorders.rest.orderHistory/cancelOrderDetail";
        } else
        {
            return "UNDEFINED";
        }



    }
};
