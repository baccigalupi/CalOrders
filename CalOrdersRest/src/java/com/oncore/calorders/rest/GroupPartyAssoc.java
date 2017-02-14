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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
@Cacheable(false)
@Table(name = "GROUP_PARTY_ASSOC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupPartyAssoc.findAll", query = "SELECT g FROM GroupPartyAssoc g")
    , @NamedQuery(name = "GroupPartyAssoc.findByGreUid", query = "SELECT g FROM GroupPartyAssoc g WHERE g.greUid = :greUid")
    , @NamedQuery(name = "GroupPartyAssoc.findByCreateUserId", query = "SELECT g FROM GroupPartyAssoc g WHERE g.createUserId = :createUserId")
    , @NamedQuery(name = "GroupPartyAssoc.findByCreateTs", query = "SELECT g FROM GroupPartyAssoc g WHERE g.createTs = :createTs")
    , @NamedQuery(name = "GroupPartyAssoc.findByUpdateUserId", query = "SELECT g FROM GroupPartyAssoc g WHERE g.updateUserId = :updateUserId")
    , @NamedQuery(name = "GroupPartyAssoc.findByUpdateTs", query = "SELECT g FROM GroupPartyAssoc g WHERE g.updateTs = :updateTs")})
public class GroupPartyAssoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GRE_UID")
    private Integer greUid;
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
    @JoinColumn(name = "GRP_UID_FK", referencedColumnName = "GRP_UID")
    @ManyToOne(optional = false)
    private Groups grpUidFk;
    @JoinColumn(name = "PTY_UID_FK", referencedColumnName = "PTY_UID")
    @ManyToOne(optional = false)
    private Party ptyUidFk;

    public GroupPartyAssoc() {
    }

    public GroupPartyAssoc(Integer greUid) {
        this.greUid = greUid;
    }

    public GroupPartyAssoc(Integer greUid, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.greUid = greUid;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getGreUid() {
        return greUid;
    }

    public void setGreUid(Integer greUid) {
        this.greUid = greUid;
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

    public Groups getGrpUidFk() {
        return grpUidFk;
    }

    public void setGrpUidFk(Groups grpUidFk) {
        this.grpUidFk = grpUidFk;
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
        hash += (greUid != null ? greUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupPartyAssoc)) {
            return false;
        }
        GroupPartyAssoc other = (GroupPartyAssoc) object;
        if ((this.greUid == null && other.greUid != null) || (this.greUid != null && !this.greUid.equals(other.greUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.rest.GroupPartyAssoc[ greUid=" + greUid + " ]";
    }
    
}
