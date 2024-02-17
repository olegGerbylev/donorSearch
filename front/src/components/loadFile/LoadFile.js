import React from 'react';
import classes from './loadFile.module.scss'

const LoadFile = ({onLoad}) => {
    return (
        <label className={classes.customFileUpload}>
            <input type="file" onLoad={onLoad} />
            <span>Добавить картинку</span>
        </label>
    );
};

export default LoadFile;