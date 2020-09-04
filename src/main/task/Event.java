package main.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents events.
 * @author Joshua Liang XingYa
 * @author joshualiang.xy@gmail.com
 * @version v0.2
 * @since v0.1
 */
public class Event extends Task {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy, h:mma");
    private final LocalDateTime time;

    /**
     * Constructs an Event instance with the name of
     * task and the event time.
     * @param name the name of task.
     * @param time the event time.
     */
    public Event(String name, LocalDateTime time) {
        super(name);
        this.time = time;
    }

    /**
     * Constructs a Event instance with the name of
     * task, time of deadline and the done state of the task.
     * @param name the name of task.
     * @param time the event time.
     * @param isDone the done state of the task.
     */
    public Event(String name, String time, boolean isDone) {
        super(name, isDone);
        this.time = LocalDateTime.parse(time);
    }

    /**
     * Returns the string meant for writing to disk.
     * @return the string meant for writing to disk.
     */
    @Override
    public String write() {
        return String.format("E,%s%s", time, super.write());
    }

    @Override
    public String toString() {
        return String.format("[E]%s\n(at: %s)", super.toString(),
                time.format(FORMATTER));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            Event o = (Event) obj;
            return super.equals(o) && this.time.equals(o.time);
        }
        return false;
    }
}
