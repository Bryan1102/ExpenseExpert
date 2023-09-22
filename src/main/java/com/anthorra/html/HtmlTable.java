
package com.anthorra.html;

import java.util.HashMap;

/**
 *
 * @author Anthorra
 */
public class HtmlTable extends HtmlBodyElement
{
    private int columns;
    private String[][] data;
    private String style;
    private boolean hasHeader;
    private HashMap<String, String> attr;
    
    /*add capability to add buttons, links etc. to table*/
    private HtmlBodyDiv nestedDiv;
    private String heading;
 
    
    /* CONSTRUCTOR */

    public HtmlTable(String[][] inputTable, boolean hasHeader)
    {
        this.data = inputTable;
        this.columns = inputTable[0].length + 1;
        this.hasHeader = hasHeader;
        this.attr = new HashMap<String, String>();
        
    }
    
    @Override
    String contructElement()
    {
        String table = "<table";
        
        if(!this.attr.isEmpty())
            {table += SupportFunctions.decodeAttributes(attr);}
        
        table += ">";
        
        int i = 0;
        if(data != null)
        {
            /* add table headers */
            table += "<tr>";
            if(hasHeader)
            {
                for(String h : data[i]) 
                {
                    table += "<th>" + h + "</th>";
                }
                if(nestedDiv!=null){table += "<th>" + heading + "</th>";}
            }
            else
            {
                int j = 0;
                for(String h : data[i]) 
                {
                    j += 1;
                    table += "<th>Column" + j + "</th>";
                }
            }
            table += "</tr>";

            /* add table rows*/
            {
                
                if(hasHeader){i++;}
                for(int k = i; k < data.length; k++)
                {
                    /* open table row */
                    table += "<tr>";
                    for(String td : data[k])
                    {
                        table += "<td>";
                        table += td;
                        table += "</td>";
                    }
                        
                        /*adding the last column - nested div*/
                        if(nestedDiv!=null)
                        {
                            table += "<td>";
                            table += nestedDiv.getDiv().replace("#Edit#", "Edit="+data[k][0]);
                            table += "</td>";
                        }
                    
                    /* close table row */
                    table += "</tr>";
                }
                
            }
        }
        
        table += "</table>";
        return table;
    }

    public HtmlTable addAttribute(String id, String value)
    {
        attr.put(id, value);
        return this;
    }
    
    public HtmlTable addNestedDiv(HtmlBodyDiv div, String heading)
    {
        this.nestedDiv = div;
        if(heading.isEmpty()){heading="edit";}
        else{this.heading = heading;}
        
        return this;
    }
}
