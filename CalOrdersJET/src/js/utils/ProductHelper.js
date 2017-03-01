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

var ProductHelper = new function ()
{
    /**
     * Gets the photo for a product and sets it on the product image
     */
    this.getPhoto = function (product)
    {
        if (product === undefined || product === null || product.prdImgImage === null
                || product.prdImgImage === undefined || product.prdImgImage.length === 0)
        {
            var src = '../../css/images/unknown_product.jpg';
            return src;
        } else
        {
            var file = product.prdImgImage;

            var imageSize = product.prdImgImage.length;
            var imageType = product.prdImgTypeCd.longDesc;

            var reader = new FileReader();

            var data = window.atob(file);
            var arr = new Uint8Array(data.length);
            for (var i = 0; i < data.length; i++) {
                arr[i] = data.charCodeAt(i);
            }

            var blob = new Blob([arr.buffer], {size: imageSize, type: imageType});

            reader.addEventListener("load", function (event) {
                var preview = document.getElementById('productImage' + product.prdUid);

                if (preview !== null)
                {
                    preview.src = reader.result;
                }
            }, false);

            if (blob) {

                try {
                    reader.readAsDataURL(blob);

                } catch (err)
                {
                    console.log(err);
                }
            }
        }
    };

    /**
     * Gets the photo for a product asyncroniously and sets it on the product image
     */
    this.getPhotoAsync = function (product)
    {
        if (product.prdUid === undefined || product.prdUid === null || product.prdUid.length === 0)
        {
            var src = '../../css/images/unknown_product.jpg';
            return src;
        } else
        {

            var serviceEndPoints = new ServiceEndPoints();

            var findProdutService = serviceEndPoints.getEndPoint("findProductById");

            var ProductModel = oj.Model.extend({
                urlRoot: findProdutService + "/" + product.prdUid,
                attributeId: 'prdUid'

            });

            var pm = new ProductModel();
            pm.fetch({
                success: function (myModel, response, options) {
                    var productMod = pm;
                    product.prdImgImage = productMod.attributes.prdImgImage;
                    if (productMod === undefined || productMod === null || productMod.attributes === undefined || productMod.attributes.prdImgImage === null
                            || productMod.attributes.prdImgImage === undefined || productMod.attributes.prdImgImage.length === 0)
                    {
                        var src = '../../css/images/unknown_product.jpg';
                        var preview = document.getElementById('productImage' + productMod.attributes.prdUid);
                        preview.src = src;
                    } else
                    {
                        var file = productMod.attributes.prdImgImage;

                        var imageSize = productMod.attributes.prdImgImage.length;
                        var imageType = productMod.attributes.prdImgTypeCd.longDesc;

                        var reader = new FileReader();

                        var data = window.atob(file);
                        var arr = new Uint8Array(data.length);
                        for (var i = 0; i < data.length; i++) {
                            arr[i] = data.charCodeAt(i);
                        }

                        var blob = new Blob([arr.buffer], {size: imageSize, type: imageType});

                        reader.addEventListener("load", function (event) {
                            var preview = document.getElementById('productImage' + productMod.attributes.prdUid);

                            if (preview !== null)
                            {
                                preview.src = reader.result;
                            }
                        }, false);

                        if (blob) {

                            try {
                                reader.readAsDataURL(blob);

                            } catch (err)
                            {
                                console.log(err);
                            }
                        }
                    }
                    return false;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("Failed to load product image" + errorThrown);
                    return false;
                }
            });

            var src = '../../css/images/unknown_product.jpg';
            return src;
        }
    };

    this.addProductToCart = function (product)
    {
        var cartProducts;

        // Get cart - if no cart yet then initialize
        if (sessionStorage.cartProducts !== undefined && sessionStorage.cartProducts !== "")
        {
            cartProducts = JSON.parse(sessionStorage.cartProducts);

        } else
        {
            cartProducts = [];
        }

        // If the product already exists in the cart, then just change the quantity
        var result = $.grep(cartProducts, function (item) {
            return item.prdUid === product.prdUid;
        });
        var cartProduct;

        if (result.length === 1)
        {
            cartProduct = result[0];
            cartProduct.quantity += Number(product.quantity());
        } else
        {
            // Deep copy
            cartProduct = jQuery.extend(true, {}, product);
            cartProduct.quantity = Number(product.quantity());
            cartProducts.push(cartProduct);
        }

        var tempItemTotalPrice = 0;
        var tempItemQuantityTotal = 0;
        for (i = 0; i < cartProducts.length; i++)
        {
            tempItemTotalPrice += (cartProducts[i].prdCntrUnitPrice * cartProducts[i].quantity);

            tempItemQuantityTotal += Number(cartProducts[i].quantity);
        }
        sessionStorage.itemTotalPrice = tempItemTotalPrice;
        sessionStorage.itemQuantityTotal = tempItemQuantityTotal;

        // Save cart back into session
        sessionStorage.cartProducts = JSON.stringify(cartProducts);
    }
    ;
};


