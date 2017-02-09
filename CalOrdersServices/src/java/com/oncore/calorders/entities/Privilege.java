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
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "PRIVILEGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Privilege.findAll", query = "SELECT p FROM Privilege p")
    , @NamedQuery(name = "Privilege.findByPrvUid", query = "SELECT p FROM Privilege p WHERE p.prvUid = :prvUid")
    , @NamedQuery(name = "Privilege.findByPrvPageId", query = "SELECT p FROM Privilege p WHERE p.prvPageId = :prvPageId")
    , @NamedQuery(name = "Privilege.findByPrvComponentId", query = "SELECT p FROM Privilege p WHERE p.prvComponentId = :prvComponentId")
    , @NamedQuery(name = "Privilege.findByPrvReadInd", query = "SELECT p FROM Privilege p WHERE p.prvReadInd = :prvReadInd")
    , @NamedQuery(name = "Privilege.findByPrvWriteInd", query = "SELECT p FROM Privilege p WHERE p.prvWriteInd = :prvWriteInd")
    , @NamedQuery(name = "Privilege.findByPrvMisc", query = "SELECT p FROM Privilege p WHERE p.prvMisc = :prvMisc")
    , @NamedQuery(name = "Privilege.findByCreateUserId", query = "SELECT p FROM Privilege p WHERE p.createUserId = :createUserId")
    , @NamedQuery(name = "Privilege.findByCreateTs", query = "SELECT p FROM Privilege p WHERE p.createTs = :createTs")
    , @NamedQuery(name = "Privilege.findByUpdateUserId", query = "SELECT p FROM Privilege p WHERE p.updateUserId = :updateUserId")
    , @NamedQuery(name = "Privilege.findByUpdateTs", query = "SELECT p FROM Privilege p WHERE p.updateTs = :updateTs")})
public class Privilege implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRV_UID")
    private Integer prvUid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PRV_PAGE_ID")
    private String prvPageId;
    @Size(max = 45)
    @Column(name = "PRV_COMPONENT_ID")
    private String prvComponentId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRV_READ_IND")
    private int prvReadInd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRV_WRITE_IND")
    private int prvWriteInd;
    @Size(max = 45)
    @Column(name = "PRV_MISC")
    private String prvMisc;
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
    private Group1 grpUidFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prvUidFk")
    private Collection<GroupPrivilegeAssoc> groupPrivilegeAssocCollection;

    public Privilege() {
    }

    public Privilege(Integer prvUid) {
        this.prvUid = prvUid;
    }

    public Privilege(Integer prvUid, String prvPageId, int prvReadInd, int prvWriteInd, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.prvUid = prvUid;
        this.prvPageId = prvPageId;
        this.prvReadInd = prvReadInd;
        this.prvWriteInd = prvWriteInd;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getPrvUid() {
        return prvUid;
    }

    public void setPrvUid(Integer prvUid) {
        this.prvUid = prvUid;
    }

    public String getPrvPageId() {
        return prvPageId;
    }

    public void setPrvPageId(String prvPageId) {
        this.prvPageId = prvPageId;
    }

    public String getPrvComponentId() {
        return prvComponentId;
    }

    public void setPrvComponentId(String prvComponentId) {
        this.prvComponentId = prvComponentId;
    }

    public int getPrvReadInd() {
        return prvReadInd;
    }

    public void setPrvReadInd(int prvReadInd) {
        this.prvReadInd = prvReadInd;
    }

    public int getPrvWriteInd() {
        return prvWriteInd;
    }

    public void setPrvWriteInd(int prvWriteInd) {
        this.prvWriteInd = prvWriteInd;
    }

    public String getPrvMisc() {
        return prvMisc;
    }

    public void setPrvMisc(String prvMisc) {
        this.prvMisc = prvMisc;
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

    public Group1 getGrpUidFk() {
        return grpUidFk;
    }

    public void setGrpUidFk(Group1 grpUidFk) {
        this.grpUidFk = grpUidFk;
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
        hash += (prvUid != null ? prvUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Privilege)) {
            return false;
        }
        Privilege other = (Privilege) object;
        if ((this.prvUid == null && other.prvUid != null) || (this.prvUid != null && !this.prvUid.equals(other.prvUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.entities.Privilege[ prvUid=" + prvUid + " ]";
    }
    
}
