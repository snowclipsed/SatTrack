package com.example.app;

/**
 * Represents information about satellite pass positions obtained from the N2YO API.
 * <p>
 * The PassPositions class provides access to various attributes related to the positions and characteristics
 * of a satellite during its pass. It includes getter and setter methods for each attribute, allowing external
 * code to retrieve or update these values. The attributes encompass details such as azimuth, azimuth compass
 * direction, elevation, start time (in UTC), maximum azimuth, maximum azimuth compass direction, maximum elevation,
 * maximum elevation time (in UTC), end azimuth, end azimuth compass direction, end elevation, end time (in UTC),
 * magnitude, duration of the pass, and start visibility time (in UTC).
 * <p>
 *
 * This class serves as a data structure to store and manage information about satellite pass positions in a
 * structured manner for the Json Parser to retrieve it.
 * <p>
 * @see <a href="https://www.n2yo.com/api/#visualpasses">N2YO API - Visual Passes</a>
 */
public class PassPositions {
    public Float getStartAz() {
        return startAz;
    }

    public void setStartAz(Float startAz) {
        this.startAz = startAz;
    }

    public String getStartAzCompass() {
        return startAzCompass;
    }

    public void setStartAzCompass(String startAzCompass) {
        this.startAzCompass = startAzCompass;
    }

    public Float getStartEl() {
        return startEl;
    }

    public void setStartEl(Float startEl) {
        this.startEl = startEl;
    }

    public Long getStartUTC() {
        return startUTC;
    }

    public void setStartUTC(Long startUTC) {
        this.startUTC = startUTC;
    }

    public Float getMaxAz() {
        return maxAz;
    }

    public void setMaxAz(Float maxAz) {
        this.maxAz = maxAz;
    }

    public String getMaxAzCompass() {
        return maxAzCompass;
    }

    public void setMaxAzCompass(String maxAzCompass) {
        this.maxAzCompass = maxAzCompass;
    }

    public Float getMaxEl() {
        return maxEl;
    }

    public void setMaxEl(Float maxEl) {
        this.maxEl = maxEl;
    }

    public Long getMaxUTC() {
        return maxUTC;
    }

    public void setMaxUTC(Long maxUTC) {
        this.maxUTC = maxUTC;
    }

    public Float getEndAz() {
        return endAz;
    }

    public void setEndAz(Float endAz) {
        this.endAz = endAz;
    }

    public String getEndAzCompass() {
        return endAzCompass;
    }

    public void setEndAzCompass(String endAzCompass) {
        this.endAzCompass = endAzCompass;
    }

    public Float getEndEl() {
        return endEl;
    }

    public void setEndEl(Float endEl) {
        this.endEl = endEl;
    }

    public Long getEndUTC() {
        return endUTC;
    }

    public void setEndUTC(Long endUTC) {
        this.endUTC = endUTC;
    }

    public Float getMag() {
        return mag;
    }

    public void setMag(Float mag) {
        this.mag = mag;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

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

    public Long getStartVisibility() {
        return startVisibility;
    }

    public void setStartVisibility(Long startVisibility) {
        this.startVisibility = startVisibility;
    }

    public Long startVisibility;
}
