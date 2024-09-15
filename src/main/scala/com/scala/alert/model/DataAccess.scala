package com.scala.alert.model

import com.scala.alert.model.AlertService.{MonitoredData, Parameters}

import scala.concurrent.Future

trait DataAccess [P <: Parameters, D <: MonitoredData] {
  def getData(params: Option[P]): Future[Option[D]]
}
