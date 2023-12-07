package com.example.app;

/**
 * The {@code Positions} class represents a set of properties related to the position and orientation
 * of a satellite in orbital mechanics.
 *
 * <p>It includes information such as latitude, longitude, altitude, azimuth, elevation, right ascension,
 * declination, timestamp, and an indicator for whether the satellite is currently in eclipse.</p>
 *
 * <p>Note: All angular measurements (latitude, longitude, azimuth, elevation, ra, and dec) are represented
 * in degrees.</p>
 *
 * <p>The timestamp is expected to be in a format compatible with the standard UNIX timestamp (i.e., seconds
 * since 1970-01-01 00:00:00 UTC).</p>
 *
 * <p>The class provides a set of public fields for accessing satellite position and orientation properties:</p>
 *
 * <ul>
 *   <li>{@code satlatitude}: The latitude of the satellite in degrees.</li>
 *   <li>{@code satlongitude}: The longitude of the satellite in degrees.</li>
 *   <li>{@code sataltitude}: The altitude of the satellite in kilometers.</li>
 *   <li>{@code azimuth}: The azimuth angle of the satellite in degrees (angle measured clockwise from north).</li>
 *   <li>{@code elevation}: The elevation angle of the satellite in degrees (angle above the horizon).</li>
 *   <li>{@code ra}: The right ascension of the satellite in degrees (celestial equivalent of longitude).</li>
 *   <li>{@code dec}: The declination of the satellite in degrees (celestial equivalent of latitude).</li>
 *   <li>{@code timestamp}: The timestamp representing the time of the satellite position data.</li>
 *   <li>{@code eclipsed}: A boolean indicating whether the satellite is currently in eclipse (shadow of the Earth).</li>
 * </ul>
 *
 * <p>Instances of this class can be used to encapsulate and transfer satellite position and orientation from the API call in {@link Satellite} into a defined object that can be accessed.</p>
 *
 * <p>This class is not intended to perform calculations but rather to store and represent satellite position
 * properties.</p>
 *
 * @author Hardik Bishnoi
 * @version 1.4
 * @since 1.0
 */

public class Positions {

    public Float satlatitude;
    public Float satlongitude;
    public Float sataltitude;
    public Float azimuth;
    public Float elevation;
    public Float ra;
    public Float dec;
    public Float timestamp;
    public Boolean eclipsed;

}
