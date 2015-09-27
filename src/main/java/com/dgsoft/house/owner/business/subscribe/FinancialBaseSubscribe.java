package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import com.dgsoft.house.owner.model.Financial;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.MortgaegeRegiste;
import org.jboss.seam.annotations.In;
import org.jboss.seam.log.Logging;

import javax.faces.event.ValueChangeEvent;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wxy on 2015-09-18.
 */
public abstract class FinancialBaseSubscribe extends OwnerEntityHome<Financial> {
    @In
    protected OwnerBusinessHome ownerBusinessHome;
    protected MortgaegeRegiste mortgaegeRegiste;
    private boolean selected;

    protected abstract void addMortgage();

    public Financial.FinancialType[] getFinancialTypes() {
        return Financial.FinancialType.values();
    }

    public MortgaegeRegiste getMortgaegeRegiste() {
        return mortgaegeRegiste;
    }

    public void setMortgaegeRegiste(MortgaegeRegiste mortgaegeRegiste) {
        this.mortgaegeRegiste = mortgaegeRegiste;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getSelectName() {
        return getInstance().getName();
    }

    public void setSelectName(String name) {
        selected = (name != null) && (!name.trim().equals(""));
        getInstance().setName(name);

        if (selected) {
            try {
                Date maxDate = getEntityManager().createQuery("select max(f.createTime) from Financial f where f.phone != null and f.name = :name and not (f.mortgaegeForNew.ownerBusiness.status in ('MODIFY', 'MODIFYING','ABORT', 'SUSPEND'))", Date.class).setParameter("name", name).getSingleResult();

                getInstance().setPhone(getEntityManager().createQuery("select max(f.phone) from Financial f where f.createTime >= :maxDate and f.phone != null and f.name = :name and not (f.mortgaegeForNew.ownerBusiness.status in ('MODIFY', 'MODIFYING','ABORT', 'SUSPEND'))", String.class).setParameter("name", name).setParameter("maxDate", maxDate).getSingleResult());


            } catch (NoResultException e1) {
                getInstance().setPhone(null);
            }
        } else {
            getInstance().setPhone(null);
        }
    }

    public List<String> getFinancialCorpNames() {
        if ((getFilterBank() != null) && (!getFilterBank().trim().equals(""))) {
            return new ArrayList<String>(getEntityManager().
                    createQuery("select distinct f.name from Financial f where f.bank = :bank and not (f.mortgaegeForNew.ownerBusiness.status in ('MODIFY', 'MODIFYING','ABORT', 'SUSPEND'))", String.class).setParameter("bank", getFilterBank()).getResultList());
        }
        return new ArrayList<String>(0);
    }

    public void setFilterBank(String bankId) {
        getInstance().setBank(bankId);
        getInstance().setName(null);
        getInstance().setCode(null);
        getInstance().setPhone(null);
        selected = false;
    }

    public String getFilterBank() {
        return getInstance().getBank();
    }

    @Override
    public Class<Financial> getEntityClass() {
        return Financial.class;
    }

    @Override
    public Financial createInstance() {
        return new Financial(new Date(), Financial.FinancialType.FINANCE_CORP);
    }

    @Override
    public void create() {
        super.create();

        if (ownerBusinessHome.getInstance().getMortgaegeRegistes().isEmpty()) {
            mortgaegeRegiste = new MortgaegeRegiste(ownerBusinessHome.getInstance());
            //发证机关
            mortgaegeRegiste.setOrgName(RunParam.instance().getStringParamValue("SetupName"));

            mortgaegeRegiste.setFinancial(getInstance());
            ownerBusinessHome.getInstance().getMortgaegeRegistes().add(mortgaegeRegiste);


            addMortgage();

            // ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getMortgaegeRegistes().add(mortgaegeRegiste);
        } else {
            mortgaegeRegiste = ownerBusinessHome.getInstance().getMortgaegeRegistes().iterator().next();
           // mortgaegeRegiste.setOrgName(RunParam.instance().getStringParamValue("SetupName"));

            if (mortgaegeRegiste.getFinancial() == null) {
                mortgaegeRegiste.setFinancial(getInstance());
            } else {
                if (mortgaegeRegiste.getFinancial().getId() == null) {
                    setInstance(mortgaegeRegiste.getFinancial());
                } else {
                    setId(mortgaegeRegiste.getFinancial().getId());
                }
            }
        }

    }

    public void typeChangeListener(ValueChangeEvent e) {
        if (Financial.FinancialType.FINANCE_CORP.equals(e.getNewValue())) {
            Logging.getLog(getClass()).debug("type is finance corp");
            getInstance().setCredentialsType(PersonEntity.CredentialsType.OTHER);
        } else {
            Logging.getLog(getClass()).debug("type is  Person");
            getInstance().setCredentialsType(PersonEntity.CredentialsType.MASTER_ID);
        }
    }

    public PersonHelper<Financial> getPersonInstance() {
        return new PersonHelper<Financial>(getInstance());
    }



    public PersonHelper<BusinessHouseOwner> getPersonMortgageInstance() {
        return new PersonHelper<BusinessHouseOwner>(mortgaegeRegiste.getBusinessHouseOwner());
    }

}
