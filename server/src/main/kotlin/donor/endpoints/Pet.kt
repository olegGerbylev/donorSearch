package donor.endpoints

import donor.api.PetApiService
import donor.api.model.CreatePetData
import donor.api.model.DonationData
import donor.api.model.PetData

class Pet():PetApiService {
    override fun addDonation(petId: Long, donationData: DonationData): List<PetData> {
        TODO("Not yet implemented")
    }

    override fun confirmPets(petId: Long) {
        TODO("Not yet implemented")
    }

    override fun createPet(petId: Long, createPetData: CreatePetData): List<PetData> {
        TODO("Not yet implemented")
    }

    override fun getPets(petId: Long): List<PetData> {
        TODO("Not yet implemented")
    }

    override fun updatePet(petId: Long, petData: PetData): List<PetData> {
        TODO("Not yet implemented")
    }
}