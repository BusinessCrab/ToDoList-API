package com.business_crab.ToDoList.API.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse<T> {
    private T data;
    private String message;
    private int page;
    private int limit;
    private long total;
}