package com.example.app;



import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Satellite{
    public Float satlat;
    public Float satlong;
    public String satname;

    /**
     * Makes an API call to @see <a href = "https://www.n2yo.com/api/"> N2YO API </a>
     *
     * <br>
     * It first utilizes a {@link HttpRequest} to formulate a GET call.
     * <br>
     * Then, it instantiates an {@link HttpClient} which GETs an {@link HttpResponse} and feeds the response into a String.
     * Then, we utilize a {@link ObjectMapper} to access the longitude and latitude values of the satellite at current time and
     * feed it to the variables.
     *
     * @param satID NORAD ID of satellite
     * @param sec Number of seconds into the future you need the API call for. 1 Second is current position.
     * @param obsLat Observer's latitude. Default set to The Roux Institute {@link App#satAPICall(Integer)}
     * @param obsLong Observer's longitude. Default set to The Roux Institute {@link App#satAPICall(Integer)}
     * @param APIKEY User's N2YO APIKEY handler. Takes APIKEY from {@link Constants#N2YOAPI}
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     *
     * @author Hardik Bishnoi
     * @version 1.4
     * @since 1.0
     */
    Satellite(String satID, String sec, String obsLat, String obsLong, String APIKEY) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.n2yo.com/rest/v1/satellite/positions/" + satID + "/" + obsLat + "/" + obsLong + "/0/" + sec + "/&apiKey="+ APIKEY))
                .GET()
                .build();


        HttpClient httpClient = HttpClient.newHttpClient();


        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
//        Uncomment the line below in case you need to check if we're getting the HTTP response or not.
//        System.out.println(getResponse.body());

        ObjectMapper mapper = new ObjectMapper();

        Request request = mapper.readValue(getResponse.body(), Request.class);

        this.satname = request.info.satname;
        this.satlong = request.positions.get(0).satlongitude;
        this.satlat = request.positions.get(0).satlatitude;

    }
}
