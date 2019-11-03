package com.github.plippe.tictactoe.models

/** Code smell:
  * An extra case class could represent if the game should continue
  * It would hold a Player and highlight whose turn it is
  */
sealed trait Outcome extends Product with Serializable
object Outcome {
  case object Draw extends Outcome
  case class Win(player: Player) extends Outcome
}
