import React, {useContext, useEffect, useState} from 'react';
import Input from "../../../components/input/Input";
import {convertErrors} from "../../../helper/convertError";
import classes from'./form.module.scss'
import Button from "../../../components/button/Button";
import LoadFile from "../../../components/loadFile/LoadFile";
import axios from "axios";
import {Context} from "../../../index";
import {useNavigate} from "react-router-dom";

const Form = ({mapState,setMapState,currentClinic, setCurrentClinic,setVetClinic, fullInfo}) => {
    const {error, user} = useContext(Context)
    const navigate = useNavigate()
    const [formData, setFormData] = useState(mapState === "edit" ? fullInfo : {})
    const [formError, setFormError] = useState({})


    const changeHandler = (event)=>{
        setFormData({
            ...formData,
            [event.target.name]: event.target.value
        })
    }

    const submit =(event)=>{
        event.preventDefault()
        setFormError({})
        if (mapState === "edit"){
            axios.put('http://localhost:8080/api/mapPoint',
                {
                    mapPoint:{
                        id: fullInfo.mapPoint.id,
                        tal: currentClinic[0],
                        lng: currentClinic[1]
                    },
                    ...formData
                },
                {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem("sessionId"),
                    }
                }
            )
                .then(function (response) {
                    setVetClinic(currentClinic)
                    setCurrentClinic(null)
                    setMapState("viewing")
                })
                .catch(function (e) {
                    if(e.response.data.error === "INVALID_ACCESS_TOKEN"){
                        user.logOut()
                    }
                    error.setMessageWithTimeout(e.response.data.message)
                    let newObject = {};
                    if(e.response.data.args){
                        newObject[e.response.data.args.field] = e.response.data.message
                    }
                    if (e.response.data.errors){
                        e.response.data.errors.forEach(elem =>{
                            newObject[elem.args.field] = elem.message
                        })
                    }
                    setFormError(newObject)
                });
            return
        }
        axios.post('http://localhost:8080/api/mapPoint',
            {
                mapPoint:{
                    tal: currentClinic[0],
                    lng: currentClinic[1]
                },
                ...formData
            },
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem("sessionId"),
                }
            }
        )
            .then(function (response) {
                setVetClinic(currentClinic)
                setCurrentClinic(null)
                setMapState("viewing")
            })
            .catch(function (e) {
                if(e.response.data.error === "INVALID_ACCESS_TOKEN"){
                    user.logOut()
                }
                error.setMessageWithTimeout(e.response.data.message)
                let newObject = {};
                if(e.response.data.args){
                    newObject[e.response.data.args.field] = e.response.data.message
                }
                if (e.response.data.errors){
                    e.response.data.errors.forEach(elem =>{
                        newObject[elem.args.field] = elem.message
                    })
                }
                setFormError(newObject)
            });
    }

     return (
        <div className={classes.form}>
            <Input label={"Название здания"} name={"name"} type={"text"} onChange={changeHandler} defaultValue={formData.name} error={formError}/>
            <Input label={"Адрес"} name={"address"} type={"text"} defaultValue={formData.address} onChange={changeHandler} error={formError}/>
            <Input label={"Телефон"} name={"phone"} type={"text"} defaultValue={formData.phone} onChange={changeHandler} error={formError}/>
            <Input label={"Важно"} name={"important"} type={"text"} defaultValue={formData.important} onChange={changeHandler} error={formError}/>
            <Input label={"Информация"} name={"info"} type={"text"} defaultValue={formData.info} onChange={changeHandler} error={formError} id={classes.info}/>
            <Input label={"Время"} name={"time"} type={"text"} defaultValue={formData.time} onChange={changeHandler} error={formError}/>
            <LoadFile/>
            <Button text={"Отправить"} onClick={submit} id={classes.submitButton}/>
        </div>
    );
};

export default Form;