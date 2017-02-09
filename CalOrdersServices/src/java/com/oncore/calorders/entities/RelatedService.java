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
@Table(name = "RELATED_SERVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelatedService.findAll", query = "SELECT r FROM RelatedService r")
    , @NamedQuery(name = "RelatedService.findByRlsUid", query = "SELECT r FROM RelatedService r WHERE r.rlsUid = :rlsUid")
    , @NamedQuery(name = "RelatedService.findByCreateUserId", query = "SELECT r FROM RelatedService r WHERE r.createUserId = :createUserId")
    , @NamedQuery(name = "RelatedService.findByCreateTs", query = "SELECT r FROM RelatedService r WHERE r.createTs = :createTs")
    , @NamedQuery(name = "RelatedService.findByUpdateUserId", query = "SELECT r FROM RelatedService r WHERE r.updateUserId = :updateUserId")
    , @NamedQuery(name = "RelatedService.findByUpdateTs", query = "SELECT r FROM RelatedService r WHERE r.updateTs = :updateTs")})
public class RelatedService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RLS_UID")
    private Integer rlsUid;
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
    @JoinColumn(name = "PRS_UID_FK", referencedColumnName = "PRS_UID")
    @ManyToOne(optional = false)
    private PrdRelService prsUidFk;
    @JoinColumn(name = "PRD_UID_FK", referencedColumnName = "PRD_UID")
    @ManyToOne(optional = false)
    private Product prdUidFk;

    public RelatedService() {
    }

    public RelatedService(Integer rlsUid) {
        this.rlsUid = rlsUid;
    }

    public RelatedService(Integer rlsUid, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.rlsUid = rlsUid;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getRlsUid() {
        return rlsUid;
    }

    public void setRlsUid(Integer rlsUid) {
        this.rlsUid = rlsUid;
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

    public PrdRelService getPrsUidFk() {
        return prsUidFk;
    }

    public void setPrsUidFk(PrdRelService prsUidFk) {
        this.prsUidFk = prsUidFk;
    }

    public Product getPrdUidFk() {
        return prdUidFk;
    }

    public void setPrdUidFk(Product prdUidFk) {
        this.prdUidFk = prdUidFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rlsUid != null ? rlsUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelatedService)) {
            return false;
        }
        RelatedService other = (RelatedService) object;
        if ((this.rlsUid == null && other.rlsUid != null) || (this.rlsUid != null && !this.rlsUid.equals(other.rlsUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.entities.RelatedService[ rlsUid=" + rlsUid + " ]";
    }
    
}
