import React from 'react';
import classes from './registrationCompleted.module.scss'
import AuthorizeFrom from "../../components/authorizeFrom/AuthorizeFrom";
import BottomLink from "../../components/bottomLink/BottomLink";

const RegistrationCompleted = () => {
    return (
        <div className={classes.registrationCompleted}>
            <AuthorizeFrom label={"Аккаунт зарегистрирован"}>
                <div className={classes.textContainer}>
                    На почту выслана ссылка для подтверждения регистрации. Пройдите по ней, и после этого сможете пользоваться
                    сервисом.
                </div>
                <BottomLink path={'/'} text={'На главную страницу'}/>
            </AuthorizeFrom>
        </div>
    );
};

export default RegistrationCompleted;