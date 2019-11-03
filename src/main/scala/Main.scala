object Main extends App {
  println("Hello, World!")

  com.github.plippe.tictactoe.Game.turn
    .map(outcome => println(outcome))
    .run(com.github.plippe.tictactoe.models.GameState.empty)
    .value
}
