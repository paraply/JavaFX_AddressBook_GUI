
package addressbook;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.chalmers.cse.dat215.lab1.Presenter;




public class AddressBookController implements Initializable {

    private Presenter presenter;

    @FXML private MenuBar menuBar;
    @FXML private Button closeAboutButton;

    @FXML private MenuItem menNewContact;
    @FXML private MenuItem menDelContact;

    @FXML private Button newButton;
    @FXML private Button deleteButton;

    @FXML private TextField txtFstName;
    @FXML private TextField txtLstName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtAddress;
    @FXML private TextField txtPostCode;
    @FXML private TextField txtCity;

    @FXML private ListView addrList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        presenter = new Presenter(
                addrList,
                txtFstName,
                txtLstName,
                txtPhone,
                txtEmail,
                txtAddress,
                txtPostCode,
                txtCity);

        txtFstName.focusedProperty().addListener(new TextFieldListener(txtFstName));
        txtLstName.focusedProperty().addListener(new TextFieldListener(txtLstName));
        txtPhone.focusedProperty().addListener(new TextFieldListener(txtPhone));
        txtEmail.focusedProperty().addListener(new TextFieldListener(txtEmail));
        txtAddress.focusedProperty().addListener(new TextFieldListener(txtAddress));
        txtPostCode.focusedProperty().addListener(new TextFieldListener(txtPostCode));
        txtCity.focusedProperty().addListener(new TextFieldListener(txtCity));

        presenter.init();

        addrList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                presenter.contactsListChanged();
            }
        });


        addrList.getSelectionModel().select(0);
        presenter.contactsListChanged();
    }


    @FXML
    protected void newButtonActionPerformed(ActionEvent event){
        presenter.newContact();
    }


    @FXML
    protected void delButtonActionPerformed(ActionEvent event){
        presenter.removeCurrentContact();
    }

    @FXML
    protected void textFieldActionPerformed(ActionEvent event){
        presenter.textFieldActionPerformed(event);
    }


    
    @FXML 
    protected void openAboutActionPerformed(ActionEvent event) throws IOException{
    
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("addressbook/resources/AddressBook");
        Parent root = FXMLLoader.load(getClass().getResource("address_book_about.fxml"), bundle);
        Stage aboutStage = new Stage();
        aboutStage.setScene(new Scene(root));
        aboutStage.setTitle(bundle.getString("about.title.text"));
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.initOwner(menuBar.getScene().getWindow());
        aboutStage.setResizable(false);
        aboutStage.showAndWait();
    }
    
    @FXML 
    protected void closeAboutActionPerformed(ActionEvent event) throws IOException{
        
        Stage aboutStage = (Stage) closeAboutButton.getScene().getWindow();
        aboutStage.hide();
    }

    @FXML
    protected void closeApplicationActionPerformed(ActionEvent event) throws IOException{

        Stage addressBookStage = (Stage) menuBar.getScene().getWindow();
        addressBookStage.hide();
    }

    private class TextFieldListener implements ChangeListener<Boolean>{

        private TextField textField;

        public TextFieldListener(TextField textField){
            this.textField = textField;
        }

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(newValue){
                presenter.textFieldFocusGained(textField);

            }
            else{
                presenter.textFieldFocusLost(textField);
            }
        }
    }

}
