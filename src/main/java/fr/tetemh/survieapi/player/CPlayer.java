package fr.tetemh.survieapi.player;

import fr.tetemh.survieapi.SurvieApi;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class CPlayer {

    private UUID uuid;
    private String name;
    private double money;
    private String jobName;

    public CPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.money = 500;
    }

    public UUID getUuid() {
        return uuid;
    }
    public String getName() {
        return name;
    }
    public double getMoney() {
        return money;
    }
    public String getJobName() {
        return jobName;
    }
    public Player getBukkitPlayer(){
        return SurvieApi.getInstance().getServer().getPlayerExact(this.name);
    }


    public CPlayer setMoney(double amount) {
        this.money = amount;
        return this;
    }
    public CPlayer addMoney(double amount){
        this.money = this.money + amount;
        return this;
    }
    public boolean removeMoney(double amount){
        if(this.money - amount >= 0){
            this.money = this.money - amount;
            return true;
        }else{
            return false;
        }
    }

    public CPlayer setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public void save(){
        try {
            Connection connection = SurvieApi.getLoader().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player_data (uuid, name, money, jobName) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE money = ?, jobName = ?");
            preparedStatement.setString(1, this.uuid.toString());
            preparedStatement.setString(2, this.name);
            preparedStatement.setDouble(3, this.money);
            preparedStatement.setString(4, this.jobName);
            preparedStatement.setDouble(5, this.money);
            preparedStatement.setString(6, this.jobName);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
