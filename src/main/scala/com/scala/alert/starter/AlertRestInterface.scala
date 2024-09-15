package com.scala.alert.starter

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.server.Directives.{complete, path, pathEnd}
import com.scala.alert.model.DatabaseConfig
import com.scala.alert.services.PasswordExpiryService

trait AlertRestInterface extends Directives{

  val routes: Route = pathPrefix("check-password-expiry") {
    pathEnd {
      complete(StatusCodes.OK, "password expiry status default path")
    }~
      path(Segment) { (accountName) =>
        complete(StatusCodes.OK, PasswordExpiryService(DatabaseConfig.GCMDB, accountName).runService())
      }
  }
}
