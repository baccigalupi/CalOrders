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
package com.oncore.calorders.core.utils;

import org.apache.commons.logging.Log;

/**
 * The <code>LogUtilities</code> utility class safely logs data to the log4j
 * logs and provides isEnabled checks for each level to improve performance.
 *
 * @author OnCore Consulting, LLC
 */
public final class Logger {

    /**
     * Writes a debug log entry if debugging is enabled
     *
     * @author OnCore Consulting LLC
     *
     * @param log a handle to the log
     * @param message the text to log
     */
    public static void debug(final Log log, final String message) {
        if (log != null && message != null) {
            if (log.isDebugEnabled()) {
                log.debug(message);
            }

        }
    }

    /**
     * Writes an info log entry if it is enabled
     *
     * @author OnCore Consulting LLC
     *
     * @param log a handle to the log
     * @param message the text to log
     */
    public static void info(final Log log, final String message) {
        if (log != null && message != null) {
            if (log.isInfoEnabled()) {
                log.info(message);
            }

        }
    }

    /**
     * Writes a trace log entry if it is enabled
     *
     * @author OnCore Consulting LLC
     *
     * @param log a handle to the log
     * @param message the text to log
     */
    public static void trace(final Log log, final String message) {
        if (log != null && message != null) {
            if (log.isTraceEnabled()) {
                log.trace(message);
            }

        }
    }

    /**
     * Writes a warn log entry if it is enabled
     *
     * @author OnCore Consulting LLC
     *
     * @param log a handle to the log
     * @param message the text to log
     */
    public static void warn(final Log log, final String message) {
        if (log != null && message != null) {
            log.warn(message);

        }
    }

    /**
     * Writes a error log entry if it is enabled
     *
     * @author OnCore Consulting LLC
     *
     * @param log a handle to the log
     * @param message the text to log
     */
    public static void error(final Log log, final String message) {
        if (log != null && message != null) {
            log.error(message);

        }
    }

}
