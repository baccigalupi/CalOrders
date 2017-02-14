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

            function OrderConfirmationViewModel() {
                var self = this;

                self.addressLine1 = ko.observable();
                self.addressLine2 = ko.observable();
                self.cityStateZip = ko.observable();
                self.itemTotalPrice = ko.observable();
                self.totalPrice = ko.observable();
                self.shippingPrice = ko.observable();




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


                    self.addressLine1('100 Test St');
                    self.addressLine2('Suite 100');
                    self.cityStateZip('Folsom, CA 95630');
                    self.itemTotalPrice('$3923.00');
                    self.totalPrice('$939.99');
                    self.shippingPrice('$34.93');


                    if (sessionStorage.authenticated === "false")
                    {
                        return self.router.go('welcome');
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

                self.itemOnly = function (context) {
                    return context['leaf'];
                };



                self.searchProducts = function (productType) {
                    var filename = 'js/data/products_desktops.json';

                    console.log("Search " + productType + " products");

                    if (productType === 'laptops') {
                        filename = 'js/data/products_laptops.json'
                    }

                    data.fetchData(filename).then(function (people) {
                        self.allPeople(people.products);
                    }).fail(function (error) {
                        console.log('Error in getting People data: ' + error.message);
                    });

                    self.model = oj.Model.extend({
                        idAttribute: 'productId'
                    });

                    self.collection = new oj.Collection(null, {
                        url: filename,
                        model: self.model
                    });
                };


                self.getPhoto = function (productId) {
                    console.log("Image for product " + productId);
                    var src = 'css/images/desktop.png';

                    if (productId < 1000)
                    {
                        src = 'css/images/laptop.png';
                    }

                    return src;
                };


                self.selectedItem = ko.observable('home');

                self.peopleLayoutType = ko.observable('peopleCardLayout');

                self.allPeople = ko.observableArray([]);
                self.ready = ko.observable(false);

                // Perform default search
                console.log("About to perform default search");
                self.searchProducts('desktops');

                self.nameSearch = ko.observable('');

                self.filteredAllPeople = ko.computed(function () {
                    var peopleFilter = new Array();

                    if (self.allPeople().length !== 0) {
                        if (self.nameSearch().length === 0)
                        {
                            peopleFilter = self.allPeople();
                        } else {
                            ko.utils.arrayFilter(self.allPeople(),
                                    function (r) {
                                        var token = self.nameSearch().toLowerCase();
                                        if (r.firstName.toLowerCase().indexOf(token) === 0 || r.lastName.toLowerCase().indexOf(token) === 0) {
                                            peopleFilter.push(r);
                                        }
                                    });
                        }
                    }

                    self.ready(true);
                    return peopleFilter;
                });

                self.listViewDataSource = ko.computed(function () {
                    return new oj.ArrayTableDataSource(self.filteredAllPeople(), {idAttribute: 'productId'});
                });

                self.cardViewDataSource = ko.computed(function () {
                    return new oj.PagingTableDataSource(self.listViewDataSource());
                });



                self.cardLayoutHandler = function () {
                    self.peopleLayoutType('peopleCardLayout');
                };

                self.listLayoutHandler = function () {
                    self.peopleLayoutType('peopleListLayout');
                };

                self.loadPersonPage = function (emp) {
                    if (emp.empId) {
                        // Temporary code until go('person/' + emp.empId); is checked in 1.1.2
                        history.pushState(null, '', 'index.html?root=person&emp=' + emp.empId);
                        oj.Router.sync();
                    } else {
                        // Default id for person is 100 so no need to specify.
                        oj.Router.rootInstance.go('person');
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
                    return true;
                };

                self.continueShoppingClick = function (data, event)
                {
                    return self.router.go("productSearch");
                };

                self.onEnter = function (data, event) {
                    if (event.keyCode === 13) {
                        var emp = {};
                        emp.empId = data.empId;
                        self.loadPersonPage(emp);
                    }
                    return true;
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

                self.addToCart = function (product) {
                    console.log("Add product id " + product.productId + " to cart");
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
