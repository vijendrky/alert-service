package com.scala.alert.model

import com.scala.alert.model.DataCenter.Value

object DatabaseConfig extends Enumeration {
  type DBConfigKey = this.Value

  val GCMDB: Value = Value("CONMON")
}
