package edu.utdallas.videoOnDemand.entities;

import java.util.Date;


public class Movie
{
	private Long movieId;
	private String title;
	private String description;
	private String director;
	private String actors;
	private String category;
	private double rating;
	private String videoURL;
	private String imdbId;
	private Date addedOn;
	private double rentAmount;
	private double purchaseAmount;
	private String posterURL;
	private Integer numOfPurchases; //from purchase info table
	private Integer numOfRents; // from rental info table
	private Integer maxActivity; // from query results for report
	private String isVisible;
	private int numTimesRated;
	
	public Integer getMaxActivity() {
		return maxActivity;
		// sum of rentals and purchases per movie
	}

	public void setMaxActivity(Integer maxActivity) {
		this.maxActivity = maxActivity;
	}

	public Integer getNumOfPurchases() {
		return numOfPurchases;
		//sum of total purchases per movie
	}

	public void setNumOfPurchases(Integer numOfPurchases) {
		this.numOfPurchases = numOfPurchases;
	}

	public Integer getNumOfRents() {
		return numOfRents;
		//sum of total rentals per movie
	}

	public void setNumOfRents(Integer numOfRents) {
		this.numOfRents = numOfRents;
	}

	public Long getMovieId()
	{
		return movieId;
	}

	public void setMovieId(Long movieId)
	{
		this.movieId = movieId;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getImdbId()
	{
		return imdbId;
	}

	public void setImdbId(String imdbId)
	{
		this.imdbId = imdbId;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDirector()
	{
		return director;
	}

	public void setDirector(String director)
	{
		this.director = director;
	}

	public String getActors()
	{
		return actors;
	}

	public void setActors(String actors)
	{
		this.actors = actors;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getVideoURL()
	{
		return videoURL;
	}

	public void setVideoURL(String videoURL)
	{
		this.videoURL = videoURL;
	}

	public Date getAddedOn()
	{
		return addedOn;
	}

	public void setAddedOn(Date addedOn)
	{
		this.addedOn = addedOn;
	}

	public double getRentAmount()
	{
		return rentAmount;
	}

	public void setRentAmount(double rentAmount)
	{
		this.rentAmount = rentAmount;
	}

	

	public double getPurchaseAmount()
	{
		return purchaseAmount;
	}

	public void setPurchaseAmount(double purchaseAmount)
	{
		this.purchaseAmount = purchaseAmount;
	}

	public double getRating()
    {
	    return rating;
    }

	public void setRating(double rating)
    {
	    this.rating = rating;
    }
			
	public String getPosterURL() {
		return posterURL;
	}

	public void setPosterURL(String posterURL) {
		this.posterURL = posterURL;
	}
	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}
	public int getNumTimesRated() {
		return numTimesRated;
	}

	public void setNumTimesRated(int numTimesRated) {
		this.numTimesRated = numTimesRated;
	}
}
