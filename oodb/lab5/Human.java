import org.postgresql.util.PGobject;
import org.postgresql.util.PGtokenizer;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Human extends PGobject {

    private String firstname;
    private String lastname;
    private String middlename;
    private String MedicalPolicy;
    private String PhoneNumber;
    private String Age;

    public Human() {
        setType("human");
    }

    @Override
    public void setValue(String value) throws SQLException {
        //String s = value.substring(1, value.length()-1);
        String s = value;
        PGtokenizer t = new PGtokenizer(s, ',');
        if(t.getSize() != 6) throw new SQLException("Failed to convert to type Human");
        firstname = t.getToken(0);
        lastname = t.getToken(1);
        middlename = t.getToken(2);
        MedicalPolicy = t.getToken(3);
        PhoneNumber = t.getToken(4);
        Age = t.getToken(5);
        //try {
        //    Age = new SimpleDateFormat("yyyy-MM-dd").parse(t.getToken(5));
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
    }

    @Override
    public String getValue() {
        return "('" + firstname + "','" + lastname + "','" + middlename + "','" +  MedicalPolicy +"','"+PhoneNumber +"','"+ Age+ "')";
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String  getAge(){
        return  Age;
  }

    public void setAge(String age) {
        Age = age;
    }

    public String getMedicalPolicy() {
        return MedicalPolicy;
    }

    public void setMedicalPolicy(String medicalPolicy) {
        MedicalPolicy = medicalPolicy;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
}