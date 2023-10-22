import com.google.gson.annotations.SerializedName;
/*
 * Entity class to represent City
 * with natural ordering of rating when compared
 */

public class Hotel implements Comparable<Hotel> {
  // inner class for rating
  class UserRating implements Comparable<UserRating>{
    @SerializedName("average_rating")
    double averageRating;
    int votes;

    public UserRating(double averageRating, int votes){
      this.averageRating = averageRating;
      this.votes = votes;
    }

    @Override
    public int compareTo(UserRating other){
      if(this.averageRating == other.averageRating){
        return Integer.compare(this.votes, other.votes);
      }
      return Double.compare(this.averageRating, other.averageRating);
    }

    @Override
    public String toString() {
      return "{averageRating:" + this.averageRating + ",votes:" + votes + "}";
    }
  }

  String id;
  String city;
  String name;

  @SerializedName("estimated_cost")
  double estimatedCost;

  @SerializedName("user_rating")
  UserRating userRating;

  public Hotel(String id, String name, String city, UserRating rating) {
    this.id = id;
    this.name = name;
    this.city = city;
    this.userRating = rating;
  }

  @Override
  public int compareTo(Hotel other){
    if(this.estimatedCost == other.estimatedCost){
      return this.userRating.compareTo(userRating);
    }
    return Double.compare(this.estimatedCost, other.estimatedCost);
  }

  @Override
  public String toString() {
    return "\nHotel id:" + id + ",name:" + name + ",city:"+ city + ",estimatedCost:" + estimatedCost
    + ",userRating:" + userRating;
  }
}