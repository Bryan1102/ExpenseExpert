
package com.anthorra.html;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Anthorra
 */
public class HtmlBodyDiv
{
    private String div;
    //private String divClass;
    //private String divStyle;
    private boolean isForm;
    private HtmlBodyDiv parentDiv;
    private HtmlBodySection parentSection;
    private ArrayList<HtmlBodyElement> elements;
    private ArrayList<HtmlBodyDiv> divs;
    private HashMap<String, String> attr;

    public HtmlBodyDiv(HtmlBodySection parentSection)
    {
        this.elements = new ArrayList<HtmlBodyElement>();
        this.divs = new ArrayList<HtmlBodyDiv>();
        this.attr = new HashMap<String, String>();
        this.parentSection = parentSection;
    }
    public HtmlBodyDiv(HtmlBodyDiv parentDiv)
    {
        this.elements = new ArrayList<HtmlBodyElement>();
        this.divs = new ArrayList<HtmlBodyDiv>();
        this.attr = new HashMap<String, String>();
        this.parentDiv = parentDiv;
    }
    public HtmlBodyDiv()
    {
        this.elements = new ArrayList<HtmlBodyElement>();
        this.divs = new ArrayList<HtmlBodyDiv>();
        this.attr = new HashMap<String, String>();
        
    }
    
    private String constructDiv()
    {
        String type = "div";
        if(isForm){type = "form";}
        
        /* START DIV */
        div = "<";
        div += type;
        
        
        if(!this.attr.isEmpty())
            {div += SupportFunctions.decodeAttributes(attr);}
        div += ">";
        
        
        if(elements.size() > 0)
        {
            for(int i = 0; i < elements.size(); i++)
            {
                div += elements.get(i).getElement();
            }
        }
        
        /* SECTION PARTS */
        if(divs != null && !divs.isEmpty())
        {
            for(int i = 0; i < divs.size(); i++)
            {
                div += divs.get(i).getDiv();
            }
        }
        
        /* END DIV */
        div += "</";
        div += type;
        div += ">";
        return div;
    }
    
    public String getDiv()
    {
        return constructDiv();
    }
    public HtmlBodyDiv addAttribute(String id, String value)
    {
        attr.put(id, value);
        return this;
    }
    
    /* SETTER */
    public void setParentDiv(HtmlBodyDiv parentDiv)
    {
        this.parentDiv = parentDiv;
    }
    public void setParentSection(HtmlBodySection parentSection)
    {
        this.parentSection = parentSection;
    }
    public HtmlBodyDiv setDivClass(String divClass)
    {
        attr.put("class", divClass);
        //this.divClass = divClass;
        return this;
    }
    public HtmlBodyDiv setDivStyle(String divStyle)
    {
        attr.put("style", divStyle);
        //this.divStyle = divStyle;
        return this;
    }

    public HtmlBodyDiv setIsForm(boolean isForm)
    {
        this.isForm = isForm;
        return this;
    }
    
    
    
    /* GETTER */
    public HtmlBodyDiv getParentDiv()
    {
        return parentDiv;
    }

    public HtmlBodySection getParentSection()
    {
        return parentSection;
    }
    
    
    /* ADD NESTED DIV */
    public HtmlBodyDiv addNestedDiv()
    {
        HtmlBodyDiv div = new HtmlBodyDiv(this);
        divs.add(div);
        return div; 
    }
    public HtmlBodyDiv addNestedDiv(HtmlBodyDiv div)
    {
        div.setParentDiv(this);
        divs.add(div);
        return div;
    }
    
    
    /* ADD ELEMENTS */
    public HtmlBodyDiv addParagraph(String text)
    {
        HtmlParagraph paragraph = new HtmlParagraph(text);
        elements.add(paragraph);
        return this;
    }
    public HtmlBodyDiv addHeaderText(String headerText, int headerSize)
    {
        HtmlHeader header = new HtmlHeader(headerText, headerSize);
        elements.add(header);
        return this;
    }
    public HtmlBodyDiv addHeaderText(String headerText, int headerSize, String headerClass)
    {
        HtmlHeader header = new HtmlHeader(headerText, headerSize, headerClass);
        elements.add(header);
        return this;
    }
    public HtmlBodyDiv addHyperlink(String url, String text)
    {
        HtmlHref href = new HtmlHref(url, text);
        elements.add(href);
        return this;
    }
    public HtmlTable addTable(String[][] inputTable, boolean hasHeader)
    {
        HtmlTable table = new HtmlTable(inputTable, hasHeader);
        elements.add(table);
        return table;
    }
    public HtmlBodyDiv addBreakline()
    {
        HtmlBreakline br = new HtmlBreakline();
        elements.add(br);
        return this;
    }
    public HtmlList addList(String[] listItems)
    {
        HtmlList list = new HtmlList(listItems);
        elements.add(list);
        return list;
    }
    public HtmlList addList(HtmlBodyElement[] listElement)
    {
        HtmlList list = new HtmlList(listElement);
        elements.add(list);
        return list;
    }
    public HtmlList addList(HtmlList list)
    {
        elements.add(list);
        return list;
    }
    public HtmlInput addInputField(String type)
    {
        HtmlInput input = new HtmlInput(type, this);
        elements.add(input);
        return input;
    } 
    public HtmlSelectList addSelectList(String[] options)
    {
        HtmlSelectList select = new HtmlSelectList(options, this);
        elements.add(select);
        return select;
    } 
    public HtmlSelectList addSelectList(String[][] options)
    {
        HtmlSelectList select = new HtmlSelectList(options, this);
        elements.add(select);
        return select;
    } 
    public HtmlButton addButton(String text, String type)
    {
        HtmlButton button = new HtmlButton(this, text, type);
        elements.add(button);
        return button;
    } 
}
