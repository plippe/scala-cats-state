package com.github.plippe.tictactoe.models

case class GameState(
    player: Option[Player],
    cells: Map[Cell, Player]
)

object GameState {
  def empty = GameState(Some(Player.O), Map.empty)
}
