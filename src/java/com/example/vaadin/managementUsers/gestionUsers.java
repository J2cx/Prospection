/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.vaadin.managementUsers;

import com.example.vaadin.TablePospecteurs;
import com.example.vaadin.managementUsers.FormChercher;
import com.example.vaadin.module.Person;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.*;

import com.vaadin.data.util.BeanItem;
import java.util.Arrays;
/**
 *
 * @author weizhe.jiao
 */
public class gestionUsers extends HorizontalLayout {
    
    Person person;
    private static final String COMMON_FIELD_WIDTH = "12em";
        //private FormPojoExample fpe=new FormPojoExample();
    TablePospecteurs tb=new TablePospecteurs();
    FormChercher fc=new FormChercher();

    public gestionUsers()
    {
        this.addComponent(tb);
        this.addComponent(fc);
    }
        public void ll()
    {
                person = new Person();

    // a person POJO
    BeanItem<Person> personItem = new BeanItem<Person>(person);
        final Form personForm = new Form();
    personForm.setCaption("Personal details");
    personForm.setWriteThrough(false);
    // we want explicit 'apply'
    personForm.setInvalidCommitted(false);
    // no invalid values in datamodel
    // FieldFactory for customizing the fields and adding validators
    personForm.setFormFieldFactory(new PersonFieldFactory());
    personForm.setItemDataSource(personItem);
    // bind to POJO via BeanItem
    // Determines which properties are shown, and in which order:
    personForm.setVisibleItemProperties(Arrays.asList(new String[] {
        "login","password","firstName", "lastName",
                "nb","rue","cp","ville",
                "portable","fix","fax"
          }));
    // Add form to layout
    addComponent(personForm);
    }


    private class PersonFieldFactory extends DefaultFieldFactory {
       final ComboBox countries = new ComboBox("Country");
       public PersonFieldFactory() {
           countries.setWidth(COMMON_FIELD_WIDTH);
           //countries.setContainerDataSource(ExampleUtil.getISO3166Container());
           //countries.setItemCaptionPropertyId(ExampleUtil.iso3166_PROPERTY_NAME);
           //countries.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);
           countries.setFilteringMode(ComboBox.FILTERINGMODE_STARTSWITH);
       }
       /*
       @Override
       public Field createField(Item item, Object propertyId,
               Component uiContext) {
           Field f;
           if ("countryCode".equals(propertyId)) {
               // filtering ComboBox w/ country names
               return countries;
           }
           else if ("password".equals(propertyId)) {
               // Create a password field so the password is not shown
               //f = createPasswordField(propertyId);
           } else {
               // Use the super class to create a suitable field base on the
               // property type.
               f = super.createField(item, propertyId, uiContext);
           }
           if ("firstName".equals(propertyId)) {
               TextField tf = (TextField) f;
               tf.setRequired(true);
               tf.setRequiredError("Please enter a First Name");
               tf.setWidth(COMMON_FIELD_WIDTH);
               tf.addValidator(new StringLengthValidator(
                       "First Name must be 3-25 characters", 3, 25, false));
           } else if ("lastName".equals(propertyId)) {
               TextField tf = (TextField) f;
               tf.setRequired(true);
               tf.setRequiredError("Please enter a Last Name");
               tf.setWidth(COMMON_FIELD_WIDTH);
               tf.addValidator(new StringLengthValidator(
                       "Last Name must be 3-50 characters", 3, 50, false));
           } else if ("password".equals(propertyId)) {
               PasswordField pf = (PasswordField) f;
               pf.setRequired(true);
               pf.setRequiredError("Please enter a password");
               pf.setWidth("10em");
               pf.addValidator(new StringLengthValidator(
                       "Password must be 6-20 characters", 6, 20, false));
           } else if ("shoesize".equals(propertyId)) {
               TextField tf = (TextField) f;
               tf.setNullRepresentation("");
               tf.setNullSettingAllowed(true);
               tf.addValidator(new IntegerValidator(
                       "Shoe size must be an Integer"));
               tf.setWidth("2em");
           } else if ("uuid".equals(propertyId)) {
               TextField tf = (TextField) f;
               tf.setWidth("20em");
           }            return f;
       }*/
       /*
       private PasswordField createPasswordField(Object propertyId) {
           PasswordField pf = new PasswordField();
           pf.setCaption(DefaultFieldFactory
                   .createCaptionByPropertyId(propertyId));
           return pf;
       }*/
   }
}
