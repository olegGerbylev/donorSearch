import React, { useState, useEffect } from 'react';
import {MapContainer, TileLayer, Marker, Popup, Pane, FeatureGroup, useMapEvents} from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import './mapStyle.css'

import customMarkerImage from './img.png'

const MapWithUserLocation = () => {
    const [userLocation, setUserLocation] = useState(null);
    const [clickedPosition, setClickedPosition] = useState([]);

    useEffect(() => {
        // Получение местоположения пользователя
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
        iconSize: [32, 32], // размеры вашей картинки
        iconAnchor: [16, 32], // точка, куда будет указывать маркер
        popupAnchor: [0, -32], // точка, где будет отображаться всплывающее окно
        html: `<img src="${customMarkerImage}" alt="Custom Marker" />`, // вставьте вашу картинку внутрь divIcon
    });

    const MapEvents = () => {
        useMapEvents({
            click(e) {
                console.log(e.latlng.lat);
                console.log(e.latlng.lng);
                const { lat, lng } = e.latlng;
                setClickedPosition([...clickedPosition, [lat, lng]]);
            },
        });
        return false;
    }
    return (
        <>
            {userLocation &&
                <MapContainer
                    center={ [59.93644042418626, 30.32089233398438]}
                    zoom={13}
                    style={{ height: '500px', width: '500px' }}
                    // onClick={e=>handleMapClick(e)}
                >
                    {/*<FeatureGroup onClick={handleMapClick}>*/}
                    <TileLayer
                        url="https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.{ext}"
                        minZoom={0}
                        maxZoom={20}
                        attribution='&copy; <a href="https://www.stadiamaps.com/" target="_blank">Stadia Maps</a> &copy; <a href="https://openmaptiles.org/" target="_blank">OpenMapTiles</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        ext="png"
                    />
                    <MapEvents/>
                    {userLocation && (
                        <Marker position={userLocation} icon={customMarker}>
                            <Popup>Your Location</Popup>
                        </Marker>
                    )}
                    {clickedPosition.map((position, index) => (
                        position &&(
                            <Marker key={index} position={position} icon={customMarker}>
                                {/*<Popup>Clicked Position: {position.join(', ')}</Popup>*/}
                            </Marker>
                        )
                    ))}
                    {/*</FeatureGroup>*/}
                </MapContainer>
            }
        </>
    );
};

export default MapWithUserLocation;
