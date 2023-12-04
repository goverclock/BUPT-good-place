package org.example.service;

import org.example.work.Welcome;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface WelcomeService {
      List<Welcome> query_response_by_requestid( String request_id);

      String add_response(String request_id, String user_id, String description,List<MultipartFile> files) throws Exception;

      Welcome query_by_response_id(String response_id);

      int response_update(String response_id, String description, List<MultipartFile> files) throws Exception;

     int response_delete(String response_id) throws Exception;

    int update_state(String response_id);
}
