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
 * Your dashboard ViewModel code goes here
 */
define(['ojs/ojcore', 'knockout', 'jquery'],
        function (oj, ko, $) {

            function DashboardViewModel() {
                var self = this;


                self.router = oj.Router.rootInstance;

                /* toggle button variables */
                self.orientationValue = ko.observable('vertical');

                /* time chart data */
                var timeSeries = [{name: "EDR LOE", items: [155, 165, 160, 140, 120]},
                    {name: "Business Development", items: [15, 8, 12, 25, 42]},
                    {name: "Holiday (Non-Billable)", items: [0, 8, 0, 16, 0]}];

                var timeGroups = ["1/2016", "2/2016", "3/2016", "4/2016", "5/2016"];


                this.timeSeriesValue = ko.observableArray(timeSeries);
                this.timeGroupsValue = ko.observableArray(timeGroups);



                /* Expense chart data */
                var expenseSeries = [{name: "EDR", items: [400, 0, 56.32, 32.33, 0]},
                    {name: "Business Development", items: [100.43, 800.32, 0, 0, 323.32]},
                    {name: "OnCore", items: [80, 80, 80, 80, 80]}];

                var expenseGroups = ["1/2016", "2/2016", "3/2016", "4/2016", "5/2016"];


                this.expenseSeriesValue = ko.observableArray(expenseSeries);
                this.expenseGroupsValue = ko.observableArray(expenseGroups);




                /* PTO chart data */
                var ptoSeries = [{name: "Vacation", items: [16, 0, 8, 12, 0]},
                    {name: "Birthday", items: [0, 0, 0, 0, 8]},
                    {name: "Floating", items: [0, 8, 0, 0, 0]},
                    {name: "Holiday", items: [16, 0, 8, 8, 8]}];

                var ptoGroups = ["1/2016", "2/2016", "3/2016", "4/2016", "5/2016"];


                this.ptoSeriesValue = ko.observableArray(ptoSeries);
                this.ptoGroupsValue = ko.observableArray(ptoGroups);



                var profileArray = [{Key: 'Name', Value: 'Ricky Bobby'},
                    {Key: 'Phone', Value: '916.392.3233'},
                    {Key: 'Address', Value: '3923 Racecar Ct Folsom, CA 95630'},
                    {Key: 'Hire Date', Value: '04/01/2016'},
                    {Key: 'Title', Value: 'Driver'}];
                self.datasource = new oj.ArrayTableDataSource(profileArray, {idAttribute: 'Key'});










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
            }




            return new DashboardViewModel();
        }
);






 