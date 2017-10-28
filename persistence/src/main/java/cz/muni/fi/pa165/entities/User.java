package cz.muni.fi.pa165.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Martin Kocisky
 */

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String forename;

    @NotNull
    private String surname;

    @Column(nullable=false,unique=true)
    @Pattern(regexp=".+@.+\\....?")
    @NotNull
    private String email;

    private String passwordHash;

    private boolean isTeacher = false;

/*    @ManyToMany*//*(mappedBy = users)*//* // TODO: add dependency to SeminarGroup as per diagram
    private Set<SeminarGroup> seminarGroupSet = new HashSet<>();*/

    public User() {

    }

    public User(String forename, String surname, String email, String passwordHash, boolean isTeacher) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isTeacher = isTeacher;
    }

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

    public boolean getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

 /*   public void addSeminarGroup(SeminarGroup g) {
        seminarGroupSet.add(g);
    }

    public void removeSeminarGroup(SeminarGroup g) {
        seminarGroupSet.remove(g);
    }

    public Set<SeminarGroup> getSeminarGroups() {
        return Collections.unmodifiableSet(seminarGroupSet);
    }
*/
    @Override
    public int hashCode() {
        if (email != null) return email.hashCode();
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (email == null) {
            if (other.getEmail() != null)
                return false;
        } else if (!email.equals(other.getEmail()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", forename='" + forename + '\'' + ", surname='" + surname + '\'' +
                '}';
    }
}