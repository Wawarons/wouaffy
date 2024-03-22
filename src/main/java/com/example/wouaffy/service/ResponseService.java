package com.example.wouaffy.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.wouaffy.ResponseData;

@Service
public class ResponseService {

  public ResponseEntity<ResponseData> createResponse(int code, String message) {
    ResponseData responseData = new ResponseData();
    responseData.setCode(code);
    responseData.setMessage(message);
    return ResponseEntity.status(code).body(responseData);
  }
}
