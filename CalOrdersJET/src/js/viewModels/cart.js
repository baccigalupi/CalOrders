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
define(['ojs/ojcore', 'knockout', 'data/data', 'libs/accounting/accounting', 'common/SecurityUtils', 'ojs/ojrouter', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojmodel', 'ojs/ojpagingcontrol', 'ojs/ojpagingcontrol-model', 'utils/ProductHelper'],
        function (oj, ko, data, accounting) {

            function CartViewModel() {
                var self = this;

                var savingData = false;

                var serviceEndPoints = new ServiceEndPoints();
                self.createOrderServiceURL = serviceEndPoints.getEndPoint("createOrder");
                self.findRelatedServiceProductsURL = serviceEndPoints.getEndPoint("findActiveProductsByProductTypeAndVendor");
                self.findProductServiceURL = serviceEndPoints.getEndPoint("findProductById");

                self.router = oj.Router.rootInstance;
                self.ready = ko.observable(false);
                // Vendor Id + Product Type is the key
                self.relatedServicesMap = new Map();

                self.itemTotalPrice = ko.observable();
                self.shippingPrice = ko.observable();
                self.totalPrice = ko.observable();
                self.cart = ko.observableArray([]);
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

                self.errorMessage = ko.observable();

                // Below are a subset of the ViewModel methods invoked by the ojModule binding
                // Please reference the ojModule jsDoc for additional available methods.

                /**
                 * Parses each related service for display in the Additional Services combo box. 
                 * @param {type} response
                 * @returns {undefined}
                 */
                self.parseRelatedService = function (response) {

                    for (i = 0; i < self.cart().length; i++)
                    {
                        if (self.cart()[i].vndUid == response.vndUid)
                        {
                            var productType1 = "";
                            var productType2 = "";

                            if (response.prdCategoryCd.code.indexOf('DS') == 0)
                            {
                                productType1 = 'DH' + response.prdCategoryCd.code.substring(2);
                                productType2 = 'DM' + response.prdCategoryCd.code.substring(2);
                            } else if (response.prdCategoryCd.code.indexOf('LS') == 0)
                            {
                                productType1 = 'LH' + response.prdCategoryCd.code.substring(2);
                            }

                            if (productType1 != "" && (self.cart()[i].prdCategoryCd.code == productType1 ||
                                    self.cart()[i].prdCategoryCd.code == productType2))
                            {

                                var containsRelatedService = false;
                                for (j = 0; j < self.cart()[i].relatedServices().length; j++)
                                {
                                    var relatedService = self.cart()[i].relatedServices()[j];
                                    if (relatedService.value === response.prdUid)
                                    {
                                        containsRelatedService = true;
                                        break;
                                    }
                                }

                                if (!containsRelatedService)
                                {
                                    self.cart()[i].relatedServices.push({label: response.prdName, value: response.prdUid});

                                    if (self.cart()[i].relatedServices().length == 1)
                                    {
                                        self.cart()[i].selectedRelatedService(response.prdUid);
                                    }
                                }
                            }

                        }
                    }
                };


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
                    $('globalBody').focus();
                    window.location.hash = 'globalBody';
                    if (sessionStorage.authenticated === "false")
                    {
                        return self.router.go('welcome');
                    }
                    if (sessionStorage.cartProducts !== "")
                    {
                        var sessionCart = JSON.parse(sessionStorage.cartProducts);

                        var tempItemTotalPrice = 0.0;
                        var tempShippingPrice = 0.00;
                        var tempTotalPrice = 0.00;

                        if (sessionCart.length > 0)
                        {
                            tempShippingPrice = 25.00;
                        }

                        for (i = 0; i < sessionCart.length; i++)
                        {
                            tempItemTotalPrice += (sessionCart[i].prdCntrUnitPrice * sessionCart[i].quantity);
                        }

                        self.itemTotalPrice(self.getPrice(tempItemTotalPrice));
                        self.shippingPrice(self.getPrice(tempShippingPrice));

                        tempTotalPrice = tempShippingPrice + tempItemTotalPrice;

                        self.totalPrice(self.getPrice(tempTotalPrice));

                        sessionStorage.itemTotalPrice = self.itemTotalPrice();
                        sessionStorage.shippingPrice = self.shippingPrice();
                        sessionStorage.totalPrice = self.totalPrice();

                        for (i = 0; i < sessionCart.length; i++)
                        {
                            var cartProduct = sessionCart[i];


                            if (cartProduct.relatedServices === undefined)
                            {
                                cartProduct.relatedServices = ko.observableArray([]);
                            }

                            if (cartProduct.selectedRelatedService === undefined)
                            {
                                cartProduct.selectedRelatedService = ko.observable();
                            }

                            var totalItemPrice = cartProduct.quantity * cartProduct.prdCntrUnitPrice;

                            cartProduct.totalItemPrice = ko.observable(totalItemPrice);
                            cartProduct.quantity = ko.observable(cartProduct.quantity);
                            cartProduct.removeProduct = ko.observable();


                            var productType = "";

                            if (cartProduct.prdCategoryCd.code.indexOf('DH') == 0)
                            {
                                productType = 'DS' + cartProduct.prdCategoryCd.code.substring(2);
                            } else if (cartProduct.prdCategoryCd.code.indexOf('LH') == 0)
                            {
                                productType = 'LS' + cartProduct.prdCategoryCd.code.substring(2);
                            } else if (cartProduct.prdCategoryCd.code.indexOf('DM') == 0)
                            {
                                productType = 'DS' + cartProduct.prdCategoryCd.code.substring(2);
                            }

                            if (productType != "")
                            {
                                var ProductModel = oj.Model.extend({
                                    urlRoot: self.findRelatedServiceProductsURL + "/" + productType + "/" + cartProduct.vndUidFk.vndUid,
                                    parse: self.parseRelatedService,
                                    idAttribute: 'prdUid'
                                });
                                var product = new ProductModel();

                                var ProductCollection = oj.Collection.extend({
                                    url: self.findRelatedServiceProductsURL + "/" + productType + "/" + cartProduct.vndUidFk.vndUid,
                                    model: product,
                                    comparator: 'prdUid'
                                });

                                var collection = new ProductCollection();

                                collection.fetch({
                                    success: function (myModel, response, options) {
                                        return false;
                                    },
                                    error: function (jqXHR, textStatus, errorThrown) {
                                        console.log("Search failed " + errorThrown);
                                        return false;
                                    }
                                });

                                var mapKey = cartProduct.vndUidFk.vndUid + productType;

                                self.relatedServicesMap.set(mapKey, collection);
                            }
                        }

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

                    self.ready(true);
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

                    if (sessionStorage.cartProducts !== "")
                    {
                        var sessionCart = JSON.parse(sessionStorage.cartProducts);

                        if (sessionCart.length == 0)
                        {
                            document.getElementById('placeOrderBottom').disabled = true;
                            document.getElementById('placeOrderTop').disabled = true;
                            document.getElementById('removeProductsButton').disabled = true;
                        }
                    } else
                    {
                        document.getElementById('placeOrderBottom').disabled = true;
                        document.getElementById('placeOrderTop').disabled = true;
                        document.getElementById('removeProductsButton').disabled = true;
                    }
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

                /**
                 * Gets the formatted price value.
                 * @param {type} price
                 * @returns {unresolved}
                 */
                self.getPrice = function (price)
                {
                    return accounting.formatMoney(price);
                };

                /**
                 * Event for item quantity change.  If new quantity value is not valid, 
                 * set it back to previous value and display an error message.  If new
                 * quantity value is valid, update the cart in session storage and update
                 * the totals.
                 * 
                 * @param {type} product
                 * @param {type} event
                 * @returns {undefined}
                 */
                self.productQuantityChange = function (product, event)
                {
                    var sessionCart = JSON.parse(sessionStorage.cartProducts);

                    if (Number(product.quantity()) <= 0 || Number(product.quantity()) > 10000)
                    {
                        self.errorMessage("Please enter a quantity between 1 and 10,000");
                        document.getElementById('pageErrorContainer').hidden = false;

                        for (i = 0; i < sessionCart.length; i++)
                        {
                            // set the quantity back to the previous value if it's out of bounds
                            if (sessionCart[i].prdUid === product.prdUid)
                            {
                                product.quantity(sessionCart[i].quantity);
                            }
                        }
                    } else
                    {
                        document.getElementById('pageErrorContainer').hidden = true;

                        for (i = 0; i < self.cart().length; i++)
                        {
                            if (self.cart()[i].prdUid === product.prdUid)
                            {
                                var totalItemPrice = product.quantity() * self.cart()[i].prdCntrUnitPrice;
                                self.cart()[i].totalItemPrice(totalItemPrice);
                            }
                        }

                        var tempItemQuantityTotal = 0;

                        for (i = 0; i < sessionCart.length; i++)
                        {
                            if (sessionCart[i].prdUid === product.prdUid)
                            {
                                sessionCart[i].quantity = Number(product.quantity());
                                sessionCart[i].totalItemPrice = sessionCart[i].quantity * sessionCart[i].prdCntrUnitPrice;
                                tempItemQuantityTotal += sessionCart[i].quantity;
                            }
                        }

                        sessionStorage.itemQuantityTotal = tempItemQuantityTotal;

                        var tempItemTotalPrice = 0.0;
                        var tempShippingPrice = 25.00;
                        var tempTotalPrice = 0.00;

                        for (i = 0; i < sessionCart.length; i++)
                        {
                            tempItemTotalPrice += (sessionCart[i].prdCntrUnitPrice * sessionCart[i].quantity);
                        }

                        self.itemTotalPrice(self.getPrice(tempItemTotalPrice));
                        self.shippingPrice(self.getPrice(tempShippingPrice));

                        tempTotalPrice = tempShippingPrice + tempItemTotalPrice;

                        self.totalPrice(self.getPrice(tempTotalPrice));

                        sessionStorage.itemTotalPrice = self.itemTotalPrice();
                        sessionStorage.shippingPrice = self.shippingPrice();
                        sessionStorage.totalPrice = self.totalPrice();

                        sessionStorage.cartProducts = JSON.stringify(sessionCart);
                    }
                };

                /**
                 * Removes selected products from the cart.  Displays an error message
                 * if no products are selected for removal.
                 * 
                 * @returns {undefined}
                 */
                self.removeProductsFromCart = function ()
                {

                    var productsToRemove = [];

                    for (var val = self.cart().length - 1; val >= 0; val--)
                    {
                        if (self.cart()[val].removeProduct() !== undefined
                                && self.cart()[val].removeProduct())
                        {
                            productsToRemove.push(self.cart()[val].prdUid);
                            self.cart.remove(self.cart()[val]);
                        }
                    }

                    if (productsToRemove.length > 0)
                    {
                        var sessionCart = JSON.parse(sessionStorage.cartProducts);

                        for (var i = sessionCart.length - 1; i >= 0; i--)
                        {
                            var index = productsToRemove.indexOf(sessionCart[i].prdUid);
                            if (index > -1)
                            {
                                sessionCart.splice(i, 1);
                            }
                        }

                        var tempItemTotalPrice = 0.0;
                        var tempShippingPrice = 0.00;
                        var tempTotalPrice = 0.00;

                        if (sessionCart.length > 0)
                        {
                            tempShippingPrice = 25.00;
                        } else
                        {
                            $("#placeOrderBottom").ojButton("option", "disabled", true);
                            $("#placeOrderTop").ojButton("option", "disabled", true);
                            $("#removeProductsButton").ojButton("option", "disabled", true);
                        }

                        var tempItemQuantityTotal = 0;

                        for (i = 0; i < sessionCart.length; i++)
                        {
                            tempItemTotalPrice += (sessionCart[i].prdCntrUnitPrice * sessionCart[i].quantity);
                            tempItemQuantityTotal += Number(sessionCart[i].quantity);
                        }

                        sessionStorage.itemQuantityTotal = tempItemQuantityTotal;

                        self.itemTotalPrice(self.getPrice(tempItemTotalPrice));
                        self.shippingPrice(self.getPrice(tempShippingPrice));

                        tempTotalPrice = tempShippingPrice + tempItemTotalPrice;

                        self.totalPrice(self.getPrice(tempTotalPrice));

                        sessionStorage.itemTotalPrice = self.itemTotalPrice();
                        sessionStorage.shippingPrice = self.shippingPrice();
                        sessionStorage.totalPrice = self.totalPrice();

                        sessionStorage.cartProducts = JSON.stringify(sessionCart);
                        document.getElementById('pageErrorContainer').hidden = true;
                    } else
                    {
                        // No products selected for removal
                        self.errorMessage("Please select one or more products to remove");
                        document.getElementById('pageErrorContainer').hidden = false;
                    }
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
                            && sessionStorage.authenticated !== "false" && savingData == false)
                    {
                        savingData = true;

                        var partyUid = sessionStorage.partyUid;

                        var sessionCart = JSON.parse(sessionStorage.cartProducts);

                        for (i = 0; i < sessionCart.length; i++)
                        {
                            sessionCart[i].quantity = parseInt(sessionCart[i].quantity);
                            sessionCart[i].prdImgImage = null;
                        }


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
                                        savingData = false;
                                        return self.router.go("orderConfirmation");
                                    },
                                    error: function (jqXHR, textStatus, errorThrown) {
                                        savingData = false;
                                        console.log("Unable to create the order: " + errorThrown);
                                    }
                                });
                    }

                };

                /**
                 * Continue Shopping event handler.
                 * 
                 * @param {type} data
                 * @param {type} event
                 * @returns {unresolved}
                 */
                self.continueShoppingClick = function (data, event)
                {
                    return self.router.go("productSearch");
                };


                /**
                 * Add Related Service button event handler.  
                 * 
                 * @param {type} product
                 * @returns {undefined}
                 */
                self.addRelatedService = function (product) {

                    // Get product id of the related service
                    var ProductModel = oj.Model.extend({
                        urlRoot: self.findProductServiceURL + "/" + product.selectedRelatedService(),
                        attributeId: 'prdUid',
                        quantityCnt: parseInt(product.quantity())
                    });

                    var pm = new ProductModel();
                    pm.fetch({
                        success: function (myModel, response, options) {
                            // If the product already exists in the cart, then just change the quantity
                            var result = $.grep(self.cart(), function (item) {
                                return item.prdUid === response.prdUid;
                            });

                            var cartProduct;

                            response.quantity = ko.observable(myModel.quantityCnt);
                            response.totalItemPrice = myModel.quantityCnt * response.prdCntrUnitPrice;
                            ProductHelper.addProductToCart(response);

                            if (result.length === 1)
                            {
                                cartProduct = result[0];
                                cartProduct.quantity((parseInt(cartProduct.quantity()) + myModel.quantityCnt).toString());
                                cartProduct.totalItemPrice = cartProduct.quantity() * cartProduct.prdCntrUnitPrice;
                            } else
                            {
                                response.removeProduct = ko.observable();
                                response.relatedServices = ko.observableArray([]);
                                response.selectedRelatedService = ko.observable();
                                response.totalItemPrice = ko.observable(myModel.quantityCnt * response.prdCntrUnitPrice);

                                self.cart.push(response);
                            }

                            var sessionCart = JSON.parse(sessionStorage.cartProducts);

                            var tempItemTotalPrice = 0.0;
                            var tempShippingPrice = 25.00;
                            var tempTotalPrice = 0.00;

                            for (i = 0; i < sessionCart.length; i++)
                            {
                                tempItemTotalPrice += (sessionCart[i].prdCntrUnitPrice * sessionCart[i].quantity);
                            }

                            self.itemTotalPrice(self.getPrice(tempItemTotalPrice));
                            self.shippingPrice(self.getPrice(tempShippingPrice));

                            tempTotalPrice = tempShippingPrice + tempItemTotalPrice;

                            self.totalPrice(self.getPrice(tempTotalPrice));

                            sessionStorage.itemTotalPrice = self.itemTotalPrice();
                            sessionStorage.shippingPrice = self.shippingPrice();
                            sessionStorage.totalPrice = self.totalPrice();
                            return false;
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log("Search for related product failed" + errorThrown);
                            return false;
                        }
                    });

                };

                self.onEnter = function (data, event) {
                    if (event.keyCode === 13) {

                    }
                    return true;
                };

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
