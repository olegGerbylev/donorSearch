import React from 'react';
import {Link} from "react-router-dom";
import './routerButton.scss'

const RouterButton = ({text, path, id}) => {

    return (
        <Link to={path} className={"routerButton"} id={id}>
            {text}
        </Link>
    );
};

export default RouterButton;