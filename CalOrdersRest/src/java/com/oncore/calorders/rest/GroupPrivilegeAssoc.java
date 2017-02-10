/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oncore.calorders.rest;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author oncore
 */
@Entity
@Table(name = "GROUP_PRIVILEGE_ASSOC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupPrivilegeAssoc.findAll", query = "SELECT g FROM GroupPrivilegeAssoc g")
    , @NamedQuery(name = "GroupPrivilegeAssoc.findByGpaUid", query = "SELECT g FROM GroupPrivilegeAssoc g WHERE g.gpaUid = :gpaUid")
    , @NamedQuery(name = "GroupPrivilegeAssoc.findByCreateUserId", query = "SELECT g FROM GroupPrivilegeAssoc g WHERE g.createUserId = :createUserId")
    , @NamedQuery(name = "GroupPrivilegeAssoc.findByCreateTs", query = "SELECT g FROM GroupPrivilegeAssoc g WHERE g.createTs = :createTs")
    , @NamedQuery(name = "GroupPrivilegeAssoc.findByUpdateUserId", query = "SELECT g FROM GroupPrivilegeAssoc g WHERE g.updateUserId = :updateUserId")
    , @NamedQuery(name = "GroupPrivilegeAssoc.findByUpdateTs", query = "SELECT g FROM GroupPrivilegeAssoc g WHERE g.updateTs = :updateTs")})
public class GroupPrivilegeAssoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GPA_UID")
    private Integer gpaUid;
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
    @Size(max = 45)
    @Column(name = "UPDATE_TS")
    private String updateTs;
    @JoinColumn(name = "GRP_UID_FK", referencedColumnName = "GRP_UID")
    @ManyToOne(optional = false)
    private Groups grpUidFk;
    @JoinColumn(name = "PRV_UID_FK", referencedColumnName = "PRV_UID")
    @ManyToOne(optional = false)
    private Privilege prvUidFk;

    public GroupPrivilegeAssoc() {
    }

    public GroupPrivilegeAssoc(Integer gpaUid) {
        this.gpaUid = gpaUid;
    }

    public GroupPrivilegeAssoc(Integer gpaUid, String createUserId, Date createTs, String updateUserId) {
        this.gpaUid = gpaUid;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
    }

    public Integer getGpaUid() {
        return gpaUid;
    }

    public void setGpaUid(Integer gpaUid) {
        this.gpaUid = gpaUid;
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

    public String getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(String updateTs) {
        this.updateTs = updateTs;
    }

    public Groups getGrpUidFk() {
        return grpUidFk;
    }

    public void setGrpUidFk(Groups grpUidFk) {
        this.grpUidFk = grpUidFk;
    }

    public Privilege getPrvUidFk() {
        return prvUidFk;
    }

    public void setPrvUidFk(Privilege prvUidFk) {
        this.prvUidFk = prvUidFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gpaUid != null ? gpaUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupPrivilegeAssoc)) {
            return false;
        }
        GroupPrivilegeAssoc other = (GroupPrivilegeAssoc) object;
        if ((this.gpaUid == null && other.gpaUid != null) || (this.gpaUid != null && !this.gpaUid.equals(other.gpaUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.rest.GroupPrivilegeAssoc[ gpaUid=" + gpaUid + " ]";
    }
    
}
