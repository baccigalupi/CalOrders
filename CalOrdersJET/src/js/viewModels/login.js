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

/**
 * The login view model supports the login page.
 * 
 * @param {type} oj oracle jet core libraries
 * @param {type} ko knockout core libraries
 * @param {type} $ jquery
 * @returns {loginL#29.LoginViewModel|LoginViewModel}
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'common/SecurityUtils', 'ojs/ojarraytabledatasource'],
        function (oj, ko, $) {






            function LoginViewModel() {
                var self = this;

                self.username = ko.observable();
                self.password = ko.observable('PASSWORD');

                self.doShowErrorMessage = ko.observable(false);
                self.router = oj.Router.rootInstance;
                self.isSuccess = false;
                self.tracker = ko.observable();

                self.userNameMessages = ko.observableArray([]);
                self.passwordMessages = ko.observableArray([]);







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

                    console.log("login self.handleActivated called!");
                    self.username = ko.observable();
                    self.doShowErrorMessage = ko.observable(false);
                    SecurityUtils.clearSessionStorage();
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
                    console.log("login handleDetached!");
                };


                self.cancelClick = function (data, event) {
                    self.username = null;
                    self.doShowErrorMessage = false;
                    self.isSuccess = false;
                    self.tracker = null;

                    self.userNameMessages = null;
                    self.passwordMessages = null;

                    return self.router.go('welcome');
                };




                self.continueClick = function (data, event) {


                    self.isSuccess = false;

                    if (oj.StringUtils.isEmptyOrUndefined(self.username()) || oj.StringUtils.isEmptyOrUndefined(self.password()))
                    {
                        self.doShowErrorMessage = true;
                        console.log(self.username());

                        document.getElementById('loginError').hidden = false;

                        return false;
                    }



                    var trackerObj = ko.utils.unwrapObservable(self.tracker);

                    // Perform form level validation
                    if (!this.showComponentValidationErrors(trackerObj))
                    {
                        return;
                    }



                    var serviceEndPoints = new ServiceEndPoints();
                    var serviceURL = serviceEndPoints.getEndPoint('findPartyByUserIdAndPassword');
                    serviceURL += "/" + self.username() + "/" + self.password();

                    console.log("serviceURL is :" + serviceURL);



                    var PartyModel = oj.Model.extend({
                        urlRoot: serviceURL,
                        idAttribute: 'ptyUid'
                    });

                    var party = new PartyModel();

                    party.fetch({
                        success: function () {
                            console.log("Party: ", party.attributes);

                            document.getElementById('loginError').hidden = true;


                            sessionStorage.userFullName = party.attributes.ptyFirstNm + " " + party.attributes.ptyLastNm;
                            sessionStorage.firstName = party.attributes.ptyFirstNm;
                            sessionStorage.lastName = party.attributes.ptyLastNm;
                            sessionStorage.partyUid = party.attributes.ptyUid;
                            sessionStorage.departmentUid = party.attributes.depUid;
                            sessionStorage.departmentName = party.attributes.depName;
                            sessionStorage.departmentAddressLine1 = party.attributes.depAddressLine1;
                            sessionStorage.departmentAddressLine2 = party.attributes.depAddressLine2;
                            sessionStorage.departmentCityStateZip = party.attributes.calculatedCityStateZip;
                            sessionStorage.userName = party.attributes.ptyUserId;
                            sessionStorage.cartProducts = [];

                            var temp = null;

                            var privilege = {
                                grpUid: '',
                                canRead: '',
                                canWrite: '',
                                pageName: '',
                                componentId: ''
                            };

                            var group = {grpUid: '',
                                grpTypeCd: '',
                                grpTypeCdDescription: '',
                                privileges: []
                            };


                            var groups = [];
                            var isUser = false;

                            for (i = 0; i < party.attributes.groupList.length; i++)
                            {
                                temp = party.attributes.groupList[i];

                                group.grpUid = temp.grpUid;
                                group.grpTypeCd = temp.grpTypeCd;
                                group.grpTypeCdDescription = temp.grpTypeCdDescription;

                                if ('EMP' === group.grpTypeCd)
                                {
                                    isUser = true;
                                }

                                for (j = 0; j < party.attributes.groupList[i].privilegesList.length; j++)
                                {
                                    temp = party.attributes.groupList[i].privilegesList[j];

                                    privilege.grpUid = group.grpUid;
                                    privilege.canRead = temp.canRead;
                                    privilege.canWrite = temp.canWrite;
                                    privilege.componentId = temp.componentIdentifier;
                                    privilege.pageName = temp.pageIdentifier;

                                    group.privileges.push(privilege);
                                }

                                groups.push(group);
                            }

                            sessionStorage.groups = groups;

                            sessionStorage.authenticated = true;

                            navBarDataSource(new oj.ArrayTableDataSource(navBarData, {idAttribute: 'id'}));
                            SecurityUtils.getNavBarItems(navBarDataSource);
                            SecurityUtils.getNavMenuItems(navMenuDataSource);

                            if (isUser)
                            {
                                return self.router.go('productSearch');
                            } else
                            {
                                return self.router.go('dashboard');
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log("Party: not found: " + errorThrown);
                            self.doShowErrorMessage = true;

                            document.getElementById('loginError').hidden = false;
                            document.getElementById('userName').focus(true);
                            return false;
                        }
                    });






                };



                /*
                 * Perform form level validation
                 * 
                 * @param {type} trackerObj
                 * @returns {Boolean}
                 */
                self.showComponentValidationErrors = function (trackerObj)
                {
                    var result = true;
                    try {
                        trackerObj.showMessages();
                        if (trackerObj.focusOnFirstInvalid())
                        {
                            result = false;
                        }

                    } catch (err)
                    {
                        result = false;
                    }

                    return result;
                };

                /**
                 * Determines when the Login button will be disabled, the 
                 * method implements best practices for form submission.
                 * 
                 * - If there are invalid components currently showing messaages 
                 * this method returns true 
                 * and the button is disabled. 
                 * - If there are no visible errors, this method returns fals
                 *  and the button is enabled. 
                 * E.g., when there are deferred errors on the component 
                 * the button is enabled.
                 * 
                 * @return {boolean} 
                 */
                self.isLoginDisabled = function ()
                {
                    var trackerObj = ko.utils.unwrapObservable(self.tracker);
                    var hasInvalidComponents =
                            trackerObj ? trackerObj["invalidShown"] : false;
                    return  hasInvalidComponents;
                };




            }


            /*
             * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
             * each time the view is displayed.  Return an instance of the ViewModel if
             * only one instance of the ViewModel is needed.
             */
            return new LoginViewModel();
        }


);
