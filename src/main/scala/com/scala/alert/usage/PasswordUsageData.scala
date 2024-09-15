package com.scala.alert.usage

import com.scala.alert.data.DBDataAccess
import com.scala.alert.model.{DBClientUtils, DataCenter, DatabaseConfig}
import com.scala.alert.model.DatabaseConfig.DBConfigKey
import com.scala.alert.types.MonitoredData.PasswordExpiryResponse
import com.scala.alert.types.Parameters
import com.scala.alert.types.Parameters.NoParams
import scalikejdbc.DB.using
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class PasswordUsageData(poolKey: DBConfigKey, accountName: String) extends DBDataAccess[NoParams.type, PasswordExpiryResponse ] {
  val query =
    s"""
       |
       |""".stripMargin
  override def getData(params: Option[Parameters.NoParams.type]): Future[Option[PasswordExpiryResponse]] = {
    Future {
      params.map { source =>
        using(DBClientUtils.getDBConnection(poolKey)) { conn =>
          using(conn.prepareStatement(query)) { ps =>
            val result = ps.executeQuery()
            if (result.next()) {
              PasswordExpiryResponse(result.getInt("total_day_left"))
            } else {
              PasswordExpiryResponse(0)
            }
          }
        }
      }
    }
  }
}
