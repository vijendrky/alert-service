package com.scala.alert.types

import com.scala.alert.model.Rule
import com.scala.alert.model.Status.{CRITICAL, OK, StatusType, WARNING}
import com.scala.alert.types.MonitoredData.PasswordExpiryResponse

object Rule {
  case object PasswordUsageGreaterThan extends Rule[PasswordExpiryResponse] {
    override def resolveStatus(data: PasswordExpiryResponse): StatusType = {
      if (data.totalDaysLeft <=60)
        CRITICAL
      else if(data.totalDaysLeft <= 90)
        WARNING
        else
          OK
    }
  }
}
