/*
 * The MIT License
 *
 * Copyright 2017 OnCore Consulting LLC, All Rights Reserved.
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
package com.oncore.calorders.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author oncore
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findByPrdUid", query = "SELECT p FROM Product p WHERE p.prdUid = :prdUid")
    , @NamedQuery(name = "Product.findByPrdSku", query = "SELECT p FROM Product p WHERE p.prdSku = :prdSku")
    , @NamedQuery(name = "Product.findByPrdName", query = "SELECT p FROM Product p WHERE p.prdName = :prdName")
    , @NamedQuery(name = "Product.findByPrdShortDesc", query = "SELECT p FROM Product p WHERE p.prdShortDesc = :prdShortDesc")
    , @NamedQuery(name = "Product.findByPrdLongDesc", query = "SELECT p FROM Product p WHERE p.prdLongDesc = :prdLongDesc")
    , @NamedQuery(name = "Product.findByPrdPrice", query = "SELECT p FROM Product p WHERE p.prdPrice = :prdPrice")
    , @NamedQuery(name = "Product.findByPrdActiveInd", query = "SELECT p FROM Product p WHERE p.prdActiveInd = :prdActiveInd")
    , @NamedQuery(name = "Product.findByPrdImgKey", query = "SELECT p FROM Product p WHERE p.prdImgKey = :prdImgKey")
    , @NamedQuery(name = "Product.findByPrdImgName", query = "SELECT p FROM Product p WHERE p.prdImgName = :prdImgName")
    , @NamedQuery(name = "Product.findByPrdImgSize", query = "SELECT p FROM Product p WHERE p.prdImgSize = :prdImgSize")
    , @NamedQuery(name = "Product.findByPrdImgOrigin", query = "SELECT p FROM Product p WHERE p.prdImgOrigin = :prdImgOrigin")
    , @NamedQuery(name = "Product.findByPrdCntrLnItm", query = "SELECT p FROM Product p WHERE p.prdCntrLnItm = :prdCntrLnItm")
    , @NamedQuery(name = "Product.findByPrdOemPartNum", query = "SELECT p FROM Product p WHERE p.prdOemPartNum = :prdOemPartNum")
    , @NamedQuery(name = "Product.findByPrdOemName", query = "SELECT p FROM Product p WHERE p.prdOemName = :prdOemName")
    , @NamedQuery(name = "Product.findByPrdCntrUnitPrice", query = "SELECT p FROM Product p WHERE p.prdCntrUnitPrice = :prdCntrUnitPrice")
    , @NamedQuery(name = "Product.findByPrdCntrDiscount", query = "SELECT p FROM Product p WHERE p.prdCntrDiscount = :prdCntrDiscount")
    , @NamedQuery(name = "Product.findByCreateUserId", query = "SELECT p FROM Product p WHERE p.createUserId = :createUserId")
    , @NamedQuery(name = "Product.findByCreateTs", query = "SELECT p FROM Product p WHERE p.createTs = :createTs")
    , @NamedQuery(name = "Product.findByUpdateUserId", query = "SELECT p FROM Product p WHERE p.updateUserId = :updateUserId")
    , @NamedQuery(name = "Product.findByUpdateTs", query = "SELECT p FROM Product p WHERE p.updateTs = :updateTs")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRD_UID")
    private Integer prdUid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PRD_SKU")
    private String prdSku;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PRD_NAME")
    private String prdName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "PRD_SHORT_DESC")
    private String prdShortDesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "PRD_LONG_DESC")
    private String prdLongDesc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRD_PRICE")
    private BigDecimal prdPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRD_ACTIVE_IND")
    private int prdActiveInd;
    @Size(max = 256)
    @Column(name = "PRD_IMG_KEY")
    private String prdImgKey;
    @Size(max = 256)
    @Column(name = "PRD_IMG_NAME")
    private String prdImgName;
    @Column(name = "PRD_IMG_SIZE")
    private Integer prdImgSize;
    @Size(max = 128)
    @Column(name = "PRD_IMG_ORIGIN")
    private String prdImgOrigin;
    @Lob
    @Column(name = "PRD_IMG_IMAGE")
    private byte[] prdImgImage;
    @Size(max = 45)
    @Column(name = "PRD_CNTR_LN_ITM")
    private String prdCntrLnItm;
    @Size(max = 45)
    @Column(name = "PRD_OEM_PART_NUM")
    private String prdOemPartNum;
    @Size(max = 45)
    @Column(name = "PRD_OEM_NAME")
    private String prdOemName;
    @Column(name = "PRD_CNTR_UNIT_PRICE")
    private BigDecimal prdCntrUnitPrice;
    @Column(name = "PRD_CNTR_DISCOUNT")
    private Integer prdCntrDiscount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "CREATE_USER_ID")
    private String createUserId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATE_TS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTs;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UPDATE_TS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTs;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prdUidFk")
    private Collection<RelatedService> relatedServiceCollection;
    @JoinColumn(name = "PRD_CATEGORY_CD", referencedColumnName = "CODE")
    @ManyToOne(optional = false)
    private PrdCategoryCd prdCategoryCd;
    @JoinColumn(name = "PRD_IMG_TYPE_CD", referencedColumnName = "CODE")
    @ManyToOne(optional = false)
    private PrdImgTypeCd prdImgTypeCd;
    @JoinColumn(name = "PRD_UNIT_CD", referencedColumnName = "CODE")
    @ManyToOne(optional = false)
    private PrdUnitCd prdUnitCd;
    @JoinColumn(name = "VND_UID_FK", referencedColumnName = "VND_UID")
    @ManyToOne(optional = false)
    private Vendor vndUidFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prdUidFk")
    private Collection<OrderProductAssoc> orderProductAssocCollection;

    public Product() {
    }

    public Product(Integer prdUid) {
        this.prdUid = prdUid;
    }

    public Product(Integer prdUid, String prdSku, String prdName, String prdShortDesc, String prdLongDesc, BigDecimal prdPrice, int prdActiveInd, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.prdUid = prdUid;
        this.prdSku = prdSku;
        this.prdName = prdName;
        this.prdShortDesc = prdShortDesc;
        this.prdLongDesc = prdLongDesc;
        this.prdPrice = prdPrice;
        this.prdActiveInd = prdActiveInd;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getPrdUid() {
        return prdUid;
    }

    public void setPrdUid(Integer prdUid) {
        this.prdUid = prdUid;
    }

    public String getPrdSku() {
        return prdSku;
    }

    public void setPrdSku(String prdSku) {
        this.prdSku = prdSku;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getPrdShortDesc() {
        return prdShortDesc;
    }

    public void setPrdShortDesc(String prdShortDesc) {
        this.prdShortDesc = prdShortDesc;
    }

    public String getPrdLongDesc() {
        return prdLongDesc;
    }

    public void setPrdLongDesc(String prdLongDesc) {
        this.prdLongDesc = prdLongDesc;
    }

    public BigDecimal getPrdPrice() {
        return prdPrice;
    }

    public void setPrdPrice(BigDecimal prdPrice) {
        this.prdPrice = prdPrice;
    }

    public int getPrdActiveInd() {
        return prdActiveInd;
    }

    public void setPrdActiveInd(int prdActiveInd) {
        this.prdActiveInd = prdActiveInd;
    }

    public String getPrdImgKey() {
        return prdImgKey;
    }

    public void setPrdImgKey(String prdImgKey) {
        this.prdImgKey = prdImgKey;
    }

    public String getPrdImgName() {
        return prdImgName;
    }

    public void setPrdImgName(String prdImgName) {
        this.prdImgName = prdImgName;
    }

    public Integer getPrdImgSize() {
        return prdImgSize;
    }

    public void setPrdImgSize(Integer prdImgSize) {
        this.prdImgSize = prdImgSize;
    }

    public String getPrdImgOrigin() {
        return prdImgOrigin;
    }

    public void setPrdImgOrigin(String prdImgOrigin) {
        this.prdImgOrigin = prdImgOrigin;
    }

    public byte[] getPrdImgImage() {
        return prdImgImage;
    }

    public void setPrdImgImage(byte[] prdImgImage) {
        this.prdImgImage = prdImgImage;
    }

    public String getPrdCntrLnItm() {
        return prdCntrLnItm;
    }

    public void setPrdCntrLnItm(String prdCntrLnItm) {
        this.prdCntrLnItm = prdCntrLnItm;
    }

    public String getPrdOemPartNum() {
        return prdOemPartNum;
    }

    public void setPrdOemPartNum(String prdOemPartNum) {
        this.prdOemPartNum = prdOemPartNum;
    }

    public String getPrdOemName() {
        return prdOemName;
    }

    public void setPrdOemName(String prdOemName) {
        this.prdOemName = prdOemName;
    }

    public BigDecimal getPrdCntrUnitPrice() {
        return prdCntrUnitPrice;
    }

    public void setPrdCntrUnitPrice(BigDecimal prdCntrUnitPrice) {
        this.prdCntrUnitPrice = prdCntrUnitPrice;
    }

    public Integer getPrdCntrDiscount() {
        return prdCntrDiscount;
    }

    public void setPrdCntrDiscount(Integer prdCntrDiscount) {
        this.prdCntrDiscount = prdCntrDiscount;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    @XmlTransient
    public Collection<RelatedService> getRelatedServiceCollection() {
        return relatedServiceCollection;
    }

    public void setRelatedServiceCollection(Collection<RelatedService> relatedServiceCollection) {
        this.relatedServiceCollection = relatedServiceCollection;
    }

    public PrdCategoryCd getPrdCategoryCd() {
        return prdCategoryCd;
    }

    public void setPrdCategoryCd(PrdCategoryCd prdCategoryCd) {
        this.prdCategoryCd = prdCategoryCd;
    }

    public PrdImgTypeCd getPrdImgTypeCd() {
        return prdImgTypeCd;
    }

    public void setPrdImgTypeCd(PrdImgTypeCd prdImgTypeCd) {
        this.prdImgTypeCd = prdImgTypeCd;
    }

    public PrdUnitCd getPrdUnitCd() {
        return prdUnitCd;
    }

    public void setPrdUnitCd(PrdUnitCd prdUnitCd) {
        this.prdUnitCd = prdUnitCd;
    }

    public Vendor getVndUidFk() {
        return vndUidFk;
    }

    public void setVndUidFk(Vendor vndUidFk) {
        this.vndUidFk = vndUidFk;
    }

    @XmlTransient
    public Collection<OrderProductAssoc> getOrderProductAssocCollection() {
        return orderProductAssocCollection;
    }

    public void setOrderProductAssocCollection(Collection<OrderProductAssoc> orderProductAssocCollection) {
        this.orderProductAssocCollection = orderProductAssocCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prdUid != null ? prdUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.prdUid == null && other.prdUid != null) || (this.prdUid != null && !this.prdUid.equals(other.prdUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.entities.Product[ prdUid=" + prdUid + " ]";
    }
    
}
