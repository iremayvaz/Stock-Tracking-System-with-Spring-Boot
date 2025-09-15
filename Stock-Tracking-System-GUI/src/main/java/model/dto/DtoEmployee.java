package model.dto;

public class DtoEmployee {
    public Long id;
    public String firstName;
    public String lastName;
    public String roleName; // sadece rol adı gelecek
    public String email;

    public String getRoleName() {
        return roleName;
    }

    public String getEmail() {
        return email;
    }
}
