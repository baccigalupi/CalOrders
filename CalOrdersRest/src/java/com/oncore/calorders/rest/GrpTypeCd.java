/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncore.calorders.rest;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "GRP_TYPE_CD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrpTypeCd.findAll", query = "SELECT g FROM GrpTypeCd g")
    , @NamedQuery(name = "GrpTypeCd.findByCode", query = "SELECT g FROM GrpTypeCd g WHERE g.code = :code")
    , @NamedQuery(name = "GrpTypeCd.findByShortDesc", query = "SELECT g FROM GrpTypeCd g WHERE g.shortDesc = :shortDesc")
    , @NamedQuery(name = "GrpTypeCd.findByLongDesc", query = "SELECT g FROM GrpTypeCd g WHERE g.longDesc = :longDesc")
    , @NamedQuery(name = "GrpTypeCd.findByCreateUserId", query = "SELECT g FROM GrpTypeCd g WHERE g.createUserId = :createUserId")
    , @NamedQuery(name = "GrpTypeCd.findByCreateTs", query = "SELECT g FROM GrpTypeCd g WHERE g.createTs = :createTs")
    , @NamedQuery(name = "GrpTypeCd.findByUpdateUserId", query = "SELECT g FROM GrpTypeCd g WHERE g.updateUserId = :updateUserId")
    , @NamedQuery(name = "GrpTypeCd.findByUpdateTs", query = "SELECT g FROM GrpTypeCd g WHERE g.updateTs = :updateTs")})
public class GrpTypeCd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "CODE")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "SHORT_DESC")
    private String shortDesc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "LONG_DESC")
    private String longDesc;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grpTypeCd")
    private Collection<Groups> groupsCollection;

    public GrpTypeCd() {
    }

    public GrpTypeCd(String code) {
        this.code = code;
    }

    public GrpTypeCd(String code, String shortDesc, String longDesc, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.code = code;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrpTypeCd)) {
            return false;
        }
        GrpTypeCd other = (GrpTypeCd) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.rest.GrpTypeCd[ code=" + code + " ]";
    }
    
}
