package com.github.plippe.tictactoe.models

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
}
