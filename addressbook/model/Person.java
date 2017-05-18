package addressbook.model;

import addressbook.resources.util.LocalDateAdapter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Giuseppe Serra
 */
public class Person implements Comparable<Person>,Comparator<Person>{
    
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty street;
    private IntegerProperty postalCode;
    private StringProperty city;
    private IntegerProperty phone;
    private ObjectProperty<LocalDate> birthday; 
    private StringProperty eMail;
    
    /**
     * Default constructor;
     */
    public Person() {
        this(null,null);
    }
    
    /**
     * Constructor with some initial data.
     * @param firstName of person;
     * @param lastName of person;
     */
    public Person(String firstName, String lastName){
        this.firstName= new SimpleStringProperty(firstName);
        this.lastName= new SimpleStringProperty(lastName);
        
        // Some initial dummy data, 
        this.street= new SimpleStringProperty("street");
        this.postalCode = new SimpleIntegerProperty(12345);
        this.city= new SimpleStringProperty("city");
        this.phone= new SimpleIntegerProperty(1234567890);
        this.birthday = new SimpleObjectProperty<>(LocalDate.of(1999, 01, 01));
        this.eMail= new SimpleStringProperty("user@example.eee");
    }
    
    /*
     * Create setter and getter and string property method;
     */
    //firstName;
    public String getFirstName(){
      return firstName.get();
    }
    
    public void setFirstName(String firstName){
        this.firstName.set(firstName);
    }
    
    public StringProperty firstNameProperty(){
        if (firstName == null){
            firstName = new SimpleStringProperty(this, "firstName");}
        return firstName;
    }
    //lastName;
    public String getLastName(){
        return lastName.get();
    }
    
    public void setLastName(String lastName){
        this.lastName.set(lastName);
    }
    
    public StringProperty lastNameProperty(){
         if (lastName == null){
            lastName = new SimpleStringProperty(this, "lastName");}
        return lastName;
    }
    //street;
    public String getStreet(){
      return street.get();
    }
    
    public void setStreet(String street){
        this.street.set(street);
    }
    
    public StringProperty streetProperty(){
        return street;
    }
    //postalCode;
    public int getPostalCode(){
        return this.postalCode.get();
    }
    
    public void setPostalCode(int code){
        this.postalCode.set(code);
    }
    
    public IntegerProperty postalCodeProperty(){
        return postalCode;
    }
    //city;
    public String getCity(){
      return this.city.get();
    }
    
    public void setCity(String city){
        this.city.set(city);
    }
    
    public StringProperty cityProperty(){
        return city;
    }
    //phone
    
    public int getPhone(){
        return this.phone.get();
    }
    
    public void setPhone(int code){
        this.phone.set(code);
    }
    
    public IntegerProperty phoneProperty(){
        return phone;
    }
   //birthday;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday(){
        return this.birthday.get();
    }
    
    public void setBirthday(LocalDate birthday){
        this.birthday.set(birthday);
    }
    
    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }
    //eMail;
    public String getEMail(){
        return this.eMail.get();
    }
    
    public void setEMail(String eMail){
        this.eMail.set(eMail);
    }
    
    public StringProperty eMailProperty(){
        return eMail;
    }
    /**
     * Compare to Person.
     * Assume that the first and last names are always not null.
     * @param p person to compare;
     * @return sign - if this is plus of p, sign + if reverse ,0 is egual;
     */
    @Override
    public int compareTo(Person p){
        // Assume that the first and last names are always not null
        int diff=this.getFirstName().compareToIgnoreCase(p.getFirstName());
        if(diff==0){
            diff=this.getLastName().compareToIgnoreCase(p.getLastName());
        }
        return diff;
    }
    
    /**
     * Compare p1, p2;
     * @param p1 person ;
     * @param p2 person;
     * @return sign - if this is plus of p, sign + if reverse ,0 is egual;
     */
    @Override
    public int compare(Person p1, Person p2) {
       return p1.compareTo(p2);  
    }
    /**
     * 
     * @param obj 
     * @return true if this and obj are egual, other is false;
     */
    @Override
  public boolean equals(Object obj) {
     if (obj == this) {
       return true;
     }
     if (!(obj instanceof Person)) {
       return false;
     }
     Person p1 = (Person) obj;
      return this.getFirstName().equalsIgnoreCase(p1.getFirstName())&&
              this.getLastName().equalsIgnoreCase(p1.getLastName());
  }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.firstName);
        hash = 23 * hash + Objects.hashCode(this.lastName);
        return hash;
    }
}