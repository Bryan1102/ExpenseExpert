
package com.anthorra.html;

import java.util.ArrayList;
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
    //private ArrayList<String[]> rows;
    private boolean hasHeader;
    private HashMap<String, String> attr;
 
    
    /* CONSTRUCTOR */

    public HtmlTable(String[][] inputTable, boolean hasHeader)
    {
        this.data = inputTable;
        this.columns = inputTable[0].length + 1;
        this.hasHeader = hasHeader;
        this.attr = new HashMap<String, String>();
        //this.rows = new ArrayList<String[]>();
    }

//    public HtmlTable addRow(String[] tableRow)
//    {
//        String[] row = new String[this.columns-1];
//        for(int i = 0; i < this.columns-1; i++)
//        {row[i] =  tableRow[i];}
//        rows.add(row);
//        return this;
//    }
    
    
    
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
            //if(this.rows != null)
            {
                
                if(hasHeader){i++;}
                for(int k = i; k < data.length; k++)
                {
                    table += "<tr>";
                    for(String td : data[k])
                    {
                        table += "<td>";
                        table += td;
                        table += "</td>";
                    }

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
    
}
