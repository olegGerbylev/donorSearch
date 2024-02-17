import classes from './login.module.scss';
import AuthorizeFrom from "../../components/authorizeFrom/AuthorizeFrom";
import React, {useContext, useState} from "react";
import Input from "../../components/input/Input";
import Button from "../../components/button/Button";
import axios from "axios";
import {Context} from "../../index";
import {Navigate, useNavigate} from "react-router-dom";
import BottomLink from "../../components/bottomLink/BottomLink";

const Login = (key, value) => {
    const {user, error} = useContext(Context)
    const navigate = useNavigate()
    const [formData, setFormData] = useState({})
    const [formError, setFormError] = useState({})

    const changeHandler = (event)=>{
        setFormData({
            ...formData,
            values: {
                ...formData.values,
                [event.target.name]: event.target.value
            }
        })
    }

    const submit =(event)=>{
        event.preventDefault()
        setFormError({})
        axios.post('http://localhost:8080/api/login',
            formData.values
        )
            .then(function (response) {
                localStorage.setItem('sessionId', response.data.sessionId)
                user.setIsAuth(true)
                user.role = response.data.permission
                user.displayName = response.data.displayName
                navigate('/')
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
        <div className={classes.login}>
            <AuthorizeFrom label={"Логин"}>
                <Input label={"Логин"} name={"login"} type={"text"} onChange={changeHandler} error={formError}/>
                <Input label={"Пароль"} name={"password"} type={"password"} onChange={changeHandler} error={formError}/>
                <Button text={"Отправить"} onClick={submit}/>
                <BottomLink path={'/register'} text={"Нет аккаунта?"}/>
            </AuthorizeFrom>
        </div>
    );
};

export default Login;