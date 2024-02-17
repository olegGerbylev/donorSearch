import React from 'react';
import {Link} from "react-router-dom";
import classes from './bottomLink.module.scss'

const BottomLink = ({path, text}) => {
    return (
            <Link to={path} className={classes.bottomLink} >
                {text}
            </Link>

    );
};

export default BottomLink;