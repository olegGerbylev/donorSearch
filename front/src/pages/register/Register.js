import React, {useContext, useState} from 'react';
import classes from "./register.module.scss";
import AuthorizeFrom from "../../components/authorizeFrom/AuthorizeFrom";
import Input from "../../components/input/Input";
import Button from "../../components/button/Button";
import {Context} from "../../index";
import axios from "axios";
import {Navigate, useNavigate} from "react-router-dom";
import BottomLink from "../../components/bottomLink/BottomLink";

const Register = () => {
    const {user, error} = useContext(Context)
    const navigate = useNavigate()

    const [formData, setFormData] = useState({})
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
        axios.post('http://localhost:8080/api/register-account',
            formData
        )
            .then(function (response) {
                setFormData("viewing")
            })
            .catch(function (e) {
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
        <div className={classes.register}>
            <AuthorizeFrom label={"Регистрация"}>
                <Input label={"Ник"} name={"displayName"} type={"text"} onChange={changeHandler} error={formError}/>
                <Input label={"Логин"} name={"login"} type={"text"} onChange={changeHandler} error={formError}/>
                <Input label={"Пароль"} name={"password"} type={"password"} onChange={changeHandler} error={formError}/>
                <Button text={"Отправить"} onClick={submit}/>
                <BottomLink path={'/login'} text={"Есть аккаунт?"}/>
            </AuthorizeFrom>
        </div>
    );
};

export default Register;