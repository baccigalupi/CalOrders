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
package com.oncore.calorders.rest.service.extension;

import com.oncore.calorders.rest.Product;
import com.oncore.calorders.rest.service.ProductFacadeREST;
import com.oncore.calorders.rest.data.ProductData;
import com.oncore.calorders.rest.service.PrdCategoryCdFacadeREST;
import com.oncore.calorders.rest.service.PrdImgTypeCdFacadeREST;
import com.oncore.calorders.rest.service.PrdUnitCdFacadeREST;
import com.oncore.calorders.rest.service.VendorFacadeREST;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author oncore
 */
@Stateless
@Path("com.oncore.calorders.rest.product")
public class ProductFacadeRESTExtension extends ProductFacadeREST {

    @EJB
    private PrdCategoryCdFacadeREST categoryCdFacadeREST;

    @EJB
    private VendorFacadeREST vendorFacadeREST;

    @EJB
    private PrdUnitCdFacadeREST prdUnitCdFacadeREST;

    @EJB
    private PrdImgTypeCdFacadeREST prdImgTypeCdFacadeREST;

    /**
     * Constructor
     */
    public ProductFacadeRESTExtension() {

    }

    /**
     * Constructor
     *
     * @param em
     */
    public ProductFacadeRESTExtension(EntityManager em) {
        super(em);
    }

    @GET
    @Path("findActiveProductsByProductType/{productTypeCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> findActiveProductsByProductType(
            @PathParam("productTypeCode") String productTypeCode) {
        System.out.println("Running findActiveProductsByProductType by " + productTypeCode);

        List<Product> results = super.getEntityManager()
                .createQuery("SELECT p FROM Product p "
                        + "        JOIN p.prdCategoryCd c"
                        + "        WHERE c.code = :categoryCode "
                        + "        AND p.prdActiveInd = 1", Product.class)
                .setParameter("categoryCode", productTypeCode)
                .getResultList();

        return results;
    }

    /**
     * Determines if a user id exists
     *
     * @param userId a valid userid
     * @return an true if the user id is already used.
     */
    @GET
    @Path("doesProductNameExist/{productName}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Product> doesProductNameExist(@PathParam("productName") String productName) {

        List<Product> products = null;

        try {
            products = getEntityManager().createNamedQuery("Product.findByPrdName",
                    Product.class).setParameter("prdName", productName).getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            products = null;
        }

        return products;
    }

    /**
     * Creates the employee
     *
     * @param productData text containing product information in JSON.
     */
    @POST
    @Path("createProduct")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createProduct(ProductData productData) {

        System.out.println("Product: " + productData);

        // Create Product
        Product product = new Product();
        product.setCreateTs(new Date());
        product.setCreateUserId(productData.getPartyUserId());
        product.setPrdActiveInd(1);
        product.setPrdCategoryCd(this.categoryCdFacadeREST.find(productData.getProductCategory()));
        product.setPrdCntrDiscount(productData.getProductContractDiscount());
        product.setPrdCntrLnItm(productData.getProductContractLineItem());
        product.setPrdCntrUnitPrice(productData.getProductContractUnitPrice());
        product.setPrdImgImage(null);
        product.setPrdImgKey(null);
        product.setPrdImgName(null);
        product.setPrdImgOrigin(null);
        product.setPrdImgSize(null);
        product.setPrdImgTypeCd(this.prdImgTypeCdFacadeREST.find("JPEG"));
        product.setPrdLongDesc(productData.getProductFullDesc());
        product.setPrdName(productData.getProductName());
        product.setPrdOemName(productData.getProductOEMName());
        product.setPrdOemPartNum(productData.getProductOEMPartNumber());
        product.setPrdPrice(productData.getProductPrice());
        product.setPrdShortDesc(productData.getProductDescription());
        product.setPrdSku(productData.getProductSKU());
        product.setPrdUnitCd(this.prdUnitCdFacadeREST.find(productData.getProductUnitCode()));
        product.setUpdateTs(new Date());
        product.setUpdateUserId(productData.getPartyUserId());
        product.setVndUidFk(this.vendorFacadeREST.find(productData.getVendor()));

//TODO:  CLEAN UP, REMOVING RELATED SERVICE TABLE
//        Collection<RelatedService> relatedServiceCollection;
//        relatedServiceCollection = this.mapPrdRelServicesToRelatedServices(productData.getPartyUserId(), product, productData.getRelatedServices());
//
//        product.setRelatedServiceCollection(relatedServiceCollection);
        super.create(product);
    }

    /**
     *
     *
     * @param userId
     * @param product
     * @param relatedServiceDatas
     * @return
     */
    private Collection<Object> mapPrdRelServicesToRelatedServices(String userId, Product product, List<Integer> relatedServiceDatas) {

//TODO:  CLEAN UP, REMOVING RELATED SERVICE TABLE
//        Collection<RelatedService> relatedServiceCollection;
//        relatedServiceCollection = this.mapPrdRelServicesToRelatedServices(productData.getPartyUserId(), product, productData.getRelatedServices());
//
//        product.setRelatedServiceCollection(relatedServiceCollection);


//        Collection<RelatedService> relatedServices = null;
//        if (relatedServiceDatas != null && relatedServiceDatas.size() > 0) {
//            relatedServices = new ArrayList<RelatedService>();
//
//            for (Integer data : relatedServiceDatas) {
//                RelatedService relatedService = new RelatedService();
//                relatedService.setCreateTs(new Date());
//                relatedService.setCreateUserId(userId);
//                relatedService.setPrdUidFk(product);
//                relatedService.setPrsUidFk(this.prdRelServiceFacadeREST.find(data));
//                relatedService.setUpdateTs(new Date());
//                relatedService.setUpdateUserId(userId);
//                relatedServices.add(relatedService);
//            }
//        }
//        return relatedServices;

        return null;
    }
}
