package com.example.reactive;

/**
 * Created by sameerpandit on 9/21/16.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;


import java.time.Duration;
import java.util.List;

@RestController
public class ImageServiceController {
  private ImageService imageService;
  private static long duration = 1000;

  @Autowired
  public ImageServiceController(ImageService imageService) {
    this.imageService = imageService;
  }

  @RequestMapping(path = "/txPoint", method = RequestMethod.POST)
  public Flux<MappedCoordinates> transform(@RequestBody Coordinates point){
    //return Flux.fromArray(new String[] {"1","2","3"}).delayMillis(500);
    return  Flux.just(imageService.doSomething(point,duration)).log();
  }

  @RequestMapping(path = "/txPointTraditional", method = RequestMethod.POST)
  public Flux<MappedCoordinates> transformNew(@RequestBody Flux<Coordinates> point){
    //return Mono.just(imageService.doSomething(point,1));
    return  point.map((c)->imageService.doSomething(c,duration));
  }
  
  @RequestMapping(path = "/txPointTraditionalSse", method = RequestMethod.GET)
  public Flux<MappedCoordinates> transformSse(){
    //return Mono.just(imageService.doSomething(point,1));
    return Flux.interval(Duration.ofSeconds(1))
            .map(l -> imageService.doSomething(new Coordinates(4,5),duration));

  }

  @RequestMapping(path = "/txPointMono", method = RequestMethod.POST)
  public Mono<MappedCoordinates> transformNew(@RequestBody Coordinates point){
    //return Mono.just(imageService.doSomething(point,1));
    return  Mono.just(imageService.doSomething(point,duration)).log();
  }

  @RequestMapping(path = "/txPointGet/{x}/{y}", method = RequestMethod.GET)
  public Mono<MappedCoordinates> transformNew(){
    //return Mono.just(imageService.doSomething(point,1));
    return  Mono.just(imageService.doSomething(new Coordinates(4,5),duration)).log();
  }

  @RequestMapping(path = "/sleep/{dur}", method = RequestMethod.GET)
  public String setSleepTime(@PathVariable("dur") long duration){
    this.duration = duration;
    return "set sleep:" + duration;
  }
}
