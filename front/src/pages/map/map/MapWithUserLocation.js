import React, {useState, useEffect, useContext} from 'react';
import {MapContainer, TileLayer, Marker, Popup, useMapEvents} from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import './mapStyle.scss'

import customMarkerImage from '../../../media/img.png'
import customMarkerMyPosition from '../../../media/img2.png'
import axios from "axios";
import {Context} from "../../../index";

const MapWithUserLocation = ({mapState, vetClinic, currentClinic, setCurrentClinic, userLocation, setMapState}) => {

    const [popupInfo, setPopupInfo] = useState("")
    const {error} = useContext(Context)

    const customMarker = new L.divIcon({
        className: 'custom-marker',
        iconSize: [32, 32],
        iconAnchor: [16, 32],
        popupAnchor: [0, -32],
        html: `<img src="${customMarkerImage}" alt="Custom Marker" />`,
    });

    const customMarkerPosition = new L.divIcon({
        className: 'custom-marker',
        iconSize: [32, 32],
        iconAnchor: [16, 32],
        popupAnchor: [0, -32],
        html: `<img src="${customMarkerMyPosition}" alt="Custom Marker" />`,
    });

    const MapEvents = () => {
        useMapEvents({
            click(e) {
                const { lat, lng } = e.latlng;
                setCurrentClinic([lat, lng]);
                setMapState("addInfo")
            },
        });
    }

    const onMarketClick = (e)=>{
        axios.post('http://localhost:8080/api/getFullInfo',
            {
                tal: e.latlng.lat,
                lng: e.latlng.lng
            },
        )
            .then(function (response) {
                console.log(response)
                setPopupInfo(response.data.name)
                setCurrentClinic([e.latlng.lat, e.latlng.lng])
            })
            .catch(function (e) {
                error.setMessageWithTimeout(e.response.data.message)
            });
    }



    return (
        <MapContainer
            center={ userLocation || [59.93644042418626, 30.32089233398438]}
            zoom={12}
            style={{height: '900px', width: '100vw', position: "relative",
                marginTop: "20px",
                left: "50%",
                transform: "translateX(-50%)"}}
        >
            <TileLayer
                url="https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.{ext}"
                minZoom={3}
                maxZoom={20}
                attribution='&copy; <a href="https://www.stadiamaps.com/" target="_blank">Stadia Maps</a> &copy; <a href="https://openmaptiles.org/" target="_blank">OpenMapTiles</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                ext="png"
            />
            {mapState === "addPoint" &&
                <MapEvents/>
            }
            {userLocation && (
                <Marker position={userLocation} icon={customMarkerPosition}>
                    <Popup>Ваша локация</Popup>
                </Marker>
            )}
            {currentClinic && (
                <Marker position={currentClinic} icon={customMarker} eventHandlers={{click: (e)=>{
                        onMarketClick(e)
                    }}}>
                    <Popup>{popupInfo}</Popup>
                </Marker>
            )}
            {vetClinic.map((position, index) => (
                position &&(
                    <Marker key={index} position={position} icon={customMarker} eventHandlers={{click: (e)=>{
                        onMarketClick(e)
                    }}}>
                        <Popup>{popupInfo}</Popup>
                    </Marker>
                )
            ))}
        </MapContainer>
    );
};

export default MapWithUserLocation;
