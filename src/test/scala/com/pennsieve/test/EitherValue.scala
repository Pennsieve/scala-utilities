package com.pennsieve.test
import org.scalactic.source
import org.scalatest.exceptions.{ StackDepthException, TestFailedException }

object EitherValue {

  implicit class RightValuable[L, R](
    either: Either[L, R]
  )(implicit
    pos: source.Position
  ) {

    /**
      * Returns the <code>Right</code> value contained in the wrapped <code>RightProjection</code>, if defined as a <code>Right</code>, else throws <code>TestFailedException</code> with
      * a detail message indicating the <code>Either</code> was defined as a <code>Right</code>, not a <code>Left</code>.
      */
    def value: R =
      either match {
        case Right(r) => r
        case Left(l) =>
          throw new TestFailedException(
            messageFun = (_: StackDepthException) =>
              Some(
                s"The Either on which value was invoked was not defined as a Right. Actually: ${l}"
              ),
            None,
            pos = pos
          )
      }
  }
}
