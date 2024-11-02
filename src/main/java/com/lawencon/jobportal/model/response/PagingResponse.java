package com.lawencon.jobportal.model.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lawencon.jobportal.model.request.SortBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagingResponse {
    private Integer page;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalItem;
    private List<SortBy> sortBy;
}
