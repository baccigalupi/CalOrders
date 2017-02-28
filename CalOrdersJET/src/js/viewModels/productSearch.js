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
define(['ojs/ojcore', 'knockout', 'data/data', 'accounting', 'common/SecurityUtils',
    'ojs/ojrouter', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojmodel',
    'ojs/ojpagingcontrol', 'ojs/ojpagingcontrol-model', 'utils/ProductHelper'],
        function (oj, ko, data, accounting) {

            function ProductSearchViewModel() {
                var self = this;

                var serviceEndPoints = new ServiceEndPoints();

                self.router = oj.Router.rootInstance;
                self.findProductsByProductTypeService = "";
                self.selectedProductMenuItem = ko.observable('DHST');
                self.productLayoutType = ko.observable('productCardLayout');
                self.allProduct = ko.observableArray([]);
                self.ready = ko.observable(false);
                self.nameSearch = ko.observable('');
                self.addedProductPhoto = ko.observable();
                self.addedProductName = ko.observable();
                self.productCategoryBreadcrumbs = ko.observable();
                self.errorMessage = ko.observable();
                self.admin = ko.observable(false);
                self.user = ko.observable(false);
                self.cartTotalPrice = ko.observable();
                self.cartTotalItems = ko.observable();

                var lgQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.LG_UP);
                var mdQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.MD_UP);
                var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_UP);
                var smOnlyQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
                self.large = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(lgQuery);
                self.medium = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(mdQuery);
                self.small = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);
                self.smallOnly = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smOnlyQuery);


                // Load Categories

                self.productCategories = [
                    {code: "DHST", level1: "Desktop Systems", level2: "Desktop Computers", level3: "Standard"},
                    {code: "DHPO", level1: "Desktop Systems", level2: "Desktop Computers", level3: "Power"},
                    {code: "DHWO", level1: "Desktop Systems", level2: "Desktop Computers", level3: "Workstation"},
                    {code: "DHTH", level1: "Desktop Systems", level2: "Desktop Computers", level3: "Thin Client"},
                    {code: "DHAL", level1: "Desktop Systems", level2: "Desktop Computers", level3: "All in One"},
                    {code: "DUST", level1: "Desktop Systems", level2: "Desktop Upgrades", level3: "Standard "},
                    {code: "DUPO", level1: "Desktop Systems", level2: "Desktop Upgrades", level3: "Power"},
                    {code: "DUWO", level1: "Desktop Systems", level2: "Desktop Upgrades", level3: "Workstation"},
                    {code: "DUTH", level1: "Desktop Systems", level2: "Desktop Upgrades", level3: "Thin Client"},
                    {code: "DUAL", level1: "Desktop Systems", level2: "Desktop Upgrades", level3: "All in One"},
                    {code: "DUMO", level1: "Monitors", level2: "Monitor Upgrades", level3: ""},
                    {code: "DMMO", level1: "Monitors", level2: "Monitors", level3: ""},
                    {code: "DSPO", level1: "Desktop Systems", level2: "Desktop Services", level3: "Power"},
                    {code: "DSST", level1: "Desktop Systems", level2: "Desktop Services", level3: "Standard"},
                    {code: "DSMO", level1: "Desktop Systems", level2: "Desktop Services", level3: "Monitor"},
                    {code: "DSWO", level1: "Desktop Systems", level2: "Desktop Services", level3: "Workstation"},
                    {code: "DSTH", level1: "Desktop Systems", level2: "Desktop Services", level3: "Thin Client"},
                    {code: "DSAL", level1: "Desktop Systems", level2: "Desktop Services", level3: "All in One"},
                    {code: "DAAL", level1: "Independent Services", level2: "Desktop Services", level3: ""},
                    {code: "LHST", level1: "Laptop Systems", level2: "Laptop Computers", level3: "Standard"},
                    {code: "LHPO", level1: "Laptop Systems", level2: "Laptop Computers", level3: "Power"},
                    {code: "LHUL", level1: "Laptop Systems", level2: "Laptop Computers", level3: "Ultralight"},
                    {code: "LHMO", level1: "Laptop Systems", level2: "Laptop Computers", level3: "Mobile"},
                    {code: "LUMO", level1: "Laptop Systems", level2: "Laptop Upgrades", level3: "Mobile"},
                    {code: "LUAL", level1: "Laptop Systems", level2: "Laptop Upgrades", level3: "All"},
                    {code: "LUST", level1: "Laptop Systems", level2: "Laptop Upgrades", level3: "Standard"},
                    {code: "LUPO", level1: "Laptop Systems", level2: "Laptop Upgrades", level3: "Power"},
                    {code: "LUUL", level1: "Laptop Systems", level2: "Laptop Upgrades", level3: "Ultralight"},
                    {code: "LSMO", level1: "Laptop Systems", level2: "Laptop Services", level3: "Mobile"},
                    {code: "LSPO", level1: "Laptop Systems", level2: "Laptop Services", level3: "Power"},
                    {code: "LSUL", level1: "Laptop Systems", level2: "Laptop Services", level3: "Ultralight"},
                    {code: "LSST", level1: "Laptop Systems", level2: "Laptop Services", level3: "Standard"},
                    {code: "LSAL", level1: "Laptop Systems", level2: "Laptop Services", level3: "All"},
                    {code: "LAAL", level1: "Independent Services", level2: "Laptop Services", level3: ""},
                    {code: "DUOT", level1: "Desktop Systems", level2: "Desktop Upgrades", level3: "Other"},

                    {code: "OPSY", level1: "Software", level2: "OS", level3: ""},
                    {code: "OFFC", level1: "Software", level2: "Office", level3: ""},
                    {code: "SECC", level1: "Software", level2: "Security", level3: ""},
                    {code: "UTIL", level1: "Software", level2: "Utilities", level3: ""}

                ];





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

                    if (sessionStorage.admin === 'true')
                    {
                        self.admin(true);
                        self.user(false);
                        self.findProductsByProductTypeService = serviceEndPoints.getEndPoint("findProductsByProductType");
                    } else
                    {
                        self.findProductsByProductTypeService = serviceEndPoints.getEndPoint("findActiveProductsByProductType");
                        self.admin(false);
                        self.user(true);
                    }

                    if (sessionStorage.keepSearchResults !== 'true')
                    {
                        self.nameSearch = ko.observable('');
                        self.selectedProductMenuItem('DHST');
                        self.searchProducts('DHST');
                    } else
                    {
                        sessionStorage.keepSearchResults = false;
                    }

                    self.cartTotalPrice(self.getPrice(sessionStorage.itemTotalPrice));
                    self.cartTotalItems(sessionStorage.itemQuantityTotal);
                };

                self.itemOnly = function (context) {
                    return context['leaf'];
                };

                self.getPrice = function (price)
                {
                    return accounting.formatMoney(price);
                };

                self.parseProduct = function (response) {
                    response.compareProduct = ko.observable();
                    response.quantity = ko.observable(1);
                    response.prdLongDescLines = response.prdLongDesc.split("\n");

                    response.prdLongDescLines = response.prdLongDesc.split("\n");

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

                    var breadCrumbs, category;
                    var result = $.grep(self.productCategories, function (item) {
                        return item.code === self.selectedProductMenuItem();
                    });
                    if (result.length === 1)
                    {
                        category = result[0];
                    }


                    breadCrumbs = category.level1 + ' > ' + category.level2;

                    if (category.level3 !== "")
                    {
                        breadCrumbs += ' > ' + category.level3;
                    }

                    return breadCrumbs;
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

                                        if (r.prdName.toLowerCase().indexOf(token) >= 0
                                                || r.prdLongDesc.toLowerCase().indexOf(token) >= 0) {
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
                    self.errorMessage("");
                    document.getElementById('pageErrorContainer').hidden = true;

                    if (Number(product.quantity()) <= 0 || Number(product.quantity()) > 10000)
                    {
                        self.errorMessage("Please enter a quantity between 1 and 10,000");
                        document.getElementById('pageErrorContainer').hidden = false;
                        return false;
                    } else
                    {

                        ProductHelper.addProductToCart(product);

                        // Show confirmation message                    
                        self.addedProductName(product.prdName);
                        self.addedProductPhoto($("#productImage" + product.prdUid).attr("src"));
                        $("#addToCartConfirmationDialog").ojDialog("open");
                    }
                };

                self.navigateToProductSearch = function () {
                    self.cartTotalPrice(self.getPrice(sessionStorage.itemTotalPrice));
                    self.cartTotalItems(sessionStorage.itemQuantityTotal);

                    $("#addToCartConfirmationDialog").ojDialog("close");
                    return self.router.go("productSearch");
                };

                self.navigateToCart = function () {
                    return self.router.go("cart");
                };

                self.navigateToProductDetail = function (product) {

                    sessionStorage.productsToCompareBreadcrumbs = self.productCategoryBreadcrumbs();

                    // Store product id parameter
                    self.router.store(product.prdUid);
                    return self.router.go("productDetail");
                };


                self.navigateToAddProduct = function (product) {
                    // Store product id parameter
                    return self.router.go("productAdd");
                };

                self.navigateToCompareProducts = function ()
                {
                    document.getElementById('pageErrorContainer').hidden = true;

                    var productsToCompare = [];
                    var val;

                    for (val in self.filteredAllProduct())
                    {
                        console.log("compare:" + self.filteredAllProduct()[val].compareProduct());

                        if (self.filteredAllProduct()[val].compareProduct() !== undefined
                                && self.filteredAllProduct()[val].compareProduct())
                        {
                            console.log("adding item to compare list");
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

                self.getCartTotalPrice = function (event, data)
                {
                    return self.getPrice(sessionStorage.itemTotalPrice);
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
