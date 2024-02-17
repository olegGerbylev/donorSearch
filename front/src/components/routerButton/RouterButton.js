import React from 'react';
import {Link} from "react-router-dom";
import classes from'./routerButton.module.scss'

const RouterButton = ({text, path, id}) => {

    return (
        <Link to={path} className={classes.routerButton} id={id}>
            {text}
        </Link>
    );
};

export default RouterButton;