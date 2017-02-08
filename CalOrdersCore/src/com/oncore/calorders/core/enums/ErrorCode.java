/*
 * The MIT License
 *
 * Copyright 2017 OnCore Consulting LLC, All Rights Reserved.
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
package com.oncore.calorders.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * ErrorCode is an extensible Enumerated type. You can create custom enumerated
 * types that include new code values and add them to the base ErrorCode type.
 * For example, we may want common errors that can occur in any situation
 * defined here and custom enumerated types for web service, data access, or
 * other error conditions with more specific errors.
 *
 * <pre>
 *
 * To add new values you can define a new enumerated type implementing the
 * AbstractEnum interface like
 *
 * public enum WebServiceErrors implements AbstractEnum{
 *
 *     MYERROR("999", "SOME DESCRIPTION");
 * ...
 * }
 *
 * Then add new errors to the base ErrorCode like
 *
 * ErrorCode.addNewErrorCode(WebServiceErrors.MYERROR);
 *
 * Then use it like
 * 
 * AbstractEnum error = ErrorCode.errorCodeFor("100");
 * </pre>
 *
 * @author OnCore Consulting LLC
 */
public enum ErrorCode implements AbstractEnum {

    WEBSERVICEERROR("100", "A fatal web service error occurred."),
    DATAACCESSERROR("101", "A fatal error occurred while trying to access the underlying datastore");

    private final String code;
    private final String value;

    private ErrorCode(String errorCode, String errorDescription) {
        this.code = errorCode;
        this.value = errorDescription;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    private static final Map< String, AbstractEnum> map
            = new HashMap< String, AbstractEnum>();

    static {
        for (AbstractEnum error : values()) {
            map.put(error.getCode(), error);
        }

    }

    public static AbstractEnum errorCodeFor(String errorCode) {
        return map.get(errorCode);
    }

    public static void addNewErrorCode(AbstractEnum errorCode) {
        if (!map.containsKey(errorCode.getCode())) {
            map.put(errorCode.getCode(), errorCode);
        }
    }

}
