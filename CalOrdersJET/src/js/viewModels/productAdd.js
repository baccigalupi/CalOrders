/* 
 * The OnCore Consulting LLC License
 *
 * Copyright 2016 OnCore Consulting LLC, All Rights Reserved.
 *
 * Permission IS NOT GRANTED to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, unless the following conditions are met:
 *
 * Written permission is obtained from  
 * OnCore Consulting LLC and the above copyright 
 * notice and this permission notice shall be included in
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
define(['ojs/ojcore', 'knockout', 'jquery', 'libs/accounting/accounting', 'common/SecurityUtils', 'reference/ReferenceData', 'utils/ProductHelper'],
        function (oj, ko, $, accounting) {

            function ProductAddViewModel() {
                var self = this;
                self.applicationVersion = ko.observable("1.0");
                var serviceEndPoints = new ServiceEndPoints();
                self.serviceURL = serviceEndPoints.getEndPoint('createProduct');
                self.productNameServiceURL = serviceEndPoints.getEndPoint('doesProductNameExist');
                self.router = oj.Router.rootInstance;
                self.tracker = ko.observable();
                self.productName = ko.observable();
                self.productNameMessage = ko.observable();
                self.productCategory = ko.observable();
                self.categories = ko.observableArray(ReferenceData.getCategories());
                self.productCategoryMessages = ko.observableArray([]);
                self.vendor = ko.observable();
                self.vendors = ko.observableArray(ReferenceData.getVendors());
                self.vendorMessages = ko.observableArray([]);
                self.productPrice = ko.observable();
                self.productDescription = ko.observable();
                self.productFullDesc = ko.observable();
                self.productImage = ko.observable();
                self.productSKU = ko.observable();
                self.productOEMPartNumber = ko.observable();
                self.productOEMName = ko.observable();
                self.productContractDiscount = ko.observable();
                self.productUnitCode = ko.observable();
                self.productUnitCodes = ko.observableArray(ReferenceData.getProductUnitCodes());
                self.productUnitCodeMessages = ko.observableArray([]);
                self.productContractLineItem = ko.observable();
                self.productContractUnitPrice = ko.observable();
                self.productContractUnitPriceDisplay = ko.observable();
                self.productActiveStatus = ko.observable("1");
                self.productImage = ko.observable();
                self.productImageBytes = ko.observable();
                self.productImgageKey = ko.observable();
                self.productImageName = ko.observable();
                self.productImageOrigin = ko.observable();
                self.productImageSize = ko.observable();
                self.productImageType = ko.observable();
                self.uploadFile = ko.observable(null);
                self.uploadName = ko.computed(function () {
                    return !!self.uploadFile() ? self.uploadFile().name : '-';
                });

                /**
                 * Apply KO bindings for the Product Image
                 */
                ko.bindingHandlers.fileUpload = {
                    init: function (element, valueAccessor) {
                        $(element).change(function () {
                            valueAccessor()(element.files[0]);
                        });
                    },
                    update: function (element, valueAccessor) {
                        if (ko.unwrap(valueAccessor()) === null) {
                            $(element).wrap('<form>').closest('form').get(0).reset();
                            $(element).unwrap();
                        }
                    }
                };

                var mdQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.MD_UP);
                self.medium = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(mdQuery);


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
                    $('globalBody').focus();
                    window.location.hash = 'globalBody';
                    if (!SecurityUtils.isAuthenticated()) {
                        return self.router.go('welcome');
                    }
                    // Implement if needed
                    // initialize session storage
                    self.productName = ko.observable();
                    self.productNameMessage = ko.observable();
                    self.productCategory = ko.observable();
                    self.categories = ko.observableArray(ReferenceData.getCategories());
                    self.productCategoryMessages = ko.observableArray([]);
                    self.vendor = ko.observable();
                    self.vendors = ko.observableArray(ReferenceData.getVendors());
                    self.vendorMessages = ko.observableArray([]);
                    self.productPrice = ko.observable();
                    self.productDescription = ko.observable();
                    self.productFullDesc = ko.observable();
                    self.productImage = ko.observable();
                    self.productSKU = ko.observable();
                    self.productOEMPartNumber = ko.observable();
                    self.productOEMName = ko.observable();
                    self.productContractUnitPrice = ko.observable();
                    self.productUnitCode = ko.observable();
                    self.productUnitCodes = ko.observableArray(ReferenceData.getProductUnitCodes());
                    self.productUnitCodeMessages = ko.observableArray([]);
                    self.productContractLineItem = ko.observable();
                    self.productContractDiscount = ko.observable();
                    self.productContractUnitPrice = ko.observable();
                    self.productContractUnitPriceDisplay = ko.observable();
                    self.productActiveStatus = ko.observable("1");
                    self.productImage = ko.observable();
                    self.productImageBytes = ko.observable();
                    self.productImgageKey = ko.observable();
                    self.productImageName = ko.observable();
                    self.productImageOrigin = ko.observable();
                    self.productImageSize = ko.observable();
                    self.productImageType = ko.observable();
                    self.uploadFile = ko.observable(null);
                    self.uploadName = ko.computed(function () {
                        return !!self.uploadFile() ? self.uploadFile().name : '-';
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
                 * Get the default photo
                 * @returns {ProductHelper.ProductHelperProductHelper.getPhoto.src|String}
                 */
                self.getPhoto = function ()
                {
                    return ProductHelper.getPhoto(undefined);
                };

                self.computeContractPrice = function (data, event) {
                    var percent = $("#productContractDiscount").val();
                    var price = $("#productPrice").val();

                    self.calculateContractPrice(price, percent);
                };

                self.calculateContractPrice = function (price, percent) {

                    var discount = 0;
                    if (percent !== NaN && percent != undefined && percent !== 0 && percent !== "0") {
                        discount = ((percent / 100) * price);
                    }
                    if (discount == NaN || discount == undefined || discount === 0) {
                        self.productContractUnitPrice(price);
                        self.productContractUnitPriceDisplay(accounting.formatMoney(price))
                    } else {
                        self.productContractUnitPrice(price - discount);
                        self.productContractUnitPriceDisplay(accounting.formatMoney(price - discount))
                    }
                };

                self.buttonClick = function (data, event) {
                    if (event.currentTarget.id === 'save')
                    {
                        var trackerObj = ko.utils.unwrapObservable(self.tracker);
                        // Perform form level validation
                        if (!this.showComponentValidationErrors(trackerObj))
                        {
                            return;
                        }

                        // Perform app level validation
                        if (!this.run)
                            var ProductService = oj.Model.extend({
                                urlRoot: self.serviceURL,
                                idAttribute: 'prdUid'
                            });
                        var productService = new ProductService();

                        productService.save(
                                {
                                    //Product Info
                                    "productSKU": self.productSKU(),
                                    "productOEMPartNumber": self.productOEMPartNumber(),
                                    "productOEMName": self.productOEMName(),
                                    "productContractUnitPrice": self.productContractUnitPrice(),
                                    "productContractDiscount": self.productContractDiscount(),
                                    "productUnitCode": self.productUnitCode(),
                                    "productContractLineItem": self.productContractLineItem(),
                                    "productName": self.productName(),
                                    "productCategory": self.productCategory(),
                                    "vendor": self.vendor(),
                                    "productPrice": self.productPrice(),
                                    "productDescription": self.productDescription(),
                                    "productFullDesc": self.productFullDesc(),
                                    "productActivationStatus": self.productActiveStatus(),
                                    "productImage": self.productImageBytes(),
                                    "productImageName": self.productImageName(),
                                    "imgOrigin": "tbd",
                                    "productImageSize": self.productImageSize(),
                                    "productImageType": self.productImageType(),
                                    "partyUserId": sessionStorage.userName

                                },
                                {
                                    success: function (myModel, response, options) {
                                        self.showSuccessMessage();
                                        return false;
                                    },
                                    error: function (jqXHR, textStatus, errorThrown) {
                                        document.getElementById('pageError').hidden = false;
                                        document.getElementById('pageErrorDetail').innerHTML = "There was an error creating the product";
                                        return false;
                                    }
                                });
                    } else if (event.currentTarget.id === 'cancel')
                    {
                        // Do nothing and go back to search page
                        return self.router.go('productSearch');
                    }
                };

                /**
                 * The showSuccessMessage method displays the success dialog.
                 * 
                 * @returns {undefined}
                 */
                self.showSuccessMessage = function ()
                {
                    $("#modalDialog1").ojDialog("open");
                };
                /**
                 * The closeClickEvent method handles the click event
                 * generated from the close button.
                 * 
                 * @param {type} data
                 * @param {type} event
                 * @returns {unresolved}
                 */
                self.closeClickEvent = function (data, event) {
                    $("#modalDialog1").ojDialog("close");
                    return self.router.go('productSearch');
                };

                /**
                 * Closes the image warning dialog box
                 * @param {type} data
                 * @param {type} event
                 * @returns {Boolean}
                 */
                self.closeImageDialogClick = function (data, event)
                {
                    $("#modalDialog2").ojDialog("close");
                    self.resetClickEvent();
                };

                self.resetClickEvent = function (data, event) {
                    self.uploadFile(null);
                    self.productImage(null);
                    self.productImageName(null);
                    self.productImageSize(null);
                    self.productImageBytes(null);
                    self.productImageType(null);
                    var preview = document.getElementById('productImage');

                    preview.src = ProductHelper.getPhoto(undefined);
                };

                /**
                 * Demonstrates uploading an image to the CalOrders domain.
                 * 
                 * @param {type} data
                 * @param {type} event
                 * @returns {Boolean}
                 */
                self.uploadImageClick = function (data, event)
                {

                    if (self.uploadFile() !== null && self.uploadFile().name !== "")
                    {
                        self.productImage(self.uploadFile());
                        self.productImageName(self.uploadFile().name);
                        self.productImageSize(self.uploadFile().size);

                        var fileTypeUtils = new FileTypeUtils();
                        var imageType = fileTypeUtils.determineTypeCode(self.uploadFile().type)
                        self.productImageType(imageType);
                        if (self.productImageType === "unknown")
                        {
                            $("#modalDialog2").ojDialog("open");
                        } else
                        {
                            var preview = document.getElementById('productImage');
                            // read the file into a byte array
                            var reader = new FileReader();

                            /**
                             * Add an event listener to the FileReader which will
                             * fire when the file has been completely read.
                             */
                            reader.onloadend = function () {
                                if (imageType === "PNG" || imageType === "JPEG")
                                {
                                    preview.src = reader.result;
                                    self.productImageBytes(reader.result.split(',')[1]);

                                } else
                                {
                                    alert("Your PDF was uploaded, but no preview is available.");
                                }
                            }, false;

                            // load the file
                            try {
                                reader.readAsDataURL(self.productImage());

                            } catch (err)
                            {
                                console.log(err);
                            }
                        }

                    }
                    return false;
                };

                self.validateProductName = {
                    validate: function (value)
                    {

                        parseProduct = function (response)
                        {
                            if (response != undefined && response.length > 0)
                            {
                                var errorMsg = new oj.Message("That product name is already taken.", "", oj.Message.SEVERITY_TYPE.ERROR);
                                self.productNameMessage([errorMsg]);
                                return false;
                            }
                        };
                        var ProductService = oj.Model.extend({
                            urlRoot: self.productNameServiceURL + "/" + value,
                            parse: parseProduct,
                            idAttribute: 'prdUid'});
                        var productService = new ProductService();
                        productService.fetch();
                        return true;
                    }
                };

                self.isValidProductName = function ()
                {

                    parseProduct = function (response)
                    {
                        if (response != undefined && response.length > 0)
                        {
                            var errorMsg = new oj.Message("That product name is already taken.", "", oj.Message.SEVERITY_TYPE.ERROR);
                            self.productNameMessage([errorMsg]);
                            return false;
                        }
                    };
                    if (typeof self.productName() === "undefined"
                            || self.productName() === "") {
                        return true;
                    } else {
                        var ProductService = oj.Model.extend({
                            urlRoot: self.productNameServiceURL + "/" + self.productName(),
                            parse: parseProduct,
                            idAttribute: 'prdUid'});
                        var productService = new ProductService();
                        productService.fetch();
                    }
                    return true;

                };

                /*
                 * Perform form level validation
                 * 
                 * @param {type} trackerObj
                 * @returns {Boolean}
                 */
                self.showComponentValidationErrors = function (trackerObj)
                {
                    trackerObj.showMessages();
                    if (!self.isValidDropDowns() || !self.isValidProductName()
                            || trackerObj.focusOnFirstInvalid()) {
                        return false;
                    }
                    return true;
                };
                self.isValidDropDowns = function ()
                {
                    var validProductCategory = self.isValidProductCategory();
                    var validVendor = self.isValidVendor();
                    var validProductUnitCode = self.isValidProductUnitCode();
                    return validProductCategory && validVendor && validProductUnitCode;
                };
                self.validateProductCategory = {validate: function (value)
                    {
                        var validProductCategory = true;
                        var serviceIndicator = undefined;
                        if (typeof value != "undefined"
                                && value != "") {
                            serviceIndicator = value[0].substring(1, 2);
                        }
                        if (typeof value != "undefined"
                                && value != "" && (value == "SERI"))
                        {
                            self.productCategoryMessages([
                                new oj.Message("Independent Services:",
                                        "Independent Services are products that can be purchased individually",
                                        oj.Message.SEVERITY_TYPE.INFO)]);
                        }
                        if (typeof serviceIndicator != "undefined"
                                && serviceIndicator != "" && serviceIndicator === "S")
                        {
                            self.productCategoryMessages([
                                new oj.Message("Upgrade Services:",
                                        "Upgrade Services are products that can only be purchased as part of an order",
                                        oj.Message.SEVERITY_TYPE.INFO)]);
                        }
                        if (typeof serviceIndicator != "undefined"
                                && serviceIndicator != "" && serviceIndicator === "A")
                        {
                            self.productCategoryMessages([
                                new oj.Message("Stand Alone Services:",
                                        "Stand Alone Services are products that can be purchased individually",
                                        oj.Message.SEVERITY_TYPE.INFO)]);
                        }

                        return validProductCategory;
                    }};
                self.isValidProductCategory = function ()
                {
                    var validProductCategory = true;
                    if (typeof self.productCategory() === "undefined"
                            || self.productCategory() === "")
                    {
                        this.productCategoryMessages([
                            new oj.Message(
                                    "Value is required.",
                                    "You must select a value.",
                                    oj.Message.SEVERITY_TYPE.ERROR)]);
                        validProductCategory = false;
                    }
                    return validProductCategory;
                };
                self.validateVendor = {validate: function (value)
                    {
                        var validVendor = true;
                        if (typeof value === "undefined"
                                || value === "")
                        {
                            self.vendorMessages([
                                new oj.Message(
                                        "Value is required.",
                                        "You must select a value.",
                                        oj.Message.SEVERITY_TYPE.ERROR)]);
                            validVendor = false;
                        }
                        return validVendor;
                    }};
                self.isValidVendor = function ()
                {
                    var validVendor = true;
                    if (typeof self.vendor() === "undefined"
                            || self.vendor() === "")
                    {
                        this.vendorMessages([
                            new oj.Message(
                                    "Value is required.",
                                    "You must select a value.",
                                    oj.Message.SEVERITY_TYPE.ERROR)]);
                        validVendor = false;
                    }
                    return validVendor;
                };
                self.validateProductUnitCode = {validate: function (value)
                    {
                        var validProductUnitCode = true;
                        if (typeof value === "undefined"
                                || value === "")
                        {
                            self.productUnitCodeMessages([
                                new oj.Message(
                                        "Value is required.",
                                        "You must select a value.",
                                        oj.Message.SEVERITY_TYPE.ERROR)]);
                            validProductUnitCode = false;
                        }
                        return validProductUnitCode;
                    }};
                self.isValidProductUnitCode = function ()
                {
                    var validProductUnitCode = true;
                    if (typeof self.productCategory() === "undefined"
                            || self.productCategory() === "")
                    {
                        this.productUnitCodeMessages([
                            new oj.Message(
                                    "Value is required.",
                                    "You must select a value.",
                                    oj.Message.SEVERITY_TYPE.ERROR)]);
                        validProductUnitCode = false;
                    }
                    return validProductUnitCode;
                };


            }



            /*
             * Returns a constructor for the ViewModel so that the ViewModel is constrcuted
             * each time the view is displayed.  Return an instance of the ViewModel if
             * only one instance of the ViewModel is needed.
             */
            return new ProductAddViewModel();
        }
);
