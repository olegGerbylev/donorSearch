package donor.endpoints.validation

import donor.api.model.MapPointData
import donor.api.model.MapPointerFullInfoData
import donor.dao.MapPointerRepository
import donor.dao.toDB
import donor.endpoints.errors.ApiErrorCode
import org.springframework.stereotype.Service

@Service
class MapApiValidator(
    private val mapPointerRepository: MapPointerRepository,
) {

    fun validateCreateData(data: MapPointerFullInfoData){
        Validator(data) {
            isNotNullOrEmpty("mapPoint")
            isNotNullOrEmpty("name")
            isNotNullOrEmpty("address")
            isNotNullOrEmpty("phone")
        }
    }

    fun validateEditData(data: MapPointerFullInfoData){
        Validator(data) {
            isNotNullOrEmpty("mapPoint")
            isNotNullOrEmpty("name")
            isNotNullOrEmpty("address")
            isNotNullOrEmpty("phone")
        }
        Validator(data){
            validateMapPointId(data.mapPoint!!)
        }
    }

    fun validateMapPointId(data: MapPointData){
        Validator(data){
            isNotNullOrEmpty("tal")
            isNotNullOrEmpty("lng")
        }
        Validator(data){
            if (mapPointerRepository.getPositionByTalLng( data.tal!!, data.lng!!) == null){
                error("position", ApiErrorCode.POSITION_NOT_EXIST)
            }
        }
    }
}