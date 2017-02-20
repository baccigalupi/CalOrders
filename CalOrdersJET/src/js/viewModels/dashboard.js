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
define(['ojs/ojcore', 'knockout', 'jquery', 'common/SecurityUtils'],
        function (oj, ko, $) {

            function DashboardViewModel() {
                var self = this;


                self.router = oj.Router.rootInstance;




                /* toggle button variables */

                self.ordersByQuarterCollection = null;
                self.stackValue = ko.observable('on');
                self.stackLabelValue = ko.observable('on');
                self.orientationValue = ko.observable('vertical');
                self.labelPosition = ko.observable('auto');




                self.barSeriesValue = ko.observableArray();
                self.barGroupsValue = ko.observableArray();

                /* toggle buttons*/
                self.stackOptions = [
                    {id: 'unstacked', label: 'unstacked', value: 'off', icon: 'oj-icon demo-bar-unstack'},
                    {id: 'stacked', label: 'stacked', value: 'on', icon: 'oj-icon demo-bar-stack'}
                ];
                self.stackLabelOptions = [
                    {id: 'labelOff', label: 'off', value: 'off'},
                    {id: 'labelOn', label: 'on', value: 'on'}
                ];
                self.orientationOptions = [
                    {id: 'vertical', label: 'vertical', value: 'vertical', icon: 'oj-icon demo-bar-vert'},
                    {id: 'horizontal', label: 'horizontal', value: 'horizontal', icon: 'oj-icon demo-bar-horiz'}
                ];
                self.labelPositionOptions = ko.observableArray([
                    {id: 'auto', label: 'auto', value: 'auto'},
                    {id: 'center', label: 'center', value: 'center'},
                    {id: 'insideBarEdge', label: 'insideBarEdge', value: 'insideBarEdge'},
                    {id: 'none', label: 'none', value: 'none'}
                ]);







                /* Active orders data */
                self.threeDValue = ko.observable('off');
                self.dataLabelPositionValue = ko.observable('auto');

                /* chart data */
                var pieSeries = [{name: "Submitted", items: [42]},
                    {name: "Processing", items: [55]},
                    {name: "Shipped", items: [36]},
                    {name: "Cancelled", items: [10]}];

                self.pieSeriesValue = ko.observableArray(pieSeries);

                /* toggle buttons*/
                self.threeDOptions = [
                    {id: '2D', label: '2D', value: 'off', icon: 'oj-icon demo-2d'},
                    {id: '3D', label: '3D', value: 'on', icon: 'oj-icon demo-3d'}
                ];
                self.threeDOptions = [
                    {id: '2D', label: '2D', value: 'off', icon: 'oj-icon demo-2d'},
                    {id: '3D', label: '3D', value: 'on', icon: 'oj-icon demo-3d'}
                ];
                self.threeDValueChange = function (event, data) {
                    self.threeDValue(data.value);
                    return true;
                };


                /* Total Sold By Month */
                var expenseSeries = [{name: "Desktops", items: [400, 0, 56.32, 32.33, 0]},
                    {name: "Laptops", items: [100.43, 800.32, 0, 0, 323.32]},
                    {name: "Monitors", items: [80, 80, 80, 80, 80]}];

                var expenseGroups = ["1/2016", "2/2016", "3/2016", "4/2016", "5/2016"];


                this.expenseSeriesValue = ko.observableArray(expenseSeries);
                this.expenseGroupsValue = ko.observableArray(expenseGroups);




                /* Orders by Agency */
                var ptoSeries = [{name: "DOJ", items: [16, 0, 8, 12, 0]},
                    {name: "FTB", items: [0, 0, 0, 0, 8]},
                    {name: "DTS", items: [0, 8, 0, 0, 0]},
                    {name: "BOE", items: [16, 0, 8, 8, 8]}];

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
                    
                    if (!SecurityUtils.isAuthenticated()) {
                        return self.router.go('welcome');
                    }
 
                    var serviceEndPoints = new ServiceEndPoints();
                    var serviceURL = serviceEndPoints.getEndPoint('fetchOrdersByQuarter');

                    self.ordersByQuarterCollection = new oj.Collection();
                    
                    var dashModel = oj.Model.extend({
                        urlRoot: serviceURL,
                        parse: parseOrderByQuarter
                    });

                    var dash = new dashModel();

                    self.DashCollection = oj.Collection.extend({
                        url: serviceURL,
                        model: dash
                    });


                    dash.fetch({
                        success: function () {

                            // load data for Total Orders by Quarter Chart
                   
                            var barGroups = ["2014", "2015", "2016", "2017"];
 
                            self.barSeriesValue(self.ordersByQuarterCollection.models[0].attributes.ordersByQuarterDataList);
                            self.barGroupsValue(barGroups);
 

                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log("Error occurred when populating the dashboard" + errorThrown);

                            return false;
                        }
                    });
 

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


 

                /* Active orders by month data */
 
                var parseOrderByQuarter = function (response)
                {
                   self.ordersByQuarterCollection.push(response);
                };


                self.labelPositionChange = function (event, ui) {
                    var seriesInfo = ko.toJS(self.barSeriesValue);
                    for (var i = 0; i < seriesInfo.length; i++) {
                        for (var j = 0; j < seriesInfo[i].items.length; j++) {
                            seriesInfo[i].items[j].labelPosition = ui.value;
                        }
                    }
                    self.barSeriesValue(seriesInfo);
                    return true;
                };
                self.stackLValueChange = function (event, ui) {
                    var isOn = true;
                    if (ui.value == "on") {
                        isOn = false;
                        self.labelPositionOptions([
                            {id: 'auto', label: 'auto', value: 'auto'},
                            {id: 'center', label: 'center', value: 'center'},
                            {id: 'insideBarEdge', label: 'insideBarEdge', value: 'insideBarEdge'},
                            {id: 'none', label: 'none', value: 'none'}
                        ]);
                    } else {
                        self.labelPositionOptions([
                            {id: 'auto', label: 'auto', value: 'auto'},
                            {id: 'center', label: 'center', value: 'center'},
                            {id: 'insideBarEdge', label: 'insideBarEdge', value: 'insideBarEdge'},
                            {id: 'outsideBarEdge', label: 'outsideBarEdge', value: 'outsideBarEdge'},
                            {id: 'none', label: 'none', value: 'none'}
                        ]);
                    }
                    $("#radioButtonset4").ojButtonset({disabled: isOn});
                };






            }




            return new DashboardViewModel();
        }
);







