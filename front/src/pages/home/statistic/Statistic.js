import React, {useCallback, useContext, useState} from 'react';
import classes from './statistic.module.scss'
import Button from "../../../components/button/Button";
import {Context} from "../../../index";


const Statistic = () => {
    const {user} = useContext(Context)
    const [count, setCount] = useState(0)

    const calculatePercentage = useCallback(()=>{
        const numerator =  Math.pow(Math.E, 2*(count/10)) - 1
        const denominator = Math.pow(Math.E, 2*(count/10)) + 1
        return numerator/denominator * 100
    },[count])

    return (
        <div className={classes.container}>
            <div className={classes.mainLabel}>
                Здесь будут отображаться
                <div className={classes.subText}>
                    донации ващих питомцев
                </div>
            </div>
            <div className={classes.rounds}>
                <div className={classes.roundBackGround}>
                    <div className={classes.round} style={{"--value": calculatePercentage()+"%"}}>
                        <div className={classes.roundCenter}/>
                    </div>
                </div>
                <div>
                    {count}
                </div>
            </div>
            {user.role === "superAdmin" && <button onClick={()=>{setCount(count+1)}}>Добавить</button> }
        </div>
    );
};

export default Statistic;