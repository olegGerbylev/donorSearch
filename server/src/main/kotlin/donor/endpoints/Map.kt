package donor.endpoints

import donor.api.MapApiService
import donor.api.model.MapPointData

import donor.api.model.MapPointerFullInfoData
import donor.endpoints.validation.MapApiValidator
import donor.services.MapService
import donor.services.SessionService
import org.springframework.stereotype.Service

@Service
class Map(
    private val validator: MapApiValidator,
    private val sessions: SessionService,
    private val maps: MapService,
):MapApiService {
    override fun addPoint(mapPointerFullInfoData: MapPointerFullInfoData): List<MapPointData> {
        TODO("Not yet implemented")
        sessions.isSuperAdmin()
        validator.validateCreateData(mapPointerFullInfoData)
        maps.createNewPoint(mapPointerFullInfoData)
        return maps.getAllPoints()
    }

    override fun deletePoint(mapPointData: MapPointData) {
        TODO("Not yet implemented")
        sessions.isSuperAdmin()
        validator.validateMapPointId(mapPointData)
        maps.deleteMapPoint(mapPointData)
    }

    override fun editPoint(mapPointerFullInfoData: MapPointerFullInfoData):MapPointerFullInfoData {
        TODO("Not yet implemented")
        sessions.isSuperAdmin()
        validator.validateEditData(mapPointerFullInfoData)
        return maps.editMapPoint(mapPointerFullInfoData)
    }

    override fun getAllPointer(): List<MapPointData> {
        TODO("Not yet implemented")
        return maps.getAllPoints()
    }

    override fun getFullInfo(mapPointData: MapPointData): MapPointerFullInfoData {
        TODO("Not yet implemented")
        validator.validateMapPointId(mapPointData)
        return maps.getPointInfo(mapPointData)
    }
}