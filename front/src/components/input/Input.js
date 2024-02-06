import React from 'react';
import classes from './input.module.scss'

const Input = ({onChangeCallback, name, label, ...arg}) => {

    const handleChange = (event) => {
        // Вызываем функцию колбэк при изменении значения ввода
        onChangeCallback(event);
    };

    return (
        <div className={classes.input} {...arg}>
            <label className={classes.inputLabel}>{label}</label>
            <div className={classes.inputContainer}>
                <input
                    className={classes.realInput}
                    name={name}
                    type="text"
                    style={{ width: '100%', padding: '5px' }}
                    onChange={handleChange}
                    />
            </div>
        </div>
    )
};

export default Input;