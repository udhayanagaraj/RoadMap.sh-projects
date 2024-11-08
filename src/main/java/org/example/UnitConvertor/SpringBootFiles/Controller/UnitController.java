package com.unitconvertor.demo.Controller;

import com.unitconvertor.demo.Model.DataModel;
import com.unitconvertor.demo.Service.LengthService;
import com.unitconvertor.demo.Service.TemperatureService;
import com.unitconvertor.demo.Service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UnitController {
    @Autowired
    LengthService unitService;
    @Autowired
    WeightService weightService;
    @Autowired
    TemperatureService temperatureService;

    @PostMapping(value = "/length")
    public ResponseEntity<?> getLength(@RequestBody DataModel data){

        try{
            if(data.getValue() == 0){
                return new ResponseEntity<>("Length value must be greater than zero", HttpStatus.BAD_REQUEST);
            }
            double result = unitService.convertLength(data);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/weight")
    public ResponseEntity<?> getWeight(@RequestBody DataModel data){

        try{
            if(data.getValue() == 0){
                return new ResponseEntity<>("Weight value must be greater than zero", HttpStatus.BAD_REQUEST);
            }
            double result = weightService.convertWeight(data);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/temperature")
    public ResponseEntity<?> getTemperature(@RequestBody DataModel data){

        try{
            if(data == null){
                return new ResponseEntity<>("Enter a valid data", HttpStatus.BAD_REQUEST);
            }
            double result = temperatureService.convertTemperature(data);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
