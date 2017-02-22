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
                self.stackValue = ko.observable();
                self.stackLabelValue = ko.observable();
                self.orientationValue = ko.observable();
                self.labelPosition = ko.observable();
                self.barSeriesValue = ko.observableArray();
                self.barGroupsValue = ko.observableArray();
                /* pie series data */
                self.threeDValue = ko.observable();
                self.dataLabelPositionValue = ko.observable();
                self.pieSeriesValue = ko.observableArray();
                self.pieSeriesCollection = null;




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


                self.threeDOptions = [
                    {id: '2D', label: '2D', value: 'off', icon: 'oj-icon demo-2d'},
                    {id: '3D', label: '3D', value: 'on', icon: 'oj-icon demo-3d'}
                ];

                self.threeDOptions = [
                    {id: '2D', label: '2D', value: 'off', icon: 'oj-icon demo-2d'},
                    {id: '3D', label: '3D', value: 'on', icon: 'oj-icon demo-3d'}
                ];


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

                    self.ordersByQuarterCollection = null;
                    self.stackValue('on');
                    self.stackLabelValue('on');
                    self.orientationValue('vertical');
                    self.labelPosition('auto');
                    self.barSeriesValue = ko.observableArray();
                    self.barGroupsValue = ko.observableArray();
                    self.threeDValue('off');
                    self.dataLabelPositionValue('auto');
                    self.pieSeriesValue = ko.observableArray();
                    self.pieSeriesCollection = null;




                    var serviceEndPoints = new ServiceEndPoints();
                    var serviceURL = serviceEndPoints.getEndPoint('fetchOrdersByQuarter');
                    serviceURL += "/" + sessionStorage.departmentUid;
                    
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


                    serviceEndPoints = new ServiceEndPoints();
                    serviceURL = serviceEndPoints.getEndPoint('fetchOrderStatusSummary');
                    serviceURL += "/" + sessionStorage.departmentUid;

                    self.pieSeriesCollection = new oj.Collection();

                    var pieModel = oj.Model.extend({
                        urlRoot: serviceURL,
                        parse: parsePieData
                    });

                    var pie = new pieModel();

                    self.PieCollection = oj.Collection.extend({
                        url: serviceURL,
                        model: pie
                    });


                    pie.fetch({
                        success: function () {
                            self.pieSeriesValue(self.pieSeriesCollection.models[0].attributes.items);
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


                /**
                 * Parse data returned from REST call
                 * 
                 * @param {type} response
                 * @returns {undefined}
                 */
                var parseOrderByQuarter = function (response)
                {
                    self.ordersByQuarterCollection.push(response);
                };

                /**
                 * Parse data returned from REST call
                 * 
                 * @param {type} response
                 * @returns {undefined}
                 */
                var parsePieData = function (response)
                {
                    self.pieSeriesCollection.push(response);
                };


                /**
                 * Adjusts position of labels on bar chart when 
                 * the chart alignment and format changes.
                 * 
                 * @param {type} event
                 * @param {type} ui
                 * @returns {Boolean}
                 */
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
                
                /**
                 * Adjusts the icon, text, value, labels etc. for the
                 * for the bar chart when in stacked view
                 * 
                 * @param {type} event
                 * @param {type} ui
                 * @returns {undefined}
                 */
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



                /**
                 * Adjusts the pie chart format from 2D to 3D and 
                 * vice versa
                 * 
                 * @param {type} event
                 * @param {type} data
                 * @returns {Boolean}
                 */
                self.threeDValueChange = function (event, data) {
                    self.threeDValue(data.value);
                    return true;
                };

            }

            return new DashboardViewModel();
        }
);







