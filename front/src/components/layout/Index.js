import React from 'react';
import Navbar from "./navbar/Navbar";
import classes from './layout.module.scss'
import {Outlet} from "react-router-dom";
import Bottombar from "./bootombar/Bottombar";

const Index = ({type}) => {
    return (
        <div>
            <Navbar type={type}/>
            {type === "max" && <Bottombar/>}
            <div style={{backgroundColor: "#F6F7F8"}}>
                <div className={classes.mainContentContainer}>
                    <Outlet/>
                </div>
            </div>
        </div>
    );
};

export default Index;