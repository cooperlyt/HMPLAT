package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.model.BusinessNeedFile;
import com.dgsoft.house.owner.model.BusinessFile;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cooper on 4/19/16.
 */
@Name("patchBusinessFile")
@Scope(ScopeType.CONVERSATION)
public class PatchBusinessFile extends OwnerBusinessFileBase{


    @Override
    protected List<ParentNode> genTree() {

            List<ParentNode> result = new ArrayList<ParentNode>(1);

            ParentNode otherNode = new AllNode(null,"附件","自定义附件");

            Logging.getLog(getClass()).debug("getTree:" + ownerBusinessHome.getInstance().getId());
            for (BusinessFile file : ownerBusinessHome.getInstance().getUploadFileses()) {
                Logging.getLog(getClass()).debug("add File:" + file.getName());
                otherNode.addChild(new OtherChildNode(otherNode,file,""));
            }

            result.add(otherNode);

            return result;

    }

}
