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
@Table(name = "PARTY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Party.findAll", query = "SELECT p FROM Party p")
    , @NamedQuery(name = "Party.findByPtyUid", query = "SELECT p FROM Party p WHERE p.ptyUid = :ptyUid")
    , @NamedQuery(name = "Party.findByPtyUserId", query = "SELECT p FROM Party p WHERE p.ptyUserId = :ptyUserId")
    , @NamedQuery(name = "Party.findByPtyPassword", query = "SELECT p FROM Party p WHERE p.ptyPassword = :ptyPassword")
    , @NamedQuery(name = "Party.findByPtyFirstNm", query = "SELECT p FROM Party p WHERE p.ptyFirstNm = :ptyFirstNm")
    , @NamedQuery(name = "Party.findByPtyMidNm", query = "SELECT p FROM Party p WHERE p.ptyMidNm = :ptyMidNm")
    , @NamedQuery(name = "Party.findByPtyLastNm", query = "SELECT p FROM Party p WHERE p.ptyLastNm = :ptyLastNm")
    , @NamedQuery(name = "Party.findByPtyTitle", query = "SELECT p FROM Party p WHERE p.ptyTitle = :ptyTitle")
    , @NamedQuery(name = "Party.findByPtyHireDt", query = "SELECT p FROM Party p WHERE p.ptyHireDt = :ptyHireDt")
    , @NamedQuery(name = "Party.findByPtyId", query = "SELECT p FROM Party p WHERE p.ptyId = :ptyId")
    , @NamedQuery(name = "Party.findByPtyImgId", query = "SELECT p FROM Party p WHERE p.ptyImgId = :ptyImgId")
    , @NamedQuery(name = "Party.findByPtyActiveInd", query = "SELECT p FROM Party p WHERE p.ptyActiveInd = :ptyActiveInd")
    , @NamedQuery(name = "Party.findByPtyDobDt", query = "SELECT p FROM Party p WHERE p.ptyDobDt = :ptyDobDt")
    , @NamedQuery(name = "Party.findByCreateUserId", query = "SELECT p FROM Party p WHERE p.createUserId = :createUserId")
    , @NamedQuery(name = "Party.findByCreateTs", query = "SELECT p FROM Party p WHERE p.createTs = :createTs")
    , @NamedQuery(name = "Party.findByUpdateUserId", query = "SELECT p FROM Party p WHERE p.updateUserId = :updateUserId")
    , @NamedQuery(name = "Party.findByUpdateTs", query = "SELECT p FROM Party p WHERE p.updateTs = :updateTs")})
public class Party implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PTY_UID")
    private Integer ptyUid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "PTY_USER_ID")
    private String ptyUserId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "PTY_PASSWORD")
    private String ptyPassword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PTY_FIRST_NM")
    private String ptyFirstNm;
    @Size(max = 45)
    @Column(name = "PTY_MID_NM")
    private String ptyMidNm;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PTY_LAST_NM")
    private String ptyLastNm;
    @Size(max = 45)
    @Column(name = "PTY_TITLE")
    private String ptyTitle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PTY_HIRE_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ptyHireDt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "PTY_ID")
    private String ptyId;
    @Size(max = 512)
    @Column(name = "PTY_IMG_ID")
    private String ptyImgId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PTY_ACTIVE_IND")
    private int ptyActiveInd;
    @Column(name = "PTY_DOB_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ptyDobDt;
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
    @OneToMany(mappedBy = "ptyUidFk")
    private Collection<ContactInfo> contactInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptyUidFk")
    private Collection<GroupPartyAssoc> groupPartyAssocCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptyUidFk")
    private Collection<OrderHistory> orderHistoryCollection;
    @OneToMany(mappedBy = "ptyUidFk")
    private Collection<Address> addressCollection;

    public Party() {
    }

    public Party(Integer ptyUid) {
        this.ptyUid = ptyUid;
    }

    public Party(Integer ptyUid, String ptyUserId, String ptyPassword, String ptyFirstNm, String ptyLastNm, Date ptyHireDt, String ptyId, int ptyActiveInd, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.ptyUid = ptyUid;
        this.ptyUserId = ptyUserId;
        this.ptyPassword = ptyPassword;
        this.ptyFirstNm = ptyFirstNm;
        this.ptyLastNm = ptyLastNm;
        this.ptyHireDt = ptyHireDt;
        this.ptyId = ptyId;
        this.ptyActiveInd = ptyActiveInd;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getPtyUid() {
        return ptyUid;
    }

    public void setPtyUid(Integer ptyUid) {
        this.ptyUid = ptyUid;
    }

    public String getPtyUserId() {
        return ptyUserId;
    }

    public void setPtyUserId(String ptyUserId) {
        this.ptyUserId = ptyUserId;
    }

    public String getPtyPassword() {
        return ptyPassword;
    }

    public void setPtyPassword(String ptyPassword) {
        this.ptyPassword = ptyPassword;
    }

    public String getPtyFirstNm() {
        return ptyFirstNm;
    }

    public void setPtyFirstNm(String ptyFirstNm) {
        this.ptyFirstNm = ptyFirstNm;
    }

    public String getPtyMidNm() {
        return ptyMidNm;
    }

    public void setPtyMidNm(String ptyMidNm) {
        this.ptyMidNm = ptyMidNm;
    }

    public String getPtyLastNm() {
        return ptyLastNm;
    }

    public void setPtyLastNm(String ptyLastNm) {
        this.ptyLastNm = ptyLastNm;
    }

    public String getPtyTitle() {
        return ptyTitle;
    }

    public void setPtyTitle(String ptyTitle) {
        this.ptyTitle = ptyTitle;
    }

    public Date getPtyHireDt() {
        return ptyHireDt;
    }

    public void setPtyHireDt(Date ptyHireDt) {
        this.ptyHireDt = ptyHireDt;
    }

    public String getPtyId() {
        return ptyId;
    }

    public void setPtyId(String ptyId) {
        this.ptyId = ptyId;
    }

    public String getPtyImgId() {
        return ptyImgId;
    }

    public void setPtyImgId(String ptyImgId) {
        this.ptyImgId = ptyImgId;
    }

    public int getPtyActiveInd() {
        return ptyActiveInd;
    }

    public void setPtyActiveInd(int ptyActiveInd) {
        this.ptyActiveInd = ptyActiveInd;
    }

    public Date getPtyDobDt() {
        return ptyDobDt;
    }

    public void setPtyDobDt(Date ptyDobDt) {
        this.ptyDobDt = ptyDobDt;
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
    public Collection<ContactInfo> getContactInfoCollection() {
        return contactInfoCollection;
    }

    public void setContactInfoCollection(Collection<ContactInfo> contactInfoCollection) {
        this.contactInfoCollection = contactInfoCollection;
    }

    @XmlTransient
    public Collection<GroupPartyAssoc> getGroupPartyAssocCollection() {
        return groupPartyAssocCollection;
    }

    public void setGroupPartyAssocCollection(Collection<GroupPartyAssoc> groupPartyAssocCollection) {
        this.groupPartyAssocCollection = groupPartyAssocCollection;
    }

    @XmlTransient
    public Collection<OrderHistory> getOrderHistoryCollection() {
        return orderHistoryCollection;
    }

    public void setOrderHistoryCollection(Collection<OrderHistory> orderHistoryCollection) {
        this.orderHistoryCollection = orderHistoryCollection;
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
        hash += (ptyUid != null ? ptyUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Party)) {
            return false;
        }
        Party other = (Party) object;
        if ((this.ptyUid == null && other.ptyUid != null) || (this.ptyUid != null && !this.ptyUid.equals(other.ptyUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.rest.Party[ ptyUid=" + ptyUid + " ]";
    }
    
}
