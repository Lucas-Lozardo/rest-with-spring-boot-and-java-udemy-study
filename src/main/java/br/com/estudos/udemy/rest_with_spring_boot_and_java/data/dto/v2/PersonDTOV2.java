package br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v2;

import br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v1.PersonDTO;
import br.com.estudos.udemy.rest_with_spring_boot_and_java.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class PersonDTOV2 extends RepresentationModel<PersonDTO> implements Serializable {


    private static final long serialVersionUID = 1L;


    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    private String address;

    @JsonIgnore
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;
    private Date birthDay;

    public PersonDTOV2() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonDTOV2 dtov2 = (PersonDTOV2) o;
        return Objects.equals(getId(), dtov2.getId()) && Objects.equals(getFirstName(), dtov2.getFirstName()) && Objects.equals(getLastName(), dtov2.getLastName()) && Objects.equals(getAddress(), dtov2.getAddress()) && Objects.equals(getGender(), dtov2.getGender()) && Objects.equals(getBirthDay(), dtov2.getBirthDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender(), getBirthDay());
    }
}
