/* 
 * The OnCore Consulting LLC License
 *
 * Copyright 2017 OnCore Consulting LLC, All Rights Reserved.
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

var SecurityUtils = new function ()
{


    this.serviceEndPoints = new ServiceEndPoints();
    this.serviceURL;
    this.parsePrivilege = function (response) {

        if (response.pageIdentifier === undefined) {
            var pageIdentifier = 'Unknown';
        } else {
            pageIdentifier = response.pageIdentifier;
        }
        if (response.pageDescription === undefined) {
            var pageDescription = 'Unknown';
        } else {
            pageDescription = response.pageDescription;
        }
        if (response.componentIdentifier === undefined) {
            var componentIdentifier = 'Unknown';
        } else {
            componentIdentifier = response.componentIdentifier;
        }
        if (response.canRead === undefined) {
            var canRead = Boolean.false;
        } else {
            canRead = response.canRead;
        }
        if (response.canWrite === undefined) {
            var canWrite = Boolean.false;
        } else {
            canWrite = response.canWrite;
        }

        var result = {'pageIdentifier': pageIdentifier,
            'pageDescription': pageDescription,
            'componentIdentifier': componentIdentifier,
            'canRead': canRead,
            'canWrite': canWrite};
        return result;
    };

    this.isAuthenticated = function () {
        if (typeof (Storage) !== "undefined" && sessionStorage.authenticated === "true") {
            return true;
        } else {
            return false;
        }
    };

    this.hasPrivilege = function (privilege) {
        var hasPrivilege = false;
        var userPrivilege = this.getPrivileges();
        for (userPrivilege in this.getPrivileges()) {
            if (userPrivilege === privilege) {
                hasPrivilege = true;
            }
        }
        return hasPrivilege;
    };

    this.getNavBarItems = function (koTableBar) {
        // if not authenticated add welcome and about
        if (this.isAuthenticated() === false) {
            koTableBar().add({name: 'Welcome', id: 'welcome',
                iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-home-icon-24'}, {idAttribute: 'id'});
            
            koTableBar().add({name: 'Products', id: 'productSearch',
                iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-library-icon-24'}, {idAttribute: 'id'});

            koTableBar().add({name: 'Cart', id: 'cart',
                iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-library-icon-24'}, {idAttribute: 'id'});

        }
        if (this.isAuthenticated() === true) {
            var serviceEndPoints = new ServiceEndPoints();
            var serviceURL = serviceEndPoints.getEndPoint('findNavBarPrivilegesByUserId') + "/" + sessionStorage.userUid;

            var PrivilegeModel = oj.Model.extend({
                urlRoot: serviceURL,
                idAttribute: 'prvUid'
            });

            var PrivilegeCollection = oj.Collection.extend({
                url: serviceURL,
                model: new PrivilegeModel(),
                comparator: 'pageIdentifier'
            });
            var navBarItems = new PrivilegeCollection();
            navBarItems.fetch({

                success: function () {

                    for (var i = 0; i < navBarItems.length; i++) {
                        koTableBar().add(
                                {name: navBarItems.models[i].attributes.pageDescription, id: navBarItems.models[i].attributes.pageIdentifier,
                                    iconClass: 'oj-navigationlist-item-icon demo-icon-font-24'}, {idAttribute: 'id'});
                    }


                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("navBarItems: not found: " + errorThrown);
                }
            });
        }

    };

    this.getNavMenuItems = function (koTableMenu) {
        // if not authenticated add welcome and about
        koTableMenu([]);
        if (this.isAuthenticated() === false) {
            koTableMenu.push({name: 'About', id: 'about',
                iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-info-icon-24'});
        }
        if (this.isAuthenticated() === true) {
            var serviceURL = this.serviceEndPoints.getEndPoint('findNavMenuPrivilegesByUserId') + "/" + sessionStorage.userUid;

            var PrivilegeModel = oj.Model.extend({
                urlRoot: serviceURL,
                idAttribute: 'prvUid'
            });

            var PrivilegeCollection = oj.Collection.extend({
                url: serviceURL,
                model: new PrivilegeModel(),
                comparator: 'pageIdentifier'
            });
            var navMenuItems = new PrivilegeCollection();
            navMenuItems.fetch({
                success: function () {

                    for (var i = 0; i < navMenuItems.length; i++) {
                        koTableMenu.push(
                                {name: navMenuItems.models[i].attributes.pageDescription, id: navMenuItems.models[i].attributes.pageIdentifier,
                                    iconClass: 'oj-navigationlist-item-icon demo-icon-font-24'});
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("navMenuItems: not found: " + errorThrown);
                }
            });

            koTableMenu.push({name: 'Logout', id: 'logout',
                iconClass: 'oj-navigationlist-item-icon demo-icon-font-24'});
            koTableMenu.push({name: 'About', id: 'about',
                iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-info-icon-24'});
        }
    };

    this.getPrivileges = function () {
        var serviceURL = SecurityUtils.serviceEndPoints.getEndPoint('findAllPrivilegesByUserId') + "/" + sessionStorage.userUid;

        var PrivilegeModel = oj.Model.extend({
            urlRoot: serviceURL,
            parse: this.parsePrivileges,
            idAttribute: 'pageIdentifier'
        });

        var PrivilegeCollection = oj.Collection.extend({
            url: serviceURL,
            model: new PrivilegeModel(),
            comparator: 'pageIdentifier'
        });

        var privCol = new PrivilegeCollection();

        return privCol.fetch({});
    };

    this.initializeSessionStorage = function () {
        if (typeof (Storage) !== "undefined" && sessionStorage.authenticated !== "true")
        {
            sessionStorage.navData = [];
            sessionStorage.userFullName = "";
            sessionStorage.firstName = "";
            sessionStorage.lastName = "";
            sessionStorage.userUid = "";
            sessionStorage.groups = [];
            sessionStorage.authenticated = false;
        }
    };

    this.clearSessionStorage = function () {
        if (typeof (Storage) !== "undefined") {
            sessionStorage.navData = [];
            sessionStorage.userFullName = "";
            sessionStorage.firstName = "";
            sessionStorage.lastName = "";
            sessionStorage.userUid = "";
            sessionStorage.groups = [];
            sessionStorage.authenticated = false;
        }
    };
};