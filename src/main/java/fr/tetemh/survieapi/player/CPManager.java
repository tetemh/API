package fr.tetemh.survieapi.player;

import fr.tetemh.survieapi.SurvieApi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CPManager {

    private static CPManager instance;
    public CPManager(){
        instance = this;
    }
    public static CPManager getInstance() {
        return instance;
    }

    private Map<UUID, CPlayer> players = new HashMap<>();

    public Map<UUID, CPlayer> getPlayers() {
        return players;
    }
    public void addCPlayer(CPlayer cPlayer){
        this.players.put(cPlayer.getUuid(), cPlayer);
    }
    public void removeCPlayer(UUID uuid){
        this.players.remove(uuid);
    }

    public void save(){
        this.players.values().forEach(CPlayer::save);
    }
    public void init(){
        try {
            Connection connection = SurvieApi.getLoader().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM player_data");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                addCPlayer(new CPlayer(UUID.fromString(resultSet.getString("uuid")), resultSet.getString("name")).setMoney(resultSet.getDouble("money")).setJobName(resultSet.getString("jobName")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
