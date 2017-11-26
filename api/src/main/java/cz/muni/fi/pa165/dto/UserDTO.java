package cz.muni.fi.pa165.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Tibor Bujdoš
 */
public class UserDTO {
    private Long id;
    private String forename;
    private String surname;
    private String email;
    private String passwordHash;
    private boolean isTeacher = false;
    private Set<SeminarGroupDTO> seminarGroupSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public Set<SeminarGroupDTO> getSeminarGroupSet() {
        return seminarGroupSet;
    }

    public void setSeminarGroupSet(Set<SeminarGroupDTO> seminarGroupSet) {
        this.seminarGroupSet = seminarGroupSet;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
}
