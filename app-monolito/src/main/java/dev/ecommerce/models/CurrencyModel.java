package dev.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyModel {

        @JsonProperty("code")
        private String code;

        @JsonProperty("codein")
        private String codein;

        @JsonProperty("name")
        private String name;

        @JsonProperty("high")
        private String high;

        @JsonProperty("low")
        private String low;

        @JsonProperty("varBid")
        private String varBid;

        @JsonProperty("pctChange")
        private String pctChange;

        @JsonProperty("bid")
        private String bid;

        @JsonProperty("ask")
        private String ask;

        @JsonProperty("timestamp")
        private String timestamp;

        @JsonProperty("create_date")
        private String createDate;

}
