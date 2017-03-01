/* 
 * The MIT License
 *
 * Copyright 2017 oncore.
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

//define(['ojs/ojcore', 'knockout', 'ojs/ojrouter', 'ojs/ojknockout', 'ojs/ojarraytabledatasource','ojs/ojpagingcontrol', 'ojs/ojpagingtabledatasource'],
define(['ojs/ojcore', 'knockout', 'common/SecurityUtils', 'ojs/ojknockout-model',
    'ojs/ojdialog', 'ojs/ojcheckboxset', 'ojs/ojdatacollection-utils',
    'ojs/ojinputnumber', 'appController', 'ojs/ojknockout',
    'promise', 'ojs/ojtable', 'ojs/ojarraytabledatasource', 'ojs/ojcollectiontabledatasource',
    'ojs/ojmodule', 'ojs/ojrouter', 'ojs/ojnavigationlist', 'ojs/ojbutton', 'ojs/ojinputtext', 'ojs/ojchart', 'ojs/ojmodel',
    'ojs/ojpagingcontrol', 'ojs/ojpagingtabledatasource', 'ojs/ojdatagrid',
    'ojs/ojcollectiondatagriddatasource', 'ojs/ojtoolbar', 'ojs/ojknockout-validation', 'ojs/ojselectcombobox',
    'ojs/ojlistview', 'ojs/ojdatacollection-common', 'ojs/ojjsontreedatasource'],
        function (oj, ko) {
            function ControllerViewModel() {
                var self = this;

                // Media queries for repsonsive layouts
                var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
                self.smScreen = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);

                // Router setup
                self.router = oj.Router.rootInstance;

                self.router.configure({
                    'welcome': {label: 'Welcome', isDefault: true},
                    'login': {label: 'Login', isDefault: false},
                    'productSearch': {label: 'Product Search', isDefault: false},
                    'productDetail': {label: 'Product Detail', isDefault: false},
                    'productCompare': {label: 'Compare Products', isDefault: false},
                    'productAdd': {label: 'Add Product', isDefault: false},
                    'productUpdate': {label: 'Update Product', isDefault: false},
                    'serviceAdd': {label: 'Add Service', isDefault: false},
                    'dashboard': {label: 'Administrator Dashboard'},
                    'cart': {label: 'Cart'},
                    'orderConfirmation': {label: 'Order Confirmation'},
                    'orderHistory': {label: 'Order History'},
                    'orderHistoryAdmin': {label: 'Order History'},
                    'orderDetail': {label: 'Order Detail'},
                    'orderConfirmation': {label: 'Order Confirmation'},
                    'about': {label: 'About'},
                    'profile': {label: 'Profile'}
                });
                oj.Router.defaults['urlAdapter'] = new oj.Router.urlParamAdapter();
                // Navigation setup                
                navBarData = ko.observableArray([]);
                navBarDataSource = ko.observable(new oj.ArrayTableDataSource(navBarData, {idAttribute: 'id'}));
                SecurityUtils.getNavBarItems(navBarDataSource);

                navMenuData = ko.observableArray([]);
                navMenuDataSource = ko.observableArray([]);
                SecurityUtils.getNavMenuItems(navMenuDataSource);
                userLogin = ko.observable();
                // Header
                // Application Name used in Branding Area
                self.appName = ko.observable("CalOrders");

                self.company = ko.observable("By OnCore");

                // User Info used in Global Navigation area
                if (!SecurityUtils.isAuthenticated()) {
                    userLogin("Options");
                } else {
                    userLogin(sessionStorage.userFullName);
                }

                if (typeof (Storage) !== "undefined" && sessionStorage.authenticated !== "true")
                {
                    sessionStorage.userFullName = "";
                    sessionStorage.firstName = "";
                    sessionStorage.lastName = "";
                    sessionStorage.userUid = "";
                    sessionStorage.groups = [];
                    sessionStorage.authenticated = false;
                }

                SecurityUtils.initializeSessionStorage();

                self.launch = function (data, event) {

                    if (this.id === 'logout') {
                        SecurityUtils.clearSessionStorage();
                        navBarDataSource(new oj.ArrayTableDataSource(navBarData, {idAttribute: 'id'}));
                        SecurityUtils.getNavBarItems(navBarDataSource);
                        SecurityUtils.getNavMenuItems(navMenuDataSource);
                        userLogin("Options");
                        return self.router.go('welcome');
                    }

                    return self.router.go(this.id);

                };

                self.navBarEventClick = function (data, event) {
                    var navBarDesc = event.target.innerText;
                    
                    if ( navBarDesc === "About")
                    {
                        return self.router.go("about");
                    }
                    else if ( navBarDesc === "Welcome")
                    {
                        return self.router.go("welcome");
                    }
                    else if ( navBarDesc === "Products")
                    {
                        return self.router.go("productSearch");
                    }
                    else if ( navBarDesc === "Orders")
                    {
                        if (sessionStorage.admin === 'true')
                        {
                            return self.router.go("orderHistoryAdmin");
                        }
                        else
                        {
                            return self.router.go("orderHistory");
                        }
                    }
                    else if ( navBarDesc === "Dashboard")
                    {
                        return self.router.go("dashboard");
                    }
                };

                self.aboutClick = function (data, event) {

//                    return self.router.go('about');
                };

                // Footer
                function footerLink(name, id, linkTarget) {
                    this.name = name;
                    this.linkId = id;
                    this.linkTarget = linkTarget;
                }
                self.footerLinks = ko.observableArray([
                    new footerLink('About OnCore', 'aboutOnCore', 'http://www.oncorellc.com/about-us2/'),
                    new footerLink('Contact Us', 'contactUs', 'http://www.oncorellc.com/contact-us/')
                ]);
            }

            return new ControllerViewModel();
        }
);
