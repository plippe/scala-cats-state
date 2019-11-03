package com.github.plippe.tictactoe.models

sealed trait Player extends Product with Serializable
object Player {
  case object X extends Player
  case object O extends Player
}
