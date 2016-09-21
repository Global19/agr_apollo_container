package org.bbop.apollo.gwt.client.assemblage;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import org.bbop.apollo.gwt.client.dto.assemblage.SequenceFeatureInfo;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Pull;

/**
 * Created by nathandunn on 9/20/16.
 */
public class AssemblageFeatureWidget extends HorizontalPanel{

    private SequenceFeatureInfo sequenceFeatureInfo ;

    private Button featureButton = new Button();
    private Icon actionIcon = new Icon();


    private void doAction() {
        if(actionIcon.getType()==IconType.EXPAND){
            actionIcon.setType(IconType.COMPRESS);
        }
        else
        if(actionIcon.getType()==IconType.COMPRESS){
            actionIcon.setType(IconType.EXPAND);
        }
    }

    public AssemblageFeatureWidget(SequenceFeatureInfo sequenceFeatureInfo){
        this.sequenceFeatureInfo = sequenceFeatureInfo ;
        featureButton.setType(ButtonType.INFO);
        String name = sequenceFeatureInfo.getName();
        featureButton.setText(name);

        IconType iconType = Random.nextBoolean() ? IconType.EXPAND : IconType.COMPRESS;
        actionIcon.setPull(Pull.RIGHT);
        actionIcon.addStyleName("rotate-icon-45");
        actionIcon.setType(iconType);

        actionIcon.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                doAction();
            }
        });


        featureButton.add(actionIcon);
        featureButton.addStyleName("assemblage-detail-widget");

        add(featureButton);
    }

    public void render(PickupDragController featureDragController){
        featureDragController.makeDraggable(this,featureButton);
    }

    public SequenceFeatureInfo getSequenceFeatureInfo() {
        return sequenceFeatureInfo;
    }
}
