package cinema

const val ROOM_SMALL = 60
const val PRICE_REGULAR = 10
const val PRICE_REDUCED = 8
const val SEAT_EMPTY = 'S'
const val SEAT_TAKEN = 'B'

var room = mutableListOf(mutableListOf<Char>())

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()
    room = MutableList(rows) { MutableList(seatsPerRow) { SEAT_EMPTY } }
    while (true) {
        println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit")
        when (readln().toIntOrNull()) {
            1 -> {
                println("\nCinema:\n  ${room.first().indices.map { it + 1 }.joinToString(" ")}")
                room.forEachIndexed { index, row -> println("${index + 1} ${row.joinToString(" ")}") }
            }
            2 -> {
                while (true) {
                    println("\nEnter a row number:")
                    val row = readln().toInt() - 1
                    println("Enter a seat number in that row:")
                    val seat = readln().toInt() - 1
                    if (row !in 0 until room.size || seat !in 0 until room[0].size) { println("\nWrong input!\n"); continue }
                    if (room[row][seat] == SEAT_TAKEN) { println("\nThat ticket has already been purchased!\n"); continue }
                    room[row][seat] = SEAT_TAKEN
                    println("\nTicket price: $${ticketPrice(row)}")
                    break
                }
            }
            3 -> {
                val tickets = room.sumOf { it.count { it == SEAT_TAKEN } }
                var currentIncome = 0
                var totalIncome = 0
                room.forEachIndexed { index, row ->
                    val price = ticketPrice(index)
                    row.map { if (it == SEAT_TAKEN) currentIncome += price }
                    totalIncome += price * row.size
                }
                println("""                    
                    Number of purchased tickets: $tickets
                    Percentage: ${"%.2f".format(100 / (room.size * room.first().size).toFloat() * tickets)}%
                    Current income: ${'$'}$currentIncome
                    Total income: ${'$'}$totalIncome
                """.trimIndent())
            }
            0 -> return
            else -> continue
        }
    }
}

fun ticketPrice(row: Int): Int {
    return if (room.size * room.first().size > ROOM_SMALL && row + 1 > room.size / 2) PRICE_REDUCED else PRICE_REGULAR
}