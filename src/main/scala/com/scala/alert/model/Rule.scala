package com.scala.alert.model

import com.scala.alert.model.AlertService.MonitoredData
import com.scala.alert.model.DataCenter.DataCenter
import com.scala.alert.model.Status.{CRITICAL, OK, StatusType, WARNING}

trait Rule[T <: MonitoredData] {

  def resolveStatus(data: T): StatusType

  def resolveStatusEnv(data: T, dataCenter: DataCenter): StatusType = {
    val status = resolveStatus(data)
    SQLEnvironmentDAO.trySomething(dataCenter, status, {
      status match {
        case OK => CRITICAL
        case CRITICAL => OK
        case WARNING => OK
      }
    })
  }

}
