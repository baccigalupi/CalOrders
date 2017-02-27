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

import com.oncore.calorders.rest.PrdCategoryCd;
import com.oncore.calorders.rest.PrdImgTypeCd;
import java.math.BigDecimal;

/**
 *
 * @author oncore
 */


public class OrderDetailProductData {
    private Integer prdUid;
    private String prdName;
    private Integer prdQuantity;
    private BigDecimal prdPrice;
    private BigDecimal prdEachPrice;
    private byte[] prdImgImage;
    private PrdImgTypeCd prdImgTypeCd;

    public Integer getPrdUid() {
        return prdUid;
    }

    public void setPrdUid(Integer prdUid) {
        this.prdUid = prdUid;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public Integer getPrdQuantity() {
        return prdQuantity;
    }

    public void setPrdQuantity(Integer prdQuantity) {
        this.prdQuantity = prdQuantity;
    }

    public BigDecimal getPrdPrice() {
        return prdPrice;
    }

    public void setPrdPrice(BigDecimal prdPrice) {
        this.prdPrice = prdPrice;
    }

    public byte[] getPrdImgImage() {
        return prdImgImage;
    }

    public void setPrdImgImage(byte[] prdImgImage) {
        this.prdImgImage = prdImgImage;
    }

    public PrdImgTypeCd getPrdImgTypeCd() {
        return prdImgTypeCd;
    }

    public void setPrdImgTypeCd(PrdImgTypeCd prdImgTypeCd) {
        this.prdImgTypeCd = prdImgTypeCd;
    }

    public BigDecimal getPrdEachPrice() {
        return prdEachPrice;
    }

    public void setPrdEachPrice(BigDecimal prdEachPrice) {
        this.prdEachPrice = prdEachPrice;
    }
    
    
}
