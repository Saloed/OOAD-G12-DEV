/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.admin;

import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.AdminController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.SystemStateController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectActorException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordStatistic;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.TableViewDtCoordStatisticWrapper;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.coordinator.CreateICrashCoordGUI;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * This is the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
/*
 * This is the end of the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */

/**
 * The Class ICrashGUIController, which deals with handling the GUI and it's functions for the Administrator.
 */
public class ICrashAdminGUIController extends AbstractAuthGUIController {

	/*
    * This section of controls and methods is to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
	* When replacing, remember to reassign the correct methods to the button event methods and set the correct types for the tableviews
	*/

    /**
     * The pane containing the logon controls.
     */
    @FXML
    private Pane pnAdminLogon;

    /**
     * The textfield that allows input of a username for logon.
     */
    @FXML
    private TextField txtfldAdminUserName;

    /**
     * The passwordfield that allows input of a password for logon.
     */
    @FXML
    private PasswordField psswrdfldAdminPassword;

    /**
     * The button that initiates the login function.
     */
    @FXML
    private Button bttnAdminLogin;

    /**
     * The borderpane that contains the normal controls the user will use.
     */
    @FXML
    private BorderPane brdpnAdmin;

    /**
     * The button that shows the controls for adding a coordinator
     */
    @FXML
    private Button bttnBottomAdminCoordinatorAddACoordinator;

    /**
     * The button that shows the controls for deleting a coordinator
     */
    @FXML
    private Button bttnBottomAdminCoordinatorDeleteACoordinator;

    /**
     * The tableview of the coordinators timing statistic
     */
    @FXML
    private TableView<TableViewDtCoordStatisticWrapper> tableCoordStats;

    /**
     * Table columns
     */
    @FXML
    private TableColumn<TableViewDtCoordStatisticWrapper, String> colCoordId;

    @FXML
    private TableColumn<TableViewDtCoordStatisticWrapper, String> colState;

    @FXML
    private TableColumn<TableViewDtCoordStatisticWrapper, Integer> colTime;


    /**
     * The tableview of the recieved messages from the system
     */
    @FXML
    private TableView<Message> tblvwAdminMessages;

    /**
     * The button that allows a user to logoff
     */
    @FXML
    private Button bttnAdminLogoff;
    /**
     * The user controller, for this GUI it's the admin controller and allows access to admin functions like adding a coordinator.
     */
    private AdminController userController;
    /**
     * Used to get the actor coordinator that was created by the admin, for creating the new window with.
     */
    private SystemStateController systemstateController;
    /**
     * The list of open windows in the system.
     * We open a new window when a coordinator is created, so we also should close the window if the coordinator is deleted
     */
    private ArrayList<CreateICrashCoordGUI> listOfOpenWindows = new ArrayList<>();

    /**
     * The button event that will show the controls for adding a coordinator
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomAdminCoordinatorAddACoordinator_OnClick(ActionEvent event) {
        showCoordinatorScreen(TypeOfEdit.Add);
    }

    /*
     * These are other classes accessed by this controller
     */

    /**
     * The button event that will show the controls for deleting a coordinator
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomAdminCoordinatorDeleteACoordinator_OnClick(ActionEvent event) {
        showCoordinatorScreen(TypeOfEdit.Delete);
    }

    /**
     * The button event that will initiate the logging on of a user
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnBottomLoginPaneLogin_OnClick(ActionEvent event) {
        logon();
    }

	/*
     * Other things created for this controller
	 */

    /**
     * The button event that will initiate the logging off of a user
     *
     * @param event The event type thrown, we do not need this, but it must be specified
     */
    @FXML
    void bttnTopLogoff_OnClick(ActionEvent event) {
        logoff();
    }

    /* (non-Javadoc)
     * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logonShowPanes(boolean)
     */
    protected void logonShowPanes(boolean loggedOn) {
        pnAdminLogon.setVisible(!loggedOn);
        brdpnAdmin.setVisible(loggedOn);
        bttnAdminLogoff.setDisable(!loggedOn);
        bttnAdminLogin.setDefaultButton(!loggedOn);
        if (!loggedOn) {
            txtfldAdminUserName.setText("");
            psswrdfldAdminPassword.setText("");
            txtfldAdminUserName.requestFocus();
        }

    }
    /*
     * Methods used within the GUI
	 */

    /**
     * Server has gone down.
     */
    /* (non-Javadoc)
     * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.AbstractGUIController#serverHasGoneDown()
	 */
    protected void serverHasGoneDown() {
        logoff();
    }

    /* (non-Javadoc)
     * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.HasTables#setUpTables()
     */
    public void setUpTables() {
        setUpMessageTables(tblvwAdminMessages);
        colCoordId.setCellValueFactory(new PropertyValueFactory<>("coordId"));
        colState.setCellValueFactory(new PropertyValueFactory<>("state"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    /**
     * Shows the modify coordinator screen.
     *
     * @param type The type of edit to be done, this could be add or delete
     */
    private void showCoordinatorScreen(TypeOfEdit type) {

        final Stage dialog = new Stage(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.WINDOW_MODAL);
        GridPane pane = new GridPane();
        Button confirm = new Button("Confirm");
        TextField id = new TextField();
        id.setPromptText("Coordinator ID");
        TextField name = new TextField();
        name.setPromptText("Coordinator name");
        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        if (type == TypeOfEdit.Add) {
            pane.add(id, 0, 0);
            pane.add(name, 0, 1);
            pane.add(pass, 0, 2);
            pane.add(confirm, 0, 3);
            pane.setVgap(20);


            confirm.setOnAction(event1 -> {
                if (!checkIfAllDialogHasBeenFilledIn(pane)) {
                    showWarningNoDataEntered();
                    dialog.close();
                    return;
                }
                try {
                    DtCoordinatorID coordID = new DtCoordinatorID(new PtString(id.getText()));
                    if (userController.oeAddCoordinator(id.getText(), name.getText(), pass.getText()).getValue()) {
                        listOfOpenWindows.add(new CreateICrashCoordGUI(coordID, systemstateController.getActCoordinator(name.getText())));

                    } else
                        showErrorMessage("Unable to add coordinator", "An error occured when adding the coordinator");
                } catch (ServerOfflineException | ServerNotBoundException | IncorrectFormatException e) {
                    showExceptionErrorMessage(e);
                }
                dialog.close();
            });

        } else {
            pane.add(id, 0, 0);
            pane.add(confirm, 0, 1);
            pane.setVgap(50);

            confirm.setOnAction(event1 -> {
                if (!checkIfAllDialogHasBeenFilledIn(pane)) {
                    showWarningNoDataEntered();
                    dialog.close();
                    return;
                }
                try {
                    DtCoordinatorID coordID = new DtCoordinatorID(new PtString(id.getText()));

                    if (userController.oeDeleteCoordinator(id.getText()).getValue()) {
                        listOfOpenWindows.stream().filter(window1 -> window1.getDtCoordinatorID().value.getValue()
                                .equals(coordID.value.getValue())).forEach(CreateICrashCoordGUI::closeWindow);
                    } else
                        showErrorMessage("Unable to delete coordinator", "An error occured when deleting the coordinator");
                } catch (ServerOfflineException | ServerNotBoundException | IncorrectFormatException e) {
                    showExceptionErrorMessage(e);
                }
                dialog.close();
            });
        }

        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, 200, 200);
        dialog.setScene(scene);
        dialog.showAndWait();

    }

    /* (non-Javadoc)
     * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logon()
     */
    @Override
    public void logon() {
        if (txtfldAdminUserName.getText().length() > 0 && psswrdfldAdminPassword.getText().length() > 0) {
            try {
                if (userController.oeLogin(txtfldAdminUserName.getText(), psswrdfldAdminPassword.getText()).getValue())
                    logonShowPanes(true);
            } catch (ServerOfflineException | ServerNotBoundException e) {
                showExceptionErrorMessage(e);
            }
        } else
            showWarningNoDataEntered();
    }

    /* (non-Javadoc)
     * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logoff()
     */
    @Override
    public void logoff() {
        try {
            if (userController.oeLogout().getValue()) {
                logonShowPanes(false);
            }
        } catch (ServerOfflineException | ServerNotBoundException e) {
            showExceptionErrorMessage(e);
        }
    }

    /* (non-Javadoc)
     * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController#closeForm()
     */
    @Override
    public void closeForm() {
        try {
            userController.removeAllListeners();
            systemstateController.closeServerConnection();
        } catch (ServerOfflineException | ServerNotBoundException e) {
            showExceptionErrorMessage(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        systemstateController = new SystemStateController();
        logonShowPanes(false);
        setUpTables();

    }

    @Override
    public PtBoolean setActor(JIntIsActor actor) {
        try {
            if (actor instanceof ActAdministrator)
                try {
                    userController = new AdminController((ActAdministrator) actor);
                    try {
                        userController.getAuthImpl().listOfMessages.addListener(new ListChangeListener<Message>() {
                            @Override
                            public void onChanged(ListChangeListener.Change<? extends Message> c) {
                                addMessageToTableView(tblvwAdminMessages, c.getList());
                            }
                        });
                        userController.getAuthImpl().mapCoordStatistic.addListener(new MapChangeListener<String, DtCoordStatistic>() {
                            @Override
                            public void onChanged(Change<? extends String, ? extends DtCoordStatistic> change) {
                                addStatsToTableView(tableCoordStats, change.getMap().values());
                            }
                        });
                    } catch (Exception e) {
                        showExceptionErrorMessage(e);
                    }
                } catch (RemoteException e) {
                    Log4JUtils.getInstance().getLogger().error(e);
                    throw new ServerOfflineException();
                } catch (NotBoundException e) {
                    Log4JUtils.getInstance().getLogger().error(e);
                    throw new ServerNotBoundException();
                }
            else
                throw new IncorrectActorException(actor, ActAdministrator.class);
        } catch (ServerOfflineException | ServerNotBoundException | IncorrectActorException e) {
            showExceptionErrorMessage(e);
            return new PtBoolean(false);
        }
        return new PtBoolean(false);
    }

    /**
     * The enumeration dictating the type of edit an admin is doing to a coordinator.
     */
    private enum TypeOfEdit {

        /**
         * Adding a coordinator.
         */
        Add,

        /**
         * Deleting a coordinator.
         */
        Delete
    }
}
