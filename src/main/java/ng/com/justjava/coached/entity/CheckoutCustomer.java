package ng.com.justjava.coached.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CheckoutCustomer {
    private String amount;
    private String email;

    @JsonProperty("callback_url")
    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    @JsonProperty("callback_url")
    private String callBack;

    public Map getMetadata() {
        return metadata;
    }

    public void setMetadata(Map metadata){
        this.metadata=metadata;
    }

    private Map metadata;

    public String getAmount() { return amount; }
    public void setAmount(String value) { this.amount = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }
}
