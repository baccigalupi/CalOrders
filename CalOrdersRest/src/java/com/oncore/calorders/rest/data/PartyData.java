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

import com.oncore.calorders.core.data.BaseData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The PartyData object wraps the common properties of an Employee.
 *
 * @author oncore
 */
@XmlRootElement
public class PartyData extends BaseData {

    private Integer empUid;

    private String empUserId;

    private String empPassword;

    private String empFirstNm;

    private String empMidNm;

    private String empLastNm;

    private String empTitle;

    private Date empHireDt;

    private String empId;

    private Integer depUid;

    private String depName;

    private List<GroupData> groupList = new ArrayList<>(1);

    /**
     * @return the empUid
     */
    public Integer getEmpUid() {
        return empUid;
    }

    /**
     * @param empUid the empUid to set
     */
    public void setEmpUid(Integer empUid) {
        this.empUid = empUid;
    }

    /**
     * @return the empUserId
     */
    public String getEmpUserId() {
        return empUserId;
    }

    /**
     * @param empUserId the empUserId to set
     */
    public void setEmpUserId(String empUserId) {
        this.empUserId = empUserId;
    }

    /**
     * @return the empPassword
     */
    public String getEmpPassword() {
        return empPassword;
    }

    /**
     * @param empPassword the empPassword to set
     */
    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    /**
     * @return the empFirstNm
     */
    public String getEmpFirstNm() {
        return empFirstNm;
    }

    /**
     * @param empFirstNm the empFirstNm to set
     */
    public void setEmpFirstNm(String empFirstNm) {
        this.empFirstNm = empFirstNm;
    }

    /**
     * @return the empMidNm
     */
    public String getEmpMidNm() {
        return empMidNm;
    }

    /**
     * @param empMidNm the empMidNm to set
     */
    public void setEmpMidNm(String empMidNm) {
        this.empMidNm = empMidNm;
    }

    /**
     * @return the empLastNm
     */
    public String getEmpLastNm() {
        return empLastNm;
    }

    /**
     * @param empLastNm the empLastNm to set
     */
    public void setEmpLastNm(String empLastNm) {
        this.empLastNm = empLastNm;
    }

    /**
     * @return the empTitle
     */
    public String getEmpTitle() {
        return empTitle;
    }

    /**
     * @param empTitle the empTitle to set
     */
    public void setEmpTitle(String empTitle) {
        this.empTitle = empTitle;
    }

    /**
     * @return the empHireDt
     */
    public Date getEmpHireDt() {
        return empHireDt;
    }

    /**
     * @param empHireDt the empHireDt to set
     */
    public void setEmpHireDt(Date empHireDt) {
        this.empHireDt = empHireDt;
    }

    /**
     * @return the empId
     */
    public String getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    /**
     * @return the depUid
     */
    public Integer getDepUid() {
        return depUid;
    }

    /**
     * @param depUid the depUid to set
     */
    public void setDepUid(Integer depUid) {
        this.depUid = depUid;
    }

    /**
     * @return the depName
     */
    public String getDepName() {
        return depName;
    }

    /**
     * @param depName the depName to set
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }

    /**
     * @return the groupList
     */
    public List<GroupData> getGroupList() {
        return groupList;
    }

    /**
     * @param groupList the groupList to set
     */
    public void setGroupList(List<GroupData> groupList) {
        this.groupList = groupList;
    }

}
