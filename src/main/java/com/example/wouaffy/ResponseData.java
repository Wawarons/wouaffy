package com.example.wouaffy;

public class ResponseData {
  private int code;
  private String message;
  private Data<?> data;

  // Constructors
  public ResponseData(int code, String message, Data<?> data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public ResponseData(Builder builder) {
    this.code = builder.code;
    this.message = builder.message;
    this.data = builder.data;
  }

  public ResponseData(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public ResponseData(int code) {
    this.code = code;
  }

  public ResponseData() {
  };

  // Getters & Setters

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Data<?> getData() {
    return data;
  }

  public void setData(Data<?> data) {
    this.data = data;
  }

  public static class Builder {
    private int code;
    private String message;
    private Data<?> data;

    public Builder setCode(int code) {
      this.code = code;
      return this;
    }

    public Builder setMessage(String message) {
      this.message = message;
      return this;
    }

    public Builder setData(Data<?> data) {
      this.data = data;
      return this;
    }

    public ResponseData build() {
      return new ResponseData(this);
    }

  }

}
