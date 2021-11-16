package com.github.hazork.adventurepass.data.sql;

import static com.github.hazork.adventurepass.AdventurePlugin.getPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLite {

  private static final File file = new File(
    getPlugin().getDataFolder().getPath(),
    "database.db"
  );
  private static final String url = "jdbc:sqlite:" + file.getAbsolutePath();
  private static final String table =
    "CREATE TABLE IF NOT EXISTS 'users' ('uuid' TEXT UNIQUE NOT NULL, 'user' BLOB)";

  private static Connection connection;

  public static void open() {
    if (connection == null) {
      try {
        connection = DriverManager.getConnection(url);
        loadTable();
        getPlugin().sendMessage("§bConexao estabelecida com a database.", false);
      } catch (Exception e) {
        getPlugin()
          .sendMessage("§cConexao falha com a database! Desabilitando plugin", true);
        e.printStackTrace();
      }
    }
  }

  public static void close() {
    if (connection != null) {
      try {
        connection.close();
        connection = null;
        getPlugin().sendMessage("§bConexao com a base de dados foi finalizada.", false);
      } catch (SQLException e) {
        getPlugin()
          .sendMessage("§cConexao com a base de dados não pode ser finalizada.", false);
        e.printStackTrace();
      }
    }
  }

  private static void loadTable() {
    try {
      PreparedStatement stm = getPreparedStatement(table);
      stm.execute();
      stm.close();
    } catch (SQLException e) {
      getPlugin()
        .sendMessage("§cDatabase nao pode ser carregada, desabilitando plugin", true);
      e.printStackTrace();
    }
  }

  public static PreparedStatement getPreparedStatement(String sql) {
    try {
      return connection.prepareStatement(sql);
    } catch (SQLException e) {
      getPlugin()
        .sendMessage(
          "§cPreparedStatement nao pode ser fornecido, desabilitando plugin",
          true
        );
      return null;
    }
  }
}
