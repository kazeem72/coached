// CheckoutPaymentResponse.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package ng.com.justjava.coached.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Map;

public class CheckoutPaymentResponse {

    @JsonProperty("data")
    private PaymentData data;
    private String event;

    public PaymentData getData() {

        return data;
    }
    public void setData(PaymentData value) { this.data = value; }

    public String getEvent() { return event; }
    public void setEvent(String value) { this.event = value; }

    public Map getCutomData(){
        return data.getMetadata();
    }
    @Override
    public String toString() {
        return "CheckoutPaymentResponse{" +
                "data=" + data +
                ", event='" + event + '\'' +
                '}';
    }
}

// PaymentData.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation




class PaymentData {
    private Map metadata;
    private long fees;
    private String channel;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;
    private Source source;

    @JsonProperty("gateway_response")
    private String gatewayResponse;


    @JsonProperty("requested_amount")
    private long requestedAmount;
    private String reference;
    private Authorization authorization;
    private Plan split;
    private String currency;
    private long id;
    private Plan plan;
    private long amount;
    private Plan subaccount;
    private String ipAddress;
    private OffsetDateTime dataPaidAt;
    private String domain;
    private OffsetDateTime paidAt;
    private String status;
    private Customer customer;

    public Map getMetadata() { return metadata; }
    public void setMetadata(Map value) { this.metadata = value; }

    public long getFees() { return fees; }
    public void setFees(long value) { this.fees = value; }

    public String getChannel() { return channel; }
    public void setChannel(String value) { this.channel = value; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

    public Source getSource() { return source; }
    public void setSource(Source value) { this.source = value; }

    public String getGatewayResponse() { return gatewayResponse; }
    public void setGatewayResponse(String value) { this.gatewayResponse = value; }

    public long getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(long value) { this.requestedAmount = value; }

    public String getReference() { return reference; }
    public void setReference(String value) { this.reference = value; }

    public Authorization getAuthorization() { return authorization; }
    public void setAuthorization(Authorization value) { this.authorization = value; }

    public Plan getSplit() { return split; }
    public void setSplit(Plan value) { this.split = value; }

    public String getCurrency() { return currency; }
    public void setCurrency(String value) { this.currency = value; }

    public long getid() { return id; }
    public void setid(long value) { this.id = value; }

    public Plan getPlan() { return plan; }
    public void setPlan(Plan value) { this.plan = value; }

    public long getAmount() { return amount; }
    public void setAmount(long value) { this.amount = value; }

    public Plan getSubaccount() { return subaccount; }
    public void setSubaccount(Plan value) { this.subaccount = value; }

    public String getipAddress() { return ipAddress; }
    public void setipAddress(String value) { this.ipAddress = value; }

    public OffsetDateTime getDataPaidAt() { return dataPaidAt; }
    public void setDataPaidAt(OffsetDateTime value) { this.dataPaidAt = value; }

    public String getDomain() { return domain; }
    public void setDomain(String value) { this.domain = value; }

    public OffsetDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(OffsetDateTime value) { this.paidAt = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer value) { this.customer = value; }

    @Override
    public String toString() {
        return "PaymentData{" +
                "metadata='" + metadata + '\'' +
                ", fees=" + fees +
                ", channel='" + channel + '\'' +
                ", createdAt=" + createdAt +
                ", source=" + source +
                ", gatewayResponse='" + gatewayResponse + '\'' +
                ", requestedAmount=" + requestedAmount +
                ", reference='" + reference + '\'' +
                ", authorization=" + authorization +
                ", split=" + split +
                ", currency='" + currency + '\'' +
                ", id=" + id +
                ", plan=" + plan +
                ", amount=" + amount +
                ", subaccount=" + subaccount +
                ", ipAddress='" + ipAddress + '\'' +
                ", dataPaidAt=" + dataPaidAt +
                ", domain='" + domain + '\'' +
                ", paidAt=" + paidAt +
                ", status='" + status + '\'' +
                ", customer=" + customer +
                '}';
    }
}

// Authorization.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Authorization {
    private String last4;
    private String signature;
    private String bin;
    private String channel;
    @JsonProperty("exp_month")
    private String expMonth;

    @JsonProperty("exp_year")
    private String expYear;

    @JsonProperty("card_type")
    private String cardType;
    private boolean reusable;

    @JsonProperty("country_code")
    private String countryCode;
    private String bank;

    @JsonProperty("authorization_code")
    private String authorizationCode;
    private String brand;

    public String getLast4() { return last4; }
    public void setLast4(String value) { this.last4 = value; }

    public String getSignature() { return signature; }
    public void setSignature(String value) { this.signature = value; }

    public String getBin() { return bin; }
    public void setBin(String value) { this.bin = value; }

    public String getChannel() { return channel; }
    public void setChannel(String value) { this.channel = value; }

    public String getExpMonth() { return expMonth; }
    public void setExpMonth(String value) { this.expMonth = value; }

    public String getExpYear() { return expYear; }
    public void setExpYear(String value) { this.expYear = value; }

    public String getCardType() { return cardType; }
    public void setCardType(String value) { this.cardType = value; }

    public boolean getReusable() { return reusable; }
    public void setReusable(boolean value) { this.reusable = value; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String value) { this.countryCode = value; }

    public String getBank() { return bank; }
    public void setBank(String value) { this.bank = value; }

    public String getAuthorizationCode() { return authorizationCode; }
    public void setAuthorizationCode(String value) { this.authorizationCode = value; }

    public String getBrand() { return brand; }
    public void setBrand(String value) { this.brand = value; }

    @Override
    public String toString() {
        return "Authorization{" +
                "last4='" + last4 + '\'' +
                ", signature='" + signature + '\'' +
                ", bin='" + bin + '\'' +
                ", channel='" + channel + '\'' +
                ", expMonth='" + expMonth + '\'' +
                ", expYear='" + expYear + '\'' +
                ", cardType='" + cardType + '\'' +
                ", reusable=" + reusable +
                ", countryCode='" + countryCode + '\'' +
                ", bank='" + bank + '\'' +
                ", authorizationCode='" + authorizationCode + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}

// Customer.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Customer {
    private String riskAction;
    private long id;
    private String customerCode;
    private String email;

    public String getRiskAction() { return riskAction; }
    public void setRiskAction(String value) { this.riskAction = value; }

    public long getid() { return id; }
    public void setid(long value) { this.id = value; }

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String value) { this.customerCode = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    @Override
    public String toString() {
        return "Customer{" +
                "riskAction='" + riskAction + '\'' +
                ", id=" + id +
                ", customerCode='" + customerCode + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

// Plan.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Plan {

}

// Source.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

class Source {
    private String source;
    private String type;
    private String entryPoint;

    public String getSource() { return source; }
    public void setSource(String value) { this.source = value; }

    public String getType() { return type; }
    public void setType(String value) { this.type = value; }

    public String getEntryPoint() { return entryPoint; }
    public void setEntryPoint(String value) { this.entryPoint = value; }

    @Override
    public String toString() {
        return "Source{" +
                "source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", entryPoint='" + entryPoint + '\'' +
                '}';
    }
}
