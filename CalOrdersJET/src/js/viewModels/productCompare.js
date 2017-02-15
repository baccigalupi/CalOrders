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
define(['ojs/ojcore', 'knockout', 'data/data', 'ojs/ojrouter', 'ojs/ojknockout', 'promise',
    'ojs/ojlistview', 'ojs/ojmodel', 'ojs/ojpagingcontrol', 'ojs/ojpagingcontrol-model', 'utils/ProductHelper'],
        function (oj, ko, data) {

            function ProductCompareViewModel() {
                var self = this;

                var lgQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.LG_UP);
                self.large = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(lgQuery);

                self.router = oj.Router.rootInstance;
                self.addedProductPhoto = ko.observable();
                self.addedProductName = ko.observable();
                self.productsToCompareBreadcrumbs = ko.observable();
                self.productsToCompare = ko.observableArray();

                /**
                 * Get the photo for the product
                 * 
                 * @param {type} product
                 * @returns {undefined}
                 */
                self.getPhoto = function (product) {
                    return ProductHelper.getPhoto(product);
                };

                /**
                 * Add product to the cart
                 * 
                 * @param {type} product
                 * @returns {undefined}
                 */
                self.addToCart = function (product) {
                    ProductHelper.addProductToCart(product);

                    // Show confirmation message                    
                    self.addedProductName(product.prdName);
                    self.addedProductPhoto($("#productImage" + product.prdUid).attr("src"));
                    $("#addToCartConfirmationDialog").ojDialog("open");
                };

                /**
                 * Navigates to the Product Search page
                 * 
                 * @returns router value for "productSearch"
                 */
                self.navigateToProductSearch = function () {
                    console.log("continue shopping");
                    $("#addToCartConfirmationDialog").ojDialog("close");
                    return self.router.go("productSearch");
                };

                /**
                 * Navigates to the Cart page
                 * 
                 * @returns router value for "cart"
                 */
                self.navigateToCart = function () {
                    return self.router.go("cart");
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
                    // Implement if needed
                    self.productsToCompareBreadcrumbs(sessionStorage.productsToCompareBreadcrumbs);
                    self.productsToCompare(JSON.parse(sessionStorage.productsToCompare));

                    self.listViewDataSource
                            = ko.observable(new oj.ArrayTableDataSource(self.productsToCompare(), {idAttribute: 'prdUid'}));
                    self.cardViewDataSource
                            = ko.observable(new oj.PagingTableDataSource(self.listViewDataSource()));
                };
            }

            /*
             * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
             * each time the view is displayed.  Return an instance of the ViewModel if
             * only one instance of the ViewModel is needed.
             */
            return new ProductCompareViewModel();
        }
);