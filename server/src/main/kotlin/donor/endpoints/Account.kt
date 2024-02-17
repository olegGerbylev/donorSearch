package donor.endpoints

import donor.api.AccountApiService
import donor.api.model.AccountData
import donor.api.model.AccountPaginationListData
import donor.api.model.AccountRegisterData

class Account():AccountApiService {
    override fun createAccount(accountRegisterData: AccountRegisterData): AccountData {
        TODO("Not yet implemented")
    }

    override fun getAccount(accountId: Long): AccountData {
        TODO("Not yet implemented")
    }

    override fun listAccounts(page: Int?, pageSize: Int?): AccountPaginationListData {
        TODO("Not yet implemented")
    }

    override fun updateAccount(accountId: Long, accountData: AccountData): AccountData {
        TODO("Not yet implemented")
    }
}