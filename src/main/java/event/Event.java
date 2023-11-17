package event;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Event {
    private String name;
    private Date date;
    private String id;

    private String details;

    private String venue;

    private String description;
    private String location;
    private Set<String> subscribers;

    public Event(){
        this.name = "";
        this.date = new Date();
        this.id = "";
        this.details = "";
        this.venue = "";
        this.subscribers = new HashSet<>();
    }
    public Event(String name, Date date, String details, String venue) {
        this.name = name;
        this.date = date;
        this.id = generateEventId();
        this.details = details;
        this.venue = venue;
        this.subscribers = new HashSet<>();
    }

    /**
     * get field
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * set field
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get field
     *
     * @return date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * get field
     *
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * set field
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return this.location;
    }

    /**
     * get field
     *
     * @return subscribers
     */
    public Set<String> getSubscribers() {
        return this.subscribers;
    }

    private String generateEventId() {
        return UUID.randomUUID().toString();
    }


    /**
     * get field
     *
     * @return details
     */
    public String getDetails() {
        return this.details;
    }

    /**
     * set field
     *
     * @param details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * get field
     *
     * @return venue
     */
    public String getVenue() {
        return this.venue;
    }

    /**
     * set field
     *
     * @param venue
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }
}