package com.dgsoft.faces.renderkit;

import javax.faces.component.*;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by cooper on 12/20/14.
 */
public class RendererUtils {

    /**
     * See JSF Spec. 8.5 Table 8-1
     * @param value
     * @return boolean
     */
    public static boolean isDefaultAttributeValue(Object value)
    {
        if (value == null)
        {
            return true;
        }
        else if (value instanceof Boolean)
        {
            return !((Boolean) value).booleanValue();
        }
        else if (value instanceof Number)
        {
            if (value instanceof Integer)
            {
                return ((Number) value).intValue() == Integer.MIN_VALUE;
            }
            else if (value instanceof Double)
            {
                return ((Number) value).doubleValue() == Double.MIN_VALUE;
            }
            else if (value instanceof Long)
            {
                return ((Number) value).longValue() == Long.MIN_VALUE;
            }
            else if (value instanceof Byte)
            {
                return ((Number) value).byteValue() == Byte.MIN_VALUE;
            }
            else if (value instanceof Float)
            {
                return ((Number) value).floatValue() == Float.MIN_VALUE;
            }
            else if (value instanceof Short)
            {
                return ((Number) value).shortValue() == Short.MIN_VALUE;
            }
        }
        return false;
    }

    public static Object getObjectValue(UIComponent component)
    {
        if (!(component instanceof ValueHolder))
        {
            throw new IllegalArgumentException("Component : "
                    + getPathToComponent(component) + "is not a ValueHolder");
        }

        if (component instanceof EditableValueHolder)
        {
            Object value = ((EditableValueHolder) component)
                    .getSubmittedValue();
            if (value != null)
            {
                return value;
            }
        }

        return ((ValueHolder) component).getValue();
    }

    public static Boolean getBooleanValue(UIComponent component)
    {
        Object value = getObjectValue(component);
        // Try to convert to Boolean if it is a String
        if (value instanceof String)
        {
            value = Boolean.valueOf((String) value);
        }

        if (value == null || value instanceof Boolean)
        {
            return (Boolean) value;
        }

        throw new IllegalArgumentException(
                "Expected submitted value of type Boolean for Component : "
                        + getPathToComponent(component));

    }

    private static void getPathToComponent(UIComponent component,
                                           StringBuilder buf)
    {
        if (component == null)
        {
            return;
        }

        StringBuilder intBuf = new StringBuilder();

        intBuf.append("[Class: ");
        intBuf.append(component.getClass().getName());
        if (component instanceof UIViewRoot)
        {
            intBuf.append(",ViewId: ");
            intBuf.append(((UIViewRoot) component).getViewId());
        }
        else
        {
            intBuf.append(",Id: ");
            intBuf.append(component.getId());
        }
        intBuf.append("]");

        buf.insert(0, intBuf.toString());

        getPathToComponent(component.getParent(), buf);
    }

    public static String getPathToComponent(UIComponent component)
    {
        StringBuilder buf = new StringBuilder();

        if (component == null)
        {
            buf.append("{Component-Path : ");
            buf.append("[null]}");
            return buf.toString();
        }

        getPathToComponent(component, buf);

        buf.insert(0, "{Component-Path : ");
        Object location = component.getAttributes().get(
                UIComponent.VIEW_LOCATION_KEY);
        if (location != null)
        {
            buf.append(" Location: ").append(location);
        }
        buf.append("}");

        return buf.toString();
    }


    public static void checkParamValidity(FacesContext facesContext,
                                          UIComponent uiComponent, Class compClass)
    {
        if (facesContext == null)
        {
            throw new NullPointerException("facesContext may not be null");
        }
        if (uiComponent == null)
        {
            throw new NullPointerException("uiComponent may not be null");
        }

        //if (compClass != null && !(compClass.isAssignableFrom(uiComponent.getClass())))
        // why isAssignableFrom with additional getClass method call if isInstance does the same?
        if (compClass != null && !(compClass.isInstance(uiComponent)))
        {
            throw new IllegalArgumentException("uiComponent : "
                    + getPathToComponent(uiComponent) + " is not instance of "
                    + compClass.getName() + " as it should be");
        }
    }


    /**
     * Convenient utility method that returns the currently given value as String,
     * using the given converter.
     * Especially usefull for dealing with primitive types.
     */
    public static String getConvertedStringValue(FacesContext context,
                                                 UIComponent component, Converter converter, Object value)
    {
        if (converter == null)
        {
            if (value == null)
            {
                return "";
            }
            else if (value instanceof String)
            {
                return (String) value;
            }
            else
            {
                return value.toString();
            }
        }

        return converter.getAsString(context, component, value);
    }

    public static boolean getBooleanAttribute(UIComponent component,
                                              String attrName, boolean defaultValue)
    {
        Boolean b = (Boolean) component.getAttributes().get(attrName);
        return b != null ? b.booleanValue() : defaultValue;
    }

    public static int getIntegerAttribute(UIComponent component,
                                          String attrName, int defaultValue)
    {
        Integer i = (Integer) component.getAttributes().get(attrName);
        return i != null ? i.intValue() : defaultValue;
    }



}
