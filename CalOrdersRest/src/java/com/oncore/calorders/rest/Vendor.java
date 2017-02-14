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
@Table(name = "VENDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vendor.findAll", query = "SELECT v FROM Vendor v")
    , @NamedQuery(name = "Vendor.findByVndUid", query = "SELECT v FROM Vendor v WHERE v.vndUid = :vndUid")
    , @NamedQuery(name = "Vendor.findByVndName", query = "SELECT v FROM Vendor v WHERE v.vndName = :vndName")
    , @NamedQuery(name = "Vendor.findByVndDesc", query = "SELECT v FROM Vendor v WHERE v.vndDesc = :vndDesc")
    , @NamedQuery(name = "Vendor.findByVndManager", query = "SELECT v FROM Vendor v WHERE v.vndManager = :vndManager")
    , @NamedQuery(name = "Vendor.findByVndActiveInd", query = "SELECT v FROM Vendor v WHERE v.vndActiveInd = :vndActiveInd")
    , @NamedQuery(name = "Vendor.findByCreateUserId", query = "SELECT v FROM Vendor v WHERE v.createUserId = :createUserId")
    , @NamedQuery(name = "Vendor.findByCreateTs", query = "SELECT v FROM Vendor v WHERE v.createTs = :createTs")
    , @NamedQuery(name = "Vendor.findByUpdateUserId", query = "SELECT v FROM Vendor v WHERE v.updateUserId = :updateUserId")
    , @NamedQuery(name = "Vendor.findByUpdateTs", query = "SELECT v FROM Vendor v WHERE v.updateTs = :updateTs")})
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "VND_UID")
    private Integer vndUid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "VND_NAME")
    private String vndName;
    @Size(max = 256)
    @Column(name = "VND_DESC")
    private String vndDesc;
    @Size(max = 128)
    @Column(name = "VND_MANAGER")
    private String vndManager;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VND_ACTIVE_IND")
    private int vndActiveInd;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vndUidFk")
    private Collection<Product> productCollection;
    @OneToMany(mappedBy = "vndUidFk")
    private Collection<Address> addressCollection;

    public Vendor() {
    }

    public Vendor(Integer vndUid) {
        this.vndUid = vndUid;
    }

    public Vendor(Integer vndUid, String vndName, int vndActiveInd, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.vndUid = vndUid;
        this.vndName = vndName;
        this.vndActiveInd = vndActiveInd;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getVndUid() {
        return vndUid;
    }

    public void setVndUid(Integer vndUid) {
        this.vndUid = vndUid;
    }

    public String getVndName() {
        return vndName;
    }

    public void setVndName(String vndName) {
        this.vndName = vndName;
    }

    public String getVndDesc() {
        return vndDesc;
    }

    public void setVndDesc(String vndDesc) {
        this.vndDesc = vndDesc;
    }

    public String getVndManager() {
        return vndManager;
    }

    public void setVndManager(String vndManager) {
        this.vndManager = vndManager;
    }

    public int getVndActiveInd() {
        return vndActiveInd;
    }

    public void setVndActiveInd(int vndActiveInd) {
        this.vndActiveInd = vndActiveInd;
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
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @XmlTransient
    public Collection<Address> getAddressCollection() {
        return addressCollection;
    }

    public void setAddressCollection(Collection<Address> addressCollection) {
        this.addressCollection = addressCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vndUid != null ? vndUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendor)) {
            return false;
        }
        Vendor other = (Vendor) object;
        if ((this.vndUid == null && other.vndUid != null) || (this.vndUid != null && !this.vndUid.equals(other.vndUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.rest.Vendor[ vndUid=" + vndUid + " ]";
    }
    
}
