import React from 'react';
import classes from './input.module.scss'

const Button = ({text, onClick, ...rest}) => {
    return (
        <div className={classes.button} onClick={onClick} {...rest}>
            {text}
        </div>
    );
};

export default Button;