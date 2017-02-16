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
import com.oncore.calorders.rest.Party;
import com.oncore.calorders.rest.Privilege;
import com.oncore.calorders.rest.data.GroupData;
import com.oncore.calorders.rest.data.PartyData;
import com.oncore.calorders.rest.data.PrivilegeData;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author oncore
 */
public class PartyFacadeRESTExtensionTest {

    public PartyFacadeRESTExtensionTest() {
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindPartyByUserIdAndPassword() throws Exception {

        Party party = this.buildPartyRecord();

        EntityManager mockedEm = mock(EntityManager.class);
        TypedQuery mockedTypedQuery = mock(TypedQuery.class);

        given(mockedEm.createQuery("SELECT p FROM Party p WHERE p.ptyUserId = :userId and p.ptyPassword = :password", Party.class))
                .willReturn(mockedTypedQuery);
        given(mockedTypedQuery.setParameter("userId", "testUser")).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.setParameter("password", "Passw0rd*")).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.getSingleResult()).willReturn(party);

        PartyFacadeRESTExtension partyFacadeRESTExtension = new PartyFacadeRESTExtension(mockedEm);

        PartyData expectedParty
                = partyFacadeRESTExtension.findPartyByUserIdAndPassword("testUser", "");

        Assert.assertEquals("John", expectedParty.getPtyFirstNm());
        Assert.assertEquals("Wick", expectedParty.getPtyLastNm());
        Assert.assertEquals("testUser", expectedParty.getPtyUserId());

        Assert.assertEquals("Depart Name", expectedParty.getDepName());
        Assert.assertEquals("123 addrs ln 1", expectedParty.getDepAddressLine1());
        Assert.assertEquals("addr ln 2", expectedParty.getDepAddressLine2());
        Assert.assertEquals("sacramento", expectedParty.getDepCity());
        Assert.assertEquals("CA", expectedParty.getDepState());
        Assert.assertEquals("95825", expectedParty.getDepZip5());
        Assert.assertEquals("1234", expectedParty.getDepZip4());

        Assert.assertEquals("ADM", expectedParty.getGroupList().get(0).getGrpTypeCd());

        Assert.assertEquals("Page desc 1.", expectedParty.getGroupList().get(0).getPrivilegesList().get(0).getPageDescription());
        Assert.assertEquals("Page ID 1", expectedParty.getGroupList().get(0).getPrivilegesList().get(0).getPageIdentifier());
        Assert.assertFalse(expectedParty.getGroupList().get(0).getPrivilegesList().get(0).getCanRead());
        Assert.assertTrue(expectedParty.getGroupList().get(0).getPrivilegesList().get(0).getCanWrite());

        verify(mockedEm, times(1)).createQuery("SELECT p FROM Party p WHERE p.ptyUserId = :userId and p.ptyPassword = :password", Party.class);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindAllGroupsByPartyId() throws Exception {

        Party party = this.buildPartyRecord();

        EntityManager mockedEm = mock(EntityManager.class);
        TypedQuery mockedTypedQuery = mock(TypedQuery.class);

        given(mockedEm.createNamedQuery("Party.findByPtyUid", Party.class)).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.setParameter("partyId", 111)).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.getSingleResult()).willReturn(party);

        PartyFacadeRESTExtension partyFacadeRESTExtension = new PartyFacadeRESTExtension(mockedEm);

        List<GroupData> expectedGroupData
                = partyFacadeRESTExtension.findAllGroupsByPartyId(111);

        Assert.assertEquals("ADM", expectedGroupData.get(0).getGrpTypeCd());

        Assert.assertEquals("Page desc 1.", expectedGroupData.get(0).getPrivilegesList().get(0).getPageDescription());
        Assert.assertEquals("Page ID 1", expectedGroupData.get(0).getPrivilegesList().get(0).getPageIdentifier());
        Assert.assertFalse(expectedGroupData.get(0).getPrivilegesList().get(0).getCanRead());
        Assert.assertTrue(expectedGroupData.get(0).getPrivilegesList().get(0).getCanWrite());

        Assert.assertEquals("Page desc 2.", expectedGroupData.get(0).getPrivilegesList().get(1).getPageDescription());
        Assert.assertEquals("Page ID 2", expectedGroupData.get(0).getPrivilegesList().get(1).getPageIdentifier());
        Assert.assertTrue(expectedGroupData.get(0).getPrivilegesList().get(1).getCanRead());
        Assert.assertFalse(expectedGroupData.get(0).getPrivilegesList().get(1).getCanWrite());

        verify(mockedEm, times(1)).createNamedQuery("Party.findByPtyUid", Party.class);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindAllPrivilegesByPartyId() throws Exception {

        List<Privilege> privileges = this.buildPrivileges();

        EntityManager mockedEm = mock(EntityManager.class);
        TypedQuery mockedTypedQuery = mock(TypedQuery.class);

        given(mockedEm.createQuery("SELECT p FROM Privilege p join p.groupPrivilegeAssocCollection gp join gp.grpUidFk g join g.groupPartyAssocCollection ge join ge.ptyUidFk e WHERE e.pryUid = :partyId ORDER BY p.prvOrder ASC", Privilege.class))
                .willReturn(mockedTypedQuery);
        given(mockedTypedQuery.setParameter("partyId", 111)).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.getResultList()).willReturn(privileges);

        PartyFacadeRESTExtension partyFacadeRESTExtension = new PartyFacadeRESTExtension(mockedEm);

        List<PrivilegeData> expectedPrivilegeData
                = partyFacadeRESTExtension.findAllPrivilegesByPartyId(111);

        Assert.assertEquals("Page desc 1.", expectedPrivilegeData.get(0).getPageDescription());
        Assert.assertEquals("Page ID 1", expectedPrivilegeData.get(0).getPageIdentifier());
        Assert.assertFalse(expectedPrivilegeData.get(0).getCanRead());
        Assert.assertTrue(expectedPrivilegeData.get(0).getCanWrite());

        Assert.assertEquals("Page desc 2.", expectedPrivilegeData.get(1).getPageDescription());
        Assert.assertEquals("Page ID 2", expectedPrivilegeData.get(1).getPageIdentifier());
        Assert.assertTrue(expectedPrivilegeData.get(1).getCanRead());
        Assert.assertFalse(expectedPrivilegeData.get(1).getCanWrite());

        verify(mockedEm, times(1)).createQuery("SELECT p FROM Privilege p join p.groupPrivilegeAssocCollection gp join gp.grpUidFk g join g.groupPartyAssocCollection ge join ge.ptyUidFk e WHERE e.pryUid = :partyId ORDER BY p.prvOrder ASC", Privilege.class);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void findNavBarPrivilegesByPartyId() throws Exception {

        List<Privilege> privileges = this.buildPrivileges();

        EntityManager mockedEm = mock(EntityManager.class);
        TypedQuery mockedTypedQuery = mock(TypedQuery.class);

        given(mockedEm.createQuery("SELECT p FROM Privilege p join p.groupPrivilegeAssocCollection gp join gp.grpUidFk g join g.groupPartyAssocCollection ge join ge.ptyUidFk e WHERE e.ptyUid = :partyId and p.prvComponentId = 'NAVBAR'  ORDER BY p.prvOrder ASC", Privilege.class))
                .willReturn(mockedTypedQuery);
        given(mockedTypedQuery.setParameter("partyId", 111)).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.getResultList()).willReturn(privileges);

        PartyFacadeRESTExtension partyFacadeRESTExtension = new PartyFacadeRESTExtension(mockedEm);

        List<PrivilegeData> expectedPrivilegeData
                = partyFacadeRESTExtension.findNavBarPrivilegesByPartyId(111);

        Assert.assertEquals("Page desc 3.", expectedPrivilegeData.get(2).getPageDescription());
        Assert.assertEquals("Page ID 3", expectedPrivilegeData.get(2).getPageIdentifier());
        Assert.assertFalse(expectedPrivilegeData.get(2).getCanRead());
        Assert.assertFalse(expectedPrivilegeData.get(2).getCanWrite());

        Assert.assertEquals("Page desc 4.", expectedPrivilegeData.get(3).getPageDescription());
        Assert.assertEquals("Page ID 4", expectedPrivilegeData.get(3).getPageIdentifier());
        Assert.assertTrue(expectedPrivilegeData.get(3).getCanRead());
        Assert.assertTrue(expectedPrivilegeData.get(3).getCanWrite());

        verify(mockedEm, times(1)).createQuery("SELECT p FROM Privilege p join p.groupPrivilegeAssocCollection gp join gp.grpUidFk g join g.groupPartyAssocCollection ge join ge.ptyUidFk e WHERE e.ptyUid = :partyId and p.prvComponentId = 'NAVBAR'  ORDER BY p.prvOrder ASC", Privilege.class);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindNavMenuPrivilegesByPartyId() throws Exception {

        List<Privilege> privileges = this.buildPrivileges();

        EntityManager mockedEm = mock(EntityManager.class);
        TypedQuery mockedTypedQuery = mock(TypedQuery.class);

        given(mockedEm.createQuery("SELECT p FROM Privilege p join p.groupPrivilegeAssocCollection gp join gp.grpUidFk g join g.groupPartyAssocCollection ge join ge.ptyUidFk e WHERE e.ptyUid = :partyId and p.prvComponentId = 'NAVMENU'  ORDER BY p.prvOrder ASC", Privilege.class))
                .willReturn(mockedTypedQuery);
        given(mockedTypedQuery.setParameter("partyId", 111)).willReturn(mockedTypedQuery);
        given(mockedTypedQuery.getResultList()).willReturn(privileges);

        PartyFacadeRESTExtension partyFacadeRESTExtension = new PartyFacadeRESTExtension(mockedEm);

        List<PrivilegeData> expectedPrivilegeData
                = partyFacadeRESTExtension.findNavMenuPrivilegesByPartyId(111);

        Assert.assertEquals("Page desc 5.", expectedPrivilegeData.get(4).getPageDescription());
        Assert.assertEquals("Page ID 5", expectedPrivilegeData.get(4).getPageIdentifier());
        Assert.assertFalse(expectedPrivilegeData.get(4).getCanRead());
        Assert.assertTrue(expectedPrivilegeData.get(4).getCanWrite());

        verify(mockedEm, times(1)).createQuery("SELECT p FROM Privilege p join p.groupPrivilegeAssocCollection gp join gp.grpUidFk g join g.groupPartyAssocCollection ge join ge.ptyUidFk e WHERE e.ptyUid = :partyId and p.prvComponentId = 'NAVMENU'  ORDER BY p.prvOrder ASC", Privilege.class);
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

    /**
     *
     * @return Party
     */
    private List<Privilege> buildPrivileges() {

        List<Privilege> privileges = new ArrayList<>();

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

        Privilege privilege3 = new Privilege();
        privilege3.setPrvPageId("Page ID 3");
        privilege3.setPrvMisc("Page desc 3.");
        privilege3.setPrvReadInd(0);
        privilege3.setPrvWriteInd(0);

        Privilege privilege4 = new Privilege();
        privilege4.setPrvPageId("Page ID 4");
        privilege4.setPrvMisc("Page desc 4.");
        privilege4.setPrvReadInd(1);
        privilege4.setPrvWriteInd(1);

        Privilege privilege5 = new Privilege();
        privilege5.setPrvPageId("Page ID 5");
        privilege5.setPrvMisc("Page desc 5.");
        privilege5.setPrvReadInd(0);
        privilege5.setPrvWriteInd(1);

        privileges.add(privilege);
        privileges.add(privilege2);
        privileges.add(privilege3);
        privileges.add(privilege4);
        privileges.add(privilege5);

        return privileges;
    }
}
