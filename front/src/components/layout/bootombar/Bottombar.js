import React, {useContext, useEffect, useState} from 'react';
import './bottombar.scss'
import PlaceIcon from "@mui/icons-material/Place";
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import LoginOutlinedIcon from '@mui/icons-material/LoginOutlined';
import {Link, useLocation} from "react-router-dom";
import {AuthContext} from "../../../context/authContext";
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';

const Bottombar = () => {
    const {currentUser} = useContext(AuthContext);
    const location = useLocation();
    const currentPath = location.pathname;

    const isLogin = () => {
        return false
    }

    return (
        <div className={"bottombar"}>
            <Link to={"/"} className={"bottomRoute"} id={currentPath === "/" ? "current": undefined}>
                <HomeOutlinedIcon/>
                главная
            </Link>
            <Link to={"/map"} className={"bottomRoute"} id={currentPath === "/map" ? "current" : undefined}>
                <PlaceIcon/>
                где сдать?
            </Link>
            {isLogin()
                ?
                <Link to={"/account"} className={"bottomRoute"} id={currentPath === "/account" ? "current" : undefined}>
                    <AccountCircleOutlinedIcon/>
                    аккаунт
                </Link>
                :
                <Link to={"/login"} className={"bottomRoute"} id={(currentPath === "/login" || currentPath === "/reg") ? "current" : undefined}>
                    <LoginOutlinedIcon/>
                    войти
                </Link>
            }
        </div>
    );
};

export default Bottombar;