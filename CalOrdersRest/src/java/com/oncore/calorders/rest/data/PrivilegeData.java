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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author oncore
 */
@XmlRootElement
public class PrivilegeData extends BaseData {

    private Integer prvUid;
    private String pageIdentifier;
    private String pageDescription;
    private String componentIdentifier;
    private Integer pageOrder;
    private Boolean canRead = Boolean.FALSE;
    private Boolean canWrite = Boolean.FALSE;

    /**
     * @return the prvUid
     */
    public Integer getPrvUid() {
        return prvUid;
    }

    /**
     * @param prvUid the prvUid to set
     */
    public void setPrvUid(Integer prvUid) {
        this.prvUid = prvUid;
    }

    /**
     * @return the pageIdentifier
     */
    public String getPageIdentifier() {
        return pageIdentifier;
    }

    /**
     * @param pageIdentifier the pageIdentifier to set
     */
    public void setPageIdentifier(String pageIdentifier) {
        this.pageIdentifier = pageIdentifier;
    }

    /**
     * @return the pageDescription
     */
    public String getPageDescription() {
        return pageDescription;
    }

    /**
     * @param pageDescription the pageDescription to set
     */
    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

    /**
     * @return the componentIdentifier
     */
    public String getComponentIdentifier() {
        return componentIdentifier;
    }

    /**
     * @param componentIdentifier the componentIdentifier to set
     */
    public void setComponentIdentifier(String componentIdentifier) {
        this.componentIdentifier = componentIdentifier;
    }

    /**
     * @return the canRead
     */
    public Boolean getCanRead() {
        return canRead;
    }

    /**
     * @param canRead the canRead to set
     */
    public void setCanRead(Boolean canRead) {
        this.canRead = canRead;
    }

    /**
     * @return the canWrite
     */
    public Boolean getCanWrite() {
        return canWrite;
    }

    /**
     * @param canWrite the canWrite to set
     */
    public void setCanWrite(Boolean canWrite) {
        this.canWrite = canWrite;
    }

    /**
     * @return the pageOrder
     */
    public Integer getPageOrder() {
        return pageOrder;
    }

    /**
     * @param pageOrder the pageOrder to set
     */
    public void setPageOrder(Integer pageOrder) {
        this.pageOrder = pageOrder;
    }

}
