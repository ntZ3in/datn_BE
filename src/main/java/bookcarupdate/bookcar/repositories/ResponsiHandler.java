package bookcarupdate.bookcar.repositories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponsiHandler {
    public static ResponseEntity<Object> responsiBuider(String message, HttpStatus httpStatus, Object responsiObject){
        Map<String, Object> responsi = new HashMap<>();
        responsi.put("message", message);
        responsi.put("status", httpStatus);
        responsi.put("data", responsiObject);
        return new ResponseEntity<>(responsi, httpStatus);
    }
}
