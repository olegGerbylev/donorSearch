import React from 'react';
import classes from './input.module.scss'

const Input = ({onChange, name, label, error, type, defaultValue, ...rest}) => {



    return (
        <div className={classes.inputContainer} {...rest}>
            <label className={classes.label}>{label}</label>
            <div className={classes.inputStyle}>
                <input
                    defaultValue={defaultValue || " "}
                    className={classes.input}
                    name={name}
                    type={type}
                    onChange={onChange}
                    />
            </div>
            <div className={classes.error}>
                {error[name] && error[name] }
            </div>
        </div>
    )
};

export default Input;