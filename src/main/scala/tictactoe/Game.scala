package com.github.plippe.tictactoe

import cats.data.State
import cats.implicits._
import com.github.plippe.tictactoe.models._
import scala.io.StdIn.{readLine => readln}

object Game {
  // Code smell: IO
  def drawCells: State[GameState, Unit] = State { gameState =>
    def cell(cell: Cell) =
      gameState.cells
        .get(cell)
        .fold(" ")(_.toString)

    def row(a: Cell, b: Cell, c: Cell) =
      s" ${cell(a)} | ${cell(b)} | ${cell(c)} "

    println(row(Cell.AA, Cell.AB, Cell.AC))
    println("---|---|---")
    println(row(Cell.BA, Cell.BB, Cell.BC))
    println("---|---|---")
    println(row(Cell.CA, Cell.CB, Cell.CC))

    (gameState, ())
  }

  // Code smell: IO
  def readCell: State[GameState, Cell] = State { gameState =>
    @annotation.tailrec
    def cell(availableCells: List[Cell]): Cell =
      Cell
        .fromString(readln("What cell do you wish to play? "))
        .orElse {
          println("Cell doesn't exist")
          None
        }
        .flatMap {
          case cell if availableCells.contains(cell) => Some(cell)
          case _ =>
            println("Cell already played")
            None
        } match {
        case Some(cell) => cell
        case None       => cell(availableCells)
      }

    val availableCells = Cell.all
      .filter(cell => !gameState.cells.contains(cell))

    // Code smell: get
    println(s"Player ${gameState.player.get}'s turn")
    println("The following cells are available:")
    availableCells.foreach(cell => println(s" - ${cell}"))
    println()

    (gameState, cell(availableCells))
  }

  def markCell(c: Cell): State[GameState, Unit] = State { gameState =>
    // Code smell: get
    val updatedCells = gameState.cells.updated(c, gameState.player.get)
    (gameState.copy(cells = updatedCells), ())
  }

  // Code smell: scaling to MxN grid
  def computeOutcome: State[GameState, Option[Outcome]] = State { gameState =>
    def cell(cell: Cell): Option[Player] = gameState.cells.get(cell)
    def row(a: Cell, b: Cell, c: Cell): Option[Player] =
      (cell(a), cell(b), cell(b)).tupled
        .filter { case (a, b, c) => a == b && b == c }
        .map { case (a, _, _) => a }

    val outcome = row(Cell.AA, Cell.AB, Cell.AC)
      .orElse(row(Cell.BA, Cell.BB, Cell.BC))
      .orElse(row(Cell.CA, Cell.CB, Cell.CC))
      .orElse(row(Cell.AA, Cell.BA, Cell.CA))
      .orElse(row(Cell.AB, Cell.BB, Cell.CB))
      .orElse(row(Cell.AC, Cell.BC, Cell.CC))
      .orElse(row(Cell.AA, Cell.BB, Cell.CC))
      .orElse(row(Cell.AC, Cell.AB, Cell.CA))
      .map(Outcome.Win.apply)
      .orElse {
        if (gameState.cells.size == Cell.all.size) Some(Outcome.Draw)
        else None
      }

    // Code smell: outcome representing next turn
    val nextPlayer =
      if (gameState.player.get == Player.X) Player.O else Player.X

    (gameState.copy(player = Some(nextPlayer)), outcome)
  }

  def turn: State[GameState, Outcome] =
    for {
      _ <- drawCells
      cell <- readCell
      _ <- markCell(cell)
      // Code smell: outcome representing next turn
      outcome <- computeOutcome.flatMap {
        case None          => turn
        case Some(outcome) => State((_, outcome))
      }
    } yield outcome
}
