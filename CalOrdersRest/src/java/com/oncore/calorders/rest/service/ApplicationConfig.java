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
package com.oncore.calorders.rest.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author oncore
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.oncore.calorders.rest.service.AddressFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.AdrCountryCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.AdrStateCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.ContactInfoFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.DepartmentFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.EmcTypeCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.GroupPartyAssocFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.GroupPrivilegeAssocFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.GroupsFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.GrpTypeCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.OrdStatusCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.OrderProductAssocFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.PrdCategoryCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.PrdImgTypeCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.PrdRelServiceFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.PrdUnitCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.PrivilegeFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.RelatedServiceFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.VendorFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.extension.Order1FacadeRESTExtension.class);
        resources.add(com.oncore.calorders.rest.service.extension.PartyFacadeRESTExtension.class);
        resources.add(com.oncore.calorders.rest.service.extension.ProductFacadeRESTExtension.class);
    }
    
}
