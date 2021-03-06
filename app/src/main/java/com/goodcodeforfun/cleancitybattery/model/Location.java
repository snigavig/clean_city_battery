package com.goodcodeforfun.cleancitybattery.model;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "Locations", id = BaseColumns._ID)
public class Location extends Model {
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_LATITUDE = "Latitude";
    public static final String COLUMN_LONGTITUDE = "Longtitude";
    public static final String COLUMN_API_ID = "ApiId";
    public static final String COLUMN_PHOTOS = "Photos";
    private static final String COLUMN_TYPE = "Type";
    private static final String COLUMN_ADDRESS = "Address";
    @SerializedName("_id")
    @Expose
    @Column(name = COLUMN_API_ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String apiId = "";
    @Expose
    private User user;
    @SerializedName("__v")
    @Expose
    private int V;
    @Expose
    private String created;
    @Expose
    @Column(name = COLUMN_LATITUDE)
    private double latitude;
    @Expose
    @Column(name = COLUMN_LONGTITUDE)
    private double longitude;
    @Expose
    @Column(name = COLUMN_NAME)
    private String name;
    @Expose
    @Column(name = COLUMN_TYPE)
    private String type;
    @Expose
    @Column(name = COLUMN_ADDRESS)
    private String address;
    @SerializedName("photos")
    @Expose
    @Column(name = COLUMN_PHOTOS)
    private String[] photos;

    public Location() {
        super();
    }

    /**
     *
     * @return
     * The photos
     */
    public String[] getPhotos() {
        return photos;
    }

    /**
     *
     * @param photos
     * The photos
     */
    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public Location withPhotos(String[] photos) {
        this.photos = photos;
        return this;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public Location withAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public Location withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return The Id
     */
    public String getApiId() {
        return apiId;
    }

    /**
     * @param Id The _id
     */
    public void setApiId(String Id) {
        this.apiId = Id;
    }

    public Location withId(String apiId) {
        this.apiId = apiId;
        return this;
    }

    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public Location withUser(User user) {
        this.user = user;
        return this;
    }

    /**
     * @return The V
     */
    public int getV() {
        return V;
    }

    /**
     * @param V The __v
     */
    public void setV(int V) {
        this.V = V;
    }

    public Location withV(int V) {
        this.V = V;
        return this;
    }

    /**
     * @return The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    public Location withCreated(String created) {
        this.created = created;
        return this;
    }

    /**
     * @return The latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Location withLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    /**
     * @return The longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Location withLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Location withName(String name) {
        this.name = name;
        return this;
    }
}