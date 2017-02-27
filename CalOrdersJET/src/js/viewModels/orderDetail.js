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
define(['ojs/ojcore', 'knockout', 'data/data', 'accounting', 'moment', 'common/SecurityUtils', 'ojs/ojrouter',
    'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojmodel', 'ojs/ojpagingcontrol',
    'ojs/ojpagingcontrol-model', 'utils/ProductHelper'],
        function (oj, ko, data, accounting, moment) {

            function OrderDetailViewModel() {
                var self = this;

                var serviceEndPoints = new ServiceEndPoints();
                self.findOrderServiceEndPoint = serviceEndPoints.getEndPoint("findOrderDetailById");
                self.cancelOrerServiceEndPoint = serviceEndPoints.getEndPoint("cancelOrder");
                self.router = oj.Router.rootInstance;
                self.errorMessage = ko.observable();
                self.orderUid = ko.observable();
                self.orderDetail = ko.observable();
                self.orderProducts = ko.observableArray([]);
                self.ready = ko.observable(false);

                self.listViewDataSource = null;
                self.cardViewDataSource = null;
                self.productLayoutType = ko.observable('productListdLayout');

                var lgQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.LG_UP);
                var mdQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.MD_UP);
                var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_UP);
                var smOnlyQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
                self.large = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(lgQuery);
                self.medium = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(mdQuery);
                self.small = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);
                self.smallOnly = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smOnlyQuery);

                self.parseOrderDetail = function (response)
                {
                    self.ready(false);

                    // Format prices
                    response.shippingPrice = accounting.formatMoney(response.shippingPrice);
                    response.totalPrice = accounting.formatMoney(response.totalPrice);
                    response.productTotalPrice = accounting.formatMoney(response.productTotalPrice);
                    response.orderDate = moment().format('MM/DD/YYYY');
                    var i;
                    for (i in response.orderDetailProductDataList)
                    {
                        response.orderDetailProductDataList[i].prdPrice = accounting.formatMoney(response.orderDetailProductDataList[i].prdPrice);
                        self.orderProducts.push(response.orderDetailProductDataList[i]);
                    }

                    self.orderDetail(response);

                    self.listViewDataSource = ko.computed(function () {
                        return new oj.ArrayTableDataSource(self.orderProducts(), {idAttribute: 'prdUid'});
                    });

                    self.cardViewDataSource = ko.computed(function () {
                        return new oj.PagingTableDataSource(self.listViewDataSource());
                    });

                    self.ready(true);
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

                    // Init
                    self.orderDetail(new Object());
                    self.orderProducts([]);

                    self.orderUid(self.router.retrieve());

                    var OrderDetailModel = oj.Model.extend({
                        urlRoot: self.findOrderServiceEndPoint + "/" + self.orderUid(),
                        parse: self.parseOrderDetail,
                        attributeId: 'id'
                    });

                    var orderDetail = new OrderDetailModel();
                    orderDetail.fetch({
                        success: function (myModel, response, options) {
                            return false;
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            self.errorMessage("There was an error loading the order detail");
                            return false;
                        }
                    });
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
            return new OrderDetailViewModel();
        }
);
