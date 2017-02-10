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
@Table(name = "ORD_STATUS_CD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdStatusCd.findAll", query = "SELECT o FROM OrdStatusCd o")
    , @NamedQuery(name = "OrdStatusCd.findByCode", query = "SELECT o FROM OrdStatusCd o WHERE o.code = :code")
    , @NamedQuery(name = "OrdStatusCd.findByShortDesc", query = "SELECT o FROM OrdStatusCd o WHERE o.shortDesc = :shortDesc")
    , @NamedQuery(name = "OrdStatusCd.findByLongDesc", query = "SELECT o FROM OrdStatusCd o WHERE o.longDesc = :longDesc")
    , @NamedQuery(name = "OrdStatusCd.findByCreateUserId", query = "SELECT o FROM OrdStatusCd o WHERE o.createUserId = :createUserId")
    , @NamedQuery(name = "OrdStatusCd.findByCreateTs", query = "SELECT o FROM OrdStatusCd o WHERE o.createTs = :createTs")
    , @NamedQuery(name = "OrdStatusCd.findByUpdateUserId", query = "SELECT o FROM OrdStatusCd o WHERE o.updateUserId = :updateUserId")
    , @NamedQuery(name = "OrdStatusCd.findByUpdateTs", query = "SELECT o FROM OrdStatusCd o WHERE o.updateTs = :updateTs")})
public class OrdStatusCd implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordStatusCd")
    private Collection<Order1> order1Collection;

    public OrdStatusCd() {
    }

    public OrdStatusCd(String code) {
        this.code = code;
    }

    public OrdStatusCd(String code, String shortDesc, String longDesc, String createUserId, Date createTs, String updateUserId, Date updateTs) {
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
    public Collection<Order1> getOrder1Collection() {
        return order1Collection;
    }

    public void setOrder1Collection(Collection<Order1> order1Collection) {
        this.order1Collection = order1Collection;
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
        if (!(object instanceof OrdStatusCd)) {
            return false;
        }
        OrdStatusCd other = (OrdStatusCd) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oncore.calorders.rest.OrdStatusCd[ code=" + code + " ]";
    }
    
}
