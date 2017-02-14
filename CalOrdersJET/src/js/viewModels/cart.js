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
define(['ojs/ojcore', 'knockout', 'data/data', 'ojs/ojrouter', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojmodel', 'ojs/ojpagingcontrol', 'ojs/ojpagingcontrol-model'],
        function (oj, ko, data) {

            function CartViewModel() {
                var self = this;

                self.router = oj.Router.rootInstance;

                self.itemTotalPrice = ko.observable();
                self.shippingPrice = ko.observable();
                self.totalPrice = ko.observable();

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
                            tempItemTotalPrice += sessionCart[i].prdPrice;
                        }

                        self.itemTotalPrice("$" + tempItemTotalPrice.toFixed(2));


                        self.shippingPrice("$" + tempShippingPrice.toFixed(2));

                        tempTotalPrice = tempShippingPrice + tempItemTotalPrice;

                        self.totalPrice("$" + tempTotalPrice.toFixed(2));

                        sessionStorage.itemTotalPrice = self.itemTotalPrice();
                        sessionStorage.shippingPrice = self.shippingPrice();
                        sessionStorage.totalPrice = self.totalPrice();

                        self.cart = ko.observableArray(sessionCart);

                        self.listViewDataSource = ko.computed(function () {
                            return new oj.ArrayTableDataSource(self.cart(), {idAttribute: 'prdUid'});
                        });
                    } else
                    {
                        self.itemTotalPrice("$0.00");
                        self.shippingPrice("$0.00");
                        self.totalPrice("$0.00");
                    }
                    sessionStorage.itemTotalPrice = self.itemTotalPrice();
                    sessionStorage.shippingPrice = self.shippingPrice();
                    sessionStorage.totalPrice = self.totalPrice();
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

                 self.getPhoto = function (product) {
                    var file = product.prdImgImage;
                    var imageSize = product.prdImgImage.length;
                    var imageType = product.prdCategoryCd.longDesc;

                    var reader = new FileReader();

                    var data = window.atob(file);
                    var arr = new Uint8Array(data.length);
                    for (var i = 0; i < data.length; i++) {
                        arr[i] = data.charCodeAt(i);
                    }

                    var blob = new Blob([arr.buffer], {size: imageSize, type: imageType});

                    reader.addEventListener("load", function (event) {
                        var preview = document.getElementById('productImage' + product.prdUid);
                        preview.src = reader.result;
                    }, false);

                    if (blob) {

                        try {
                            reader.readAsDataURL(blob);

                        } catch (err)
                        {
                            console.log(err);
                        }
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
                    // TODO: Replace with cart items from session
                    var order = {createUserId: "Create1", updateUserId: "Update1",
                        orderStatusCd: "SUBT",
                        partyUid: 1,
                        products: [{prdUid: 6, quantity: 3}, {prdUid: 10, quantity: 1}],
                        services: [{prsUid: 1, quantity: 5}, {prsUid: 2, quantity: 6}]};

                    // build our REST URL
                    var serviceEndPoints = new ServiceEndPoints();
                    var serviceURL = serviceEndPoints.getEndPoint('createOrder');


                    var OrderService = oj.Model.extend({
                        urlRoot: serviceURL
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
