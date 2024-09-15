package com.scala.alert.starter


trait AkkaStarterArgs {
  //@JOption(name = "-config", usage = "Akka startup configuration", required = true)
  var configFn: String = null
}
object AlertStarter extends App with AlertRestInterface with AkkaStarterArgs {

}