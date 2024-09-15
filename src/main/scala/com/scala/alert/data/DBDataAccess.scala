package com.scala.alert.data

import donutstore.model.AlertService.{MonitoredData, Parameters}
import donutstore.model.DataAccess
import donutstore.model.DatabaseConfig.DBConfigKey

trait
DBDataAccess[P <: Parameters, D <: MonitoredData] extends DataAccess[P, D] {
  val poolKey: DBConfigKey
}
