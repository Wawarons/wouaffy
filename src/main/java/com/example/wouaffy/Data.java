package com.example.wouaffy;

import java.util.List;

public class Data<T> {

  private List<T> data;

  public Data(List<T> data) {
    this.data = data;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

}
