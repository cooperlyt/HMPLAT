package com.dgsoft.common.utils.seam;

/**
 * Created by cooper on 7/23/15.
 */

import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Logging;
import org.jboss.seam.persistence.PersistenceProvider;
import org.jboss.seam.transaction.Transaction;

import javax.persistence.EntityManager;
import javax.transaction.SystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A Query object for JPA.
 *
 * @author Gavin King
 *
 */
public class MultiOperatorEntityQuery<E> extends MultiOperatorQuery<EntityManager, E>
{

    private List<E> resultList;
    private E singleResult;
    private Long resultCount;
    private Map<String, String> hints;

    /**
     * Validate the query
     *
     * @throws IllegalStateException if the query is not valid
     */
    @Override
    public void validate()
    {
        super.validate();
        if ( getEntityManager()==null )
        {
            throw new IllegalStateException("entityManager is null");
        }

        if (!PersistenceProvider.instance().supportsFeature(PersistenceProvider.Feature.WILDCARD_AS_COUNT_QUERY_SUBJECT)) {
            setUseWildcardAsCountQuerySubject(false);
        }
    }

    @Override
    @Transactional
    public boolean isNextExists()
    {
        return resultList!=null && getMaxResults()!=null &&
                resultList.size() > getMaxResults();
    }


    /**
     * Get the list of results this query returns
     *
     * Any changed restriction values will be applied
     */
    @Transactional
    @Override
    public List<E> getResultList()
    {
        if ( isAnyParameterDirty() )
        {
            refresh();
        }
        initResultList();
        return truncResultList(resultList);
    }

    private void initResultList()
    {
        if (resultList==null)
        {
            Logging.getLog(getClass()).debug("MultiOperatorEntity  resultList is null call query");
            javax.persistence.Query query = createQuery();
            resultList = query==null ? null : query.getResultList();
        }
    }

    /**
     * Get a single result from the query
     *
     * Any changed restriction values will be applied
     *
     * @throws javax.persistence.NonUniqueResultException if there is more than one result
     */
    @Transactional
    @Override
    public E getSingleResult()
    {
        if (isAnyParameterDirty())
        {
            refresh();
        }
        initSingleResult();
        return singleResult;
    }

    private void initSingleResult()
    {
        if ( singleResult==null)
        {
            javax.persistence.Query query = createQuery();
            singleResult = (E) (query==null ?
                    null : query.getSingleResult());
        }
    }

    /**
     * Get the number of results this query returns
     *
     * Any changed restriction values will be applied
     */
    @Transactional
    @Override
    public Long getResultCount()
    {
        if (isAnyParameterDirty())
        {
            refresh();
        }
        initResultCount();
        return resultCount;
    }

    private void initResultCount()
    {
        if ( resultCount==null )
        {
            javax.persistence.Query query = createCountQuery();
            resultCount = query==null ?
                    null : (Long) query.getSingleResult();
        }
    }

    /**
     * The refresh method will cause the result to be cleared.  The next access
     * to the result set will cause the query to be executed.
     *
     * This method <b>does not</b> cause the ejbql or restrictions to reread.
     * If you want to update the ejbql or restrictions you must call
     * {@link #setEjbql(String)} or {@link #setRestrictions(List)}
     */
    @Override
    public void refresh()
    {
        Logging.getLog(getClass()).debug("call Multi Operator refresh");
        super.refresh();
        resultCount = null;
        resultList = null;
        singleResult = null;
    }

    public EntityManager getEntityManager()
    {
        return getPersistenceContext();
    }

    public void setEntityManager(EntityManager entityManager)
    {
        setPersistenceContext(entityManager);
    }

    @Override
    protected String getPersistenceContextName()
    {
        return "entityManager";
    }

    protected javax.persistence.Query createQuery()
    {
        parseEjbql();

        evaluateAllParameters();

        joinTransaction();

        //Logging.getLog(getClass()).debug("renderedEjbql:" + getRenderedEjbql());

        javax.persistence.Query query = getEntityManager().createQuery( getRenderedEjbql() );
        RestrictionGroup.setParameters(query, getQueryParameterValues(), 0);
        if (getRestrictionGroup() != null)
            getRestrictionGroup().setParameters( query, getQueryParameterValues().size() );
        if ( getFirstResult()!=null) query.setFirstResult( getFirstResult() );
        if ( getMaxResults()!=null) query.setMaxResults( getMaxResults()+1 ); //add one, so we can tell if there is another page
        if ( getHints()!=null )
        {
            for ( Map.Entry<String, String> me: getHints().entrySet() )
            {
                query.setHint(me.getKey(), me.getValue());
            }
        }
        return query;
    }

    protected javax.persistence.Query createCountQuery()
    {
        parseEjbql();

        evaluateAllParameters();

        joinTransaction();

        javax.persistence.Query query = getEntityManager().createQuery( getCountEjbql() );

        RestrictionGroup.setParameters(query, getQueryParameterValues(), 0);
        if (getRestrictionGroup() != null)
            getRestrictionGroup().setParameters( query, getQueryParameterValues().size() );
        return query;
    }



    public Map<String, String> getHints()
    {
        return hints;
    }

    public void setHints(Map<String, String> hints)
    {
        this.hints = hints;
    }

    protected void joinTransaction()
    {
        try
        {
            Transaction.instance().enlist( getEntityManager() );
        }
        catch (SystemException se)
        {
            throw new RuntimeException("could not join transaction", se);
        }
    }

    public List<Long> getShowPageNumbers(Long maxPageCount) {
        long halfCount = (maxPageCount.longValue() - 1) / 2;
        long beginPage = getPage().longValue() - halfCount;
        if (beginPage <= 0) {
            beginPage = 1;
        }
        if ((getPageCount().longValue() - beginPage + 1) < maxPageCount) {
            beginPage = getPageCount().longValue() - (maxPageCount - 1);
        }
        if (beginPage <= 0) {
            beginPage = 1;
        }


        List<Long> result = new ArrayList<Long>(maxPageCount.intValue());
        for (int i = 0; i < maxPageCount; i++) {
            if (beginPage > getPageCount()) {
                break;
            }
            result.add(Long.valueOf(beginPage));

            beginPage++;
        }
        return result;
    }

    private String orderExpress;

    private String order;

    public String getOrderExpress() {
        return orderExpress;
    }

    public void setOrderExpress(String orderExpress) {
        this.orderExpress = orderExpress;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
        refresh();
    }

    @Override
    public String getOrder() {
        String column = getOrderColumn();

        if (column == null) {
            column = getOrderExpress();
        }

        if (column == null) {
            return order;
        }

        String direction = getOrderDirection();

        if (direction == null) {
            return column;
        } else {
            return column + ' ' + direction;
        }
    }

    public Long getPage() {

        if ((getFirstResult() == null) || getFirstResult().equals(0)) {
            return Long.valueOf(1);
        }

        return getFirstResult().longValue() / getMaxResults().longValue() + 1;
    }


}
