package com.scala.alert.types

import com.scala.alert.model.AlertService.MonitoredData

object MonitoredData {
  case class PasswordExpiryResponse(totalDaysLeft: Int) extends MonitoredData
}
