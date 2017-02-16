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
package com.oncore.calorders.rest.data;

import com.oncore.calorders.core.data.BaseData;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Product Data object wraps related product data
 *
 * @author oncore
 */
@XmlRootElement
public class ProductData extends BaseData {

    private String productName;
    private String productCategory;
    private Integer vendor;
    private BigDecimal productPrice;
    private String productDescription;
    private String productFullDesc;
    private List<Integer> relatedServices;
    private String partyUserId;
    private String productSKU;
    private String productOEMPartNumber;
    private String productOEMName;
    private BigDecimal productContractUnitPrice;
    private Integer productContractDiscount;
    private String productUnitCode;
    private String productContractLineItem;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getVendor() {
        return vendor;
    }

    public void setVendor(Integer vendor) {
        this.vendor = vendor;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductFullDesc() {
        return productFullDesc;
    }

    public void setProductFullDesc(String productFullDesc) {
        this.productFullDesc = productFullDesc;
    }

    public List<Integer> getRelatedServices() {
        return relatedServices;
    }

    public void setRelatedServices(List<Integer> relatedServices) {
        this.relatedServices = relatedServices;
    }

    public String getPartyUserId() {
        return partyUserId;
    }

    public void setPartyUserId(String partyUserId) {
        this.partyUserId = partyUserId;
    }

    public String getProductSKU() {
        return productSKU;
    }

    public void setProductSKU(String productSKU) {
        this.productSKU = productSKU;
    }

    public String getProductOEMPartNumber() {
        return productOEMPartNumber;
    }

    public void setProductOEMPartNumber(String productOEMPartNumber) {
        this.productOEMPartNumber = productOEMPartNumber;
    }

    public String getProductOEMName() {
        return productOEMName;
    }

    public void setProductOEMName(String productOEMName) {
        this.productOEMName = productOEMName;
    }

    public BigDecimal getProductContractUnitPrice() {
        return productContractUnitPrice;
    }

    public void setProductContractUnitPrice(BigDecimal productContractUnitPrice) {
        this.productContractUnitPrice = productContractUnitPrice;
    }

    public Integer getProductContractDiscount() {
        return productContractDiscount;
    }

    public void setProductContractDiscount(Integer productContractDiscount) {
        this.productContractDiscount = productContractDiscount;
    }

    public String getProductUnitCode() {
        return productUnitCode;
    }

    public void setProductUnitCode(String productUnitCode) {
        this.productUnitCode = productUnitCode;
    }

    public String getProductContractLineItem() {
        return productContractLineItem;
    }

    public void setProductContractLineItem(String productContractLineItem) {
        this.productContractLineItem = productContractLineItem;
    }

}
