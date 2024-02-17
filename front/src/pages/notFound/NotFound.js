import React from 'react';
import classes from './notFound.module.scss'

const NotFound = () => {
    return (
        <div className={classes.notFound}>
            <div className={classes.label}>
                Страница не найдена
            </div>
            <img className={classes.imgContainer} src={require('../../media/notFound.png')} alt={"blood"}/>
        </div>
    );
};

export default NotFound;