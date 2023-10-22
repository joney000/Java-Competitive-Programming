import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.UUID;

import com.google.gson.Gson;

/*
 * GET API: https://jsonmock.hackerrank.com/api/food_outlets?page={page_no}
 */

public class RestWithGson {
  final String BASE_URL = "https://jsonmock.hackerrank.com";
  final int TIMEOUT_MILLISEC = (int)1e3; // 1 sec timeout

  PriorityQueue<Hotel> getTopHotels(int top){
    PriorityQueue <Hotel> topHotels = new PriorityQueue<Hotel>(); 
    // MAX Bounded Queue of Size `top`
    int currentPage = 0;
    int maxPages = 1;
    // todo: currently flow is sequential one by one page
    // we can spawn new thread pool of size K to get data to reduce overall network time 
    // to MAX_PAGES/K
    while(currentPage < maxPages){
      HotelPage currentSetOfHotels = getCurrentPage(currentPage);
      maxPages = currentSetOfHotels.totalPages;
      if(currentSetOfHotels.data != null && currentSetOfHotels.data.size() == 0){
        System.out.println("empty data\n"); //todo: retry to get current page with exponential backoff
        break;
      }
      add(currentSetOfHotels.data, topHotels, top);
      currentPage++;
    }
    return topHotels;
  }

  // make a network get call to get the current page data
  // and add it to queue as rolling window
  HotelPage getCurrentPage(int page){
    String route = BASE_URL + "/api/food_outlets?page=" + page;
    try {
      URL url = new URL(route);
      HttpURLConnection connection =  (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("GET"); // anyway default it get but specifying it improves 
      // code readibility
      connection.setConnectTimeout(TIMEOUT_MILLISEC);
      if(connection.getResponseCode() == 200){
        BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line = null;
        while((line = response.readLine()) != null){
          responseBuilder.append(line);
        }
        return parseStringToCities(responseBuilder.toString());
      }
    } catch (Exception exception){
      System.out.println(exception);
      return null;
    }
    return null;
  }
  
  // deserialize text to object
  HotelPage parseStringToCities(String responseText){
    // using gson to cast into 
    Gson deserializer = new Gson();
    return deserializer.fromJson(responseText, HotelPage.class);
  }

  // adding to bounded queue
  // add time: O(log `top`), space: O(top)
  void add(List<Hotel> currentSetOfHotels, PriorityQueue<Hotel> topHotels, int top) {
    for(Hotel hotel: currentSetOfHotels){
      // to ensure heap having at least `top` elements
      if(topHotels.size() < top){
        topHotels.add(hotel);
        continue;
      }
      // re-balancing heap in log n
      topHotels.add(hotel);
      topHotels.poll(); // todo: we can maintain maxHeap instad of minHeap
      // and we can check top element in O(1) and avoid everytime rebalancing heap
      // although it does not impact time complexity but a very good perf improvement
    }
  }

  void run()throws Exception{
      PriorityQueue<Hotel> topHotels = getTopHotels(10);
      while(!topHotels.isEmpty()) {
        System.out.println("top hotel:" + topHotels.poll());
      }
  }

  public static void main(String[] args) throws Exception{
    RestWithGson driver = new RestWithGson();
    driver.run();
    driver.closeResources();
  }

  private void closeResources(){
    System.out.flush();
    // clear up class level resources like io, dbconnections etc.
  }
}