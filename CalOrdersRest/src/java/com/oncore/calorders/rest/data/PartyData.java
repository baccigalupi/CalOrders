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

    private Integer ptyUid;

    private String ptyUserId;

    private String ptyPassword;

    private String ptyFirstNm;

    private String ptyMidNm;

    private String ptyLastNm;

    private String ptyTitle;

    private Date ptyHireDt;

    private String ptyId;

    private Integer depUid;

    private String depName;

    private List<GroupData> groupList = new ArrayList<>(1);

    /**
     * @return the ptyUid
     */
    public Integer getPtyUid() {
        return ptyUid;
    }

    /**
     * @param ptyUid the ptyUid to set
     */
    public void setPtyUid(Integer ptyUid) {
        this.ptyUid = ptyUid;
    }

    /**
     * @return the ptyUserId
     */
    public String getPtyUserId() {
        return ptyUserId;
    }

    /**
     * @param ptyUserId the ptyUserId to set
     */
    public void setPtyUserId(String ptyUserId) {
        this.ptyUserId = ptyUserId;
    }

    /**
     * @return the ptyPassword
     */
    public String getPtyPassword() {
        return ptyPassword;
    }

    /**
     * @param ptyPassword the ptyPassword to set
     */
    public void setPtyPassword(String ptyPassword) {
        this.ptyPassword = ptyPassword;
    }

    /**
     * @return the ptyFirstNm
     */
    public String getPtyFirstNm() {
        return ptyFirstNm;
    }

    /**
     * @param ptyFirstNm the ptyFirstNm to set
     */
    public void setPtyFirstNm(String ptyFirstNm) {
        this.ptyFirstNm = ptyFirstNm;
    }

    /**
     * @return the ptyMidNm
     */
    public String getPtyMidNm() {
        return ptyMidNm;
    }

    /**
     * @param ptyMidNm the ptyMidNm to set
     */
    public void setPtyMidNm(String ptyMidNm) {
        this.ptyMidNm = ptyMidNm;
    }

    /**
     * @return the ptyLastNm
     */
    public String getPtyLastNm() {
        return ptyLastNm;
    }

    /**
     * @param ptyLastNm the ptyLastNm to set
     */
    public void setPtyLastNm(String ptyLastNm) {
        this.ptyLastNm = ptyLastNm;
    }

    /**
     * @return the ptyTitle
     */
    public String getPtyTitle() {
        return ptyTitle;
    }

    /**
     * @param ptyTitle the ptyTitle to set
     */
    public void setPtyTitle(String ptyTitle) {
        this.ptyTitle = ptyTitle;
    }

    /**
     * @return the ptyHireDt
     */
    public Date getPtyHireDt() {
        return ptyHireDt;
    }

    /**
     * @param ptyHireDt the ptyHireDt to set
     */
    public void setPtyHireDt(Date ptyHireDt) {
        this.ptyHireDt = ptyHireDt;
    }

    /**
     * @return the ptyId
     */
    public String getPtyId() {
        return ptyId;
    }

    /**
     * @param ptyId the ptyId to set
     */
    public void setPtyId(String ptyId) {
        this.ptyId = ptyId;
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
