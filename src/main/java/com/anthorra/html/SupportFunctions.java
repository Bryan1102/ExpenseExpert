
package com.anthorra.html;

import java.util.HashMap;

/**
 *
 * @author Anthorra
 */
public class SupportFunctions
{
    
    public static String decodeAttributes(HashMap<String, String> attributes)
    {
        String retVal = "";
        
        if(!attributes.isEmpty())
        {
            for(String t : attributes.keySet())
            {
                retVal += " " + t + "=\"" + attributes.get(t) + "\"";
            }
        }
        
        return retVal;
    }
}
