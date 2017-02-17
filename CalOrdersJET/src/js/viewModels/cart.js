/* 
 * The MIT License
 *
 * Copyright 2017 OnCore Consulting LLC, 2017
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
/*
 * Your about ViewModel code goes here
 */
define(['ojs/ojcore', 'knockout', 'data/data', 'common/SecurityUtils', 'ojs/ojrouter', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojmodel', 'ojs/ojpagingcontrol', 'ojs/ojpagingcontrol-model'],
        function (oj, ko, data) {

            function CartViewModel() {
                var self = this;

                var serviceEndPoints = new ServiceEndPoints();
                self.createOrderServiceURL = serviceEndPoints.getEndPoint('createOrder');

                self.router = oj.Router.rootInstance;

                self.itemTotalPrice = ko.observable();
                self.shippingPrice = ko.observable();
                self.totalPrice = ko.observable();
                self.cart = ko.observableArray();
                self.listViewDataSource = null;
                self.cardViewDataSource = null;
                self.productLayoutType = ko.observable('productCardLayout');

                var lgQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.LG_UP);
                var mdQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.MD_UP);
                var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_UP);
                var smOnlyQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
                self.large = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(lgQuery);
                self.medium = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(mdQuery);
                self.small = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);
                self.smallOnly = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smOnlyQuery);

                // Below are a subset of the ViewModel methods invoked by the ojModule binding
                // Please reference the ojModule jsDoc for additionaly available methods.

                /**
                 * Optional ViewModel method invoked when this ViewModel is about to be
                 * used for the View transition.  The application can put data fetch logic
                 * here that can return a Promise which will delay the handleAttached function
                 * call below until the Promise is resolved.
                 * @param {Object} info - An object with the following key-value pairs:
                 * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
                 * @param {Function} info.valueAccessor - The binding's value accessor.
                 * @return {Promise|undefined} - If the callback returns a Promise, the next phase (attaching DOM) will be delayed until
                 * the promise is resolved
                 */
                self.handleActivated = function (info) {

                    if (sessionStorage.authenticated === "false")
                    {
                        return self.router.go('welcome');
                    }
                    if (sessionStorage.cartProducts !== "")
                    {
                        var sessionCart = JSON.parse(sessionStorage.cartProducts);

                        var tempItemTotalPrice = 0.0;
                        var tempShippingPrice = 25.00;
                        var tempTotalPrice = 0.00;

                        for (i = 0; i < sessionCart.length; i++)
                        {
                            tempItemTotalPrice += (sessionCart[i].prdPrice * sessionCart[i].quantity);
                        }

                        self.itemTotalPrice("$" + tempItemTotalPrice.toFixed(2));
                        self.shippingPrice("$" + tempShippingPrice.toFixed(2));

                        tempTotalPrice = tempShippingPrice + tempItemTotalPrice;

                        self.totalPrice("$" + tempTotalPrice.toFixed(2));

                        sessionStorage.itemTotalPrice = self.itemTotalPrice();
                        sessionStorage.shippingPrice = self.shippingPrice();
                        sessionStorage.totalPrice = self.totalPrice();

                        self.cart(sessionCart);

                        self.listViewDataSource = ko.computed(function () {
                            return new oj.ArrayTableDataSource(self.cart(), {idAttribute: 'prdUid'});
                        });

                        self.cardViewDataSource = ko.computed(function () {
                            return new oj.PagingTableDataSource(self.listViewDataSource());
                        });

                    } else
                    {
                        self.itemTotalPrice("$0.00");
                        self.shippingPrice("$0.00");
                        self.totalPrice("$0.00");
                        sessionStorage.itemTotalPrice = self.itemTotalPrice();
                        sessionStorage.shippingPrice = self.shippingPrice();
                        sessionStorage.totalPrice = self.totalPrice();
                        self.cart = ko.observableArray([]);

                        self.listViewDataSource = ko.computed(function () {
                            return new oj.ArrayTableDataSource(self.cart(), {idAttribute: 'prdUid'});
                        });

                        self.cardViewDataSource = ko.computed(function () {
                            return new oj.PagingTableDataSource(self.listViewDataSource());
                        });
                    }
                };

                /**
                 * Optional ViewModel method invoked after the View is inserted into the
                 * document DOM.  The application can put logic that requires the DOM being
                 * attached here.
                 * @param {Object} info - An object with the following key-value pairs:
                 * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
                 * @param {Function} info.valueAccessor - The binding's value accessor.
                 * @param {boolean} info.fromCache - A boolean indicating whether the module was retrieved from cache.
                 */
                self.handleAttached = function (info) {
                    // Implement if needed
                };


                /**
                 * Optional ViewModel method invoked after the bindings are applied on this View. 
                 * If the current View is retrieved from cache, the bindings will not be re-applied
                 * and this callback will not be invoked.
                 * @param {Object} info - An object with the following key-value pairs:
                 * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
                 * @param {Function} info.valueAccessor - The binding's value accessor.
                 */
                self.handleBindingsApplied = function (info) {
                    // Implement if needed
                };

                /*
                 * Optional ViewModel method invoked after the View is removed from the
                 * document DOM.
                 * @param {Object} info - An object with the following key-value pairs:
                 * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
                 * @param {Function} info.valueAccessor - The binding's value accessor.
                 * @param {Array} info.cachedNodes - An Array containing cached nodes for the View if the cache is enabled.
                 */
                self.handleDetached = function (info) {
                    // Implement if needed
                };

                /**
                 * Get the photo for the product.
                 * 
                 * @param {type} product
                 * @returns {undefined}
                 */
                self.getPhoto = function (product) {
                    return ProductHelper.getPhoto(product);
                };

                self.cardLayoutHandler = function () {
                    self.productLayoutType('productCardLayout');
                };

                self.listLayoutHandler = function () {
                    self.productLayoutType('productListLayout');
                };

                /*
                 * Places the Order.
                 * 
                 * @param {type} trackerObj
                 * @returns {Boolean}
                 */
                self.placeOrderClick = function (trackerObj)
                {
                    if (sessionStorage.partyUid !== "" && sessionStorage.cartProducts !== ""
                            && sessionStorage.authenticated !== "false")
                    {
                        var partyUid = sessionStorage.partyUid;

                        var sessionCart = JSON.parse(sessionStorage.cartProducts);


                        var order = {createUserId: partyUid, updateUserId: partyUid,
                            orderStatusCd: "SUBT",
                            partyUid: partyUid,
                            products: sessionCart};


                        // call our REST service
                        var OrderService = oj.Model.extend({
                            urlRoot: self.createOrderServiceURL
                        });

                        var orderService = new OrderService();


                        // execute REST createOrder operation
                        orderService.save(
                                order,
                                {
                                    success: function (myModel, response, options) {
                                        return self.router.go("orderConfirmation");
                                    },
                                    error: function (jqXHR, textStatus, errorThrown) {

                                        console.log("Unable to create the order: " + errorThrown);
                                    }
                                });
                    }

                };

                self.continueShoppingClick = function (data, event)
                {
                    return self.router.go("productSearch");
                };

// TODO: Do we want Enter key to do anything by default on Cart page?
                self.onEnter = function (data, event) {
                    if (event.keyCode === 13) {

                    }
                    return true;
                };

// TODO: Do we want to do anything when clicking on an item from the Cart?
                self.changeHandler = function (page, event) {
                    if (event.option === 'currentItem') {

                    }
                };

            }

            /*
             * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
             * each time the view is displayed.  Return an instance of the ViewModel if
             * only one instance of the ViewModel is needed.
             */
            return new CartViewModel();
        }
);
