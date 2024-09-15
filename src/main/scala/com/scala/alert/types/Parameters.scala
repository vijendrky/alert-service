package com.scala.alert.types

import com.scala.alert.model.AlertService.Parameters

object Parameters {
  case object NoParams extends Parameters {
    override def validate: Some[Parameters.NoParams.type ] = Some(this)
  }
}
