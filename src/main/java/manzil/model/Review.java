package manzil.model;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

@Entity
@Data
public class Review{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long reviewID;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Column(nullable = false)
    private LocalDate reviewDate;

    private int likesCount;

    @Min(1)
    @Max(5)
    private int ratingScore;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place reviewPlace;

    @ManyToOne
    @JoinColumn(name = "registeredUser")
    private RegisteredUser reviewRegisteredUser;

    Review(String comments, LocalDate reviewDate, int likesCount, int ratingScore){
        this.ratingScore = ratingScore;
        this.likesCount = likesCount;
        this.reviewDate = reviewDate;
        this.comments = comments;
    }

    int getLikesCount(){
        return likesCount;
    }

    LocalDate getReviewDate(){
        return reviewDate;
    }

    int getRatingScore(){
        return ratingScore;
    }

    String comments(){
        return comments;
    }

    void setLikesCount(int likesCount){
        this.likesCount = likesCount;
    }

    void setRatingScore(int ratingScore){
        this.ratingScore = ratingScore;
    }

    void setreviewDate(LocalDate reviewDate){
        this.reviewDate = reviewDate;
    }

    void setComments(String comments){
        this.comments = comments;
    }

}

