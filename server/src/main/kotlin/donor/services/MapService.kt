package donor.services

import donor.api.model.MapPointData
import donor.api.model.MapPointerFullInfoData
import donor.dao.*
import donor.endpoints.errors.ApiErrorCode
import donor.endpoints.errors.WebException
import org.springframework.stereotype.Service

@Service
class MapService(
    private val mapPointRepository: MapPointerRepository,
    private val mapPointInfoRepository: MapPointInfoRepository,
){

    fun editMapPoint(mapPointerFullInfoData: MapPointerFullInfoData): MapPointerFullInfoData{
        val editPoint = mapPointerFullInfoData.toDB(id = null)
        mapPointInfoRepository.save(editPoint)
        return editPoint.toApi(mapPointerFullInfoData.mapPoint!!.toDB(id = null))
    }

    fun createNewPoint(mapPointerFullInfoData: MapPointerFullInfoData){
        val id = mapPointRepository.nextId()
        val mapPoint = mapPointerFullInfoData.mapPoint!!.toDB(id)
        val mapPointInfo = mapPointerFullInfoData.toDB(id)
        mapPointRepository.save(mapPoint)
        mapPointInfoRepository.save(mapPointInfo)
    }

    fun getAllPoints(): List<MapPointData>{
        return mapPointRepository.getAllPoints().map { it.toAPI() }
    }

    fun getPointInfo(mapPointData: MapPointData):MapPointerFullInfoData{
        val mapPoint = mapPointRepository.getPositionByTalLng(mapPointData.tal!!, mapPointData.lng!!) ?: throw WebException(ApiErrorCode.POSITION_NOT_EXIST)
        val mapPointInfo = mapPointInfoRepository.getPointInfo(mapPoint.id) ?: throw WebException(ApiErrorCode.SOMETHING_GO_WRONG)
        return mapPointInfo.toApi(mapPoint)
    }

    fun deleteMapPoint(mapPointData: MapPointData){
        val mapPoint = mapPointRepository.getPositionByTalLng(mapPointData.tal!!,mapPointData.lng!!)?:throw WebException(ApiErrorCode.INTERNAL_SERVER_ERROR)
        mapPointRepository.delete(mapPoint)
    }
}