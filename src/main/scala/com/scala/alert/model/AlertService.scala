package com.scala.alert.model

import com.typesafe.scalalogging.LazyLogging
import com.scala.alert.model.Status.StatusType
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object AlertService {

  trait MonitoredData

  trait Parameters {

    def validate: Option[Parameters]
  }

  trait Description[T <: MonitoredData] {

    def description(data: T, status: StatusType): String
  }

  trait AlertService extends LazyLogging {
    logger info s"Instantiated ${logger.underlying.getName}"

    type Params <: Parameters
    type Data<: MonitoredData
    type DAO <: DataAccess[Params, Data]

    val rule: Rule[Data]
    val description: Description[Data]
    val dao: DAO

    private def dependsOnDataCenter: Boolean = false

    protected def readParams(params: String*): Option[Params]

    private def runRule(params: String*): Future[(StatusType, String)] = {
      dao.getData({
        logger info s"Querying for${params.mkString(" and ")}"
          readParams(params: _*)
      })
        .map { dataOpt =>
          dataOpt.map { data =>
            val status = if (dependsOnDataCenter) {
              rule.resolveStatusEnv(data, DataCenter.PADC)
            }
            else {
              rule.resolveStatus(data)
            }
            (status, description.description(data, status))
          }.getOrElse(Status.WARNING, "Could not retrieve data fro this alert")
        }
        .recover{ case _ =>
          (Status.CRITICAL, s"failed ${params.mkString(" and ")}")
        }
    }

    def runService(params: String*): Future[String] = {
      runRule(params:_*)
        .map { case  (status, desc) =>
          val serviceStatus =
          s"""
               | status: ${status.toString}
               | description: $desc
          """.stripMargin
          logger info serviceStatus
          serviceStatus
        }
    }
  }
}
