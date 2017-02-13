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

            function ProductSearchViewModel() {
                var self = this;

                var serviceEndPoints = new ServiceEndPoints();

                self.router = oj.Router.rootInstance;
                self.findProductsByProductTypeService = serviceEndPoints.getEndPoint("findActiveProductsByProductType");
                self.selectedProductMenuItem = ko.observable('desktops');
                self.productLayoutType = ko.observable('productCardLayout');
                self.allProduct = ko.observableArray([]);
                self.ready = ko.observable(false);
                self.nameSearch = ko.observable('');
                self.addedProductPhoto = ko.observable();
                self.addedProductName = ko.observable();

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

                self.itemOnly = function (context) {
                    return context['leaf'];
                };

                self.parseProduct = function (response) {
                    self.allProduct.push(response);
                };

                self.searchProducts = function (productType) {
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

                // Perform default product search
                self.searchProducts('DESK');

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

                self.cardLayoutHandler = function () {
                    self.productLayoutType('productCardLayout');
                };

                self.listLayoutHandler = function () {
                    self.productLayoutType('productListLayout');
                };

                self.navigateToProductDetail = function (product) {
                    console.log("navigating to product detail for " + product.prdUid);
                    // Store product id parameter
                    self.router.store(product.prdUid);
                    return self.router.go("productDetail");
                };

                self.onEnter = function (data, event) {
                    if (event.keyCode === 13) {
                        var emp = {};
                        emp.empId = data.empId;
                        self.loadPersonPage(emp);
                    }
                    return true;
                };

                self.addToCart = function (product) {
                    // TODO: Add product to cart
                    console.log("Add product id " + product.prdUid + " to cart");
                    
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
            }

    /*
     * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
     * each time the view is displayed.  Return an instance of the ViewModel if
     * only one instance of the ViewModel is needed.
     */
    return new ProductSearchViewModel();
}
);
