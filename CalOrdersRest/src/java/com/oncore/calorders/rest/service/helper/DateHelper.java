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

 
import com.oncore.calorders.core.exceptions.BaseRuntimeException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author oncore
 */
public class DateHelper {
    
    public static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            
    public static Date parseJSONDate(String dateString)
    {
        Date date = null;
        try
        {
            date = formatter.parse(dateString);
            
            System.out.println("Date: " + dateString + " is now " + date);
        }
        catch(Exception e)
        {
            throw new BaseRuntimeException("Unable to parse date: " + dateString);
        }
        
        return date;
    }
    
}
