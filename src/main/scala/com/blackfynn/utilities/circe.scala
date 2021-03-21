package com.blackfynn.utilities

import io.circe._
import shapeless.{ ::, Generic, HNil, Lazy }

package object circe {
  implicit def decoderValueClass[T <: AnyVal, V](
    implicit
    g: Lazy[Generic.Aux[T, V :: HNil]],
    decoder: Decoder[V]
  ): Decoder[T] = Decoder.instance { cursor ⇒
    decoder(cursor).map { value ⇒
      g.value.from(value :: HNil)
    }
  }

  implicit def encoderValueClass[T <: AnyVal, V](
    implicit
    g: Lazy[Generic.Aux[T, V :: HNil]],
    encoder: Encoder[V]
  ): Encoder[T] = Encoder.instance { value ⇒
    encoder(g.value.to(value).head)
  }

}
