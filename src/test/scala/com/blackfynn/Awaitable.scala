package com.pennsieve.test

import cats.data.EitherT

import scala.concurrent.{ Await, Future }
import scala.concurrent.duration.{ Duration, DurationDouble, FiniteDuration }

case class Awaitable[A](f: Future[A]) {
  def await: A = Await.result(f, Duration.Inf)

  def awaitFinite(wait: FiniteDuration = 5.seconds): A = Await.result(f, wait)
}

trait AwaitableImplicits {

  implicit def toAwaitable[A](f: Future[A]): Awaitable[A] = Awaitable(f)

  implicit def toAwaitable[A, B](
    e: EitherT[Future, A, B]
  ): Awaitable[Either[A, B]] = Awaitable(e.value)

}
