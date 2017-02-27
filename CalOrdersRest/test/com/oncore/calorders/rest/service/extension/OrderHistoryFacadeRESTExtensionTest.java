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

import com.oncore.calorders.rest.Address;
import com.oncore.calorders.rest.AdrStateCd;
import com.oncore.calorders.rest.Department;
import com.oncore.calorders.rest.GroupPartyAssoc;
import com.oncore.calorders.rest.GroupPrivilegeAssoc;
import com.oncore.calorders.rest.Groups;
import com.oncore.calorders.rest.GrpTypeCd;
import com.oncore.calorders.rest.OrdStatusCd;
import com.oncore.calorders.rest.OrderHistory;
import com.oncore.calorders.rest.OrderProductAssoc;
import com.oncore.calorders.rest.Party;
import com.oncore.calorders.rest.Privilege;
import com.oncore.calorders.rest.Product;
import com.oncore.calorders.rest.data.OrderDetailData;
import com.oncore.calorders.rest.data.OrderHistoryData;
import com.oncore.calorders.rest.data.OrderStatusSummaryData;
import com.oncore.calorders.rest.data.OrdersByQuarterSeriesData;
import com.oncore.calorders.rest.service.OrdStatusCdFacadeREST;
import com.oncore.calorders.rest.service.OrderHistoryFacadeREST;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author oncore
 */
public class OrderHistoryFacadeRESTExtensionTest {

    private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

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

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFetchOrdersByQuarter() throws Exception {
        List<OrderHistory> orderHistoryList = new ArrayList<OrderHistory>();
        orderHistoryList.add(new OrderHistory(1, "1", formatter.parse("01/01/2015"), "1", new Date()));
        orderHistoryList.add(new OrderHistory(2, "1", formatter.parse("07/01/2015"), "1", new Date()));
        orderHistoryList.add(new OrderHistory(3, "1", formatter.parse("07/01/2015"), "1", new Date()));
        orderHistoryList.add(new OrderHistory(4, "1", formatter.parse("10/01/2015"), "1", new Date()));

        EntityManager mockedEm = mock(EntityManager.class);
        TypedQuery mockedTypedQuery = mock(TypedQuery.class);

        given(mockedEm.createNamedQuery("Department.findByDepUid", Department.class)).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.setParameter("depUid", 1)).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.getSingleResult()).willReturn(new Department());

        given(mockedEm.createQuery("SELECT o FROM OrderHistory o WHERE o.depUidFk = :departmentId AND o.createTs > '2014:01:01 15:06:39.673' ORDER BY o.createTs ASC", OrderHistory.class))
                .willReturn(mockedTypedQuery);
        given(mockedTypedQuery.setParameter("departmentId", new Department())).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.getResultList()).willReturn(orderHistoryList);

        OrderHistoryFacadeRESTExtension orderHistoryFacadeRESTExtension = new OrderHistoryFacadeRESTExtension(mockedEm);

        OrdersByQuarterSeriesData expectedData
                = orderHistoryFacadeRESTExtension.fetchOrdersByQuarter(1);

        Assert.assertEquals("Jan", expectedData.getOrdersByQuarterDataList().get(0).getName());
        Assert.assertEquals(1, expectedData.getOrdersByQuarterDataList().get(0).getItems().size());

        Assert.assertEquals("Apr", expectedData.getOrdersByQuarterDataList().get(1).getName());
        Assert.assertEquals(1, expectedData.getOrdersByQuarterDataList().get(1).getItems().size());

        Assert.assertEquals("Jul", expectedData.getOrdersByQuarterDataList().get(2).getName());
        Assert.assertEquals(1, expectedData.getOrdersByQuarterDataList().get(2).getItems().size());

        Assert.assertEquals("Oct", expectedData.getOrdersByQuarterDataList().get(3).getName());
        Assert.assertEquals(0, expectedData.getOrdersByQuarterDataList().get(3).getItems().size());

        verify(mockedEm, times(1)).createNamedQuery("Department.findByDepUid", Department.class);
        verify(mockedEm, times(1)).createQuery("SELECT o FROM OrderHistory o WHERE o.depUidFk = :departmentId AND o.createTs > '2014:01:01 15:06:39.673' ORDER BY o.createTs ASC", OrderHistory.class);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFetchOrderStatusSummary() throws Exception {

        EntityManager mockedEm = mock(EntityManager.class);
        TypedQuery mockedTypedQuery1 = mock(TypedQuery.class);
        TypedQuery mockedTypedQuery2 = mock(TypedQuery.class);
        TypedQuery mockedTypedQuery3 = mock(TypedQuery.class);

        given(mockedEm.createNamedQuery("Department.findByDepUid", Department.class)).willReturn(mockedTypedQuery1);
        given(mockedTypedQuery1.setParameter("depUid", 1)).willReturn(mockedTypedQuery1);
        given(mockedTypedQuery1.getSingleResult()).willReturn(new Department());

        given(mockedEm.createNamedQuery("OrdStatusCd.findByCode", OrdStatusCd.class)).willReturn(mockedTypedQuery2);
        given(mockedTypedQuery2.setParameter("code", "CANC")).willReturn(mockedTypedQuery2);
        given(mockedTypedQuery2.setParameter("code", "PRCS")).willReturn(mockedTypedQuery2);
        given(mockedTypedQuery2.setParameter("code", "SHIP")).willReturn(mockedTypedQuery2);
        given(mockedTypedQuery2.setParameter("code", "SUBT")).willReturn(mockedTypedQuery2);
        given(mockedTypedQuery2.getSingleResult()).willReturn(new OrdStatusCd());

        given(mockedEm.createQuery("SELECT COUNT(o) FROM OrderHistory o WHERE o.depUidFk = :departmentId AND o.ordStatusCd = :code", Long.class)).willReturn(mockedTypedQuery3);
        given(mockedTypedQuery3.setParameter("departmentId", new Department())).willReturn(mockedTypedQuery3);
        given(mockedTypedQuery3.setParameter("code", new OrdStatusCd())).willReturn(mockedTypedQuery3);
        given(mockedTypedQuery3.getSingleResult()).willReturn(new Long(3));

        OrderHistoryFacadeRESTExtension orderHistoryFacadeRESTExtension = new OrderHistoryFacadeRESTExtension(mockedEm);

        OrderStatusSummaryData expectedData
                = orderHistoryFacadeRESTExtension.fetchOrderStatusSummary(1);

        Assert.assertEquals("Cancelled", expectedData.getItems().get(0).getName());
        Assert.assertTrue(expectedData.getItems().get(0).getItems().get(0) == 3);

        Assert.assertEquals("Processing", expectedData.getItems().get(1).getName());
        Assert.assertTrue(expectedData.getItems().get(1).getItems().get(0) == 3);

        Assert.assertEquals("Shipped", expectedData.getItems().get(2).getName());
        Assert.assertTrue(expectedData.getItems().get(2).getItems().get(0) == 3);

        Assert.assertEquals("Submitted", expectedData.getItems().get(3).getName());
        Assert.assertTrue(expectedData.getItems().get(3).getItems().get(0) == 3);

        verify(mockedEm, times(1)).createNamedQuery("Department.findByDepUid", Department.class);
        verify(mockedEm, times(4)).createNamedQuery("OrdStatusCd.findByCode", OrdStatusCd.class);
        verify(mockedEm, times(4)).createQuery("SELECT COUNT(o) FROM OrderHistory o WHERE o.depUidFk = :departmentId AND o.ordStatusCd = :code", Long.class);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindAllOrderHistoryByPartyUid() throws Exception {
        List<OrderHistory> orderHistoryList = new ArrayList<OrderHistory>();
        orderHistoryList.add(new OrderHistory(1, "1", formatter.parse("01/01/2015"), "1", new Date()));
        orderHistoryList.add(new OrderHistory(2, "1", formatter.parse("07/01/2015"), "1", new Date()));
        orderHistoryList.add(new OrderHistory(3, "1", formatter.parse("07/01/2015"), "1", new Date()));
        orderHistoryList.add(new OrderHistory(4, "1", formatter.parse("10/01/2015"), "1", new Date()));

        orderHistoryList.get(2).setPtyUidFk(this.buildPartyRecord());

        EntityManager mockedEm = mock(EntityManager.class);
        TypedQuery mockedTypedQuery = mock(TypedQuery.class);

        given(mockedEm.createQuery("SELECT oh FROM OrderHistory oh join oh.ordStatusCd os join oh.ptyUidFk pt join pt.groupPartyAssocCollection gpa join gpa.grpUidFk g join g.depUidFk d join oh.orderProductAssocCollection opa WHERE pt.ptyUid = :partyUid ORDER BY oh.createTs DESC", OrderHistory.class))
                .willReturn(mockedTypedQuery);
        given(mockedTypedQuery.setParameter("partyUid", new Integer(1))).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.getResultList()).willReturn(orderHistoryList);

        OrderHistoryFacadeRESTExtension orderHistoryFacadeRESTExtension = new OrderHistoryFacadeRESTExtension(mockedEm);

        List<OrderHistoryData> expectedData
                = orderHistoryFacadeRESTExtension.findAllOrderHistoryByPartyUid(1);

        Assert.assertTrue(expectedData.size() == 4);
        Assert.assertEquals("Depart Name", expectedData.get(2).getOrderAgency());

        verify(mockedEm, times(1)).createQuery("SELECT oh FROM OrderHistory oh join oh.ordStatusCd os join oh.ptyUidFk pt join pt.groupPartyAssocCollection gpa join gpa.grpUidFk g join g.depUidFk d join oh.orderProductAssocCollection opa WHERE pt.ptyUid = :partyUid ORDER BY oh.createTs DESC", OrderHistory.class);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindAllOrderHistory() throws Exception {
        List<OrderHistory> orderHistoryList = new ArrayList<OrderHistory>();
        orderHistoryList.add(new OrderHistory(1, "1", formatter.parse("01/01/2015"), "1", new Date()));
        orderHistoryList.add(new OrderHistory(2, "1", formatter.parse("07/01/2015"), "1", new Date()));
        orderHistoryList.add(new OrderHistory(3, "1", formatter.parse("07/01/2015"), "1", new Date()));
        orderHistoryList.add(new OrderHistory(4, "1", formatter.parse("10/01/2015"), "1", new Date()));

        orderHistoryList.get(2).setPtyUidFk(this.buildPartyRecord());

        EntityManager mockedEm = mock(EntityManager.class);
        TypedQuery mockedTypedQuery = mock(TypedQuery.class);

        given(mockedEm.createQuery("SELECT oh FROM OrderHistory oh join oh.ordStatusCd os join oh.ptyUidFk pt join pt.groupPartyAssocCollection gpa join gpa.grpUidFk g join g.depUidFk d join oh.orderProductAssocCollection opa ORDER BY oh.createTs DESC", OrderHistory.class))
                .willReturn(mockedTypedQuery);
        given(mockedTypedQuery.getResultList()).willReturn(orderHistoryList);

        OrderHistoryFacadeRESTExtension orderHistoryFacadeRESTExtension = new OrderHistoryFacadeRESTExtension(mockedEm);

        List<OrderHistoryData> expectedData
                = orderHistoryFacadeRESTExtension.findAllOrderHistory();

        Assert.assertTrue(expectedData.size() == 4);
        Assert.assertEquals("Depart Name", expectedData.get(2).getOrderAgency());

        verify(mockedEm, times(1)).createQuery("SELECT oh FROM OrderHistory oh join oh.ordStatusCd os join oh.ptyUidFk pt join pt.groupPartyAssocCollection gpa join gpa.grpUidFk g join g.depUidFk d join oh.orderProductAssocCollection opa ORDER BY oh.createTs DESC", OrderHistory.class);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindOrderDetailById() throws Exception {
        OrderHistory orderHistory = new OrderHistory(1, "1", formatter.parse("01/01/2015"), "1", new Date());
        orderHistory.setOrdStatusCd(new OrdStatusCd("PROC"));
        Department dept = new Department(11);

        Address address = new Address();
        address.setAdrLine1("1 s st");
        address.setAdrCity("SF");
        address.setAdrStateCd(new AdrStateCd("CA"));

        dept.setAddressCollection(new ArrayList<>());
        dept.getAddressCollection().add(address);
        orderHistory.setDepUidFk(dept);

        Product product = new Product();
        product.setPrdActiveInd(1);
        product.setPrdName("Laptop");
        product.setPrdSku("LT1234");
        product.setPrdPrice(new BigDecimal(100));
        product.setPrdCntrUnitPrice(new BigDecimal(50));

        OrderProductAssoc orderProductAssoc = new OrderProductAssoc();
        orderProductAssoc.setOrdUidFk(orderHistory);
        orderProductAssoc.setPrdUidFk(product);
        orderProductAssoc.setOpaPrice(new BigDecimal(200));
        orderProductAssoc.setOpaQuantity(2);

        orderHistory.setOrderProductAssocCollection(new ArrayList<>());
        orderHistory.getOrderProductAssocCollection().add(orderProductAssoc);

        EntityManager mockedEm = mock(EntityManager.class);

        given(mockedEm.find(OrderHistory.class, 1)).willReturn(orderHistory);

        OrderHistoryFacadeRESTExtension instance = new OrderHistoryFacadeRESTExtension(mockedEm);

        OrderDetailData orderDetailData = instance.findOrderDetailById(1);

        Assert.assertEquals("SF", orderDetailData.getShippingAddressCity());
        Assert.assertEquals(new BigDecimal(200), orderDetailData.getProductTotalPrice());
        Assert.assertEquals("1 s st", orderDetailData.getShippingAddressLine1());

        verify(mockedEm, times(1)).find(OrderHistory.class, 1);
    }

    /**
     *
     * @return Party
     */
    private Party buildPartyRecord() {
        Party party = new Party();

        GroupPartyAssoc groupPartyAssoc = new GroupPartyAssoc();
        groupPartyAssoc.setGreUid(111);

        Groups groups = new Groups(11111);
        groups.setGrpTypeCd(new GrpTypeCd("ADM"));
        groupPartyAssoc.setGrpUidFk(groups);

        Address address = new Address();
        address.setAdrLine1("123 addrs ln 1");
        address.setAdrLine2("addr ln 2");
        address.setAdrCity("sacramento");
        address.setAdrStateCd(new AdrStateCd("CA"));
        address.setAdrZip5("95825");
        address.setAdrZip4("1234");

        Department department = new Department();
        department.setDepName("Depart Name");
        department.setAddressCollection(new ArrayList<>());
        department.getAddressCollection().add(address);
        groups.setDepUidFk(department);

        Privilege privilege = new Privilege();
        privilege.setPrvPageId("Page ID 1");
        privilege.setPrvMisc("Page desc 1.");
        privilege.setPrvReadInd(0);
        privilege.setPrvWriteInd(1);

        Privilege privilege2 = new Privilege();
        privilege2.setPrvPageId("Page ID 2");
        privilege2.setPrvMisc("Page desc 2.");
        privilege2.setPrvReadInd(1);
        privilege2.setPrvWriteInd(0);

        GroupPrivilegeAssoc groupPrivilegeAssoc = new GroupPrivilegeAssoc();
        groupPrivilegeAssoc.setPrvUidFk(privilege);
        groups.setGroupPrivilegeAssocCollection(new ArrayList<>());
        groups.getGroupPrivilegeAssocCollection().add(groupPrivilegeAssoc);

        GroupPrivilegeAssoc groupPrivilegeAssoc2 = new GroupPrivilegeAssoc();
        groupPrivilegeAssoc2.setPrvUidFk(privilege2);
        groups.getGroupPrivilegeAssocCollection().add(groupPrivilegeAssoc2);

        party.setPtyUid(1111);
        party.setPtyFirstNm("John");
        party.setPtyLastNm("Wick");
        party.setPtyUserId("testUser");
        party.setPtyPassword("password");
        party.setPtyTitle("user title");
        party.setGroupPartyAssocCollection(new ArrayList<>());
        party.getGroupPartyAssocCollection().add(groupPartyAssoc);

        return party;
    }
}
