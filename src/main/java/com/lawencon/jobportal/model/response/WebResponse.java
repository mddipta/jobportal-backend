package com.lawencon.jobportal.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebResponse<T> {

    /**
     * Code , usually same as HTTP Code
     */
    @JsonProperty("code")
    private Integer code;

    /**
     * Status, usually same as HTTP status
     */
    @JsonProperty("status")
    private String status;

    /**
     * Dynamic Column
     */
    @JsonProperty("column")
    private List<CustomColumn> column;

    /**
     * Response data
     */
    @JsonProperty("data")
    private T data;

    /**
     * Paging information, if response is paginate data
     */
    @JsonProperty("paging")
    private PagingResponse paging;

    /**
     * Error information, if request is not valid
     */
    @JsonProperty("errors")
    private Map<String, List<String>> errors;

    /**
     * Metadata information
     */
    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("message")
    private String message;

    public static <T> WebResponseBuilder<T> builder() {
        return new WebResponseBuilder<T>();
    }

    public static class WebResponseBuilder<T> {
        private Integer code;
        private String status;
        private List<CustomColumn> column;
        private T data;
        private PagingResponse paging;
        private Map<String, List<String>> errors;
        private Map<String, Object> metadata;
        private String message;

        WebResponseBuilder() {}

        @JsonProperty("code")
        public WebResponseBuilder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        @JsonProperty("status")
        public WebResponseBuilder<T> status(String status) {
            this.status = status;
            return this;
        }

        @JsonProperty("column")
        public WebResponseBuilder<T> column(List<CustomColumn> column) {
            this.column = column;
            return this;
        }

        @JsonProperty("data")
        public WebResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        @JsonProperty("paging")
        public WebResponseBuilder<T> paging(PagingResponse paging) {
            this.paging = paging;
            return this;
        }

        @JsonProperty("errors")
        public WebResponseBuilder<T> errors(Map<String, List<String>> errors) {
            this.errors = errors;
            return this;
        }

        @JsonProperty("metadata")
        public WebResponseBuilder<T> metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        @JsonProperty("message")
        public WebResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public WebResponse<T> build() {
            return new WebResponse<T>(this.code, this.status, this.column, this.data, this.paging,
                    this.errors, this.metadata, this.message);
        }

        public String toString() {
            return "WebResponse.WebResponseBuilder(code=" + this.code + ", status=" + this.status
                    + ", column=" + this.column + ", data=" + this.data + ", paging=" + this.paging
                    + ", errors=" + this.errors + ", metadata=" + this.metadata + ", message="
                    + this.message + ")";
        }
    }
}
