package br.com.estudos.udemy.rest_with_spring_boot_and_java.data.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class BookDTO extends RepresentationModel<BookDTO> implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String author;

    @JsonProperty("launch_date")
    private Date launchDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal price;

    private String title;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BookDTO dto = (BookDTO) o;
        return Objects.equals(getId(), dto.getId()) && Objects.equals(getAuthor(), dto.getAuthor()) && Objects.equals(getLaunchDate(), dto.getLaunchDate()) && Objects.equals(getPrice(), dto.getPrice()) && Objects.equals(getTitle(), dto.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getAuthor(), getLaunchDate(), getPrice(), getTitle());
    }
}
