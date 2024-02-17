package donor.dao

import donor.api.model.MapPointData
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

@Entity(name="map_points")
data class MapPoint (
    @Id
    val id: Long,
    val tal: Double,
    val lng: Double,
)

interface MapPointerRepository: JpaRepository<MapPoint, Long>{
    @Query("SELECT nextval('map_point_id_seq')", nativeQuery = true)
    fun nextId(): Long

    @Query("SELECT * FROM map_points", nativeQuery = true)
    fun getAllPoints(): List<MapPoint>

    @Query("SELECT * FROM map_points WHERE tal=:tal AND lng=:lng", nativeQuery = true)
    fun getPositionByTalLng(tal: Double, lng: Double): MapPoint?
}

fun MapPointData.toDB(id: Long?):MapPoint{
    return MapPoint(
        id = id ?: this.id!!,
        tal = this.tal!!,
        lng = this.lng!!,
    )
}

fun MapPoint.toAPI(): MapPointData{
    return MapPointData(
        id =this.id,
        tal =this.tal,
        lng = this.lng,
    )
}