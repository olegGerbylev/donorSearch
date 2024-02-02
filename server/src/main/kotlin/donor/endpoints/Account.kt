package donor.endpoints

import donor.api.AccountApiService
import donor.api.model.AccountData
import donor.api.model.Feature
import org.springframework.stereotype.Service

@Service
class Account():AccountApiService {
    override fun getAccount(): List<AccountData> {
        return emptyList()
    }

    override fun getFeatures(accountData: AccountData): List<Feature> {
        return emptyList()
    }
}