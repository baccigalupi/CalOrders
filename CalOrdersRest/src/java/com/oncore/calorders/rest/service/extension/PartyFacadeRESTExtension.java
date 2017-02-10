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
 
import com.oncore.calorders.core.enums.ErrorCode;
import com.oncore.calorders.core.exceptions.DataAccessException;
import com.oncore.calorders.core.utils.FormatHelper;
import static com.oncore.calorders.core.utils.FormatHelper.LOG;
import com.oncore.calorders.core.utils.Logger;
import com.oncore.calorders.rest.GroupPartyAssoc;
import com.oncore.calorders.rest.GroupPrivilegeAssoc;
import com.oncore.calorders.rest.Party;
import com.oncore.calorders.rest.Privilege;
import com.oncore.calorders.rest.data.GroupData;
import com.oncore.calorders.rest.data.PartyData;
import com.oncore.calorders.rest.data.PrivilegeData;
import com.oncore.calorders.rest.service.AdrCountryCdFacadeREST;
import com.oncore.calorders.rest.service.AdrStateCdFacadeREST;
import com.oncore.calorders.rest.service.EmcTypeCdFacadeREST;
import com.oncore.calorders.rest.service.GrpTypeCdFacadeREST;
import com.oncore.calorders.rest.service.PartyFacadeREST;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author OnCore
 */
@Stateless
@Path("com.oncore.calorders.rest.party")
public class PartyFacadeRESTExtension extends PartyFacadeREST {

    private static final String DEFAULT_PASSWORD = "Passw0rd*";

    @EJB
    private EmcTypeCdFacadeREST emcTypeCdFacadeREST;

    @EJB
    private GrpTypeCdFacadeREST grpTypeCdFacadeREST;

    @EJB
    private AdrStateCdFacadeREST adrStateCdFacadeREST;

    @EJB
    private AdrCountryCdFacadeREST adrCountryCdFacadeREST;

//    @EJB
//    private DepartmentFacadeExtensionREST companyFacadeExtensionREST;

    public PartyFacadeRESTExtension() {
        super();
    }

//    public PartyFacadeRESTExtension(EntityManager em) {
//        super(em);
//    }

    /**
     * Finds an employee by user id and password
     *
     * @param userId a valid userid
     * @param password a valid password
     * @return an Employee record if found
     * @throws com.oncore.tempus.core.exceptions.DataAccessException
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

            party = getEntityManager().createQuery("SELECT e FROM Employees e WHERE e.empUserId = :empUserId and e.empPassword = :empPassword", Party.class).setParameter("empUserId", userId).setParameter("empPassword", DEFAULT_PASSWORD).getSingleResult();

            if (party != null) {
                partyData = new PartyData();

                partyData.setEmpFirstNm(party.getPtyFirstNm());
                partyData.setEmpHireDt(party.getPtyHireDt());
                partyData.setEmpId(party.getPtyId());
                partyData.setEmpLastNm(party.getPtyLastNm());
                partyData.setEmpMidNm(party.getPtyMidNm());
                partyData.setEmpTitle(party.getPtyTitle());
                partyData.setEmpUid(party.getPtyUid());

                for (GroupPartyAssoc assoc : party.getGroupPartyAssocCollection()) {
                    partyData.setDepName(assoc.getGrpUidFk().getDepUidFk().getDepName());
                    partyData.setDepUid(assoc.getGrpUidFk().getDepUidFk().getDepUid());

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

//    /**
//     * Finds an employee by user id and password
//     *
//     * @param employeeId
//     * @return an List of GroupData if found
//     * @throws com.oncore.tempus.core.exceptions.DataAccessException
//     */
//    @GET
//    @Path("findAllGroupsByEmployeeId/{employeeId}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<GroupData> findAllGroupsByEmployeeId(@PathParam("employeeId") Integer employeeId) throws DataAccessException {
//
//        Employees party = null;
//        List<GroupData> groupDataList = null;
//        GroupData groupData = null;
//        PrivilegeData privilegeData = null;
//
//        try {
//
//            Logger.debug(LOG, "Hey testing logging, the findAllGroupsByEmployeeId is being called!");
//
//            party = getEntityManager().createNamedQuery("Employees.findAllGroupsByEmployeeId",
//                    Employees.class).setParameter("employeeId", employeeId).getSingleResult();
//
//            if (party != null) {
//                groupDataList = new ArrayList<GroupData>();
//
//                for (GroupEmployeeAssoc assoc : party.getGroupEmployeeAssocCollection()) {
//
//                    groupData = new GroupData();
//                    groupData.setGrpUid(assoc.getGrpUidFk().getGrpUid());
//                    groupData.setGrpTypeCd(assoc.getGrpUidFk().getGrpTypeCd().getCode());
//                    groupData.setGrpTypeCdDescription(assoc.getGrpUidFk().getGrpTypeCd().getShortDesc());
//
//                    for (Iterator<GroupPrivilegesAssoc> it = assoc.getGrpUidFk().getGroupPrivilegesAssocCollection().iterator(); it.hasNext();) {
//                        GroupPrivilegesAssoc groupPrivAssoc = it.next();
//                        Privileges privs = groupPrivAssoc.getPrvUidFk();
//                        privilegeData = new PrivilegeData();
//                        if (privs.getPrvReadInd() == 0) {
//                            privilegeData.setCanRead(Boolean.FALSE);
//                        } else {
//                            privilegeData.setCanRead(Boolean.TRUE);
//                        }
//                        if (privs.getPrvWriteInd() == 0) {
//                            privilegeData.setCanWrite(Boolean.FALSE);
//                        } else {
//                            privilegeData.setCanWrite(Boolean.TRUE);
//                        }
//                        privilegeData.setPrvUid(privs.getPrvUid());
//                        privilegeData.setComponentIdentifier(privs.getPrvComponentId());
//                        privilegeData.setPageIdentifier(privs.getPrvPageId());
//                        privilegeData.setPageDescription(privs.getPrvPageDescription());
//                        if (!groupData.getPrivilegesList().contains(privilegeData)) {
//                            groupData.getPrivilegesList().add(privilegeData);
//                        }
//                    }
//
//                    groupDataList.add(groupData);
//                }
//            }
//        } catch (Exception ex) {
//            groupDataList = null;
//
//            Logger.error(LOG, FormatHelper.getStackTrace(ex));
//            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
//        }
//
//        if (groupDataList == null) {
//            throw new RuntimeException("Record not found");
//        }
//
//        return groupDataList;
//    }
//
//    /**
//     * Finds an employee privileges by employee id
//     *
//     * @param employeeId
//     * @return an List of Privilege Data if found
//     * @throws com.oncore.tempus.core.exceptions.DataAccessException
//     */
//    @GET
//    @Path("findAllPrivilegesByEmployeeId/{employeeId}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<PrivilegeData> findAllPrivilegesByEmployeeId(@PathParam("employeeId") Integer employeeId) throws DataAccessException {
//
//        List<Privileges> privilegeses = null;
//        List<PrivilegeData> privilegeDatas = null;
//        PrivilegeData privilegeData = null;
//
//        try {
//
//            Logger.debug(LOG, "Hey testing logging, the findAllPrivilegesByEmployeeId is being called!");
//
//            privilegeses = getEntityManager().createQuery("SELECT p FROM Privileges p join p.groupPrivilegesAssocCollection gp join gp.grpUidFk g join g.groupEmployeeAssocCollection ge join ge.empUidFk e WHERE e.empUid = :employeeId", Privileges.class).setParameter("employeeId", employeeId).getResultList();
//
//            if (privilegeses != null && privilegeses.size() > 0) {
//                privilegeDatas = new ArrayList<PrivilegeData>();
//                for (Iterator<Privileges> it = privilegeses.iterator(); it.hasNext();) {
//
//                    Privileges privs;
//                    privs = it.next();
//                    privilegeData = new PrivilegeData();
//                    if (privs.getPrvReadInd() == 0) {
//                        privilegeData.setCanRead(Boolean.FALSE);
//                    } else {
//                        privilegeData.setCanRead(Boolean.TRUE);
//                    }
//                    if (privs.getPrvWriteInd() == 0) {
//                        privilegeData.setCanWrite(Boolean.FALSE);
//                    } else {
//                        privilegeData.setCanWrite(Boolean.TRUE);
//                    }
//                    privilegeData.setPrvUid(privs.getPrvUid());
//                    privilegeData.setComponentIdentifier(privs.getPrvComponentId());
//                    privilegeData.setPageIdentifier(privs.getPrvPageId());
//                    privilegeData.setPageDescription(privs.getPrvPageDescription());
//                    if (!privilegeDatas.contains(privilegeData)) {
//                        privilegeDatas.add(privilegeData);
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//
//            Logger.error(LOG, FormatHelper.getStackTrace(ex));
//            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
//        }
//        return privilegeDatas;
//    }
//
//    /**
//     * Finds an employee navbar privileges by employee id
//     *
//     * @param employeeId
//     * @return an List of Privilege Data if found
//     * @throws com.oncore.tempus.core.exceptions.DataAccessException
//     */
//    @GET
//    @Path("findNavBarPrivilegesByEmployeeId/{employeeId}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<PrivilegeData> findNavBarPrivilegesByEmployeeId(@PathParam("employeeId") Integer employeeId) throws DataAccessException {
//
//        List<Privileges> privilegeses = null;
//        List<PrivilegeData> privilegeDatas = null;
//        PrivilegeData privilegeData = null;
//
//        try {
//
//            Logger.debug(LOG, "Hey testing logging, the findNavBarPrivilegesByEmployeeId is being called!");
//
//            privilegeses = getEntityManager().createQuery("SELECT p FROM Privileges p join p.groupPrivilegesAssocCollection gp join gp.grpUidFk g join g.groupEmployeeAssocCollection ge join ge.empUidFk e WHERE e.empUid = :employeeId and p.prvComponentId = 'NAVBAR'", Privileges.class).setParameter("employeeId", employeeId).getResultList();
//
//            if (privilegeses != null && privilegeses.size() > 0) {
//                privilegeDatas = new ArrayList<PrivilegeData>();
//                for (Iterator<Privileges> it = privilegeses.iterator(); it.hasNext();) {
//
//                    Privileges privs;
//                    privs = it.next();
//                    privilegeData = new PrivilegeData();
//                    if (privs.getPrvReadInd() == 0) {
//                        privilegeData.setCanRead(Boolean.FALSE);
//                    } else {
//                        privilegeData.setCanRead(Boolean.TRUE);
//                    }
//                    if (privs.getPrvWriteInd() == 0) {
//                        privilegeData.setCanWrite(Boolean.FALSE);
//                    } else {
//                        privilegeData.setCanWrite(Boolean.TRUE);
//                    }
//                    privilegeData.setPrvUid(privs.getPrvUid());
//                    privilegeData.setComponentIdentifier(privs.getPrvComponentId());
//                    privilegeData.setPageIdentifier(privs.getPrvPageId());
//                    privilegeData.setPageDescription(privs.getPrvPageDescription());
//                    if (!privilegeDatas.contains(privilegeData)) {
//                        privilegeDatas.add(privilegeData);
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//
//            Logger.error(LOG, FormatHelper.getStackTrace(ex));
//            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
//        }
//        return privilegeDatas;
//    }
//
//    /**
//     * Finds an employee navigation menu privileges by employee id
//     *
//     * @param employeeId
//     * @return an List of Privilege Data if found
//     * @throws com.oncore.tempus.core.exceptions.DataAccessException
//     */
//    @GET
//    @Path("findNavMenuPrivilegesByEmployeeId/{employeeId}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<PrivilegeData> findNavMenuPrivilegesByEmployeeId(@PathParam("employeeId") Integer employeeId) throws DataAccessException {
//
//        List<Privileges> privilegeses = null;
//        List<PrivilegeData> privilegeDatas = null;
//        PrivilegeData privilegeData = null;
//
//        try {
//
//            Logger.debug(LOG, "Hey testing logging, the findNavMenuPrivilegesByEmployeeId is being called!");
//
//            privilegeses = getEntityManager().createQuery("SELECT p FROM Privileges p join p.groupPrivilegesAssocCollection gp join gp.grpUidFk g join g.groupEmployeeAssocCollection ge join ge.empUidFk e WHERE e.empUid = :employeeId and p.prvComponentId = 'NAVMENU'", Privileges.class).setParameter("employeeId", employeeId).getResultList();
//
//            if (privilegeses != null && privilegeses.size() > 0) {
//                privilegeDatas = new ArrayList<PrivilegeData>();
//                for (Iterator<Privileges> it = privilegeses.iterator(); it.hasNext();) {
//
//                    Privileges privs;
//                    privs = it.next();
//                    privilegeData = new PrivilegeData();
//                    if (privs.getPrvReadInd() == 0) {
//                        privilegeData.setCanRead(Boolean.FALSE);
//                    } else {
//                        privilegeData.setCanRead(Boolean.TRUE);
//                    }
//                    if (privs.getPrvWriteInd() == 0) {
//                        privilegeData.setCanWrite(Boolean.FALSE);
//                    } else {
//                        privilegeData.setCanWrite(Boolean.TRUE);
//                    }
//                    privilegeData.setPrvUid(privs.getPrvUid());
//                    privilegeData.setComponentIdentifier(privs.getPrvComponentId());
//                    privilegeData.setPageIdentifier(privs.getPrvPageId());
//                    privilegeData.setPageDescription(privs.getPrvPageDescription());
//                    if (!privilegeDatas.contains(privilegeData)) {
//                        privilegeDatas.add(privilegeData);
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//
//            Logger.error(LOG, FormatHelper.getStackTrace(ex));
//            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
//        }
//        return privilegeDatas;
//
//    }
//
//    /**
//     * Creates the employee
//     *
//     * @param registration text containing employee information in JSON.
//     */
//    @POST
//    @Path("createEmployee")
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public void createEmployee(String registration) {
//
//        System.out.println("Employee: " + registration);
//
//        JsonReader reader = Json.createReader(new StringReader(registration));
//        JsonObject registrationObject = reader.readObject();
//        reader.close();
//
//        // Create Admin
//        Employees employee = new Employees();
//        employee.setEmpFirstNm(registrationObject.getString("employeeFirstNm", null));
//        employee.setEmpMidNm(registrationObject.getString("employeeMidNm", null));
//        employee.setEmpLastNm(registrationObject.getString("employeeLastNm", null));
//        employee.setEmpHireDt(DateHelper.parseJSONDate(registrationObject.getString("employeeHireDate", null)));
//        employee.setEmpDobDt(DateHelper.parseJSONDate(registrationObject.getString("employeeDOB", null)));
//
//        employee.setEmpId(registrationObject.getString("employeeSSN", null));
//        employee.setEmpUserId(registrationObject.getString("employeeUserName", null));
//        employee.setEmpPassword(registrationObject.getString("employeePassword", null));
//        //TODO: Figure out how to set audit columns
//        employee.setCreateTs(new Date());
//        employee.setCreateUserId("test");
//        employee.setUpdateTs(new Date());
//        employee.setUpdateUserId("test");
//
//        List<Contact> employeeContacts = new ArrayList<Contact>();
//
//        // Create Admin Phone/Email
//        if (StringUtils.isNotBlank(registrationObject.getString("employeePhone", null))) {
//            Contact employeePhone = ContactHelper.createContact("HPH", ContactHelper.stripPhonePunctuation(registrationObject.getString("employeePhone", null)), this.emcTypeCdFacadeREST);
//
//            employeePhone.setEmpUidFk(employee);
//            employeeContacts.add(employeePhone);
//        }
//        if (StringUtils.isNotBlank(registrationObject.getString("employeeEmail", null))) {
//            Contact employeeEmail = ContactHelper.createContact("PEM", registrationObject.getString("employeeEmail", null), this.emcTypeCdFacadeREST);
//
//            employeeEmail.setEmpUidFk(employee);
//            employeeContacts.add(employeeEmail);
//        }
//
//        employee.setContactCollection(employeeContacts);
//
//        // Create Employee Address
//        Address employeeAddress = new Address();
//        employeeAddress.setAdrLine1(registrationObject.getString("employeeAddressLine1", null));
//        employeeAddress.setAdrLine2(registrationObject.getString("employeeAddressCont", null));
//        employeeAddress.setAdrCity(registrationObject.getString("employeeCity", null));
//        employeeAddress.setAdrStateCd(this.adrStateCdFacadeREST.find(registrationObject.getString("employeeState")));
//        ContactHelper.setAddressZipCode(employeeAddress, registrationObject.getString("employeeZipCode", null));
//        employeeAddress.setAdrCountryCd(this.adrCountryCdFacadeREST.find("USA"));
//        //TODO: Figure out how to set audit columns
//        employeeAddress.setCreateTs(new Date());
//        employeeAddress.setCreateUserId("test");
//        employeeAddress.setUpdateTs(new Date());
//        employeeAddress.setUpdateUserId("test");
//        employeeAddress.setEmpUidFk(employee);
//        employee.setAddressCollection(Arrays.asList(employeeAddress));
//
//        // Create Groups
//        Company company = this.companyFacadeExtensionREST.find(Integer.valueOf(registrationObject.getString("companyUid")));
//        Groups group = this.getOrCreateGroup(company, registrationObject.getString("employeeGroup", null));
//
//        // Create Group Employee Association
//        GroupEmployeeAssoc groupEmployeeAssoc = new GroupEmployeeAssoc();
//        groupEmployeeAssoc.setGrpUidFk(group);
//        groupEmployeeAssoc.setEmpUidFk(employee);
//        //TODO: Figure out how to set audit columns
//        groupEmployeeAssoc.setCreateTs(new Date());
//        groupEmployeeAssoc.setCreateUserId("test");
//        groupEmployeeAssoc.setUpdateTs(new Date());
//        groupEmployeeAssoc.setUpdateUserId("test");
//
//        if (group.getGroupEmployeeAssocCollection() == null) {
//            group.setGroupEmployeeAssocCollection(new ArrayList<GroupEmployeeAssoc>());
//        }
//        group.getGroupEmployeeAssocCollection().add(groupEmployeeAssoc);
//
//        employee.setGroupEmployeeAssocCollection(Arrays.asList(groupEmployeeAssoc));
//
//        super.create(employee);
//    }
//
//    /**
//     * Determines if a user id exists
//     *
//     * @param userId a valid userid
//     * @return an true if the user id is already used.
//     */
//    @GET
//    @Path("doesUserIdExist/{userId}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Employees doesUserIdExist(@PathParam("userId") String userId) {
//
//        Employees employee = null;
//
//        //TODO: just hackin some errors here for testing, will create some
//        //valid exceptions later
//        try {
//            employee = getEntityManager().createNamedQuery("Employees.findByEmpUserId",
//                    Employees.class).setParameter("empUserId", userId).getSingleResult();
//        } catch (Exception ex) {
//            employee = null;
//        }
//
//        return employee;
//    }
//
//    public static final Log LOG = LogFactory
//            .getLog(PartyFacadeRESTExtension.class);
//
//    private Groups getOrCreateGroup(Company company, String groupType) {
//        Groups employeeGroup = null;
//
//        if (company.getGroupsCollection() == null) {
//            company.setGroupsCollection(new ArrayList<Groups>());
//        }
//
//        for (Iterator<Groups> itr = company.getGroupsCollection().iterator(); itr.hasNext();) {
//            Groups group = itr.next();
//
//            if (groupType.equals(group.getGrpTypeCd().getCode())) {
//                employeeGroup = group;
//            }
//        }
//
//        if (employeeGroup == null) {
//            employeeGroup = new Groups();
//            employeeGroup.setGrpTypeCd(this.grpTypeCdFacadeREST.find(groupType));
//            employeeGroup.setCmpUidFk(company);
//            employeeGroup.setGroupEmployeeAssocCollection(new ArrayList<GroupEmployeeAssoc>());
//            //TODO: Figure out how to set audit columns
//            employeeGroup.setCreateTs(new Date());
//            employeeGroup.setCreateUserId("test");
//            employeeGroup.setUpdateTs(new Date());
//            employeeGroup.setUpdateUserId("test");
//            company.getGroupsCollection().add(employeeGroup);
//        }
//
//        return employeeGroup;
//    }
//
//    /**
//     * Finds an employee by user id and password
//     *
//     * @param cmpUid a valid company unique id
//     *
//     * @return a List of EmployeeData objects
//     *
//     * @throws com.oncore.tempus.core.exceptions.DataAccessException
//     */
//    @GET
//    @Path("findAllActiveEmployeesByName/{cmpUid}/{firstName}/{lastName}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<EmployeeData> findAllActiveEmployeesByName(@PathParam("cmpUid") Integer cmpUid, @PathParam("firstName") String firstName,
//            @PathParam("lastName") String lastName) throws DataAccessException {
//
//        List<Employees> employeesList = null;
//        List<EmployeeData> employeeDataList = null;
//
//        try {
//
//            Logger.debug(LOG, "Hey testing logging, the findAllActiveEmployees is being called!");
//
//            if(EMPTY.equalsIgnoreCase(firstName))
//            {
//                firstName = StringUtils.EMPTY;
//            }
//            
//            if(EMPTY.equalsIgnoreCase(lastName))
//            {
//                lastName = StringUtils.EMPTY;
//            }
//            
//            if (StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName)) {
//                employeeDataList = new ArrayList<>(1);
//            } else if (StringUtils.isBlank(firstName) && StringUtils.isNotBlank(lastName)) {
//
//                employeesList = getEntityManager().createNamedQuery("Employees.findAllActiveEmployeesByLastName",
//                        Employees.class).setParameter("empLastNm", "%" + lastName + "%").getResultList();
//                employeeDataList = populateEmployeeList(employeesList, employeeDataList, cmpUid);
//
//            } else if (StringUtils.isNotBlank(firstName) && StringUtils.isBlank(lastName)) {
//
//                employeesList = getEntityManager().createNamedQuery("Employees.findAllActiveEmployeesByFirstName",
//                        Employees.class).setParameter("empFirstNm", "%" + firstName + "%").getResultList();
//                employeeDataList = populateEmployeeList(employeesList, employeeDataList, cmpUid);
//
//            } else {
//
//                employeesList = getEntityManager().createNamedQuery("Employees.findAllActiveEmployeesByName",
//                        Employees.class).setParameter("empFirstNm", "%" + firstName + "%").setParameter("empLastNm", "%" + lastName + "%").getResultList();
//                employeeDataList = populateEmployeeList(employeesList, employeeDataList, cmpUid);
//            }
//
//        } catch (Exception ex) {
//            Logger.error(LOG, FormatHelper.getStackTrace(ex));
//            throw new DataAccessException(ex, ErrorCode.DATAACCESSERROR);
//        }
//
//        return employeeDataList;
//    }
//
//    private List<EmployeeData> populateEmployeeList(List<Employees> employeesList, List<EmployeeData> employeeDataList, Integer cmpUid) {
//        if (CollectionUtils.isNotEmpty(employeesList)) {
//            employeeDataList = new ArrayList<>(1);
//
//            for (Employees party : employeesList) {
//
//                for (GroupEmployeeAssoc groupEmployeeAssoc : party.getGroupEmployeeAssocCollection()) {
//                    if (groupEmployeeAssoc.getGrpUidFk().getCmpUidFk().getCmpUid().equals(cmpUid)) {
//                        EmployeeData partyData = new EmployeeData();
//
//                        partyData.setEmpFirstNm(party.getEmpFirstNm());
//                        partyData.setEmpHireDt(party.getEmpHireDt());
//                        partyData.setEmpId(party.getEmpId());
//                        partyData.setEmpLastNm(party.getEmpLastNm());
//                        partyData.setEmpMidNm(party.getEmpMidNm());
//                        partyData.setEmpTitle(party.getEmpTitle());
//                        partyData.setEmpUid(party.getEmpUid());
//
//                        employeeDataList.add(partyData);
//                    }
//                }
//
//            }
//        }
//        return employeeDataList;
//    }

    private static final String EMPTY = "EMPTY";
}
