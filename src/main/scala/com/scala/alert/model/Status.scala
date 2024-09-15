package com.scala.alert.model

object Status extends Enumeration {

  type  StatusType = this.Value

  val OK: Value = Value("OK")
  val WARNING: Value = Value("WARNING")
  val CRITICAL: Value = Value("CRITICAL")

  def contains(message: String, status: Status.Value) : Boolean = {
    message.contains(status.toString)
  }
}
