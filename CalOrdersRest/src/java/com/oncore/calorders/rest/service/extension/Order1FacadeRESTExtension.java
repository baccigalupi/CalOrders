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
package com.oncore.calorders.rest.service.extension;

import com.oncore.calorders.core.enums.ErrorCode;
import com.oncore.calorders.core.exceptions.DataAccessException;
import com.oncore.calorders.core.utils.FormatHelper;
import static com.oncore.calorders.core.utils.FormatHelper.LOG;
import com.oncore.calorders.core.utils.Logger;
import com.oncore.calorders.rest.OrdStatusCd;
import com.oncore.calorders.rest.Order1;
import com.oncore.calorders.rest.OrderProductAssoc;
import com.oncore.calorders.rest.Party;
import com.oncore.calorders.rest.PrdRelService;
import com.oncore.calorders.rest.Product;
import com.oncore.calorders.rest.service.OrdStatusCdFacadeREST;
import com.oncore.calorders.rest.service.Order1FacadeREST;
import com.oncore.calorders.rest.service.OrderProductAssocFacadeREST;
import com.oncore.calorders.rest.service.PrdRelServiceFacadeREST;
import com.oncore.calorders.rest.service.ProductFacadeREST;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author oncore
 */
@Stateless
@Path("com.oncore.calorders.rest.order1")
public class Order1FacadeRESTExtension extends Order1FacadeREST {

    @EJB
    OrdStatusCdFacadeREST ordStatusCdFacadeREST;

    @EJB
    PartyFacadeRESTExtension partyFacadeRESTExtension;

    @EJB
    ProductFacadeREST productFacadeREST;

    @EJB
    OrderProductAssocFacadeREST orderProductAssocFacadeREST;

    @EJB
    PrdRelServiceFacadeREST prdRelServiceFacadeREST;

    public Order1FacadeRESTExtension() {
        super();
    }

    /**
     * Creates an order, containing the ordered products and related services.
     * 
     * @param orderjson The order, represented as a JSON string
     * @throws DataAccessException 
     */
    @POST
    @Path("createOrder")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void createOrder(String orderjson) throws DataAccessException {

        try {
            JsonReader reader = Json.createReader(new StringReader(orderjson));
            JsonObject orderObject = reader.readObject();
            reader.close();

            Order1 order = new Order1();

            order.setUpdateTs(new Date());
            order.setUpdateUserId(orderObject.getString("updateUserId", null));
            order.setCreateTs(new Date());
            order.setCreateUserId(orderObject.getString("createUserId", null));

            OrdStatusCd ordStatusCd = this.ordStatusCdFacadeREST.find(orderObject.getString("orderStatusCd", null));

            if (ordStatusCd == null) {
                throw new DataAccessException(ErrorCode.DATAACCESSERROR.toString());
            } else {
                order.setOrdStatusCd(ordStatusCd);
            }

            Party party = this.partyFacadeRESTExtension.find(orderObject.getInt("partyUid"));

            if (party == null) {
                throw new DataAccessException(ErrorCode.DATAACCESSERROR.toString());
            } else {
                order.setPtyUidFk(party);
            }

            super.create(order);

            order.setOrderProductAssocCollection(new ArrayList<OrderProductAssoc>());

            JsonArray productList = orderObject.getJsonArray("products");

            for (int i = 0; i < productList.size(); i++) {
                JsonObject productObject = productList.getJsonObject(i);
                OrderProductAssoc orderProductAssoc = new OrderProductAssoc();

                Product product = this.productFacadeREST.find(productObject.getInt("prdUid"));

                orderProductAssoc.setPrdUidFk(product);
                orderProductAssoc.setOrdUidFk(order);
                orderProductAssoc.setUpdateTs(new Date());
                orderProductAssoc.setUpdateUserId(orderObject.getString("updateUserId", null));
                orderProductAssoc.setCreateTs(new Date());
                orderProductAssoc.setCreateUserId(orderObject.getString("createUserId", null));
                orderProductAssoc.setOpaQuantity(productObject.getInt("quantity"));
                orderProductAssoc.setOpaPrice(product.getPrdPrice().multiply(BigDecimal.valueOf(orderProductAssoc.getOpaQuantity())));

                this.orderProductAssocFacadeREST.create(orderProductAssoc);

                order.getOrderProductAssocCollection().add(orderProductAssoc);
            }

            JsonArray serviceList = orderObject.getJsonArray("services");

            for (int i = 0; i < serviceList.size(); i++) {
                JsonObject serviceObject = serviceList.getJsonObject(i);
                OrderProductAssoc orderProductAssoc = new OrderProductAssoc();

                PrdRelService service = this.prdRelServiceFacadeREST.find(serviceObject.getInt("prsUid"));

                orderProductAssoc.setPrsUidFk(service);
                orderProductAssoc.setOrdUidFk(order);
                orderProductAssoc.setUpdateTs(new Date());
                orderProductAssoc.setUpdateUserId(orderObject.getString("updateUserId", null));
                orderProductAssoc.setCreateTs(new Date());
                orderProductAssoc.setCreateUserId(orderObject.getString("createUserId", null));
                orderProductAssoc.setOpaQuantity(serviceObject.getInt("quantity"));
                orderProductAssoc.setOpaPrice(service.getPrsPrice().multiply(BigDecimal.valueOf(orderProductAssoc.getOpaQuantity())));

                this.orderProductAssocFacadeREST.create(orderProductAssoc);

                order.getOrderProductAssocCollection().add(orderProductAssoc);
            }

        } catch (Exception ex) {
            Logger.error(LOG, FormatHelper.getStackTrace(ex));
            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
        }

    }

}
