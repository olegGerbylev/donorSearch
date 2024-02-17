import React, {useContext, useEffect, useState} from 'react';
import classes from "../registrationCompleted/registrationCompleted.module.scss";
import AuthorizeFrom from "../../components/authorizeFrom/AuthorizeFrom";
import BottomLink from "../../components/bottomLink/BottomLink";
import axios from "axios";
import {useLocation, useParams} from "react-router-dom";
import {Context} from "../../index";

const RegistrationConfirmAccount = () => {
    const {error} = useContext(Context)
    const [text, setText] = useState("Подождите секунду...")
    const params = useParams();

    useEffect(()=>{
        axios.get('http://localhost:8080/api/confirm-account/'+ params.id).then(function (response) {
            setText("Аккаунт подтверждён")
        })
            .catch(function (e) {
                error.setMessageWithTimeout(e.response.data.message)
                setText(e.response.data.message)
            });
    },[])

    return (
        <div>
            <div className={classes.registrationCompleted}>
                <AuthorizeFrom label={"Подтверждение аккаунта"}>
                    <div className={classes.textContainer}>
                        {text}
                    </div>
                    <BottomLink path={'/'} text={'На главную страницу'}/>
                </AuthorizeFrom>
            </div>
        </div>
    );
};

export default RegistrationConfirmAccount;