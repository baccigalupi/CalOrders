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
import com.oncore.calorders.rest.Address;
import com.oncore.calorders.rest.GroupPartyAssoc;
import com.oncore.calorders.rest.GroupPrivilegeAssoc;
import com.oncore.calorders.rest.Party;
import com.oncore.calorders.rest.Privilege;
import com.oncore.calorders.rest.data.GroupData;
import com.oncore.calorders.rest.data.PartyData;
import com.oncore.calorders.rest.data.PrivilegeData;
import com.oncore.calorders.rest.service.PartyFacadeREST;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author OnCore
 */
@Stateless
@Path("com.oncore.calorders.rest.party")
public class PartyFacadeRESTExtension extends PartyFacadeREST {

    private static final String DEFAULT_PASSWORD = "Passw0rd*";

    public PartyFacadeRESTExtension() {
        super();
    }

    /**
     * Finds an employee by user id and password
     *
     * @param userId a valid userid
     * @param password a valid password
     * @return an Employee record if found
     * @throws com.oncore.calorders.core.exceptions.DataAccessException
     */
    @GET
    @Path("findPartyByUserIdAndPassword/{userId}/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public PartyData findPartyByUserIdAndPassword(@PathParam("userId") String userId, @PathParam("password") String password) throws DataAccessException {

        Party party = null;
        PartyData partyData = null;
        GroupData groupData = null;
        PrivilegeData privilegeData = null;

        try {

            Logger.debug(LOG, "Hey testing logging, the findPartyByUserIdAndPassword is being called!");

            party = getEntityManager().createQuery("SELECT p FROM Party p WHERE p.ptyUserId = :userId and p.ptyPassword = :password", Party.class).setParameter("userId", userId).setParameter("password", DEFAULT_PASSWORD).getSingleResult();

            if (party != null) {
                partyData = new PartyData();

                partyData.setPtyFirstNm(party.getPtyFirstNm());
                partyData.setPtyHireDt(party.getPtyHireDt());
                partyData.setPtyId(party.getPtyId());
                partyData.setPtyLastNm(party.getPtyLastNm());
                partyData.setPtyMidNm(party.getPtyMidNm());
                partyData.setPtyTitle(party.getPtyTitle());
                partyData.setPtyUid(party.getPtyUid());

                for (GroupPartyAssoc assoc : party.getGroupPartyAssocCollection()) {
                    partyData.setDepName(assoc.getGrpUidFk().getDepUidFk().getDepName());
                    partyData.setDepUid(assoc.getGrpUidFk().getDepUidFk().getDepUid());
                    
                    if(CollectionUtils.isNotEmpty(assoc.getGrpUidFk().getDepUidFk().getAddressCollection()))
                    {
                        Address address = assoc.getGrpUidFk().getDepUidFk().getAddressCollection().iterator().next();
                        
                        partyData.setDepAddressLine1(address.getAdrLine1());
                        partyData.setDepAddressLine2(address.getAdrLine2());
                        partyData.setDepCity(address.getAdrCity());
                        partyData.setDepState(address.getAdrStateCd().getCode());
                        partyData.setDepZip5(address.getAdrZip5());
                        partyData.setDepZip4(address.getAdrZip4());
      
                    }

                    groupData = new GroupData();
                    groupData.setGrpUid(assoc.getGrpUidFk().getGrpUid());
                    groupData.setGrpTypeCd(assoc.getGrpUidFk().getGrpTypeCd().getCode());
                    groupData.setGrpTypeCdDescription(assoc.getGrpUidFk().getGrpTypeCd().getShortDesc());

                    for (Iterator<GroupPrivilegeAssoc> it = assoc.getGrpUidFk().getGroupPrivilegeAssocCollection().iterator(); it.hasNext();) {
                        GroupPrivilegeAssoc groupPrivAssoc = it.next();
                        Privilege privs = groupPrivAssoc.getPrvUidFk();
                        privilegeData = new PrivilegeData();
                        if (privs.getPrvReadInd() == 0) {
                            privilegeData.setCanRead(Boolean.FALSE);
                        } else {
                            privilegeData.setCanRead(Boolean.TRUE);
                        }
                        if (privs.getPrvWriteInd() == 0) {
                            privilegeData.setCanWrite(Boolean.FALSE);
                        } else {
                            privilegeData.setCanWrite(Boolean.TRUE);
                        }
                        privilegeData.setComponentIdentifier(privs.getPrvComponentId());
                        privilegeData.setPageIdentifier(privs.getPrvPageId());

                        privilegeData.setPageDescription(privs.getPrvMisc());
                        if (!groupData.getPrivilegesList().contains(privilegeData)) {
                            groupData.getPrivilegesList().add(privilegeData);
                        }
                    }

                    partyData.getGroupList().add(groupData);
                }
            }
        } catch (Exception ex) {
            partyData = null;

            Logger.error(LOG, FormatHelper.getStackTrace(ex));
            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
        }

        if (partyData == null) {
            throw new RuntimeException("Record not found");
        }

        return partyData;
    }

    /**
     * Finds an employee by user id and password
     *
     * @param partyId
     * @return an List of GroupData if found
     * @throws com.oncore.calorders.core.exceptions.DataAccessException
     */
    @GET
    @Path("findAllGroupsByPartyId/{partyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<GroupData> findAllGroupsByPartyId(@PathParam("partyId") Integer partyId) throws DataAccessException {

        Party party = null;
        List<GroupData> groupDataList = null;
        GroupData groupData = null;
        PrivilegeData privilegeData = null;

        try {

            Logger.debug(LOG, "Hey testing logging, the findAllGroupsByPartyId is being called!");

            party = getEntityManager().createNamedQuery("Party.findByPtyUid",
                    Party.class).setParameter("partyId", partyId).getSingleResult();

            if (party != null) {
                groupDataList = new ArrayList<GroupData>();

                for (GroupPartyAssoc assoc : party.getGroupPartyAssocCollection()) {

                    groupData = new GroupData();
                    groupData.setGrpUid(assoc.getGrpUidFk().getGrpUid());
                    groupData.setGrpTypeCd(assoc.getGrpUidFk().getGrpTypeCd().getCode());
                    groupData.setGrpTypeCdDescription(assoc.getGrpUidFk().getGrpTypeCd().getShortDesc());

                    for (Iterator<GroupPrivilegeAssoc> it = assoc.getGrpUidFk().getGroupPrivilegeAssocCollection().iterator(); it.hasNext();) {
                        GroupPrivilegeAssoc groupPrivAssoc = it.next();
                        Privilege privs = groupPrivAssoc.getPrvUidFk();
                        privilegeData = new PrivilegeData();
                        if (privs.getPrvReadInd() == 0) {
                            privilegeData.setCanRead(Boolean.FALSE);
                        } else {
                            privilegeData.setCanRead(Boolean.TRUE);
                        }
                        if (privs.getPrvWriteInd() == 0) {
                            privilegeData.setCanWrite(Boolean.FALSE);
                        } else {
                            privilegeData.setCanWrite(Boolean.TRUE);
                        }
                        privilegeData.setPrvUid(privs.getPrvUid());
                        privilegeData.setComponentIdentifier(privs.getPrvComponentId());
                        privilegeData.setPageIdentifier(privs.getPrvPageId());
                        privilegeData.setPageDescription(privs.getPrvMisc());
                        if (!groupData.getPrivilegesList().contains(privilegeData)) {
                            groupData.getPrivilegesList().add(privilegeData);
                        }
                    }

                    groupDataList.add(groupData);
                }
            }
        } catch (Exception ex) {
            groupDataList = null;

            Logger.error(LOG, FormatHelper.getStackTrace(ex));
            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
        }

        if (groupDataList == null) {
            throw new RuntimeException("Record not found");
        }

        return groupDataList;
    }

    /**
     * Finds a party's privileges by party id
     *
     * @param partyId
     * @return an List of Privilege Data if found
     * @throws com.oncore.calorders.core.exceptions.DataAccessException
     */
    @GET
    @Path("findAllPrivilegesByPartyId/{partyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<PrivilegeData> findAllPrivilegesByPartyId(@PathParam("partyId") Integer partyId) throws DataAccessException {

        List<Privilege> privilegeses = null;
        List<PrivilegeData> privilegeDatas = null;
        PrivilegeData privilegeData = null;

        try {

            Logger.debug(LOG, "Hey testing logging, the findAllPrivilegesByPartyId is being called!");

            privilegeses = getEntityManager().createQuery("SELECT p FROM Privilege p join p.groupPrivilegeAssocCollection gp join gp.grpUidFk g join g.groupPartyAssocCollection ge join ge.ptyUidFk e WHERE e.pryUid = :partyId ORDER BY p.prvOrder ASC", Privilege.class).setParameter("partyId", partyId).getResultList();

            if (privilegeses != null && privilegeses.size() > 0) {
                privilegeDatas = new ArrayList<PrivilegeData>();
                for (Iterator<Privilege> it = privilegeses.iterator(); it.hasNext();) {

                    Privilege privs;
                    privs = it.next();
                    privilegeData = new PrivilegeData();
                    if (privs.getPrvReadInd() == 0) {
                        privilegeData.setCanRead(Boolean.FALSE);
                    } else {
                        privilegeData.setCanRead(Boolean.TRUE);
                    }
                    if (privs.getPrvWriteInd() == 0) {
                        privilegeData.setCanWrite(Boolean.FALSE);
                    } else {
                        privilegeData.setCanWrite(Boolean.TRUE);
                    }
                    privilegeData.setPrvUid(privs.getPrvUid());
                    privilegeData.setComponentIdentifier(privs.getPrvComponentId());
                    privilegeData.setPageIdentifier(privs.getPrvPageId());
                    privilegeData.setPageDescription(privs.getPrvMisc());
                    privilegeData.setPageOrder(privs.getPrvOrder());
                    if (!privilegeDatas.contains(privilegeData)) {
                        privilegeDatas.add(privilegeData);
                    }
                }
            }

        } catch (Exception ex) {

            Logger.error(LOG, FormatHelper.getStackTrace(ex));
            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
        }
        return privilegeDatas;
    }

    /**
     * Finds an party navbar privileges by party id
     *
     * @param partyId
     * @return an List of Privilege Data if found
     * @throws com.oncore.calorders.core.exceptions.DataAccessException
     */
    @GET
    @Path("findNavBarPrivilegesByPartyId/{partyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<PrivilegeData> findNavBarPrivilegesByPartyId(@PathParam("partyId") Integer partyId) throws DataAccessException {

        List<Privilege> privilegeses = null;
        List<PrivilegeData> privilegeDatas = null;
        PrivilegeData privilegeData = null;

        try {

            Logger.debug(LOG, "Hey testing logging, the findNavBarPrivilegesByPartyId is being called!");

            privilegeses = getEntityManager().createQuery("SELECT p FROM Privilege p join p.groupPrivilegeAssocCollection gp join gp.grpUidFk g join g.groupPartyAssocCollection ge join ge.ptyUidFk e WHERE e.ptyUid = :partyId and p.prvComponentId = 'NAVBAR'  ORDER BY p.prvOrder ASC", Privilege.class).setParameter("partyId", partyId).getResultList();

            if (privilegeses != null && privilegeses.size() > 0) {
                privilegeDatas = new ArrayList<PrivilegeData>();
                for (Iterator<Privilege> it = privilegeses.iterator(); it.hasNext();) {

                    Privilege privs;
                    privs = it.next();
                    privilegeData = new PrivilegeData();
                    if (privs.getPrvReadInd() == 0) {
                        privilegeData.setCanRead(Boolean.FALSE);
                    } else {
                        privilegeData.setCanRead(Boolean.TRUE);
                    }
                    if (privs.getPrvWriteInd() == 0) {
                        privilegeData.setCanWrite(Boolean.FALSE);
                    } else {
                        privilegeData.setCanWrite(Boolean.TRUE);
                    }
                    privilegeData.setPrvUid(privs.getPrvUid());
                    privilegeData.setComponentIdentifier(privs.getPrvComponentId());
                    privilegeData.setPageIdentifier(privs.getPrvPageId());
                    privilegeData.setPageDescription(privs.getPrvMisc());
                    privilegeData.setPageOrder(privs.getPrvOrder());
                    if (!privilegeDatas.contains(privilegeData)) {
                        privilegeDatas.add(privilegeData);
                    }
                }
            }

        } catch (Exception ex) {

            Logger.error(LOG, FormatHelper.getStackTrace(ex));
            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
        }
        return privilegeDatas;
    }

    /**
     * Finds an party navigation menu privileges by party id
     *
     * @param partyId
     * @return an List of Privilege Data if found
     * @throws com.oncore.calorders.core.exceptions.DataAccessException
     */
    @GET
    @Path("findNavMenuPrivilegesByPartyId/{partyId}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<PrivilegeData> findNavMenuPrivilegesByPartyId(@PathParam("partyId") Integer partyId) throws DataAccessException {

        List<Privilege> privilegeses = null;
        List<PrivilegeData> privilegeDatas = null;
        PrivilegeData privilegeData = null;

        try {
 
            Logger.debug(LOG, "Hey testing logging, the findNavMenuPrivilegesByPartyId is being called!");

            privilegeses = getEntityManager().createQuery("SELECT p FROM Privilege p join p.groupPrivilegeAssocCollection gp join gp.grpUidFk g join g.groupPartyAssocCollection ge join ge.ptyUidFk e WHERE e.ptyUid = :partyId and p.prvComponentId = 'NAVMENU'  ORDER BY p.prvOrder ASC", Privilege.class).setParameter("partyId", partyId).getResultList();

            if (privilegeses != null && privilegeses.size() > 0) {
                privilegeDatas = new ArrayList<PrivilegeData>();
                for (Iterator<Privilege> it = privilegeses.iterator(); it.hasNext();) {

                    Privilege privs;
                    privs = it.next();
                    privilegeData = new PrivilegeData();
                    if (privs.getPrvReadInd() == 0) {
                        privilegeData.setCanRead(Boolean.FALSE);
                    } else {
                        privilegeData.setCanRead(Boolean.TRUE);
                    }
                    if (privs.getPrvWriteInd() == 0) {
                        privilegeData.setCanWrite(Boolean.FALSE);
                    } else {
                        privilegeData.setCanWrite(Boolean.TRUE);
                    }
                    privilegeData.setPrvUid(privs.getPrvUid());
                    privilegeData.setComponentIdentifier(privs.getPrvComponentId());
                    privilegeData.setPageIdentifier(privs.getPrvPageId());
                    privilegeData.setPageDescription(privs.getPrvMisc());
                    privilegeData.setPageOrder(privs.getPrvOrder());
                    if (!privilegeDatas.contains(privilegeData)) {
                        privilegeDatas.add(privilegeData);
                    }
                }
            }

        } catch (Exception ex) {

            Logger.error(LOG, FormatHelper.getStackTrace(ex));
            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
        }
        return privilegeDatas;

    }

}
