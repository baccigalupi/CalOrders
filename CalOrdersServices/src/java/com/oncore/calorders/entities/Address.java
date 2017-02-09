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
@Table(name = "ADDRESS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")
    , @NamedQuery(name = "Address.findByAdrUid", query = "SELECT a FROM Address a WHERE a.adrUid = :adrUid")
    , @NamedQuery(name = "Address.findByAdrLine1", query = "SELECT a FROM Address a WHERE a.adrLine1 = :adrLine1")
    , @NamedQuery(name = "Address.findByAdrLine2", query = "SELECT a FROM Address a WHERE a.adrLine2 = :adrLine2")
    , @NamedQuery(name = "Address.findByAdrCity", query = "SELECT a FROM Address a WHERE a.adrCity = :adrCity")
    , @NamedQuery(name = "Address.findByAdrZip5", query = "SELECT a FROM Address a WHERE a.adrZip5 = :adrZip5")
    , @NamedQuery(name = "Address.findByAdrZip4", query = "SELECT a FROM Address a WHERE a.adrZip4 = :adrZip4")
    , @NamedQuery(name = "Address.findByAdrForeignZip", query = "SELECT a FROM Address a WHERE a.adrForeignZip = :adrForeignZip")
    , @NamedQuery(name = "Address.findByAdrRtnInd", query = "SELECT a FROM Address a WHERE a.adrRtnInd = :adrRtnInd")
    , @NamedQuery(name = "Address.findByAdrLocationInd", query = "SELECT a FROM Address a WHERE a.adrLocationInd = :adrLocationInd")
    , @NamedQuery(name = "Address.findByCreateUserId", query = "SELECT a FROM Address a WHERE a.createUserId = :createUserId")
    , @NamedQuery(name = "Address.findByCreateTs", query = "SELECT a FROM Address a WHERE a.createTs = :createTs")
    , @NamedQuery(name = "Address.findByUpdateUserId", query = "SELECT a FROM Address a WHERE a.updateUserId = :updateUserId")
    , @NamedQuery(name = "Address.findByUpdateTs", query = "SELECT a FROM Address a WHERE a.updateTs = :updateTs")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ADR_UID")
    private Integer adrUid;
    @Size(max = 128)
    @Column(name = "ADR_LINE1")
    private String adrLine1;
    @Size(max = 128)
    @Column(name = "ADR_LINE2")
    private String adrLine2;
    @Size(max = 64)
    @Column(name = "ADR_CITY")
    private String adrCity;
    @Size(max = 5)
    @Column(name = "ADR_ZIP5")
    private String adrZip5;
    @Size(max = 4)
    @Column(name = "ADR_ZIP4")
    private String adrZip4;
    @Size(max = 32)
    @Column(name = "ADR_FOREIGN_ZIP")
    private String adrForeignZip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ADR_RTN_IND")
    private int adrRtnInd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ADR_LOCATION_IND")
    private int adrLocationInd;
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
    @JoinColumn(name = "PTY_UID_FK", referencedColumnName = "PTY_UID")
    @ManyToOne
    private Party ptyUidFk;
    @JoinColumn(name = "VND_UID_FK", referencedColumnName = "VND_UID")
    @ManyToOne
    private Vendor vndUidFk;
    @JoinColumn(name = "ADR_COUNTRY_CD", referencedColumnName = "CODE")
    @ManyToOne
    private AdrCountryCd adrCountryCd;
    @JoinColumn(name = "ADR_STATE_CD", referencedColumnName = "CODE")
    @ManyToOne
    private AdrStateCd adrStateCd;

    public Address() {
    }

    public Address(Integer adrUid) {
        this.adrUid = adrUid;
    }

    public Address(Integer adrUid, int adrRtnInd, int adrLocationInd, String createUserId, Date createTs, String updateUserId, Date updateTs) {
        this.adrUid = adrUid;
        this.adrRtnInd = adrRtnInd;
        this.adrLocationInd = adrLocationInd;
        this.createUserId = createUserId;
        this.createTs = createTs;
        this.updateUserId = updateUserId;
        this.updateTs = updateTs;
    }

    public Integer getAdrUid() {
        return adrUid;
    }

    public void setAdrUid(Integer adrUid) {
        this.adrUid = adrUid;
    }

    public String getAdrLine1() {
        return adrLine1;
    }

    public void setAdrLine1(String adrLine1) {
        this.adrLine1 = adrLine1;
    }

    public String getAdrLine2() {
        return adrLine2;
    }

    public void setAdrLine2(String adrLine2) {
        this.adrLine2 = adrLine2;
    }

    public String getAdrCity() {
        return adrCity;
    }

    public void setAdrCity(String adrCity) {
        this.adrCity = adrCity;
    }

    public String getAdrZip5() {
        return adrZip5;
    }

    public void setAdrZip5(String adrZip5) {
        this.adrZip5 = adrZip5;
    }

    public String getAdrZip4() {
        return adrZip4;
    }

    public void setAdrZip4(String adrZip4) {
        this.adrZip4 = adrZip4;
    }

    public String getAdrForeignZip() {
        return adrForeignZip;
    }

    public void setAdrForeignZip(String adrForeignZip) {
        this.adrForeignZip = adrForeignZip;
    }

    public int getAdrRtnInd() {
        return adrRtnInd;
    }

    public void setAdrRtnInd(int adrRtnInd) {
        this.adrRtnInd = adrRtnInd;
    }

    public int getAdrLocationInd() {
        return adrLocationInd;
    }

    public void setAdrLocationInd(int adrLocationInd) {
        this.adrLocationInd = adrLocationInd;
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

    public Party getPtyUidFk() {
        return ptyUidFk;
    }

    public void setPtyUidFk(Party ptyUidFk) {
        this.ptyUidFk = ptyUidFk;
    }

    public Vendor getVndUidFk() {
        return vndUidFk;
    }

    public void setVndUidFk(Vendor vndUidFk) {
        this.vndUidFk = vndUidFk;
    }

    public AdrCountryCd getAdrCountryCd() {
        return adrCountryCd;
    }

    public void setAdrCountryCd(AdrCountryCd adrCountryCd) {
        this.adrCountryCd = adrCountryCd;
    }

    public AdrStateCd getAdrStateCd() {
        return adrStateCd;
    }

    public void setAdrStateCd(AdrStateCd adrStateCd) {
        this.adrStateCd = adrStateCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adrUid != null ? adrUid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.adrUid == null && other.adrUid != null) || (this.adrUid != null && !this.adrUid.equals(other.adrUid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.entities.Address[ adrUid=" + adrUid + " ]";
    }
    
}
