package com.ws16.ssfWorkshop16.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ws16.ssfWorkshop16.Constants.Constant;
import com.ws16.ssfWorkshop16.Model.Game;
import com.ws16.ssfWorkshop16.Service.GameService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("api/boardgame")
public class GameController {

    @Autowired
    GameService gameService;
    //hardcoded just for game.json
    @PostMapping("/loadAllGames")
    public ResponseEntity<String> storeGames() throws IOException {
        try{
            gameService.storeToDB("game.json");
            return ResponseEntity.status(200).header("Content-Type","application/json").body("Loaded board games onto Redis.");
        }
        catch(FileNotFoundException e){
            return ResponseEntity.status(404).header("Content-Type","application/json").body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(500).header("Content-Type","application/json").body("Error! " + e.getMessage());
        }
    }
    //task1
    @PostMapping("/addGame/{fileName}")
    public ResponseEntity<String> addGame(@PathVariable String fileName) {
        //add a game entry - to catch if the format is not json
        try{
            gameService.storeToDB(fileName);
            JsonObject jsonResponse = Json.createObjectBuilder()
                                            .add("insert_count",1)
                                            .add("id",Constant.redisKey)
                                            .build();
            return ResponseEntity.status(201).header("Content-Type","application/json").body(jsonResponse.toString());
        }   
        catch (FileNotFoundException e){
            return ResponseEntity.status(404).header("Content-Type","application/json").body("File at "+fileName+" cannot be found.");
        }
        catch(Exception e){
            return ResponseEntity.status(500).header("Content-Type","application/json").body("Error! :"+e.getMessage());
        }
    }
    //task2
    @GetMapping("/{id}")
    public ResponseEntity<String> getMethodName(@PathVariable("id") String id) {
        try{
            Game game =gameService.returnBoardGame(id);
            JsonObject jsonResponse = Json.createObjectBuilder()
                                        .add("gid",game.getGid())
                                        .add("name",game.getName())
                                        .add("year",game.getYear())
                                        .add("ranking",game.getRanking())
                                        .add("users_rated",game.getUsers_rated())
                                        .add("url",game.getUrl())
                                        .add("image",game.getImage())
                                        .build();
            return ResponseEntity.status(200).header("Content-Type","application/json").body(jsonResponse.toString());
        }
        catch (Exception e){
            return ResponseEntity.status(404).header("Content-Type","application/json").body("The game ID that you have requested does not exist.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBoardGame(@PathVariable("id") String id, @RequestParam(required=false, defaultValue = "false") Boolean upsert, @RequestBody String gameData) {
        //task3 - take payload from request body, update data corresponding to the id
        try{
            return gameService.updateBoardGame(id,upsert,gameData);
        }   
        catch(Exception e){ 
            return ResponseEntity.status(500).header("Content-Type","application/json").body("Error! :"+e.getMessage());
        //count is the number of documents updated
        }
    }    
    
    
    
    
    

    
}
