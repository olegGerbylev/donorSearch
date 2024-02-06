import React from 'react';
import Navbar from "./navbar/Navbar";
import './layout.scss'
import {Outlet} from "react-router-dom";
import Bottombar from "./bootombar/Bottombar";

const Index = () => {
    return (
        <div className={"layout"}>
            <Navbar/>
            <Bottombar/>
            <div className={"mainContentContainer"}>
                <Outlet/>
            </div>
        </div>
    );
};

export default Index;