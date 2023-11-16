package event;

import java.util.Set;

public class Event {
    private String name;
    private String date;
    private String id;
    private String description;
    private String location;
    private Set<String> subscribers;


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
    public String getDate() {
        return this.date;
    }

    /**
     * set field
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
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

}