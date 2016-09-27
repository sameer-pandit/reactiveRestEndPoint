package com.example.reactive;

/**
 * Created by sameerpandit on 9/21/16.
 */

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Service
public class ImageService {

  public MappedCoordinates doSomething(Coordinates coordinates, long sleepDuration){
    try {
      Thread.sleep(sleepDuration);
    }catch (Exception e){

    }
    return new MappedCoordinates(coordinates.x,coordinates.y);
  }


}
