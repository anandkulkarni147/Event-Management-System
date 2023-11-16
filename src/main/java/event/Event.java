package event;

import java.util.Date;
import java.util.Set;

public class Event {
    private String name;
    private Date date;
    private String id;
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
    public Date getDate() {
        return this.date;
    }

    /**
     * set field
     *
     * @param date
     */
    public void setDate(Date date) {
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

    /**
     * get field
     *
     * @return subscribers
     */
    public Set<String> getSubscribers() {
        return this.subscribers;
    }

}