/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        resources.add(com.oncore.calorders.rest.service.Order1FacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.OrderProductAssocFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.PrdCategoryCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.PrdImgTypeCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.PrdRelServiceFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.PrdUnitCdFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.PrivilegeFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.ProductFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.RelatedServiceFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.VendorFacadeREST.class);
        resources.add(com.oncore.calorders.rest.service.extension.PartyFacadeRESTExtension.class);
    }
    
}
