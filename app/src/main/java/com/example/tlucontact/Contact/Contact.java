package com.example.tlucontact.Contact;

public class Contact {
    private String name;
    private String phone;
    private String email;
    private String position;
    private String unit;

    public Contact() {
    }

    public Contact(String name, String phone, String email, String position, String unit) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.unit = unit;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}
