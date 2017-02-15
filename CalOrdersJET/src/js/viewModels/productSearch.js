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
define(['ojs/ojcore', 'knockout', 'data/data', 'ojs/ojrouter', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojmodel', 'ojs/ojpagingcontrol', 'ojs/ojpagingcontrol-model', 'utils/ProductHelper'],
        function (oj, ko, data) {

            function ProductSearchViewModel() {
                var self = this;

                var serviceEndPoints = new ServiceEndPoints();

                self.router = oj.Router.rootInstance;
                self.findProductsByProductTypeService = serviceEndPoints.getEndPoint("findActiveProductsByProductType");
                self.selectedProductMenuItem = ko.observable('DESK');
                self.productLayoutType = ko.observable('productCardLayout');
                self.allProduct = ko.observableArray([]);
                self.ready = ko.observable(false);
                self.nameSearch = ko.observable('');
                self.addedProductPhoto = ko.observable();
                self.addedProductName = ko.observable();
                self.productCategoryBreadcrumbs = ko.observable();
                self.errorMessage = ko.observable();

                self.productCategories = [
                    {code: "DESK", description: "Desktops", parent: "Hardware"},
                    {code: "LAPT", description: "Laptops", parent: "Hardware"},
                    {code: "MONT", description: "Monitors", parent: "Hardware"},
                    {code: "PRNT", description: "Printers", parent: "Hardware"},
                    {code: "PERI", description: "Peripherals", parent: "Hardware"},

                    {code: "OPSY", description: "OS", parent: "Software"},
                    {code: "OFFC", description: "Office", parent: "Software"},
                    {code: "SECC", description: "Security", parent: "Software"},
                    {code: "UTIL", description: "Utilities", parent: "Software"}
                ];

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
                    // Implement if needed
                    console.log("product search = handleActivated");
                    self.searchProducts('DESK');
                };

                self.itemOnly = function (context) {
                    return context['leaf'];
                };

                self.parseProduct = function (response) {
                    response.compareProduct = ko.observable();

                    self.allProduct.push(response);
                };

                self.searchProducts = function (productType) {
                    if (document.getElementById('pageErrorContainer') !== null)
                    {
                        document.getElementById('pageErrorContainer').hidden = true;
                    }

                    console.log("Search products by " + productType);

                    // Remove previous search results
                    self.allProduct([]);

                    var ProductModel = oj.Model.extend({
                        urlRoot: self.findProductsByProductTypeService + "/" + productType,
                        parse: self.parseProduct,
                        idAttribute: 'prdUid'
                    });
                    var product = new ProductModel();

                    var ProductCollection = oj.Collection.extend({
                        url: self.findProductsByProductTypeService + "/" + productType,
                        model: product,
                        comparator: 'prdUid'
                    });

                    self.collection = new ProductCollection();

                    self.collection.fetch({
                        success: function (myModel, response, options) {
                            console.log("Search success");
                            return false;
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log("Search failed" + errorThrown);
                            return false;
                        }
                    });
                };

                self.productCategoryBreadcrumbs = ko.computed(function () {

                    var result = $.grep(self.productCategories, function (item) {
                        return item.code === self.selectedProductMenuItem();
                    });
                    var category;
                    if (result.length === 1)
                    {
                        category = result[0];
                    }

                    return category.parent + ' > ' + category.description;
                });

                self.filteredAllProduct = ko.computed(function () {

                    var productFilter = new Array();

                    if (self.allProduct().length !== 0) {
                        if (self.nameSearch().length === 0)
                        {
                            productFilter = self.allProduct();
                        } else {
                            ko.utils.arrayFilter(self.allProduct(),
                                    function (r) {
                                        var token = self.nameSearch().toLowerCase();

                                        if (r.prdName.toLowerCase().indexOf(token) >= 0) {
                                            productFilter.push(r);
                                        }
                                    });
                        }
                    }

                    self.ready(true);
                    return productFilter;
                });

                self.listViewDataSource = ko.computed(function () {
                    return new oj.ArrayTableDataSource(self.filteredAllProduct(), {idAttribute: 'prdUid'});
                });

                self.cardViewDataSource = ko.computed(function () {
                    return new oj.PagingTableDataSource(self.listViewDataSource());
                });

                /**
                 * Get the photo for the product
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

                self.navigateToProductSearch = function () {
                    console.log("continue shopping");
                    $("#addToCartConfirmationDialog").ojDialog("close");
                    return self.router.go("productSearch");
                };

                self.navigateToCart = function () {
                    return self.router.go("cart");
                };

                self.navigateToProductDetail = function (product) {
                    console.log("navigating to product detail for " + product.prdUid);
                    // Store product id parameter
                    self.router.store(product.prdUid);
                    return self.router.go("productDetail");
                };

                self.navigateToCompareProducts = function ()
                {
                    document.getElementById('pageErrorContainer').hidden = true;

                    var productsToCompare = [];
                    var val;

                    for (val in self.filteredAllProduct())
                    {
                        if (self.filteredAllProduct()[val].compareProduct())
                        {
                            productsToCompare.push(self.filteredAllProduct()[val]);
                        }
                    }

                    if (productsToCompare.length === 2)
                    {
                        sessionStorage.productsToCompare = JSON.stringify(productsToCompare);
                        sessionStorage.productsToCompareBreadcrumbs = self.productCategoryBreadcrumbs();
                        
                        return self.router.go("productCompare");
                    } else
                    {
                        self.errorMessage("Please select two products to compare");
                        document.getElementById('pageErrorContainer').hidden = false;
                        return false;
                    }
                };

                self.productSelectChange = function (event, data)
                {
                    // Only call if small screen
                    if (self.smallOnly() && data.value !== "")
                    {
                        self.searchProducts(data.value);
                    }
                };
            }

            /*
             * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
             * each time the view is displayed.  Return an instance of the ViewModel if
             * only one instance of the ViewModel is needed.
             */
            return new ProductSearchViewModel();
        }
);
