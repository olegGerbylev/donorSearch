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

    fun editMapPoint(mapPointerFullInfoData: MapPointerFullInfoData){
        val editPoint = mapPointerFullInfoData.toDB(id = null)
        mapPointInfoRepository.save(editPoint)
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
        val mapPoint = mapPointRepository.findById(mapPointData.id!!).get()
        val mapPointInfo = mapPointInfoRepository.getPointInfo(mapPointData.id) ?: throw WebException(ApiErrorCode.SOMETHING_GO_WRONG)
        return mapPointInfo.toApi(mapPoint)
    }

    fun deleteMapPoint(mapPointData: MapPointData){
        val mapPoint = mapPointRepository.findById(mapPointData.id!!).get()
        mapPointRepository.delete(mapPoint)
    }
}