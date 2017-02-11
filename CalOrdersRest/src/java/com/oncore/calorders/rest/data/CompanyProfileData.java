/*
 * The MIT License
 *
 * Copyright 2017 oncore.
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
package com.oncore.calorders.rest.data;

/**
 * The CompanyData object wraps the common properties of a Company.
 *
 * @author oncore
 */
public class CompanyProfileData {

    private Integer cmpUid;
    private String cmpName;
    private String cmpAdrLine1;
    private String cmpAdrLine2;
    private String cmpAdrCity;
    private String cmpAdrStateCd;
    private String cmpAdrZip;
    private String cmpPhone;
    private String cmpFEIN;
    
    private Integer adminEmpUid;
    private String adminFirstName;
    private String adminLastName;
    private String adminAdrLine1;
    private String adminAdrLine2;
    private String adminAdrCity;
    private String adminAdrStateCd;
    private String adminAdrZip;
    private String adminPhone;
    private String adminEmail;
    private String adminPassword;

    public Integer getCmpUid() {
        return cmpUid;
    }

    public void setCmpUid(Integer cmpUid) {
        this.cmpUid = cmpUid;
    }

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    public String getCmpAdrLine1() {
        return cmpAdrLine1;
    }

    public void setCmpAdrLine1(String cmpAdrLine1) {
        this.cmpAdrLine1 = cmpAdrLine1;
    }

    public String getCmpAdrLine2() {
        return cmpAdrLine2;
    }

    public void setCmpAdrLine2(String cmpAdrLine2) {
        this.cmpAdrLine2 = cmpAdrLine2;
    }

    public String getCmpAdrCity() {
        return cmpAdrCity;
    }

    public void setCmpAdrCity(String cmpAdrCity) {
        this.cmpAdrCity = cmpAdrCity;
    }

    public String getCmpAdrStateCd() {
        return cmpAdrStateCd;
    }

    public void setCmpAdrStateCd(String cmpAdrStateCd) {
        this.cmpAdrStateCd = cmpAdrStateCd;
    }

    public String getCmpAdrZip() {
        return cmpAdrZip;
    }

    public void setCmpAdrZip(String cmpAdrZip) {
        this.cmpAdrZip = cmpAdrZip;
    }

    public String getCmpPhone() {
        return cmpPhone;
    }

    public void setCmpPhone(String cmpPhone) {
        this.cmpPhone = cmpPhone;
    }

    public String getCmpFEIN() {
        return cmpFEIN;
    }

    public void setCmpFEIN(String cmpFEIN) {
        this.cmpFEIN = cmpFEIN;
    }

    public String getAdminFirstName() {
        return adminFirstName;
    }

    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }

    public String getAdminLastName() {
        return adminLastName;
    }

    public void setAdminLastName(String adminLastName) {
        this.adminLastName = adminLastName;
    }

    public String getAdminAdrLine1() {
        return adminAdrLine1;
    }

    public void setAdminAdrLine1(String adminAdrLine1) {
        this.adminAdrLine1 = adminAdrLine1;
    }

    public String getAdminAdrLine2() {
        return adminAdrLine2;
    }

    public void setAdminAdrLine2(String adminAdrLine2) {
        this.adminAdrLine2 = adminAdrLine2;
    }

    public String getAdminAdrCity() {
        return adminAdrCity;
    }

    public void setAdminAdrCity(String adminAdrCity) {
        this.adminAdrCity = adminAdrCity;
    }

    public String getAdminAdrStateCd() {
        return adminAdrStateCd;
    }

    public void setAdminAdrStateCd(String adminAdrStateCd) {
        this.adminAdrStateCd = adminAdrStateCd;
    }

    public String getAdminAdrZip() {
        return adminAdrZip;
    }

    public void setAdminAdrZip(String adminAdrZip) {
        this.adminAdrZip = adminAdrZip;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public Integer getAdminEmpUid() {
        return adminEmpUid;
    }

    public void setAdminEmpUid(Integer adminEmpUid) {
        this.adminEmpUid = adminEmpUid;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    
    
}
