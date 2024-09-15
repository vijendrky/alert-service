package com.scala.alert.model

import com.scala.alert.model.DatabaseConfig.DBConfigKey
import scalikejdbc.{ConnectionPool, ConnectionPoolSettings}

import java.sql.Connection

object DBClientUtils {
  def getDBConnection(db: DBConfigKey): Connection = {
    ConnectionPool.borrow(db.toString)
  }
}
