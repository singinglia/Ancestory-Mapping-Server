package services;

import java.util.UUID;

public class BasicService {
    public String generateID(){
        return UUID.randomUUID().toString();
    }


}
