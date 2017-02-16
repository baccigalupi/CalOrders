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

import com.oncore.calorders.rest.OrdStatusCd;
import com.oncore.calorders.rest.OrderHistory;
import com.oncore.calorders.rest.OrderProductAssoc;
import com.oncore.calorders.rest.Party;
import com.oncore.calorders.rest.Product;
import com.oncore.calorders.rest.service.OrdStatusCdFacadeREST;
import com.oncore.calorders.rest.service.OrderHistoryFacadeREST;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author oncore
 */
public class OrderHistoryFacadeRESTExtensionTest {

    public OrderHistoryFacadeRESTExtensionTest() {
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testCreateOrder() throws Exception {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrdStatusCd(new OrdStatusCd("PND"));
        orderHistory.setOrdUid(2222);

        Product product = new Product();
        product.setPrdActiveInd(1);
        product.setPrdName("Laptop");
        product.setPrdSku("LT1234");

        OrderProductAssoc orderProductAssoc = new OrderProductAssoc();
        orderProductAssoc.setOrdUidFk(orderHistory);
        orderProductAssoc.setPrdUidFk(product);

        orderHistory.setOrderProductAssocCollection(new ArrayList<>());
        orderHistory.getOrderProductAssocCollection().add(orderProductAssoc);

        Party party = new Party();
        party.setPtyFirstNm("John");
        party.setPtyLastNm("Wick");
        orderHistory.setPtyUidFk(party);

        OrderHistoryFacadeRESTExtension instance = spy(new OrderHistoryFacadeRESTExtension());

        ArgumentCaptor<OrderHistory> orderHistoryCaptor = ArgumentCaptor.forClass(OrderHistory.class);

        doNothing().when((OrderHistoryFacadeREST) instance).create(orderHistory);

        OrdStatusCdFacadeREST ordStatusCdFacadeRESTMocked = mock(OrdStatusCdFacadeREST.class);
        when(ordStatusCdFacadeRESTMocked.find("PEN")).thenReturn(null);

        PartyFacadeRESTExtension partyFacadeRESTExtensionMocked = mock(PartyFacadeRESTExtension.class);
        when(partyFacadeRESTExtensionMocked.find(1111)).thenReturn(null);

        ProductFacadeRESTExtension productFacadeRESTExtensionMocked = mock(ProductFacadeRESTExtension.class);
        when(productFacadeRESTExtensionMocked.find(111)).thenReturn(null);

        instance.create(orderHistory);

        verify(instance).create(orderHistoryCaptor.capture());

        OrderHistory actual = orderHistoryCaptor.getValue();

        Assert.assertEquals(2222, actual.getOrdUid().intValue());
        Assert.assertEquals("PND", actual.getOrdStatusCd().getCode());
        Assert.assertEquals("John", actual.getPtyUidFk().getPtyFirstNm());
        Assert.assertEquals("Wick", actual.getPtyUidFk().getPtyLastNm());
        Assert.assertEquals("Laptop", actual.getOrderProductAssocCollection().iterator().next().getPrdUidFk().getPrdName());
        Assert.assertEquals("LT1234", actual.getOrderProductAssocCollection().iterator().next().getPrdUidFk().getPrdSku());
    }
}