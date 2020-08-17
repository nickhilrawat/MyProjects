package com.programwithnickhil.restservice.RestApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.programwithnickhil.restservice.RestApp.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nikhil Rawat
 * @since August 17, 2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

  int statusCode; // 0 => SUCCESS, else FAILURE
  /** The payload. UI will be expecting data within this payload */
  private T payload;
  /** Status if the request (0-error AND 1-success) */
  private ResponseStatus status;
  /** Error message if any */
  private String errorMessage;
  /** The error details from body in case of Exception */
  private Object errors;

  public static <T> Response<T> success(T payload) {
    return new Response<>(0, payload, ResponseStatus.SUCCESS, null, null);
  }

  public static <T> Response<T> failure(int statusCode, String errorMessage, Object errors) {
    return new Response<>(statusCode, null, ResponseStatus.FAILURE, errorMessage, errors);
  }

  public static <T> Response<T> failure(int statusCode, String errorMessage) {
    return failure(statusCode, errorMessage, null);
  }

  public static <T> Response<T> failure(String errorMessage, Object errors) {
    return failure(1, errorMessage, errors);
  }

  public static <T> Response<T> failure(String errorMessage) {
    return failure(errorMessage, null);
  }

  public static <T> Response<T> notFound(String errorMessage) {
    return failure(404, errorMessage);
  }
}
