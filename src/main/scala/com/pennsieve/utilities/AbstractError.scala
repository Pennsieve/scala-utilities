package com.pennsieve.utilities

import java.io.{ PrintWriter, StringWriter }

trait AbstractError extends Exception {
  def stackTraceToString: String = {
    val stringWriter = new StringWriter()

    super.printStackTrace(new PrintWriter(stringWriter))
    stringWriter.toString
  }

  def print(debug: Boolean = false): String = {
    if (debug) this.stackTraceToString
    else this.getMessage
  }
}
