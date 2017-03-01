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
 * Your customer ViewModel code goes here
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'moment', 'accounting', 'common/SecurityUtils'],
        function (oj, ko, $, moment, accounting) {

            function OrdersAdminViewModel() {
                var self = this;
                self.router = oj.Router.rootInstance;
                var self = this;
                var serviceEndPoints = new ServiceEndPoints();
                self.serviceURL = serviceEndPoints.getEndPoint('findAllOrderHistory');
                self.orderHistoryCol = ko.observable();
                self.datasource = ko.observable();
                self.tracker = ko.observable();
                self.index = ko.observable();
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
                    self.index = ko.observable();
                    self.orderHistoryCol = ko.observable();
                    self.datasource = ko.observable();
                    self.serviceURL = serviceEndPoints.getEndPoint('findAllOrderHistory');
                    var OrderHistoryModel = oj.Model.extend({
                        urlRoot: self.serviceURL,
                        parse: self.parseOrderHistory,
                        idAttribute: 'orderHistoryId'
                    });
                    var orderHistory = new OrderHistoryModel();
                    var OrderHistoryCollection = oj.Collection.extend({
                        url: self.serviceURL,
                        model: orderHistory,
                        comparator: 'orderDate',
                        sortDirection: -1
                    });
                    self.orderHistoryCol = new OrderHistoryCollection();

                    self.datasource(new oj.PagingTableDataSource(new oj.CollectionTableDataSource(self.orderHistoryCol, {idAttribute: 'orderHistoryId'})));
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
                self.fetch = function (successCallBack) {
                    self.orderHistoryCol().fetch({
                        success: successCallBack,
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log('Error in fetch: ' + textStatus);
                        }
                    });
                };
                self.parseOrderHistory = function (response) {
                    var orderPoNumber = "";

                    if (response.orderPoNumber !== undefined)
                    {
                        orderPoNumber = response.orderPoNumber;
                    }

                    var result = {'orderHistoryId': response['orderHistoryId'],
                        'orderDate': response['orderDate'],
                        'orderStatus': response['orderStatus'],
                        'orderPoNumber': orderPoNumber,
                        'orderAgency': response['orderAgency'],
                        'orderPrice': response['orderPrice'],
                        'orderDescription': response['orderDescription']};
                    return result;
                };

                self.datasource.comparator = function (rowA, rowB)
                {
                    var key = this['sortCriteria']['key'];
                    var direction = this['sortCriteria']['direction'];
                    var a, b;
                    a = rowA[key];
                    b = rowB[key];

                    if (a === b)
                    {
                        return 0;
                    }

                    if (key != 'orderDate')
                    {
                        if (direction == 'ascending')
                        {
                            return a < b ? -1 : 1;
                        } else
                        {
                            return a > b ? -1 : 1;
                        }
                    } else
                    {
                        var dateA = new Date(a).getTime();
                        var dateB = new Date(b).getTime();

                        if (direction == 'ascending')
                        {
                            return dateA < dateB ? -1 : 1;
                        } else
                        {
                            return dateA > dateB ? -1 : 1;
                        }
                    }
                };

                self.refreshClick = function (data, event) {
                    self.orderHistoryCol.reset();
                };


                self.priceRenderer = function (context)
                {
                    return accounting.formatMoney(context.row.orderPrice);
                };

                self.orderDateRenderer = function (context) {
                    if (context.row.orderDate !== undefined)
                    {
                        return moment(context.row.orderDate).format('MM/DD/YYYY');
                    } else {
                        return "";
                    }
                };

                self.currentRowListener = function (event, ui) {
                    var orderHistoryId = self.datasource._latestValue.dataSource.data.models[ui.currentRow.rowIndex].attributes.orderHistoryId;
                    self.navigateToOrderDetail(orderHistoryId);
                };


                self.navigateToOrderDetail = function (orderHistoryId) {
                    // Store order id parameter
                    self.router.store(orderHistoryId);
                    return self.router.go("orderDetail");
                };
            }


            /*
             * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
             * each time the view is displayed.  Return an instance of the ViewModel if
             * only one instance of the ViewModel is needed.
             */
            return new OrdersAdminViewModel();

        }
);
