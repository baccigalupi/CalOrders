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
package com.oncore.calorders.rest;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Cacheable(false)
@Table(name = "ORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Order1.findAll", query = "SELECT o FROM Order1 o")
    , @NamedQuery(name = "Order1.findByOrdUid", query = "SELECT o FROM Order1 o WHERE o.ordUid = :ordUid")
    , @NamedQuery(name = "Order1.findByCreateUserId", query = "SELECT o FROM Order1 o WHERE o.createUserId = :createUserId")
    , @NamedQuery(name = "Order1.findByCreateTs", query = "SELECT o FROM Order1 o WHERE o.createTs = :createTs")
    , @NamedQuery(name = "Order1.findByUpdateUserId", query = "SELECT o FROM Order1 o WHERE o.updateUserId = :updateUserId")
    , @NamedQuery(name = "Order1.findByUpdateTs", query = "SELECT o FROM Order1 o WHERE o.updateTs = :updateTs")})
public class Order1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORD_UID")
    private Integer ordUid;
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
    @JoinColumn(name = "ORD_STATUS_CD", referencedColumnName = "CODE")
    @ManyToOne(optional = false)
    private OrdStatusCd ordStatusCd;
    @JoinColumn(name = "PTY_UID_FK", referencedColumnName = "PTY_UID")
    @ManyToOne(optional = false)
    private Party ptyUidFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordUidFk")
    private Collection<OrderProductAssoc> orderProductAssocCollection;

    public Order1() {
    }

    public Order1(Integer ordUid) {
        this.ordUid = ordUid;
    }

    public Order1(Integer ordUid, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.ordUid = ordUid;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getOrdUid() {
        return ordUid;
    }

    public void setOrdUid(Integer ordUid) {
        this.ordUid = ordUid;
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

    public OrdStatusCd getOrdStatusCd() {
        return ordStatusCd;
    }

    public void setOrdStatusCd(OrdStatusCd ordStatusCd) {
        this.ordStatusCd = ordStatusCd;
    }

    public Party getPtyUidFk() {
        return ptyUidFk;
    }

    public void setPtyUidFk(Party ptyUidFk) {
        this.ptyUidFk = ptyUidFk;
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
        hash += (ordUid != null ? ordUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.ordUid == null && other.ordUid != null) || (this.ordUid != null && !this.ordUid.equals(other.ordUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.rest.Order1[ ordUid=" + ordUid + " ]";
    }
    
}
