package donor.dao

import donor.api.model.MapPointerFullInfoData
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

@Entity(name="map_point_info")
data class MapPointInfo (
    @Id
    val id: Long,
    val name: String,
    val address: String,
    val phone: String,
    val info: String?,
    val important: String?,
    val time: String?,
    val photo: String?,
)

interface MapPointInfoRepository: JpaRepository<MapPointInfo, Long>{

    @Query("SELECT * FROM map_point_info WHERE id =:id", nativeQuery = true)
    fun getPointInfo(id: Long): MapPointInfo?
}

fun MapPointerFullInfoData.toDB(id: Long?):MapPointInfo{
    return MapPointInfo(
        id = id ?: this.mapPoint?.id!!,
        name = this.name!!,
        address = this.address!!,
        phone = this.phone!!,
        info = this.info,
        important = this.important,
        time = this.time,
        photo = this.photo,
    )
}
fun MapPointInfo.toApi(mapPoint: MapPoint): MapPointerFullInfoData{
    return MapPointerFullInfoData(
        mapPoint = mapPoint.toAPI(),
        name = this.name,
        address = this.address,
        phone = this.phone,
        info = this.info,
        important = this.important,
        time = this.time,
        photo = this.photo,
    )
}