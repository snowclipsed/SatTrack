package com.example.app;

/**
 * Represents information about a satellite obtained from the N2YO API.
 * <p>
 * The PassInfo class encapsulates details related to a satellite, particularly information retrieved
 * from the N2YO API response. It includes public fields for the satellite name (satname), satellite ID
 * (satid), the number of transactions (transactionscount), and the number of passes (passescount) associated
 * with the satellite.
 * <p>
 * The class serves as a simple data structure to store and provide access to basic information about a satellite
 * in a concise manner. Typically, instances of this class are used in conjunction with other classes or methods
 * for processing and displaying information related to satellite passes.
 *
 * @see Passes
 * @see <a href="https://www.n2yo.com/api/#satellite-info">N2YO API - Satellite Information</a>
 */
public class PassInfo {

    public String satname;
    public Integer satid;
    public Integer transactionscount;

    public Integer passescount;
}
