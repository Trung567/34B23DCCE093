package model;

import java.util.Date;

public class Member3 {
    private int id;
    private String username;
    private String password;
    private Date birthDate;
    private String email;
    private String phone;
    private String note;
    private FullName3 fullName;
    private Address3 address;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public FullName3 getFullName() { return fullName; }
    public void setFullName(FullName3 fullName) { this.fullName = fullName; }
    public Address3 getAddress() { return address; }
    public void setAddress(Address3 address) { this.address = address; }
}
