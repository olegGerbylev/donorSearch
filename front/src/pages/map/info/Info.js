import React, {useContext, useEffect, useState} from 'react';
import Input from "../../../components/input/Input";
import Button from "../../../components/button/Button";
import {Context} from "../../../index";
import axios from "axios";
import classes from './info.module.scss'
import InfoBlock from "../../../components/infoBlock/InfoBlock";


const Info = ({currentClinic, fullInfo, setFullInfo,setMapState }) => {
    const {user, error} = useContext(Context)

    // const {}

    useEffect(()=>{
        if (!currentClinic){
            return
        }
        axios.post('http://localhost:8080/api/getFullInfo',
            {
                    tal: currentClinic[0],
                    lng: currentClinic[1]
            },
        )
            .then(function (response) {
                setFullInfo(response.data)
            })
            .catch(function (e) {
                if (e.response?.data){
                    error.setMessageWithTimeout(e.response.data.message)
                }
                console.log(e)
            });
    },[currentClinic])


    const deletePoint = ()=>{
        if (!currentClinic){
            return
        }
        axios.post('http://localhost:8080/api/deleteMapPoint',{
            tal: currentClinic[0],
            lng: currentClinic[1]
        },
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem("sessionId"),
                }})
            .then(function (response) {

            })
            .catch(function (e) {
                if(e.response.data.error === "INVALID_ACCESS_TOKEN"){
                    user.logOut()
                }
                error.setMessageWithTimeout(e.response.data.message)
            });
    }


    return (
        <div className={classes.infoContainer}>
            <InfoBlock label={"Название"} text={fullInfo.name} id={classes.nameField}/>
            <InfoBlock label={"Адрес"} text={fullInfo.address}/>
            <InfoBlock label={"Важно"} text={fullInfo.important}/>
            <InfoBlock label={"Информация"} text={fullInfo.info}/>
            <InfoBlock label={"телефон"} text={fullInfo.phone}/>
             {user.role==="superAdmin" &&
                <>
                    <Button text={"Редактировать"} onClick={()=>setMapState("edit")} />
                    <Button text={"Удалить"} onClick={deletePoint} />
                </>
            }
        </div>
    );
};

export default Info;