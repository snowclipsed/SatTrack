package com.example.app;



import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Passes{
    public Float satlat;
    public Float satlong;
    public String satname;

    public Float startAz;
    public String startAzCompass;
    public Float startEl;
    public Long startUTC;
    public Float maxAz;
    public String maxAzCompass;
    public Float maxEl;
    public Long maxUTC;
    public Float endAz;
    public String endAzCompass;
    public Float endEl;
    public Long endUTC;
    public Float mag;
    public Float duration;

    /**
     * Constructs a Passes object by making an API call to retrieve satellite pass information.
     * <p>
     * This constructor initializes a Passes object by sending an HTTP GET request to the N2YO API
     * using the specified parameters, including the satellite ID (satID), observation latitude (obsLat),
     * observation longitude (obsLong), and API key (APIKEY). The API call is made to obtain visual passes
     * information for the given satellite as observed from the specified location.
     * <p>
     * The obtained JSON response from the API is then parsed using the Jackson ObjectMapper, and the relevant
     * information is extracted and assigned to the fields of the Passes object, including satellite name, pass
     * duration, start time, azimuth, azimuth compass direction, elevation, maximum elevation time, maximum azimuth,
     * maximum azimuth compass direction, maximum elevation, end time, end azimuth, end azimuth compass direction,
     * and end elevation.
     *
     *
     * @param satID   The NORAD satellite ID for which pass information is requested.
     * @param days    The number of days for which pass information is requested.
     * @param obsLat  The observer's latitude for the pass calculation.
     * @param obsLong The observer's longitude for the pass calculation.
     * @param APIKEY  The API key required for accessing the N2YO API.
     * @throws URISyntaxException If there is an issue with the URI syntax while creating the HTTP request.
     * @throws IOException        If there is an issue with the IO operations while making the API call.
     * @throws InterruptedException If the execution is interrupted during the API call.
     * @see <a href="https://www.n2yo.com/api/#visualpasses">N2YO API - Visual Passes</a>
     * @see HttpRequest
     * @see HttpClient
     * @see HttpResponse
     * @see ObjectMapper
     * @see PassRequest
     *
     * @author Hardik Bishnoi
     * @since 1.0
     */
    Passes(String satID, String days, String obsLat, String obsLong, String APIKEY) throws URISyntaxException, IOException, InterruptedException {
        String requesturi = "https://api.n2yo.com/rest/v1/satellite/visualpasses/" + satID + "/" + obsLat + "/" + obsLong + "/0/" + days + "/60" + "/&apiKey="+ APIKEY;
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(requesturi))
                .GET()
                .build();


        HttpClient httpClient = HttpClient.newHttpClient();


        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
//        Uncomment the line below in case you need to check if we're getting the HTTP response or not.
        System.out.println(requesturi);
        System.out.println(getResponse.body());

        ObjectMapper mapper = new ObjectMapper();

        PassRequest request = mapper.readValue(getResponse.body(), PassRequest.class);

        this.satname = request.info.satname;
        this.duration = request.passes.get(0).duration;
        this.startUTC = request.passes.get(0).startUTC;
        this.startAz = request.passes.get(0).startAz;
        this.startAzCompass = request.passes.get(0).startAzCompass;
        this.startEl = request.passes.get(0).startEl;
        this.maxUTC = request.passes.get(0).maxUTC;
        this.maxAz = request.passes.get(0).maxAz;
        this.maxAzCompass = request.passes.get(0).maxAzCompass;
        this.maxEl = request.passes.get(0).maxEl;
        this.endUTC = request.passes.get(0).endUTC;
        this.endAz = request.passes.get(0).endAz;
        this.endAzCompass = request.passes.get(0).endAzCompass;
        this.endEl = request.passes.get(0).endEl;
    }
}
