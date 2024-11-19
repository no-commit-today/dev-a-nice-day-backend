package com.devniceday.batch.domain;

public class ContentNotFoundException extends RuntimeException {

  public ContentNotFoundException(String message) {
    super(message);
  }
}
