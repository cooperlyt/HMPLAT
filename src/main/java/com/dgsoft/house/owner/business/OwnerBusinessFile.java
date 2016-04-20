package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.*;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.model.BusinessNeedFile;
import com.dgsoft.house.owner.AttachFileNameCache;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessFile;
import com.dgsoft.house.owner.model.UploadFile;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;

import java.util.*;

/**
 * Created by cooper on 7/17/15.
 */
@Name("ownerBusinessFile")
@Scope(ScopeType.CONVERSATION)
public class OwnerBusinessFile extends OwnerBusinessFileBase {

    @In
    private BusinessDefineHome businessDefineHome;


    protected List<ParentNode> genTree() {

        List<ParentNode> result = new ArrayList<ParentNode>(2);


        List<BusinessNeedFile> rootNeedFile = businessDefineHome.getNeedFileRootList();


        if (!rootNeedFile.isEmpty()) {
            ParentNode fileNode = new AllNode(null,"要件","必要要件");
            fillImportTree(fileNode,rootNeedFile);
            result.add(fileNode);
        }

        ParentNode otherNode = new AllNode(null,"附件","自定义附件");


        for (BusinessFile file : ownerBusinessHome.getInstance().getUploadFileses()) {
            if (!file.isImportant()) {
                otherNode.addChild(new OtherChildNode(otherNode,file,""));
            }
        }

        result.add(otherNode);

        return result;
    }

    private void fillImportTree(ParentNode node, List<BusinessNeedFile> defineNodes){
        for(BusinessNeedFile defineNode: defineNodes){

            if (defineNode.getCondition() == null || defineNode.getCondition().trim().equals("") ||
                    Expressions.instance().createValueExpression(defineNode.getCondition(), Boolean.class).getValue()) {


                if (BusinessNeedFile.NeedFileNodeFile.CHILDREN.equals(defineNode.getType())) {

                    BusinessFile linkFile = null;
                    for (BusinessFile file : ownerBusinessHome.getInstance().getUploadFileses()) {
                        if (file.isImportant() && file.getImportantCode().equals(defineNode.getId())) {
                            linkFile = file;
                            break;
                        }
                    }
                    if (linkFile == null) {
                        Logging.getLog(getClass()).debug("create need File:"  + defineNode.getName());
                        linkFile = new BusinessFile(ownerBusinessHome.getInstance(),defineNode.getName(), defineNode.getId(), defineNode.getPriority());
                        ownerBusinessHome.getInstance().getUploadFileses().add(linkFile);
                    }

                    node.addChild(new FileChildNode(node,linkFile, defineNode.getDescription(), defineNode.getTaskNameList().contains(businessDefineHome.getTaskName())));

                } else {

                    ParentNode fileNode = BusinessNeedFile.NeedFileNodeFile.ALL.equals(defineNode.getType()) ? new AllNode(node,defineNode.getName(),defineNode.getDescription()) : new AnyNode(node,defineNode.getName(),defineNode.getDescription());

                    node.addChild(fileNode);

                    fillImportTree(fileNode, defineNode.getChildrenList());
                }
            }

        }
    }

}
