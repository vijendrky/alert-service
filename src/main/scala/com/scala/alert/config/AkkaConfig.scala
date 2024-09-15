package com.scala.alert.config

import akka.actor.ActorPath
import com.typesafe.config.{Config, ConfigFactory}

import java.io.File
import scala.annotation.tailrec

case class AkkaConfig(actorSystem: String,
                      hostname: String,
                      port: Int,
                      typesafe: Config)
object AkkaConfig {
  def apply(fileName: String): AkkaConfig = AkkaConfig(new File(fileName))

  def apply(file: java.io.File): AkkaConfig = AkkaConfig(ConfigFactory.parseFile(file))

  def apply(config: Config): AkkaConfig = {
    val akkaConfig = config.getConfig("akka")
    AkkaConfig(
      akkaConfig.getString("actorSystem"),
      akkaConfig.getString("hostname"),
      akkaConfig.getInt("port"),
      config
    )
  }

}

trait ActorConfig {
  def actorPath: ActorPath = parentPath / actorName

  def parentPath:  ActorPath

  def actorName: String
}

trait SupervisorConfig extends ActorConfig {
  def childrenConfigs: Seq[ActorConfig]

  def actorProps: ActorProps
}