package fr.jl.todolist;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Task class represents a task in the ToDo List application.
 * It implements Serializable to allow instances of this class to be passed between activities.
 */
public class Task implements Serializable {
    private String title;
    private String description;
    private String context;
    private String webLink;
    private Date date;
    private int duration;

    // Static date format to be used for parsing and formatting dates
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor to initialize a new Task object with the provided details.
     * @param title The title of the task.
     * @param description The description of the task.
     * @param duration The duration of the task in minutes.
     * @param dateString The date of the task as a string.
     * @param context The context or category of the task.
     */
    public Task(String title, String description, int duration, String dateString, String context, String webLink) {
        this.title = title;
        this.description = description;
        this.context = context;
        this.webLink = webLink;
        this.duration = duration;
        try {
            this.date = dateFormat.parse(dateString);
        } catch (Exception e) {
            this.date = new Date(); // default to current time if parsing fails
        }
    }

    /**
     * Gets the title of the task.
     * @return The title of the task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the task.
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the duration of the task in minutes.
     * @return The duration of the task.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the date of the task.
     * @return The date of the task.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the date of the task formatted as a string.
     * @return The formatted date string.
     */
    public String getFormattedDate() {
        return dateFormat.format(date);
    }

    /**
     * Gets the context or category of the task.
     * @return The context of the task.
     */
    public String getContext() {
        return context;
    }

    /**
     * Gets the web link of the task.
     * @return The web link of the task.
     */
    public String getWebLink() {
        return webLink;
    }

    /**
     * Sets the title of the task.
     * @param title The new title of the task.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the task.
     * @param description The new description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the duration of the task in minutes.
     * @param duration The new duration of the task.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Sets the date of the task.
     * @param date The new date of the task.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the context or category of the task.
     * @param context The new context of the task.
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * Sets the web link of the task.
     * @param webLink The new web link of the task.
     */
    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }
}
