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
@Table(name = "CONTACT_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContactInfo.findAll", query = "SELECT c FROM ContactInfo c")
    , @NamedQuery(name = "ContactInfo.findByEmcUid", query = "SELECT c FROM ContactInfo c WHERE c.emcUid = :emcUid")
    , @NamedQuery(name = "ContactInfo.findByEmcValue", query = "SELECT c FROM ContactInfo c WHERE c.emcValue = :emcValue")
    , @NamedQuery(name = "ContactInfo.findByCreateUserId", query = "SELECT c FROM ContactInfo c WHERE c.createUserId = :createUserId")
    , @NamedQuery(name = "ContactInfo.findByCreateTs", query = "SELECT c FROM ContactInfo c WHERE c.createTs = :createTs")
    , @NamedQuery(name = "ContactInfo.findByUpdateUserId", query = "SELECT c FROM ContactInfo c WHERE c.updateUserId = :updateUserId")
    , @NamedQuery(name = "ContactInfo.findByUpdateTs", query = "SELECT c FROM ContactInfo c WHERE c.updateTs = :updateTs")})
public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EMC_UID")
    private Integer emcUid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "EMC_VALUE")
    private String emcValue;
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
    @JoinColumn(name = "DEP_UID_FK", referencedColumnName = "DEP_UID")
    @ManyToOne
    private Department depUidFk;
    @JoinColumn(name = "EMC_TYPE_CD", referencedColumnName = "CODE")
    @ManyToOne(optional = false)
    private EmcTypeCd emcTypeCd;
    @JoinColumn(name = "PTY_UID_FK", referencedColumnName = "PTY_UID")
    @ManyToOne
    private Party ptyUidFk;

    public ContactInfo() {
    }

    public ContactInfo(Integer emcUid) {
        this.emcUid = emcUid;
    }

    public ContactInfo(Integer emcUid, String emcValue, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.emcUid = emcUid;
        this.emcValue = emcValue;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getEmcUid() {
        return emcUid;
    }

    public void setEmcUid(Integer emcUid) {
        this.emcUid = emcUid;
    }

    public String getEmcValue() {
        return emcValue;
    }

    public void setEmcValue(String emcValue) {
        this.emcValue = emcValue;
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

    public Department getDepUidFk() {
        return depUidFk;
    }

    public void setDepUidFk(Department depUidFk) {
        this.depUidFk = depUidFk;
    }

    public EmcTypeCd getEmcTypeCd() {
        return emcTypeCd;
    }

    public void setEmcTypeCd(EmcTypeCd emcTypeCd) {
        this.emcTypeCd = emcTypeCd;
    }

    public Party getPtyUidFk() {
        return ptyUidFk;
    }

    public void setPtyUidFk(Party ptyUidFk) {
        this.ptyUidFk = ptyUidFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emcUid != null ? emcUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContactInfo)) {
            return false;
        }
        ContactInfo other = (ContactInfo) object;
        if ((this.emcUid == null && other.emcUid != null) || (this.emcUid != null && !this.emcUid.equals(other.emcUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.rest.ContactInfo[ emcUid=" + emcUid + " ]";
    }
    
}
