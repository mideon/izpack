package com.izforge.izpack.panels.userinput;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.data.Panel;
import com.izforge.izpack.api.data.PanelActionConfiguration;
import com.izforge.izpack.api.exception.IzPackException;
import com.izforge.izpack.api.handler.AbstractUIHandler;
import com.izforge.izpack.api.resource.Messages;
import com.izforge.izpack.core.resource.ResourceManager;
import com.izforge.izpack.data.PanelAction;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PrintCustomFields implements PanelAction {
    /**
     * The panel.
     */
    private final Panel panel;

    /**
     * The installation data.
     */
    private final InstallData installData;

    private final ActionStage stage;

    public PrintCustomFields(Panel panel, ActionStage stage, InstallData installData) {
        this.panel = panel;
        this.installData = installData;
        this.stage = stage;
    }

    @Override
    public void executeAction(InstallData adata, AbstractUIHandler handler) {
        if(stage == ActionStage.postvalidate) {
            String messageValue = adata.getVariable("rk7SetupFile");

            FileInputStream fis;
            Properties props = new Properties();
            try{
                fis = new FileInputStream(messageValue);
                props.load(fis);
                fis.close();

                if( props.getProperty("waiterIdent", null) != null) {
                    adata.setVariable("waiterIdent", props.getProperty("waiterIdent", adata.getVariable("waiterIdent")));
                    adata.setVariable("tableIdent", props.getProperty("tableIdent", adata.getVariable("tableIdent")));
                    adata.setVariable("voidIdent", props.getProperty("voidIdent", adata.getVariable("voidIdent")));
                    adata.setVariable("currencyIdent", props.getProperty("currencyIdent", adata.getVariable("currencyIdent")));
                    adata.setVariable("orderTypes", props.getProperty("orderTypes", adata.getVariable("orderTypes")));
                    adata.setVariable("hiddenSelectorIdent", props.getProperty("hiddenSelectorIdent", adata.getVariable("hiddenSelectorIdent")));
                    adata.setVariable("zReportMaketIdent", props.getProperty("zReportMaketIdent", adata.getVariable("zReportMaketIdent")));
                    adata.setVariable("xReportMaketIdent", props.getProperty("xReportMaketIdent", adata.getVariable("xReportMaketIdent")));
                    adata.setVariable("printerTestMaketIdent", props.getProperty("printerTestMaketIdent", adata.getVariable("printerTestMaketIdent")));
                    adata.setVariable("defaultSelectorIdent", props.getProperty("defaultSelectorIdent", adata.getVariable("defaultSelectorIdent")));
                } else {
                    Messages messages = adata.getMessages();
                    handler.emitError(messages.get("UserInputPanel.file.notfile.caption"), messages.get("UserInputPanel.file.notfile.message"));
                    throw new IzPackException(messages.get("UserInputPanel.file.notfile.caption"));
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    @Override
    public void initialize(PanelActionConfiguration configuration) {

    }
}
