package ng.com.justjava.coached.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public abstract class Completable {

    private Boolean complete;
    private LocalDateTime lastActive = LocalDateTime.now();
    public Boolean getComplete() {
        return complete;
    }
    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public Date getLastActiveInDateFormat(){
        return Date.from(lastActive.atZone(ZoneId.systemDefault()).toInstant());
    }
}
