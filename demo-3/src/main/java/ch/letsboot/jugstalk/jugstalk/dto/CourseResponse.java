package ch.letsboot.jugstalk.jugstalk.dto;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private String city;
    private Date startDate;
    private Date endDate;
    private int durationInDays;
    private double price;
    private Long trainerId;
    private String trainerName;
    private String trainerEmail;


    public CourseResponse(Long id, String title, String description, String city, int durationInDays, Date startDate, Date endDate,
                          double price, Long trainerId, String trainerName, String trainerEmail)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.city = city;
        this.durationInDays = durationInDays;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.trainerEmail = trainerEmail;
    }

    public CourseResponse() {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        if (startDate == null) {
            return "Start date not set yet.";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", new Locale("de", "CH"));
        return sdf.format(startDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        if (endDate == null) {
            return "End Date not set yet.";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", new Locale("de", "CH"));
        return sdf.format(endDate);
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int duration) {
        this.durationInDays = duration;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPrice() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("de", "CH"));
        currencyFormat.setCurrency(Currency.getInstance("CHF"));
        return currencyFormat.format(price);
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerEmail() {
        return trainerEmail;
    }

    public void setTrainerEmail(String trainerEmail) {
        this.trainerEmail = trainerEmail;
    }

}
