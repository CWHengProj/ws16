package com.ws16.ssfWorkshop16.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ws16.ssfWorkshop16.Model.Game;
import com.ws16.ssfWorkshop16.Repository.GameRepo;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.json.JsonReader;
import jakarta.json.JsonObject;

@Service
public class GameService {
    @Autowired
    GameRepo gameRepo;
    public Integer count =0;
    public void storeToDB(String fileName) throws IOException {
        //to store the game.json into redis db.
        ClassPathResource resource = new ClassPathResource(fileName);
        InputStream is =resource.getInputStream();
        JsonReader jr = Json.createReader(is);
        JsonArray jArray = jr.readArray();
        for (JsonValue jval: jArray){
            JsonObject jObj = (JsonObject) jval;
            Game game = stringToObject(jObj);
            String id = game.getGid().toString();
            gameRepo.addGame(id, game);
        }
    
    }

    public Game returnBoardGame(String id) {
        return gameRepo.getBoardGame(id);
    }

    public ResponseEntity<String> updateBoardGame(String id, Boolean upsert, String gameData) throws JsonProcessingException {
        JsonReader jReader = Json.createReader(new StringReader(gameData));
        JsonObject gameObject = jReader.readObject();
        Game game = stringToObject(gameObject);
        if(gameRepo.idExists(id)){
            gameRepo.updateBoardGame(id,game);
            JsonObject payload = Json.createObjectBuilder()
                                    .add("update_count",++count)
                                    .add("id",id)
                                    .build();
            return ResponseEntity.status(200).body(payload.toString());
        }
        else if(upsert){
            gameRepo.addGame(id,game);
            JsonObject payload = Json.createObjectBuilder()
                                    .add("insert_count", ++count)
                                    .add("id", id)
                                    .build();

            return ResponseEntity.status(201).body(payload.toString());

        }
        return ResponseEntity.status(404).body("Board game with ID: "+id+" not found." );
    }
    public static Game stringToObject(JsonObject jObj){
            //Use JsonObject methods to obtain the json values
            Integer id = jObj.getInt("gid");
            String name = jObj.getString("name");
            Integer year =jObj.getInt("year");
            Integer ranking = jObj.getInt("ranking");
            Integer userRating = jObj.getInt("users_rated");
            String url = jObj.getString("url");
            String image = jObj.getString("image");
            //Add the values to Game
            Game game = new Game(id, name, year, ranking, userRating, url, image);
            return game;
    }
}
