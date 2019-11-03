package com.github.plippe.tictactoe.models

sealed trait Outcome extends Product with Serializable
object Outcome {
  case object Draw extends Outcome
  case class Win(player: Player) extends Outcome
}
