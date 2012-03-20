    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Prospection.module;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
/**
 *
 * @author weizhe.jiao
 */
   public class Person implements Serializable {

      private int id;

       private String firstName = "";
       private String lastName = "";
       private Date birthdate;
       private Integer shoesize = 42;
       private String password = "";
       private UUID uuid;
       private String countryCode = "";

       private String login="";
       private String nb="";
       private String rue="";
       private String cp="";
       private String ville="";
       private String portable="";
       private String fix="";
       private String fax="";

       private String observation="";
       private String status="";

       public Person() {

           uuid = UUID.fromString("3856c3da-ea56-4717-9f58-85f6c5f560a5");
       }

       public Person(Object operson)
       {

       }

       public String getPre() {
           return firstName;
       }

       public void setPre(String firstName) {
           this.firstName = firstName;
       }

       public String getNom() {
           return lastName;
       }
       public void setNom(String lastName) {
           this.lastName = lastName;
       }
       public Date getBirthdate() {
           return birthdate;
       }
       public void setBirthdate(Date birthdate) {
           this.birthdate = birthdate;
       }
       public Integer getShoesize() {
           return shoesize;
       }
       public void setShoesize(Integer shoesize) {
           this.shoesize = shoesize;
       }
       public String getPass() {
           return password;
       }
       public void setPass(String password) {
           this.password = password;
       }
       public UUID getUuid() {
           return uuid;
       }
       public String getCountryCode() {
           return countryCode;
       }
       public void setCountryCode(String countryCode) {
           this.countryCode = countryCode;
       }

       public String getMail() {
           return login;
       }

       public void setMail(String login) {
           this.login = login;
       }


       public String getAdr_Nr() {
           return nb;
       }

       public void setAdr_Nr(String nb) {
           this.nb = nb;
       }

       public String getAdr_Rue() {
           return rue;
       }

       public void setAdr_Rue(String rue) {
           this.rue = rue;
       }

              public String getAdr_CP() {
           return cp;
       }

       public void setAdr_CP(String cp) {
           this.cp = cp;
       }

       public String getAdr_Ville() {
           return ville;
       }

       public void setAdr_Ville(String ville) {
           this.ville = ville;
       }

              public String getTel_Mob() {
           return portable;
       }

       public void setTel_Mob(String portable) {
           this.portable = portable;
       }

                     public String getTel_Fix() {
           return fix;
       }

       public void setTel_Fix(String fix) {
           this.fix = fix;
       }

                            public String getTel_DA() {
           return fax;
       }

       public void setTel_DA(String fax) {
           this.fax = fax;
       }

              public Date getBirthday() {
           return birthdate;
       }
                     public void setBirthday(Date birth) {
           this.birthdate = birth;
       }

                     public String getObservation() {
           return observation;
       }
                     public void setObservation(String note) {
           this.observation = note;
       }

                     public String getStatus() {
           return status;
       }
                     public void setStatus(String s) {
           this.status = s;
       }

                     public int getId() {
           return id;
       }
                     public void setId(int s) {
           this.id = s;
       }



   }

