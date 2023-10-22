import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Entity to hold per page api response from GET hotels API calls
*/
public class HotelPage {
    
    int page;
    
    @SerializedName("per_page")
    int perPage;
    
    int total;
    
    @SerializedName("total_pages")
    int totalPages;
    
    List<Hotel> data;

    @Override
    public String toString() {
        return "\nHotelPage page:" + page + ",per_page:" + perPage + ",total:" + total + ",total_pages:" + totalPages + ",data:" + data;
    }
}