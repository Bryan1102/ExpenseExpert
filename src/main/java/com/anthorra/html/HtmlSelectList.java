
package com.anthorra.html;

import java.util.HashMap;

/**
 *
 * @author Anthorra
 */
public class HtmlSelectList extends HtmlBodyElement
{
    private HashMap<String, String> attr;
    private String[] options;
    private String[][] valuesOptions;
    private HtmlBodyDiv parentDiv;
    private String selected;
    private int selectedId;
    
    public HtmlSelectList(String[] options, HtmlBodyDiv parentDiv)
    {
        this.attr = new HashMap<>();
        this.options = options;
        this.parentDiv = parentDiv;
    }
    public HtmlSelectList(String[][] valuesOptions, HtmlBodyDiv parentDiv)
    {
        this.attr = new HashMap<>();
        this.valuesOptions = valuesOptions;
        this.parentDiv = parentDiv;
    }
    
    
    @Override
    String contructElement()
    {
        String select = "<select";
        if(!this.attr.isEmpty())
            {select += SupportFunctions.decodeAttributes(attr);}
        select += ">";
        
        // TO BE ADDED IN NEXT VERSION MAYBE
        //select += "<option value=\"\" disabled hidden>Kérlek Válassz</option>"; /*nem működik?*/
        //value hozzáadható????
        //<option value="1">One</option>
        //<option value="2">Two</option>
        
        if(options != null)
        {
            for(String o : options)
            {
                if(o.equals(selected)) /* ha a liste eleme egyezik a defaultnak kiválasztott értékkel*/
                {
                    select += "<option selected>";
                }
                else
                {
                    select += "<option>";
                }
                select += o;

                select += "</option>";
            }
        }
        if(valuesOptions != null)
        {
            for(String[] o : valuesOptions)
            {
                if(o[0].equals(selectedId + "") || o[1].equals(selected))
                {
                    select += "<option value=" + o[0] + " selected>";
                }
                else
                {
                    select += "<option value=" + o[0] + ">";
                }
                select += o[1];

                select += "</option>";
            }
        }
            
        
        
        select += "</select>";
        return select;
    }
    
    public HtmlSelectList addAttribute(String id, String value)
    {
        attr.put(id, value);
        return this;
    }
        public HtmlBodyDiv getParentDiv()
    {
        return parentDiv;
    }
    public HtmlSelectList setSelectedOption(String selected)
    {
        this.selected = selected;
        return this;
    }
    public HtmlSelectList setSelectedOptionId(int selected)
    {
        this.selectedId = selected;
        return this;
    }
}
