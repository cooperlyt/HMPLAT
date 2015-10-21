package com.dgsoft.common.utils.seam;

import org.jboss.el.lang.FunctionMapperImpl;
import org.jboss.el.lang.VariableMapperImpl;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.el.EL;
import org.jboss.seam.el.SeamELResolver;
import org.jboss.seam.el.SeamExpressionFactory;
import org.jboss.seam.log.Logging;

import javax.el.*;
import java.beans.FeatureDescriptor;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by cooper on 10/7/15.
 */
@Scope(ScopeType.APPLICATION)
@BypassInterceptors
@Name("expressionsUtils")
public class ExpressionsUtils {


    public static ELContext createELContext(final ELResolver resolver, final FunctionMapper functionMapper)
    {
        return new ELContext()
        {
            final VariableMapperImpl variableMapper = new VariableMapperImpl();

            @Override
            public ELResolver getELResolver()
            {
                return resolver;
            }

            @Override
            public FunctionMapper getFunctionMapper()
            {
                return functionMapper;
            }

            @Override
            public VariableMapper getVariableMapper()
            {
                return variableMapper;
            }

        };
    }

    public static ELContext getELContext(ELResolver localResolver) {
        CompositeELResolver resolver = new CompositeELResolver();
        resolver.add( new SeamELResolver() );
        resolver.add( new MapELResolver() );
        resolver.add( new ListELResolver() );
        resolver.add( new ArrayELResolver() );
        resolver.add( new ResourceBundleELResolver() );
        resolver.add( new BeanELResolver() );
        resolver.add(localResolver);

        return createELContext( resolver, new FunctionMapperImpl() );
    }

    public BigDecimal foreachSum(Expressions.ValueExpression<Collection> items, String var, String itemEl){
        BigDecimal result = BigDecimal.ZERO;
        for(Object item: items.getValue()){
            result = result.add(BigDecimal.valueOf(createLocalValueExpression(itemEl, Double.class, var, item).getValue()));
        }
        return result;
    }

    public String foreachLink(Expressions.ValueExpression<Collection> items, String var, String itemEl){
        String result = "";
        for(Object item: items.getValue()){
            result += createLocalValueExpression(itemEl, String.class, var, item).getValue();
        }
        return result;
    }

    private class LocalElResolver extends ELResolver{


        private String varName;

        private Object varValue;

        public LocalElResolver(String varName, Object varValue) {
            this.varName = varName;
            this.varValue = varValue;
        }

        @Override
        public Object getValue(ELContext context, Object base, Object property) {
            if (base == null && property.equals(varName)){
                context.setPropertyResolved(true);
                return varValue;
            }
            return null;
        }

        @Override
        public Class<?> getType(ELContext context, Object base, Object property) {
            if (base == null && property.equals(varName)){
                return varValue.getClass();
            }
            return null;
        }

        @Override
        public void setValue(ELContext context, Object base, Object property, Object value) {

        }

        @Override
        public boolean isReadOnly(ELContext context, Object base, Object property) {
            return false;
        }

        @Override
        public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
            return null;
        }

        @Override
        public Class<?> getCommonPropertyType(ELContext context, Object base) {
            return null;
        }
    };


    public <T> Expressions.ValueExpression<T> createLocalValueExpression(final String expression, final Class<T> type,final String varName,  final Object varValue)
    {

        return new Expressions.ValueExpression<T>()
        {

            private ELResolver localVarResolver = new LocalElResolver(varName,varValue);


            private javax.el.ValueExpression seamValueExpression;

            public javax.el.ValueExpression toUnifiedValueExpression()
            {

                    if (seamValueExpression==null)
                    {
                        seamValueExpression = createExpression();
                    }
                    return seamValueExpression;

            }

            private javax.el.ValueExpression createExpression()
            {
                return SeamExpressionFactory.INSTANCE.createValueExpression(getELContext(localVarResolver), expression, type);
            }

            public T getValue()
            {
                return (T) toUnifiedValueExpression().getValue( getELContext(localVarResolver) );
            }

            public void setValue(T value)
            {
                toUnifiedValueExpression().setValue( getELContext(localVarResolver), value );
            }

            public String getExpressionString()
            {
                return expression;
            }

            public Class<T> getType()
            {
                // QUESTION shouldn't we use the type provided in the constructor?
                return (Class<T>) toUnifiedValueExpression().getType( getELContext(localVarResolver) );
            }

        };
    }

    public static ExpressionsUtils instance()
    {
        if (!Contexts.isApplicationContextActive()) {
            return new ExpressionsUtils();
        } else {
            return (ExpressionsUtils) Component.getInstance(ExpressionsUtils.class, ScopeType.APPLICATION);
        }
    }
}
