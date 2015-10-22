package com.dgsoft.common.utils.seam;

import org.jboss.seam.core.Expressions;
import org.jboss.seam.persistence.QueryParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by cooper on 7/23/15.
 */
public class RestrictionGroup {


    static boolean isRestrictionParameterSet(Object parameterValue)
    {
        return parameterValue != null && !"".equals(parameterValue) && (parameterValue instanceof Collection ? !((Collection) parameterValue).isEmpty() : true);
    }

    static boolean isAnyParameterDirty(List<Expressions.ValueExpression> valueBindings, List<Object> lastParameterValues)
    {
        if (lastParameterValues==null) return true;
        for (int i=0; i<valueBindings.size(); i++)
        {
            Object parameterValue = valueBindings.get(i).getValue();
            Object lastParameterValue = lastParameterValues.get(i);
            //treat empty strings as null, for consistency with isRestrictionParameterSet()
            if ( "".equals(parameterValue) ) parameterValue = null;
            if ( "".equals(lastParameterValue) ) lastParameterValue = null;
            if ( parameterValue!=lastParameterValue && ( parameterValue==null || !parameterValue.equals(lastParameterValue) ) )
            {
                return true;
            }
        }
        return false;
    }

    static List<Object> getParameterValues(List<Expressions.ValueExpression> valueBindings)
    {
        List<Object> values = new ArrayList<Object>( valueBindings.size() );
        for (int i=0; i<valueBindings.size(); i++)
        {
            values.add( valueBindings.get(i).getValue() );
        }
        return values;
    }

    static void setParameters(javax.persistence.Query query, List<Object> parameters, int start){
        for (int i=0; i<parameters.size(); i++)
        {
            Object parameterValue = parameters.get(i);
            if ( isRestrictionParameterSet(parameterValue) )
            {
                query.setParameter( QueryParser.getParameterName(start + i), parameterValue );
            }
        }
    }

    private String itemLogicOperator;

    private List<Expressions.ValueExpression> restrictions = new ArrayList<Expressions.ValueExpression>(0);

    private List<Expressions.ValueExpression> restrictionParameters;

    private List<Object> restrictionParameterValues;

    private List<String> parsedRestrictions;


    public RestrictionGroup() {
    }

    public RestrictionGroup(String itemLogicOperator) {
        this.itemLogicOperator = itemLogicOperator;
    }

    public RestrictionGroup(String itemLogicOperator, List<String> expressionStrings){
        this.itemLogicOperator = itemLogicOperator;
        setRestrictionExpressionStrings(expressionStrings);
    }

    private List<RestrictionGroup> children = new ArrayList<RestrictionGroup>(0);

    public List<RestrictionGroup> getChildren() {
        return children;
    }

    public void setChildren(List<RestrictionGroup> children) {
        this.children = children;
    }

    public String getItemLogicOperator() {
        return itemLogicOperator;
    }

    public void setItemLogicOperator(String itemLogicOperator) {
        this.itemLogicOperator = itemLogicOperator;
    }

    protected boolean isAnyParameterDirty(){
        for(RestrictionGroup child: children){
            if (child.isAnyParameterDirty()){
                return true;
            }
        }
        return isAnyParameterDirty(restrictionParameters, restrictionParameterValues);
    }

    public List<Expressions.ValueExpression> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Expressions.ValueExpression> restrictions)
    {
        this.restrictions = restrictions;
        parsedRestrictions = null;
    }

    public void setRestrictionExpressionStrings(List<String> expressionStrings)
    {
        Expressions expressions = new Expressions();
        List<Expressions.ValueExpression> restrictionVEs = new ArrayList<Expressions.ValueExpression>(expressionStrings.size());
        for (String expressionString : expressionStrings)
        {
            restrictionVEs.add(expressions.createValueExpression(expressionString));
        }
        setRestrictions(restrictionVEs);
    }


    public boolean isParsed(){
        return (parsedRestrictions != null);
    }

    public void parseEjbql(int startingParameterNumber){

        List<Expressions.ValueExpression> restrictionFragments = getRestrictions();
        parsedRestrictions = new ArrayList<String>( restrictionFragments.size() );
        restrictionParameters = new ArrayList<Expressions.ValueExpression>( restrictionFragments.size() );
        for ( Expressions.ValueExpression restriction: restrictionFragments )
        {
            QueryParser rqp = new QueryParser( restriction.getExpressionString(), startingParameterNumber + restrictionParameters.size() );
            if ( rqp.getParameterValueBindings().size()!=1 )
            {
                throw new IllegalArgumentException("there should be exactly one value binding in a restriction: " + restriction);
            }
            parsedRestrictions.add( rqp.getEjbql() );
            restrictionParameters.addAll( rqp.getParameterValueBindings() );
        }
        int childStartingParameterNumber = startingParameterNumber + restrictionParameters.size();
        for(RestrictionGroup child: children){

            child.parseEjbql(childStartingParameterNumber);
            childStartingParameterNumber += child.restrictionParameters.size();
        }

    }

    protected void evaluateAllParameters(){
        restrictionParameterValues =  getParameterValues(restrictionParameters);
        for(RestrictionGroup child: children){
            child.evaluateAllParameters();
        }
    }

    public String getRenderedEjbql(){

        StringBuilder builder = new StringBuilder();

        boolean added = false;
        for (int i=0; i<getRestrictions().size(); i++)
        {
            Object parameterValue = restrictionParameters.get(i).getValue();
            if ( isRestrictionParameterSet(parameterValue) )
            {
                if (added) {
                    builder.append(" ").append(getItemLogicOperator()).append(" ");
                }

                builder.append( parsedRestrictions.get(i) );
                added = true;
            }
        }

        for (RestrictionGroup child : children){

            String childEjbql = child.getRenderedEjbql();

            if ((childEjbql != null) && !childEjbql.trim().equals("")){

                if (added)
                    builder.append(" ").append(getItemLogicOperator()).append(" ");



                builder.append(child.getRenderedEjbql());

                added = true;

            }

        }

        if(added){
            return " (" + builder.toString() + ") ";
        }else {
            return null;
        }


    }

    public void setParameters(javax.persistence.Query query,int start){
        setParameters(query,restrictionParameterValues,start);
        int childStart = start + restrictionParameterValues.size();
        for(RestrictionGroup child: children){
            child.setParameters(query,childStart);
            childStart += child.restrictionParameterValues.size();
        }
    }

}
