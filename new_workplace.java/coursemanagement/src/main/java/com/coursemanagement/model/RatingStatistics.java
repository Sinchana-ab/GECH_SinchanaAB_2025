package com.coursemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingStatistics {
    private Double averageRating;
    private Long totalRatings;
    private Integer fiveStars;
    private Integer fourStars;
    private Integer threeStars;
    private Integer twoStars;
    private Integer oneStar;
	public Double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}
	public Long getTotalRatings() {
		return totalRatings;
	}
	public void setTotalRatings(Long totalRatings) {
		this.totalRatings = totalRatings;
	}
	public Integer getFiveStars() {
		return fiveStars;
	}
	public void setFiveStars(Integer fiveStars) {
		this.fiveStars = fiveStars;
	}
	public Integer getFourStars() {
		return fourStars;
	}
	public void setFourStars(Integer fourStars) {
		this.fourStars = fourStars;
	}
	public Integer getThreeStars() {
		return threeStars;
	}
	public void setThreeStars(Integer threeStars) {
		this.threeStars = threeStars;
	}
	public Integer getTwoStars() {
		return twoStars;
	}
	public void setTwoStars(Integer twoStars) {
		this.twoStars = twoStars;
	}
	public Integer getOneStar() {
		return oneStar;
	}
	public void setOneStar(Integer oneStar) {
		this.oneStar = oneStar;
	}
	public RatingStatistics(Double averageRating, Long totalRatings, Integer fiveStars, Integer fourStars,
			Integer threeStars, Integer twoStars, Integer oneStar) {
		super();
		this.averageRating = averageRating;
		this.totalRatings = totalRatings;
		this.fiveStars = fiveStars;
		this.fourStars = fourStars;
		this.threeStars = threeStars;
		this.twoStars = twoStars;
		this.oneStar = oneStar;
	}
	public RatingStatistics() {
		super();
	}
    
    
}