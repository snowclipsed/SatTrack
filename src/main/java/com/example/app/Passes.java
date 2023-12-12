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
     * @param days Number of seconds into the future you need the API call for. 1 Second is current position.
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
