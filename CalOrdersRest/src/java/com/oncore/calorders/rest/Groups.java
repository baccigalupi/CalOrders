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
@Table(name = "GROUPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Groups g")
    , @NamedQuery(name = "Groups.findByGrpUid", query = "SELECT g FROM Groups g WHERE g.grpUid = :grpUid")
    , @NamedQuery(name = "Groups.findByCreateUserId", query = "SELECT g FROM Groups g WHERE g.createUserId = :createUserId")
    , @NamedQuery(name = "Groups.findByCreateTs", query = "SELECT g FROM Groups g WHERE g.createTs = :createTs")
    , @NamedQuery(name = "Groups.findByUpdateUserId", query = "SELECT g FROM Groups g WHERE g.updateUserId = :updateUserId")
    , @NamedQuery(name = "Groups.findByUpdateTs", query = "SELECT g FROM Groups g WHERE g.updateTs = :updateTs")})
public class Groups implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GRP_UID")
    private Integer grpUid;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grpUidFk")
    private Collection<Privilege> privilegeCollection;
    @JoinColumn(name = "DEP_UID_FK", referencedColumnName = "DEP_UID")
    @ManyToOne(optional = false)
    private Department depUidFk;
    @JoinColumn(name = "GRP_TYPE_CD", referencedColumnName = "CODE")
    @ManyToOne(optional = false)
    private GrpTypeCd grpTypeCd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grpUidFk")
    private Collection<GroupPartyAssoc> groupPartyAssocCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grpUidFk")
    private Collection<GroupPrivilegeAssoc> groupPrivilegeAssocCollection;

    public Groups() {
    }

    public Groups(Integer grpUid) {
        this.grpUid = grpUid;
    }

    public Groups(Integer grpUid, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.grpUid = grpUid;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getGrpUid() {
        return grpUid;
    }

    public void setGrpUid(Integer grpUid) {
        this.grpUid = grpUid;
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
    public Collection<Privilege> getPrivilegeCollection() {
        return privilegeCollection;
    }

    public void setPrivilegeCollection(Collection<Privilege> privilegeCollection) {
        this.privilegeCollection = privilegeCollection;
    }

    public Department getDepUidFk() {
        return depUidFk;
    }

    public void setDepUidFk(Department depUidFk) {
        this.depUidFk = depUidFk;
    }

    public GrpTypeCd getGrpTypeCd() {
        return grpTypeCd;
    }

    public void setGrpTypeCd(GrpTypeCd grpTypeCd) {
        this.grpTypeCd = grpTypeCd;
    }

    @XmlTransient
    public Collection<GroupPartyAssoc> getGroupPartyAssocCollection() {
        return groupPartyAssocCollection;
    }

    public void setGroupPartyAssocCollection(Collection<GroupPartyAssoc> groupPartyAssocCollection) {
        this.groupPartyAssocCollection = groupPartyAssocCollection;
    }

    @XmlTransient
    public Collection<GroupPrivilegeAssoc> getGroupPrivilegeAssocCollection() {
        return groupPrivilegeAssocCollection;
    }

    public void setGroupPrivilegeAssocCollection(Collection<GroupPrivilegeAssoc> groupPrivilegeAssocCollection) {
        this.groupPrivilegeAssocCollection = groupPrivilegeAssocCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grpUid != null ? grpUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        if ((this.grpUid == null && other.grpUid != null) || (this.grpUid != null && !this.grpUid.equals(other.grpUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.rest.Groups[ grpUid=" + grpUid + " ]";
    }
    
}
