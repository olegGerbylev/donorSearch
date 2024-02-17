import React from 'react';
import classes from './infoBlock.module.scss'

const InfoBlock = ({label, text, ...rest}) => {
    return (
        <div className={classes.container} {...rest}>
            <label className={classes.label}>{label}</label>
            <div className={classes.textContainer}>
                { text}
            </div>
        </div>
    );
};

export default InfoBlock;