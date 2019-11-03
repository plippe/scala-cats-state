package com.github.plippe.tictactoe.models

/** Code smell:
  * The player attribute shouldn't be optional
  * Furthermore, it would better represent the state of the game as an Outcome
  * This would highlight whose turn it is, but also if the game finished
  */
case class GameState(
    player: Option[Player],
    cells: Map[Cell, Player]
)

object GameState {
  def empty = GameState(Some(Player.O), Map.empty)
}
