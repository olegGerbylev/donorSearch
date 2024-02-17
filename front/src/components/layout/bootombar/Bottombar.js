import React, {useContext, useEffect, useState} from 'react';
import classes from './bottombar.module.scss'
import PlaceIcon from "@mui/icons-material/Place";
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import LoginOutlinedIcon from '@mui/icons-material/LoginOutlined';
import {Link, useLocation} from "react-router-dom";
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import {Context} from "../../../index";
import {observer} from "mobx-react-lite";

const Bottombar =observer( () => {
    const {user} = useContext(Context)
    const location = useLocation();
    const currentPath = location.pathname;




    return (
        <div className={classes.bottombar}>
            <Link to={"/"} className={classes.bottomRoute} id={currentPath === "/" ? classes.current: undefined}>
                <HomeOutlinedIcon/>
                главная
            </Link>
            <Link to={"/map"} className={classes.bottomRoute} id={currentPath === "/map" ? classes.current : undefined}>
                <PlaceIcon/>
                где сдать?
            </Link>
            {user.isAuth
                ?
                <Link to={"/home"} className={classes.bottomRoute} id={currentPath === "/home" ? classes.current : undefined}>
                    <AccountCircleOutlinedIcon/>
                    аккаунт
                </Link>
                :
                <Link to={"/login"} className={classes.bottomRoute} id={(currentPath === "/login" || currentPath === "/reg") ? classes.current : undefined}>
                    <LoginOutlinedIcon/>
                    войти
                </Link>
            }
        </div>
    );
});

export default Bottombar;