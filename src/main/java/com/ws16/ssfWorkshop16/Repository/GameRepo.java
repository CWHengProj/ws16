package com.ws16.ssfWorkshop16.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ws16.ssfWorkshop16.Constants.Constant;
import com.ws16.ssfWorkshop16.Model.Game;


@Repository
public class GameRepo {
    @Autowired
    @Qualifier(Constant.template01)
    RedisTemplate<String,Object> template;

    
        public void addGame(String gameId,Game game) throws JsonProcessingException {
            template.opsForHash().put("game", gameId, game);        
        }


        public Game getBoardGame(String id) {
            Game game = (Game) template.opsForHash().get("game", id);
            return game;
        }

        public Boolean idExists(String id){
            return template.opsForHash().hasKey("game",id);
        }

        public void updateBoardGame(String gameId,Game game) throws JsonProcessingException {
            template.opsForHash().delete("game", gameId);
            template.opsForHash().put("game", gameId, game);        
        }
        
    
}
