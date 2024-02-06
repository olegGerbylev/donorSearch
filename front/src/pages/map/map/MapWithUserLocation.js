import React, { useState, useEffect } from 'react';
import {MapContainer, TileLayer, Marker, Popup, Pane, FeatureGroup, useMapEvents} from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import './mapStyle.scss'

import customMarkerImage from '../../../media/img.png'
import customMarkerMyPosition from '../../../media/img2.png'

const MapWithUserLocation = () => {
    const [userLocation, setUserLocation] = useState(null);
    const [clickedPosition, setClickedPosition] = useState([]);
    const [mapState, setMapState] = useState("viewing")

    useEffect(() => {
        if ('geolocation' in navigator) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const { latitude, longitude } = position.coords;
                    setUserLocation([latitude, longitude]);
                },
                (error) => {
                    console.error('Error getting user location:', error);
                }
            );
        } else {
            console.error('Geolocation is not supported by your browser');
        }
    }, []);

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
                console.log(e.latlng.lat);
                console.log(e.latlng.lng);
                const { lat, lng } = e.latlng;
                setClickedPosition([...clickedPosition, [lat, lng]]);
                setMapState("viewing")
            },
        });
    }


    return (
        <>
            {/*<button onClick={e => mapState === "viewing" ? setMapState("edit") : setMapState("viewing")}>add</button>*/}
            {/*{userLocation && <div>{userLocation.join(" ,")}</div>}*/}
            {/*{userLocation &&*/}
                <MapContainer
                    className={mapState === "edit" ? "edit" : "viewing"}
                    center={ [59.93644042418626, 30.32089233398438]}
                    zoom={12}
                    style={{height: '900px', width: '100vw', position: "relative",
                        marginTop: "20px",
                        left: "50%",
                        transform: "translateX(-50%)"}}
                >
                    {/*<FeatureGroup onClick={handleMapClick}>*/}
                    <TileLayer
                        url="https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.{ext}"
                        minZoom={3}
                        // maxNativeZoom={19}
                        maxZoom={20}
                        attribution='&copy; <a href="https://www.stadiamaps.com/" target="_blank">Stadia Maps</a> &copy; <a href="https://openmaptiles.org/" target="_blank">OpenMapTiles</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        ext="png"
                    />
                    {mapState === "edit" &&
                        <MapEvents/>
                    }
                    {userLocation && (
                        <Marker position={userLocation} icon={customMarkerPosition}>
                            <Popup>Your Location</Popup>
                        </Marker>
                    )}
                    {clickedPosition.map((position, index) => (
                        position &&(
                            <Marker key={index} position={position} icon={customMarker}>
                                <Popup>Clicked Position: {position.join(', ')}</Popup>
                            </Marker>
                        )
                    ))}
                    {/*</FeatureGroup>*/}
                </MapContainer>
            {/*}*/}
        </>
    );
};

export default MapWithUserLocation;
