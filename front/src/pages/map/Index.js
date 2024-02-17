import React, {useContext, useEffect, useState} from 'react';
import MapWithUserLocation from "./map/MapWithUserLocation";
import Form from "./form/Form";
import classes from './map.module.scss'

import Info from "./info/Info";
import {Context} from "../../index";
import {observer} from "mobx-react-lite";
import axios from "axios";

const Index =observer( () => {
    const {user, error} = useContext(Context)
    const [mapState, setMapState] = useState("viewing")
    const [userLocation, setUserLocation] = useState(null);
    const [vetClinic, setVetClinic] = useState([]);
    const [currentClinic, setCurrentClinic] = useState(null);
    const [fullInfo, setFullInfo]= useState({})

    useEffect(()=>{
        console.log("=>"+mapState)
    },[mapState])

    useEffect(() => {
        if ('geolocation' in navigator) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const { latitude, longitude } = position.coords;
                    setUserLocation([latitude, longitude]);
                },
                (error) => {
                    console.log('Error getting user location:', error);
                }
            );
        } else {
            console.log('Geolocation is not supported by your browser');
        }
    }, []);


    useEffect(()=>{
        axios.get('http://localhost:8080/api/getAllPoint').then(function (response) {
            let data = response.data.map(item => [item.tal, item.lng])
            if(userLocation){
                let closestClinic
                let closestClinicCoef = 100000
                data.forEach(item =>{
                    const coefficient = Math.abs(userLocation[0] - item[0]) + Math.abs(userLocation[1] - item[1])
                    if (coefficient < closestClinicCoef){
                        closestClinic = item
                        closestClinicCoef = coefficient
                    }
                })
                setCurrentClinic(closestClinic)
            }
            setVetClinic(data)
        })
        .catch(function (e) {
            error.setMessageWithTimeout(e.response.data.message)
        });
    },[userLocation])

    const addPoint = (point)=>{
        setVetClinic([
            ...vetClinic,
            point
    ])
    }

    return (
        <div>
            {user.role === "superAdmin" && mapState !== "viewing" && <Form mapState={mapState}
                                                             setMapState={setMapState}
                                                             currentClinic={currentClinic}
                                                             setCurrentClinic={setCurrentClinic}
                                                             setVetClinic={addPoint}
                                                             fullInfo={fullInfo}
            />}
            {mapState === "viewing"  &&
                <Info currentClinic={currentClinic} setFullInfo={setFullInfo} fullInfo={fullInfo} setMapState={setMapState}/>
            }
            {user.role === "superAdmin" &&
                <>
                    <button onClick={()=>setMapState("addPoint")}>Добавить точку сбора крови</button>
                    <button onClick={()=>setMapState("viewing")}>Просмотр</button>
                </>
            }
            {userLocation?
                <MapWithUserLocation setVetClinic={setVetClinic}
                     mapState={mapState}
                     vetClinic={vetClinic}
                     userLocation={userLocation}
                     currentClinic={currentClinic}
                     setCurrentClinic={setCurrentClinic}
                     setMapState={setMapState}
                />
                :
                <MapWithUserLocation mapState={mapState} vetClinic={vetClinic} setMapState={setMapState}
                                     setVetClinic={setVetClinic}/>}
            <div className={classes.bottomIndent}></div>
        </div>
    );
});

export default Index;