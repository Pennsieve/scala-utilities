package com.pennsieve.utilities

import cats.data._
import cats.implicits._

import scala.concurrent.{ ExecutionContext, Future }

object FutureEitherHelpers {

  def unit[T](implicit ec: ExecutionContext): EitherT[Future, T, Unit] =
    EitherT.rightT[Future, T](())

  def assert[T](
    predicate: => Boolean
  )(
    error: T
  )(implicit
    ec: ExecutionContext
  ): EitherT[Future, T, Unit] =
    if (predicate) unit else EitherT.leftT[Future, Unit](error)

  object implicits {

    implicit class FutureEitherT[A](f: Future[A]) {
      def toEitherT[B](
        exceptionHandler: Exception => B
      )(implicit
        ec: ExecutionContext
      ): EitherT[Future, B, A] = {
        val futureEither: Future[Either[B, A]] = f
          .map { value =>
            Right(value)
          }
          .recover {
            case e: Exception => Left(exceptionHandler(e))
          }

        EitherT(futureEither)
      }

      def toEitherT(
        implicit
        ec: ExecutionContext
      ): EitherT[Future, Exception, A] =
        f.toEitherT[Exception](identity)
    }

    implicit class FutureEitherEitherT[A, B](f: Future[Either[B, A]]) {
      def toEitherT(
        exceptionHandler: Exception => B
      )(implicit
        ec: ExecutionContext
      ): EitherT[Future, B, A] = {
        val futureEither: Future[Either[B, A]] = f.recover {
          case e: Exception => Left(exceptionHandler(e))
        }

        EitherT(futureEither)
      }
    }

    implicit class FutureOption[A](f: Future[Option[A]]) {

      def whenNone[B](
        l: B
      )(implicit
        ec: ExecutionContext
      ): EitherT[Future, B, A] = {
        val e = f.map {
          case None => Left(l)
          case Some(r) => Right(r)
        }

        EitherT(e)
      }
    }

    implicit class ListEitherTFuture[A, B](l: List[EitherT[Future, A, B]]) {
      def separate(
        implicit
        ec: ExecutionContext
      ): Future[(List[A], List[B])] = {
        Future.sequence(l.map(_.value)).map(x => x.separate)
      }

      def separateE(
        implicit
        ec: ExecutionContext
      ): EitherT[Future, Exception, (List[A], List[B])] = {
        l.separate.toEitherT
      }

    }
  }

}
