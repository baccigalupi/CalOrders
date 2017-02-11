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
package com.oncore.calorders.rest.service.helper;

import com.oncore.calorders.rest.Address;
import com.oncore.calorders.rest.ContactInfo;
import com.oncore.calorders.rest.service.EmcTypeCdFacadeREST;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author oncore
 */
public class ContactHelper {

    public static ContactInfo createContact(String contactType, String contactValue, EmcTypeCdFacadeREST emcTypeCdFacadeREST) {
        ContactInfo contact = new ContactInfo();
        contact.setEmcValue(contactValue);
        contact.setEmcTypeCd(emcTypeCdFacadeREST.find(contactType));
        //TODO: Figure out how to set audit columns
        contact.setCreateTs(new Date());
        contact.setCreateUserId("test");
        contact.setUpdateTs(new Date());
        contact.setUpdateUserId("test");

        return contact;
    }

    public static void setAddressZipCode(Address address, String zipCode) {
        zipCode = zipCode.replace("-", "");
        address.setAdrZip5(StringUtils.substring(zipCode, 0, 5));

        if (zipCode.length() == 9) {
            address.setAdrZip4(StringUtils.substring(zipCode, 5, 9));
        }
    }

    public static String stripPhonePunctuation(String phone) {
        if (phone != null) {
            phone = StringUtils.replaceChars(phone, " ()-", "");
        }
        return phone;
    }
}
