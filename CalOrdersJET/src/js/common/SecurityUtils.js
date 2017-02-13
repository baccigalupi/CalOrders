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

            koTableBar().add({name: 'About', id: 'about',
                iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-info-icon-24'}, {idAttribute: 'id'});

        }

        if (this.isAuthenticated() === true) {
            var serviceEndPoints = new ServiceEndPoints();
            var serviceURL = serviceEndPoints.getEndPoint('findNavBarPrivilegesByPartyId') + "/" + sessionStorage.partyUid;


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

            var iconStyle = '';

            navBarItems.fetch({

                success: function () {

                    // create a temporary model just for sorting.. we 
                    // need the tabs to reflect the order specified 
                    // in the database
                    var Item = oj.Model.extend({
                       description: '', pageId: '', order: 0 
                    });
                    var item = null;
                    var data = [];
             
                    for (var i = 0; i < navBarItems.length; i++) {

                       item = new Item();
                       item.description = navBarItems.models[i].attributes.pageDescription;
                       item.pageId = navBarItems.models[i].attributes.pageIdentifier;
                       item.order = navBarItems.models[i].attributes.pageOrder;
                       data.push(item);
 
                    }

                    data.sort(function(a, b){return a.order > b.order});

                    for(var i = 0; i < data.length; i++)
                    {
                        
                         if ('productSearch' === data[i].pageId)
                        {
                            iconStyle = 'demo-catalog-icon-24';
                        } else if ('orderHistory' === data[i].pageId)
                        {
                            iconStyle = 'demo-library-icon-24';
                        } else if ('cart' === data[i].pageId)
                        {
                            iconStyle = 'demo-download-icon-24';
                        } else if ('dashboard' === data[i].pageId)
                        {
                            iconStyle = 'demo-chart-icon-24';
                        } else
                        {
                            iconStyle = 'demo-info-icon-24';
                        }
                        
                        koTableBar().add(
                            {name: data[i].description, id: data[i].pageId,
                                iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 ' + iconStyle}, {idAttribute: 'id'});
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
                iconClass: 'oj-navigationlist-item-icon demo-icon-font-24'});
        }
        if (this.isAuthenticated() === true) {
            var serviceURL = this.serviceEndPoints.getEndPoint('findNavMenuPrivilegesByPartyId') + "/" + sessionStorage.partyUid;

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
                    
                    
                    // create a temporary model just for sorting.. we 
                    // need the tabs to reflect the order specified 
                    // in the database
                    var Item = oj.Model.extend({
                       description: '', pageId: '', order: 0 
                    });
                    var item = null;
                    var data = [];
             
                    for (var i = 0; i < navMenuItems.length; i++) {

                       item = new Item();
                       item.description = navMenuItems.models[i].attributes.pageDescription;
                       item.pageId = navMenuItems.models[i].attributes.pageIdentifier;
                       item.order = navMenuItems.models[i].attributes.pageOrder;
                       data.push(item);
 
                    }

                    data.sort(function(a, b){return a.order > b.order});
                    

                    for (var i = 0; i < navMenuItems.length; i++) {
                        koTableMenu.push(
                                {name: data[i].description, id: data[i].pageId,
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
        var serviceURL = SecurityUtils.serviceEndPoints.getEndPoint('findAllPrivilegesByPartyId') + "/" + sessionStorage.partyUid;

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
            sessionStorage.partyUid = "";
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
            sessionStorage.partyUid = "";
            sessionStorage.groups = [];
            sessionStorage.authenticated = false;
        }
    };
};