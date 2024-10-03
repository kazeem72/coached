// InitiateCheckoutResponse.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package ng.com.justjava.coached.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InitiateCheckoutResponse {

    private Data data;
    private String message;
    private boolean status;

    public Data getData() { return data; }
    public void setData(Data value) { this.data = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean value) { this.status = value; }

    public Data getFullData(){
        return getData();
    }


    public String getAccessCode(){
        return getData().getAccessCode();
    }

    @Override
    public String toString() {
        return "InitiateCheckoutResponse{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}

// PaymentData.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation


class Data {

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("authorization_url")
    private String authorizationurl;

    @JsonProperty("access_code")
    private String accessCode;

    public String getReference() { return reference; }
    public void setReference(String value) { this.reference = value; }

    public String getAuthorizationurl() { return authorizationurl; }
    public void setAuthorizationurl(String value) { this.authorizationurl = value; }

    public String getAccessCode() { return accessCode; }

    public void setAccessCode(String value) { this.accessCode = value; }

    @Override
    public String toString() {
        return "PaymentData{" +
                "reference='" + reference + '\'' +
                ", authorizationurl='" + authorizationurl + '\'' +
                ", accessCode='" + accessCode + '\'' +
                '}';
    }
}
