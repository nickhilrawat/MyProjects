package com.programwithnickhil.restservice.RestApp.enums;

import lombok.NonNull;

/**
 * @author Nikhil Rawat
 * @since August 16, 2020
 */
public enum ResponseStatus {
  SUCCESS,
  FAILURE;

  @NonNull
  public static ResponseStatus getRequestStatus(@NonNull final String val) {
    for (ResponseStatus responseStatus : ResponseStatus.values()) {
      if (responseStatus.name().equalsIgnoreCase(val)) {
        return responseStatus;
      }
    }
    return ResponseStatus.FAILURE;
  }
}
