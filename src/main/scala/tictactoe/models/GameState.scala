package com.github.plippe.tictactoe.models

case class GameState(
    player: Option[Player],
    cells: Map[Cell, Player]
)
