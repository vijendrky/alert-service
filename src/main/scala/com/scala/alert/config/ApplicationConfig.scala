package com.scala.alert.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory.parseFile
import java.io.File

trait ApplicationConfig {

  val configPropKey = "configPath"

  protected lazy val typesafeConf: Config = {
    val configPath = Option(System.getProperty("configPath"))
      .getOrElse(throw new IllegalStateException("configPath must be set to config path."))
    val configFile = new File(configPath)
    if (!configFile.exists)
      throw new IllegalStateException(s"$configPath config file does not exit")
    parseFile(configFile).resolve()
  }

}
