package donor

import donor.dao.Account
import donor.dao.AccountRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DonorApplication

fun main(args: Array<String>) {

    runApplication<DonorApplication>(*args)
}