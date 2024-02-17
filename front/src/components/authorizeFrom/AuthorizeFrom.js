import React from 'react';
import classes from './authorizeForm.module.scss'
const AuthorizeFrom = ({label, children}) => {
    return (
        <form className={classes.form}>
            <label className={classes.label}>{label}</label>
            {children}
        </form>
    );
};

export default AuthorizeFrom;