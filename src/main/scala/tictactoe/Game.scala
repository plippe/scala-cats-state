package com.github.plippe.tictactoe

import cats.data.State
import com.github.plippe.tictactoe.models._

object Game {
  def drawCells: State[GameState, Unit] = ???
  def readCell: State[GameState, Cell] = ???
  def markCell(c: Cell): State[GameState, Unit] = ???
  def computeOutcome: State[GameState, Option[Outcome]] = ???

  def turn: State[GameState, Outcome] =
    for {
      _ <- drawCells
      cell <- readCell
      _ <- markCell(cell)
      outcome <- computeOutcome.flatMap {
        case None          => turn
        case Some(outcome) => State((_, outcome))
      }
    } yield outcome
}
