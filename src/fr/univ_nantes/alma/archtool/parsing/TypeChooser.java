package fr.univ_nantes.alma.archtool.parsing;

import java.util.HashSet;
import java.util.Set;

import fr.univ_nantes.alma.archtool.sourceModel.ComplexType;
import fr.univ_nantes.alma.archtool.sourceModel.PrimitiveType;
import fr.univ_nantes.alma.archtool.sourceModel.Type;


public class TypeChooser
{
    ComplexType complexType = null;
    Set<String> attributes = new HashSet<String>();
    
    public void addAttribute(String attribute)
    {
        this.attributes.add(attribute);
    }
    
    public void setComplexType(ComplexType complexType)
    {
        this.complexType = complexType;
    }
    
    public Type getType()
    {
        Type type = null;
        
        if(this.complexType != null)
        {
            type = this.complexType;
        }
        else
        {
            if(this.attributes.contains("char"))
            {
                if(this.attributes.contains("unsigned"))
                {
                    type = PrimitiveType.unsignedCharType();
                }
                else
                {
                    type = PrimitiveType.charType();
                }
            }
            else if(this.attributes.contains("short"))
            {
                if(this.attributes.contains("unsigned"))
                {
                    type = PrimitiveType.unsignedShortType();
                }
                else
                {
                    type = PrimitiveType.shortType();
                }
            }
            else if(this.attributes.contains("int"))
            {
                if(this.attributes.contains("unsigned"))
                {
                    type = PrimitiveType.unsignedIntType();
                }
                else
                {
                    type = PrimitiveType.intType();
                }
            }
            else if(this.attributes.contains("long"))
            {
                if(this.attributes.contains("unsigned"))
                {
                    type = PrimitiveType.unsignedLongType();
                }
                else
                {
                    type = PrimitiveType.longType();
                }
            }
            else if(this.attributes.contains("signed"))
            {
                type = PrimitiveType.intType();
            }
            else if(this.attributes.contains("unsigned"))
            {
                type = PrimitiveType.unsignedIntType();
            }
            else if(this.attributes.contains("float"))
            {
                type = PrimitiveType.floatType();
            }
            else if(this.attributes.contains("double"))
            {
                type = PrimitiveType.doubleType();
            }
            else if(this.attributes.contains("void"))
            {
                type = PrimitiveType.voidType();
            }
        }
        
        return type;
    }
}
