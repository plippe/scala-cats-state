package com.github.plippe.tictactoe.models

/** Code smell:
  * TicTacToe has a 3x3 grid
  * Scaling this approach to an MxN grid isn't possible
  */
sealed trait Cell extends Product with Serializable
object Cell {
  case object AA extends Cell
  case object AB extends Cell
  case object AC extends Cell
  case object BA extends Cell
  case object BB extends Cell
  case object BC extends Cell
  case object CA extends Cell
  case object CB extends Cell
  case object CC extends Cell

  val all = List(AA, AB, AC, BA, BB, BC, CA, CB, CC)

  def fromString(str: String): Option[Cell] = str.trim.toUpperCase match {
    case "AA" => Some(AA)
    case "AB" => Some(AB)
    case "AC" => Some(AC)
    case "BA" => Some(BA)
    case "BB" => Some(BB)
    case "BC" => Some(BC)
    case "CA" => Some(CA)
    case "CB" => Some(CB)
    case "CC" => Some(CC)
    case _    => None
  }
}
