package com.lawencon.jobportal.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.SortBy;
import com.lawencon.jobportal.model.request.SortByDirection;

@Component
public class PagingRequestArgumentResolver implements HandlerMethodArgumentResolver {

    @SuppressWarnings("null")
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PagingRequest.class.isAssignableFrom(parameter.getParameterType());
    }

    @SuppressWarnings("null")
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            org.springframework.web.bind.support.WebDataBinderFactory binderFactory)
            throws Exception {
        PagingRequest pagingRequest =
                PagingRequest.builder().page(Integer.parseInt(webRequest.getParameter("page")))
                        .pageSize(Integer.parseInt(webRequest.getParameter("pageSize"))).build();

        String sortByParam = webRequest.getParameter("sortBy");
        if (sortByParam != null) {
            List<SortBy> sortByList = new ArrayList<>();
            String[] sortFields = sortByParam.split(",");
            for (String field : sortFields) {
                String[] fieldParts = field.split(":");
                if (fieldParts.length == 2) {
                    sortByList.add(SortBy.builder().propertyName(fieldParts[0])
                            .direction(SortByDirection.valueOf(fieldParts[1].toUpperCase()))
                            .build());
                }
            }
            pagingRequest.setSortBy(sortByList);
        }

        return pagingRequest;
    }
}
