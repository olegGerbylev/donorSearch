package donor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class DonorApplication

fun main(args: Array<String>) {
    runApplication<DonorApplication>(*args)
}
