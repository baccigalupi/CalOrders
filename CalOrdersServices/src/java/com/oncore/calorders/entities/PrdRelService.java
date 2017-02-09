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
@Table(name = "PRD_REL_SERVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrdRelService.findAll", query = "SELECT p FROM PrdRelService p")
    , @NamedQuery(name = "PrdRelService.findByPrsUid", query = "SELECT p FROM PrdRelService p WHERE p.prsUid = :prsUid")
    , @NamedQuery(name = "PrdRelService.findByPrsName", query = "SELECT p FROM PrdRelService p WHERE p.prsName = :prsName")
    , @NamedQuery(name = "PrdRelService.findByPrsDesc", query = "SELECT p FROM PrdRelService p WHERE p.prsDesc = :prsDesc")
    , @NamedQuery(name = "PrdRelService.findByPrsPrice", query = "SELECT p FROM PrdRelService p WHERE p.prsPrice = :prsPrice")
    , @NamedQuery(name = "PrdRelService.findByPrsActiveInd", query = "SELECT p FROM PrdRelService p WHERE p.prsActiveInd = :prsActiveInd")
    , @NamedQuery(name = "PrdRelService.findByCreateUserId", query = "SELECT p FROM PrdRelService p WHERE p.createUserId = :createUserId")
    , @NamedQuery(name = "PrdRelService.findByCreateTs", query = "SELECT p FROM PrdRelService p WHERE p.createTs = :createTs")
    , @NamedQuery(name = "PrdRelService.findByUpdateUserId", query = "SELECT p FROM PrdRelService p WHERE p.updateUserId = :updateUserId")
    , @NamedQuery(name = "PrdRelService.findByUpdateTs", query = "SELECT p FROM PrdRelService p WHERE p.updateTs = :updateTs")})
public class PrdRelService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRS_UID")
    private Integer prsUid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "PRS_NAME")
    private String prsName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "PRS_DESC")
    private String prsDesc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRS_PRICE")
    private BigDecimal prsPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRS_ACTIVE_IND")
    private int prsActiveInd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "CREATE_USER_ID")
    private String createUserId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATE_TS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTs;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UPDATE_TS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTs;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prsUidFk")
    private Collection<RelatedService> relatedServiceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prsUidFk")
    private Collection<OrderProductAssoc> orderProductAssocCollection;

    public PrdRelService() {
    }

    public PrdRelService(Integer prsUid) {
        this.prsUid = prsUid;
    }

    public PrdRelService(Integer prsUid, String prsName, String prsDesc, BigDecimal prsPrice, int prsActiveInd, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.prsUid = prsUid;
        this.prsName = prsName;
        this.prsDesc = prsDesc;
        this.prsPrice = prsPrice;
        this.prsActiveInd = prsActiveInd;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getPrsUid() {
        return prsUid;
    }

    public void setPrsUid(Integer prsUid) {
        this.prsUid = prsUid;
    }

    public String getPrsName() {
        return prsName;
    }

    public void setPrsName(String prsName) {
        this.prsName = prsName;
    }

    public String getPrsDesc() {
        return prsDesc;
    }

    public void setPrsDesc(String prsDesc) {
        this.prsDesc = prsDesc;
    }

    public BigDecimal getPrsPrice() {
        return prsPrice;
    }

    public void setPrsPrice(BigDecimal prsPrice) {
        this.prsPrice = prsPrice;
    }

    public int getPrsActiveInd() {
        return prsActiveInd;
    }

    public void setPrsActiveInd(int prsActiveInd) {
        this.prsActiveInd = prsActiveInd;
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
        hash += (prsUid != null ? prsUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrdRelService)) {
            return false;
        }
        PrdRelService other = (PrdRelService) object;
        if ((this.prsUid == null && other.prsUid != null) || (this.prsUid != null && !this.prsUid.equals(other.prsUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.entities.PrdRelService[ prsUid=" + prsUid + " ]";
    }
    
}
