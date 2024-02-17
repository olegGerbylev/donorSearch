import React, {useContext, useEffect, useState} from 'react';
import classes from './error.module.scss'
import CancelIcon from '@mui/icons-material/Cancel';
import {Context} from "../../index";
import {observer} from "mobx-react-lite";

const Error = observer(()=> {
    const {error} = useContext(Context)
    const [wight, setWight] = useState("100px")


    const closeError = () =>{
        error.message = ""
    }

    return (
        <div className={classes.error}>
            <a className={classes.labelError}>Error: </a>
            <a>{error.message}</a>
            <CancelIcon className={classes.icon} onClick={closeError}/>
        </div>
    );
});

export default Error;