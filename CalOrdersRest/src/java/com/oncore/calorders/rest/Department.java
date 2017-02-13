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
@Table(name = "DEPARTMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d")
    , @NamedQuery(name = "Department.findByDepUid", query = "SELECT d FROM Department d WHERE d.depUid = :depUid")
    , @NamedQuery(name = "Department.findByDepName", query = "SELECT d FROM Department d WHERE d.depName = :depName")
    , @NamedQuery(name = "Department.findByDepActRep", query = "SELECT d FROM Department d WHERE d.depActRep = :depActRep")
    , @NamedQuery(name = "Department.findByDepImgId", query = "SELECT d FROM Department d WHERE d.depImgId = :depImgId")
    , @NamedQuery(name = "Department.findByCreateUserId", query = "SELECT d FROM Department d WHERE d.createUserId = :createUserId")
    , @NamedQuery(name = "Department.findByCreateTs", query = "SELECT d FROM Department d WHERE d.createTs = :createTs")
    , @NamedQuery(name = "Department.findByUpdateUserId", query = "SELECT d FROM Department d WHERE d.updateUserId = :updateUserId")
    , @NamedQuery(name = "Department.findByUpdateTs", query = "SELECT d FROM Department d WHERE d.updateTs = :updateTs")})
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DEP_UID")
    private Integer depUid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "DEP_NAME")
    private String depName;
    @Size(max = 45)
    @Column(name = "DEP_ACT_REP")
    private String depActRep;
    @Size(max = 128)
    @Column(name = "DEP_IMG_ID")
    private String depImgId;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depUidFk")
    private Collection<Groups> groupsCollection;
    @OneToMany(mappedBy = "depUidFk")
    private Collection<ContactInfo> contactInfoCollection;
    @OneToMany(mappedBy = "depUidFk")
    private Collection<Address> addressCollection;

    public Department() {
    }

    public Department(Integer depUid) {
        this.depUid = depUid;
    }

    public Department(Integer depUid, String depName, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.depUid = depUid;
        this.depName = depName;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getDepUid() {
        return depUid;
    }

    public void setDepUid(Integer depUid) {
        this.depUid = depUid;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepActRep() {
        return depActRep;
    }

    public void setDepActRep(String depActRep) {
        this.depActRep = depActRep;
    }

    public String getDepImgId() {
        return depImgId;
    }

    public void setDepImgId(String depImgId) {
        this.depImgId = depImgId;
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
    public Collection<Groups> getGroupsCollection() {
        return groupsCollection;
    }

    public void setGroupsCollection(Collection<Groups> groupsCollection) {
        this.groupsCollection = groupsCollection;
    }

    @XmlTransient
    public Collection<ContactInfo> getContactInfoCollection() {
        return contactInfoCollection;
    }

    public void setContactInfoCollection(Collection<ContactInfo> contactInfoCollection) {
        this.contactInfoCollection = contactInfoCollection;
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
        hash += (depUid != null ? depUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.depUid == null && other.depUid != null) || (this.depUid != null && !this.depUid.equals(other.depUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.rest.Department[ depUid=" + depUid + " ]";
    }
    
}
