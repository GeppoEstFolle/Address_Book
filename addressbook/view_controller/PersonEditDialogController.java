package addressbook.view_controller;

import addressbook.model.Person;
import addressbook.resources.util.DateUtil;
import addressbook.resources.util.EMailValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Giuseppe Serra
 */
public class PersonEditDialogController  {

    @FXML
    private Button ok;
    @FXML
    private Button cancel;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField eMailField;

    
    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;
    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
        // TODO
    } 
    
    /**
     * Called if user click Ok button
     * 
     */
    @FXML
    private void handleOk(ActionEvent event){
        if(isInputValid()){
          person.setFirstName(firstNameField.getText());
          person.setLastName(lastNameField.getText());
          person.setStreet(streetField.getText());
          person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
          person.setCity(cityField.getText());
          person.setBirthday(DateUtil.parse(birthdayField.getText()));
          person.setPhone(Integer.parseInt(phoneField.getText()));
          person.setEMail(eMailField.getText());
          
          okClicked = true;
          dialogStage.close();  
        }
    }
    
    /**
     * Called when user click cancel buttn.
     */
    @FXML
    private void handleCancel(ActionEvent event){
        dialogStage.close();
    }
    
    /**
     * Set dialog stage.
     * @param dialogStage this dialog stage;
     */
    public void setDialogStage(Stage dialogStage){
        this.dialogStage=dialogStage;
    }
    
    /**
     * Set the Person in dialog stage.
     * @param person.
     */
    public void setPerson(Person person){
        this.person=person;
        
        this.firstNameField.setText(person.getFirstName());
        this.lastNameField.setText(person.getLastName());
        this.streetField.setText(person.getStreet());
        this.postalCodeField.setText(Integer.toString(person.getPostalCode()));
        this.cityField.setText(person.getCity());
        this.phoneField.setText(Integer.toString(person.getPhone()));
        this.birthdayField.setText(DateUtil.format(person.getBirthday()));
        this.birthdayField.setPromptText("dd-mm-yyyy");
        this.eMailField.setText(person.getEMail());
    }
    
    /**
     * Return true if Ok is cliked;
     * @return true if is clicked other false;
     */
    public boolean isOkCliked(){
        return okClicked;
    }

    /**
     * Validate the user input in text fields.
     * @return true if input is valid;
     */
    private boolean isInputValid() {
      String errorMessage= ""; 
      if(firstNameField.getText()==null||firstNameField.getText().length()==0){
          errorMessage += "No Valid First Name! \n";
      }
      if(lastNameField.getText()==null||lastNameField.getText().length()==0){
          errorMessage += "No Valid Last Name! \n";
      }
      if(streetField.getText()==null||streetField.getText().length()==0){
          errorMessage += "No Valid street! \n";
      }
      if(postalCodeField.getText()==null||postalCodeField
                                                       .getText().length()==0){
          errorMessage += "No Valid Postal Code! \n";
        }else{
          try{
              Integer.parseInt(postalCodeField.getText());
            }catch(NumberFormatException e){
             errorMessage+="No Valid Postal Code.Use Number not letter!\n";
            }    
        }
      if(cityField.getText()==null||cityField.getText().length()==0){
          errorMessage += "No Valid City! \n";
      }
      if (birthdayField.getText() == null || birthdayField
                                                      .getText().length() == 0){
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
              errorMessage += "No valid birthday. Use the format dd-mm-yyyy!\n";
            }
        }
      if(phoneField.getText()==null||phoneField.getText().length()==0){
          errorMessage += "No Valid Phone Number! \n";
        }else{
          try{
              Integer.parseInt(phoneField.getText());
            }catch(NumberFormatException e){
             errorMessage+="No Valid Phone Number.Use Number not letter!\n";
            }    
        }
      if(eMailField.getText()==null||eMailField.getText().length()==0){
          errorMessage += "No Valid E-Mail! \n";
      }else{
          EMailValidator val= new EMailValidator();
          if(!val.validate(eMailField.getText())){
              errorMessage += "No Valid E-Mail!Please use user@example.eee \n";
          }
      }
      if(errorMessage.length()==0){
          return true;
        }else{
          // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
       }
    }   
}
