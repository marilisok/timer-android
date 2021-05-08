package ru.etu.timer.dto;

import org.json.JSONException;
import org.json.JSONObject;

import ru.etu.timer.utils.TimeContainer;

import java.time.LocalDateTime;
import java.util.Objects;

public class TimerData {
    private final String id;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final TimeContainer value;


    private TimerData(String id, LocalDateTime startDateTime, LocalDateTime endDateTime, TimeContainer value) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.value = value;
    }


    public TimeContainer getValue() {
        return value;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public String getId() {
        return id;
    }

    public String toJsonString() throws JSONException {
        JSONObject container = new JSONObject();
        container.put("id", id);
        container.put("start", startDateTime);
        container.put("end", endDateTime);
        container.put("value", value.toFormattedString());
        return container.toString();
    }

    public static TimerData fromJsonString(String jsonString) throws JSONException {
        JSONObject container = new JSONObject(jsonString);
        return new TimerData(container.getString("id"),
                LocalDateTime.parse(container.getString("start")),
                LocalDateTime.parse(container.getString("end")),
                TimeContainer.fromFormattedString(container.getString("value"))
        );
    }

    public static class Builder {
        private String id;
        private LocalDateTime startDateTime = LocalDateTime.now();
        private LocalDateTime endDateTime = LocalDateTime.now();
        private TimeContainer value = new TimeContainer(0);

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
            return this;
        }

        public Builder setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
            return this;
        }

        public Builder setValue(TimeContainer value) {
            this.value = value;
            return this;
        }

        public TimerData build() {
            Objects.requireNonNull(id);
            return new TimerData(id, startDateTime, endDateTime, value);
        }
    }

}
