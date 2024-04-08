package ch.letsboot.jugstalk.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Date;


@Entity
public class Course {

    @Id
    @NotNull(message = "Id is required")
    @Positive(message = "Id must be greater than zero")
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2048, message = "Description cannot be longer than 255 characters")
    private String description;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than zero")
    @Column(name = "duration_in_days")
    private Integer durationInDays;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date")
    private Date startDate;

    @NotNull(message = "End date is required")
    @Column(name = "end_date")
    private Date endDate;

    @NotBlank(message = "City is required")
    @Size(max = 80, message = "City cannot be longer than 80 characters")
    private String city;

    public Course() {
    }

    public Course(String title, String description, Integer durationInDays, Double price, Trainer trainer, Date startDate, Date endDate, String city) {
        this.title = title;
        this.description = description;
        this.durationInDays = durationInDays;
        this.price = price;
        this.trainer = trainer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (!id.equals(course.id)) return false;
        if (!title.equals(course.title)) return false;
        if (!description.equals(course.description)) return false;
        if (!durationInDays.equals(course.durationInDays)) return false;
        if (!price.equals(course.price)) return false;
        if (!trainer.equals(course.trainer)) return false;
        if (!startDate.equals(course.startDate)) return false;
        if (!endDate.equals(course.endDate)) return false;
        return city.equals(course.city);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + durationInDays.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + trainer.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + city.hashCode();
        return result;
    }
}
