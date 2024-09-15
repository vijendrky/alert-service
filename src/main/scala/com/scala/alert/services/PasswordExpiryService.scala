package com.scala.alert.services

import com.scala.alert.model.{AlertService, Rule, Status}
import com.scala.alert.model.AlertService.{AlertService, Description}
import com.scala.alert.model.DatabaseConfig.DBConfigKey
import com.scala.alert.model.Status.StatusType
import com.scala.alert.types.MonitoredData.PasswordExpiryResponse
import com.scala.alert.types.Parameters.NoParams
import com.scala.alert.types.Rule.PasswordUsageGreaterThan
import com.scala.alert.usage.PasswordUsageData

case class PasswordExpiryService(poolKey: DBConfigKey, accountName: String) extends AlertService {
  override type Params = NoParams.type
  override type Data = PasswordExpiryResponse
  override type DAO = PasswordUsageData

  override val rule: Rule[PasswordExpiryResponse] = PasswordUsageGreaterThan

  override val description: Description[PasswordExpiryResponse] = new Description[Data] {
    override def description(data: PasswordExpiryResponse, status: StatusType): String = {
      status match {
        case Status.OK => s"Service account '${accountName}' has ${data.totalDaysLeft} days left for password expiry"
        case _ => s"Service account '${accountName}' has only ${data.totalDaysLeft} days left for password expiry"
      }
    }
  }

  override val dao: PasswordUsageData = PasswordUsageData(poolKey, accountName)

  override protected def readParams(params: String*): Option[Params] = {
    Some(NoParams)
  }
}
