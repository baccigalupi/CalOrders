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
define(['ojs/ojcore', 'knockout', 'data/data', 'accounting', 'common/SecurityUtils', 'ojs/ojrouter', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojmodel', 'ojs/ojpagingcontrol', 'ojs/ojpagingcontrol-model', 'utils/ProductHelper'],
        function (oj, ko, data, accounting) {

            function OrderConfirmationViewModel() {
                var self = this;

                self.addressLine1 = ko.observable();
                self.addressLine2 = ko.observable();
                self.cityStateZip = ko.observable();
                self.itemTotalPrice = ko.observable();
                self.totalPrice = ko.observable();
                self.shippingPrice = ko.observable();
                self.cart = ko.observableArray();
                self.errorMessage = ko.observable();
                self.doShowErrorMessage = false;
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

                self.router = oj.Router.rootInstance;

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
                    if (!SecurityUtils.isAuthenticated()) {
                        return self.router.go('welcome');
                    }

                    try {
                        self.doShowErrorMessage = false;

                        self.addressLine1(sessionStorage.departmentAddressLine1);
                        self.addressLine2(sessionStorage.departmentAddressLine2);
                        self.cityStateZip(sessionStorage.departmentCityStateZip);
                        self.itemTotalPrice(accounting.formatMoney(sessionStorage.itemTotalPrice));
                        self.totalPrice(accounting.formatMoney(sessionStorage.totalPrice));
                        self.shippingPrice(accounting.formatMoney(sessionStorage.shippingPrice));

                        if (sessionStorage.cartProducts !== "")
                        {
                            var sessionCart = JSON.parse(sessionStorage.cartProducts);
 
 
                            for (i = 0; i < sessionCart.length; i++)
                            {
                                var cartProduct = sessionCart[i];

                                var totalItemPrice = accounting.formatMoney(cartProduct.quantity * cartProduct.prdCntrUnitPrice);
                                cartProduct.prdCntrUnitPrice = accounting.formatMoney(cartProduct.prdCntrUnitPrice);
                                cartProduct.totalItemPrice = ko.observable(totalItemPrice);
                                cartProduct.quantity = ko.observable(cartProduct.quantity);

                            }

                            self.cart(sessionCart);

                            self.listViewDataSource = ko.computed(function () {
                                return new oj.ArrayTableDataSource(self.cart(), {idAttribute: 'prdUid'});
                            });

                            self.cardViewDataSource = ko.computed(function () {
                                return new oj.PagingTableDataSource(self.listViewDataSource());
                            });

                            sessionStorage.cartProducts = [];

                        }




                    } catch (err)
                    {
                        self.doShowErrorMessage = true;
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

                    if (self.doShowErrorMessage)
                    {
                        document.getElementById('errorMessage').hidden = false;

                        document.getElementById('confirmationMessage').hidden = true;
                        self.errorMessage('Oops we can not get your cart right now, please try refreshing the screen.')
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




                self.continueShoppingClick = function (data, event)
                { 
                    return self.router.go("productSearch");
                };


                self.changeHandler = function (page, event) {
                    if (event.option === 'selection') {
                        if (event.value[0]) {
                            var emp = {};
                            emp.empId = event.value[0];
                            self.loadPersonPage(emp);
                        }
                    }
                };


                self.getPhoto = function (product) {

                    return ProductHelper.getPhoto(product);

                };

                self.getPrice = function (price)
                {
                    return accounting.formatMoney(price);
                };
            }

            /*
             * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
             * each time the view is displayed.  Return an instance of the ViewModel if
             * only one instance of the ViewModel is needed.
             */
            return new OrderConfirmationViewModel();
        }
);
