package chucknorris

fun main() {
    while (true) {
        println("Please input operation (encode/decode/exit):")
        val command = readln()
        when(command) {
            "encode" -> {
                println("Input string:")
                println("Encoded string: \n${encrypt(readln())}")
            }
            "decode" -> {
                println("Input encoded string:")
                val result = decrypt(readln())
                println (if (result.contains("not valid")) "Encoded string is not valid."
                else "Decoded string: \n $result")
            }
            "exit" -> {
                println("Bye!")
                break
            }
            else -> println("There is no \'$command\' operation")
        }
    }
}

fun convertToBinary(char: Char): String {
    return String.format("%07d", Integer.toBinaryString(char.code).toInt())
}

fun encrypt(input: String): String {
    var binary = ""
    for (i in input) {
        binary += convertToBinary(i)
    }
    return encryptBynary(binary)
}

fun encryptBynary(binary: String): String {
    var result = ""
    var previous = ' '

    for (i in binary.indices) {
        when(binary[i]) {
            previous -> result += "0"
            '1' -> result += " 0 0"
            '0' -> result += " 00 0"
        }
        previous = binary[i]
    }
    return result.trim()
}

fun decrypt(encripted: String): String {
    var resultLine = ""
    var resultSymbols = ""
    var bite = 0
    val array = encripted.split(" ")

    if (array.size % 2 == 1 || encripted.contains("[^0\\s]".toRegex())) {
        return "Encoded string is not valid."
    }

    for (i in array.indices) {
        if (i % 2 == 0) {
            bite = if (array[i] == "0") {
                1
            } else if (array[i] == "00") {
                0
            } else return "Encoded string is not valid."
        } else {
            repeat(array[i].length) {
                resultLine += bite
            }
        }
    }
    if (resultLine.length % 7 != 0) {
        return "Encoded string is not valid."
    }
    val resultArray = resultLine.chunked(7)
    for (i in resultArray) {
        resultSymbols += Integer.parseInt(i, 2).toChar()
    }
    return resultSymbols
}