package com.scala.alert.model

import com.scala.alert.model.DataCenter.DataCenter
import scalikejdbc.DB.using

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

trait EnvironmentDAO {
  def getActiveDataCenter: Future[DataCenter]

  def trySomething[T](environmentIn: DataCenter,
                      toTry: => T,
                      runAnyway: => T,
                      duration: Duration = Duration.Inf): T = {
    val activeEnv = Await.result(SQLEnvironmentDAO.getActiveDataCenter, duration)
    if(environmentIn == activeEnv)
      toTry
      else
        runAnyway
  }
}

object SQLEnvironmentDAO extends EnvironmentDAO {

  override def getActiveDataCenter: Future[DataCenter.Value] =
    Future(
      using(DBClientUtils.getDBConnection(DatabaseConfig.GCMDB)) { conn =>
        val sql = ""
        val statement = conn.prepareStatement(sql)
        statement.setInt(1,1)
        val rs = statement.executeQuery()
        rs.next()
        DataCenter.withName(rs.getString("datacenter"))
      }
    )
}