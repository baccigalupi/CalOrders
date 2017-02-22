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
define(['ojs/ojcore', 'knockout', 'data/data', 'common/SecurityUtils', 'ojs/ojrouter', 
    'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojmodel', 'ojs/ojpagingcontrol', 
    'ojs/ojpagingcontrol-model', 'utils/ProductHelper','ojs/ojtabs'],
        function (oj, ko, data) {

            function ProductDetailViewModel() {
                var self = this;
                var serviceEndPoints = new ServiceEndPoints();

                self.router = oj.Router.rootInstance;
                self.findProdutService = serviceEndPoints.getEndPoint("findProductById");
                self.product = ko.observable();
                self.prdUid = ko.observable();
                self.addedProductPhoto = ko.observable();
                self.addedProductName = ko.observable();
                self.productsToCompareBreadcrumbs = ko.observable();
                self.admin = ko.observable(false);
                self.user = ko.observable(false);


                var lgQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.LG_UP);
                var mdQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.MD_UP);
                var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_UP);
                var smOnlyQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
                self.large = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(lgQuery);
                self.medium = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(mdQuery);
                self.small = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);
                self.smallOnly = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smOnlyQuery);

                /**
                 * Parse product from Rest service
                 * @param {type} response
                 * @returns {undefined}
                 */
                self.parseProduct = function (response)
                {
                    response.quantity = ko.observable(1);
                    response.prdLongDescLines = ko.observableArray(response.prdLongDesc.split("\n"));
                    response.vndName = ko.observable(response.vndUidFk.vndName);
                    response.categoryLongDesc = ko.observable(response.prdCategoryCd.longDesc);
                    response.unitLongDesc = ko.observable(response.prdUnitCd.longDesc);
                    
                    self.product(response);
                };

                self.getPhoto = function ()
                {
                    return ProductHelper.getPhoto(self.product());
                };

                self.getPrdUid = function ()
                {
                    return self.product().prdUid;
                };

                /**
                 * Add product to the cart
                 * 
                 * @param {type} product
                 * @returns {undefined}
                 */
                self.addToCart = function () {
                    ProductHelper.addProductToCart(self.product());

                    // Show confirmation message                    
                    self.addedProductName(self.product().prdName);
                    self.addedProductPhoto($("#productImage" + self.product().prdUid).attr("src"));
                    $("#addToCartConfirmationDialog").ojDialog("open");
                };

                self.navigateToProductSearch = function () {
                    sessionStorage.keepSearchResults = true;
                    console.log("continue shopping");
                    $("#addToCartConfirmationDialog").ojDialog("close");
                    return self.router.go("productSearch");
                };

                self.navigateToCart = function () {
                    
                    return self.router.go("cart");
                };
                
                self.navigateToProductUpdate = function (product) {
                    // Store product id parameter
                    self.router.store(self.getPrdUid());
                    return self.router.go("productUpdate");
                };

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
                    self.productsToCompareBreadcrumbs(sessionStorage.productsToCompareBreadcrumbs);

                    // Get product id from the search page
                    self.prdUid(self.router.retrieve());

                    var ProductModel = oj.Model.extend({
                        urlRoot: self.findProdutService + "/" + self.prdUid(),
                        parse: self.parseProduct,
                        attributeId: 'prdUid'
                    });

                    var pm = new ProductModel();
                    pm.fetch();
                    
                    // Initialize a blank object
                    self.product(new Object());
                        
                    if ( sessionStorage.admin === 'true')
                    {
                        self.admin(true);   
                        self.user(false);
                    }
                    else
                    {
                        self.admin(false);   
                        self.user(true);
                    }
                };
            }

            /*
             * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
             * each time the view is displayed.  Return an instance of the ViewModel if
             * only one instance of the ViewModel is needed.
             */
            return ProductDetailViewModel;
        }
);
