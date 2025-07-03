package com.convicted.iplogger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class IPLoggerPlugin extends JavaPlugin implements Listener {

    private Connection connection;
    private String host, database, username, password;

    @Override
    public void onEnable() {
        // Load configuration
        getConfig().options().copyDefaults(true);
        saveConfig();

        // Initialize database connection
        host = getConfig().getString("database.host");
        database = getConfig().getString("database.name");
        username = getConfig().getString("database.username");
        password = getConfig().getString("database.password");

        connectToDatabase();

        // Register events
        getServer().getPluginManager().registerEvents(this, this);
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, username, password);
            getLogger().info("Connected to database!");
        } catch (SQLException e) {
            getLogger().warning("Failed to connect to database: " + e.getMessage());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        String playerIP = event.getPlayer().getAddress().getAddress().getHostAddress();
        UUID playerUUID = event.getPlayer().getUniqueId();

        // Check if IP and UUID already exist in the database
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM player_ips WHERE player_name=? AND ip_address=? AND uuid=?"
            );
            statement.setString(1, playerName);
            statement.setString(2, playerIP);
            statement.setString(3, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                // IP and UUID don't exist in the database, so insert them
                PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO player_ips (player_name, ip_address, uuid) VALUES (?, ?, ?)"
                );
                insertStatement.setString(1, playerName);
                insertStatement.setString(2, playerIP);
                insertStatement.setString(3, playerUUID.toString());
                insertStatement.executeUpdate();
                insertStatement.close();
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            getLogger().warning("Error checking database: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        // Disconnect from database
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                getLogger().warning("Error closing database connection: " + e.getMessage());
            }
        }
    }
}
