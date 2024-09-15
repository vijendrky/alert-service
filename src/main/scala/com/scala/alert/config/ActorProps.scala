package com.scala.alert.config

import akka.actor.Props

trait ActorProps {
  val dispathcher: String

  def applicationConfigToProps: PartialFunction[ActorConfig, Props]


}
