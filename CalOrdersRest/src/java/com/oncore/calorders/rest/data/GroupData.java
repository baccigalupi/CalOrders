package com.oncore.calorders.rest.data;

import com.oncore.calorders.core.data.BaseData;
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
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GroupData object wraps Group related data
 *
 * @author oncore
 */
@XmlRootElement
public class GroupData extends BaseData {

    private Integer grpUid;

    private String grpTypeCd;

    private String grpTypeCdDescription;

    private List<PrivilegeData> privilegesList = new ArrayList<>(1);

    /**
     * @return the grpUid
     */
    public Integer getGrpUid() {
        return grpUid;
    }

    /**
     * @param grpUid the grpUid to set
     */
    public void setGrpUid(Integer grpUid) {
        this.grpUid = grpUid;
    }

    /**
     * @return the grpTypeCd
     */
    public String getGrpTypeCd() {
        return grpTypeCd;
    }

    /**
     * @param grpTypeCd the grpTypeCd to set
     */
    public void setGrpTypeCd(String grpTypeCd) {
        this.grpTypeCd = grpTypeCd;
    }

    /**
     * @return the grpTypeCdDescription
     */
    public String getGrpTypeCdDescription() {
        return grpTypeCdDescription;
    }

    /**
     * @param grpTypeCdDescription the grpTypeCdDescription to set
     */
    public void setGrpTypeCdDescription(String grpTypeCdDescription) {
        this.grpTypeCdDescription = grpTypeCdDescription;
    }

    /**
     * @return the privilegesList
     */
    public List<PrivilegeData> getPrivilegesList() {
        return privilegesList;
    }

    /**
     * @param privilegesList the privilegesList to set
     */
    public void setPrivilegesList(List<PrivilegeData> privilegesList) {
        this.privilegesList = privilegesList;
    }

}
